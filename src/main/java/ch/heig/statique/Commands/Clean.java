package ch.heig.statique.Commands;

import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "clean",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Clean a static site"
)
public class Clean implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "The directory where the static site was initiated")
    String file;

    /**
     * Deletes file or directory
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
    public Integer call() throws Exception {
        File f = new File(System.getProperty("user.dir")+ "\\site" + file + "\\build");
        deleteDirectory(f);
        return 0;
    }
}
