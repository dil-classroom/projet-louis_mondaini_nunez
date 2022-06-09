package ch.heig.statique.Commands;

import ch.heig.statique.Build.Builder;
import ch.heig.statique.Utils.DirectoryWatchingUtility;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import picocli.CommandLine;

/**
 * Command to build the site from existing source files.
 */
@CommandLine.Command(
        name = "build",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Build a static site")
public class Build implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", description = "The directory containing the site")
    private File siteFolder;

    @CommandLine.Option(
            names = {"--watch"},
            description = "Watch site directory for changes and hot rebuild")
    private boolean watch = false;

    /**
     * Callable method to build the site.
     * @return 0 if the build was successful, 1 otherwise
     * @throws IOException if an error occurs while reading or writing files
     */
    @Override
    public Integer call() throws IOException {
        File buildFolder;
        try {
            buildFolder = Builder.buildSite(siteFolder);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 1;
        }
        System.out.println("Build sucessful");

        if (watch) {
            DirectoryWatchingUtility directoryWatchingUtility =
                    new DirectoryWatchingUtility(siteFolder, buildFolder);
            directoryWatchingUtility.watch();
        }

        return 0;
    }
}
