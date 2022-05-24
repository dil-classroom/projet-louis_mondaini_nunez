package ch.heig.statique.Build;

import ch.heig.statique.Parser.PageParser;
import ch.heig.statique.Site.Page;
import ch.heig.statique.Utils.Utils;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.yaml.snakeyaml.Yaml;

public class Builder {
    public static File buildSite(File siteFolder) throws IOException {
        if (siteFolder.isAbsolute()) {
            siteFolder = new File(siteFolder.toString() + Utils.SEPARATOR + "site");
        } else {
            throw new RuntimeException("Please use an absolute path");
        }

        File config = new File(siteFolder + Utils.SEPARATOR + "config.yaml");
        if (!FileUtils.directoryContains(siteFolder, config)) {
            throw new RuntimeException("Could not find any config.yaml file");
        }

        File buildFolder = new File(siteFolder + Utils.SEPARATOR + "build");
        FileUtils.deleteDirectory(buildFolder);

        FileUtils.copyDirectory(
                siteFolder,
                buildFolder,
                pathname -> {
                    if (pathname.getName().equals("build") && pathname.isDirectory()
                            || pathname.getName().equals("config.yaml")
                            || pathname.getName().equals("template")) {
                        return false;
                    }

                    return true;
                });

        var it = FileUtils.iterateFiles(buildFolder, new String[] {"md"}, true);

        TemplateLoader loader =
                new FileTemplateLoader(new File(siteFolder + Utils.SEPARATOR + "template"));
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile("layout");

        Yaml yaml = new Yaml();

        while (it.hasNext()) {
            File md = it.next();
            Page page = PageParser.parseFile(md);

            Map<String, Object> siteConfig =
                    yaml.load(FileUtils.readFileToString(config, StandardCharsets.UTF_8));

            Context context =
                    Context.newBuilder(new Object())
                            .combine("site", siteConfig)
                            .combine("page", page.getMetadata())
                            .combine("content", page.getContent())
                            .resolver(MapValueResolver.INSTANCE, JavaBeanValueResolver.INSTANCE)
                            .build();

            FileUtils.writeStringToFile(
                    md,
                    PageParser.convertMdToHtml(template.apply(context)),
                    StandardCharsets.UTF_8,
                    false);

            if (!md.renameTo(
                    new File(FilenameUtils.removeExtension(md.getAbsolutePath()) + ".html"))) {
                throw new RuntimeException("Cannot rename the md files to html files");
            }
        }

        return buildFolder;
    }
}
