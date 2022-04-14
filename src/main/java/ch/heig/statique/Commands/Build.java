package ch.heig.statique.Commands;

import ch.heig.statique.Parser.PageParser;
import ch.heig.statique.Site.Page;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import picocli.CommandLine;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "build",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Build a static site"
)
public class Build implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", description = "The directory containing the site")
    private File file;

    @Override
    public Integer call() throws IOException {
        if (file.isAbsolute())  {
            file = new File(file.toString() + "\\site");
        } else {
            file = new File(Paths.get("").toAbsolutePath() + "\\" + file.toString() + "\\site");
        }

        File config = new File(file + "\\config.yaml");
        if (!FileUtils.directoryContains(file, config)) {
            throw new RuntimeException("Could not find any config.yaml file");
        }

        File build = new File(file + "\\build");
        FileUtils.deleteDirectory(build);

        FileUtils.copyDirectory(file, build, pathname -> {
            if (pathname.getName().equals("build") && pathname.isDirectory()
                || pathname.getName().equals("config.yaml")) {
                return false;
            }

            return true;
        });

        var it = FileUtils.iterateFiles(build, new String[]{"md"}, true);

        while (it.hasNext()) {
            File md = it.next();
            Page page = PageParser.parseFromMarkdownFile(md);
            FileUtils.writeStringToFile(md, page.getHtml(), StandardCharsets.UTF_8, false);
            if (!md.renameTo(new File(FilenameUtils.removeExtension(md.getAbsolutePath()) + ".html"))
            ) {
                throw new RuntimeException("Cannot rename the md files to html files");
            }
        }

        return 0;
    }
}
