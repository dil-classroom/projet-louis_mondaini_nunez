package ch.heig.statique.Commands;

import ch.heig.statique.Build.Builder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import ch.heig.statique.Utils.DirectoryWatchingUtility;

/** Permet de compiler un projet en site statique Le site généré se trouve dans le dossier build/ */
@CommandLine.Command(
        name = "build",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Build a static site")
public class Build implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", description = "The directory containing the site")
    private File siteFolder;

    @CommandLine.Option(names = { "--watch" }, description = "Watch site directory for changes and hot rebuild")
    private boolean watch = false;

    @Override
    public Integer call() throws IOException {
        File buildFolder = Builder.buildSite(siteFolder);

        if (watch) {
            DirectoryWatchingUtility directoryWatchingUtility = new DirectoryWatchingUtility(siteFolder, buildFolder);
            directoryWatchingUtility.watch();
        }

        return 0;
    }
}
