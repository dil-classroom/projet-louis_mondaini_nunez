package ch.heig.statique.Commands;

import picocli.CommandLine;

import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "init",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Init a static site"
)
public class Init implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "The directory where the site will be initiated")
    private File file;

    @Override
    public Integer call() throws Exception {
        try {


            if (file.isAbsolute()) // Si l'utilisateur donne un chemin absolu, lance une exception puisque nous
                // souhaitons créer le site statique à un endroit spécifique
                throw new RuntimeException("You must provide a relative path for the command");
            file = new File(Paths.get("").toAbsolutePath() + "\\site" + file.toString());
            if (!file.mkdirs()) {
                throw new RuntimeException("Could not create directory: " + file.toString());
            }

            File config = new File(file.toString() + "\\config.yaml");
            File index = new File(file.toString() + "\\index.md");

            if (config.createNewFile()) {
                System.out.println("Config file created");
            } else {
                System.out.println("Config file could not be created");
            }

            if (index.createNewFile()) {
                System.out.println("Index file created");
            } else {
                System.out.println("Index file could not be created");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
        return 0;
    }
}
