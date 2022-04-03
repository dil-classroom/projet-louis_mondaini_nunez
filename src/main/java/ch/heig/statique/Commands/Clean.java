package ch.heig.statique.Commands;

import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * Permet d'effacer le dossier build/ d'un site statique
 */
@CommandLine.Command(
        name = "clean",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Clean a static site"
)
public class Clean implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "The directory where the static site was initiated")
    String file;

    void deleteDirectory(File file) {
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    deleteDirectory(entry);
                }
            }
        }
        file.delete();
    }

    @Override
    public Integer call() throws Exception {
        File f = new File(System.getProperty("user.dir")+ "\\site" + file + "\\build");
        deleteDirectory(f);
        return 0;
    }
}
