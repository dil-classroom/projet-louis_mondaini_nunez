package ch.heig.statique.Commands;

import java.util.concurrent.Callable;
import picocli.CommandLine;

/** Permet de compiler un projet en site statique Le site généré se trouve dans le dossier build/ */
@CommandLine.Command(
        name = "build",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Build a static site")
public class Build implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
