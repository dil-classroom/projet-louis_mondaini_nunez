package ch.heig.statique.Commands;

import ch.heig.statique.Utils.Utils;
import java.io.File;
import java.util.concurrent.Callable;
import picocli.CommandLine;

/** Permet d'effacer le dossier build/ d'un site statique */
@CommandLine.Command(
        name = "clean",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Clean a static site")
public class Clean implements Callable<Integer> {

    @CommandLine.Parameters(
            index = "0",
            description = "The directory where the static site was initiated")
    private File file;

    /**
     * Deletes file or directory
     *
     * @param file the file to delete. If the file is a directory, delete its content recursively
     */
    void deleteDirectory(File file) {
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    deleteDirectory(entry);
                }
            }
        }
        if (!file.exists())
            System.out.println("File " + file.getAbsolutePath() + " does not exist");
        else if (!file.delete())
            System.out.println("File " + file.getAbsolutePath() + " could not be deleted");
    }

    @Override
    public Integer call() {
        if (file.isAbsolute()) {
            file = new File(file.toString() + Utils.SEPARATOR + "site" + Utils.SEPARATOR + "build");
        } else {
            throw new RuntimeException("Please use an aboslute path");
        }
        deleteDirectory(file);
        return 0;
    }
}
