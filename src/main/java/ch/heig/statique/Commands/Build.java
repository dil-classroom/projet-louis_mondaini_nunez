package ch.heig.statique.Commands;

import ch.heig.statique.Utils.Utils;
import org.apache.commons.io.FileUtils;
import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "build",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Build a static site"
)
public class Build implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", description = "The directory containing the site")
    private File dir;

    @Override
    public Integer call() throws Exception {
        dir = new File(System.getProperty("user.dir") + "\\site" + dir.toString());
        Utils.copyDirectory(dir.getPath(), System.getProperty("user.dir") + "\\build");

        var it = FileUtils.iterateFiles(dir, null, true);



        return 0;
    }
}
