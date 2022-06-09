package ch.heig.statique.Commands;

import ch.heig.statique.Utils.Utils;
import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import org.apache.commons.io.FileUtils;
import picocli.CommandLine;

/**
 * Permet d'initialiser un site statique Génère un fichier config.yaml contenant les configs du site
 * Génère un fichier index.md page racine du projet
 */
@CommandLine.Command(
        name = "init",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Init a static site")
public class Init implements Callable<Integer> {

    @CommandLine.Parameters(
            index = "0",
            description =
                    "The absolute path where the " + "root directory of the site will be initiated")
    private File file;

    @Override
    public Integer call() {
        try {
            if (!file.isAbsolute()) {
                throw new RuntimeException("Please use an absolute path");
            } else {
                file = new File(file + Utils.SEPARATOR + "site");
                if (!file.mkdirs()) {
                    throw new RuntimeException("Failed to create a new folder");
                }
            }

            FileUtils.copyDirectory(
                    new File(
                            Path.of("").toAbsolutePath()
                                    + Utils.SEPARATOR
                                    + "src"
                                    + Utils.SEPARATOR
                                    + "archtemplate"
                                    + Utils.SEPARATOR
                                    + "site"),
                    file);
            System.out.println("Init successful");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
