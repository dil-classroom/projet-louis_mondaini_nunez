package ch.heig.statique.Commands;

import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "clean",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Clean a static site"
)
public class Clean implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "The file whose checksum to calculate.")
    String file;

    void deleteDirectoryRecursionJava6(File file) {
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    deleteDirectoryRecursionJava6(entry);
                }
            }
        }
        file.delete();
    }

    @Override
    public Integer call() throws Exception {
        File f = new File(System.getProperty("user.dir")+ "\\site" + file + "\\build");
        deleteDirectoryRecursionJava6(f);
        return 0;
    }
}
