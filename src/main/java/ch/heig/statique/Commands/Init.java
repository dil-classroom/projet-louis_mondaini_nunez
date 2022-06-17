package ch.heig.statique.Commands;

import ch.heig.statique.Utils.Utils;
import java.io.File;
import java.util.concurrent.Callable;
import org.apache.commons.io.FileUtils;
import picocli.CommandLine;

/** Command to initialize a new site in the given directory. */
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

    /**
     * Callable method to initialize the site.
     *
     * @return 0 if the initialization was successful, 1 otherwise
     */
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
                    new File(getClass().getClassLoader().getResource("archtemplate").getFile()),
                    file);
            System.out.println("Init successful");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
