package ch.heig.statique.Commands;

import picocli.CommandLine;
import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import org.apache.commons.io.FileUtils;

@CommandLine.Command(
        name = "init",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Init a static site"
)
public class Init implements Callable<Integer> {

    static String CRLF = "\r\n";

    @CommandLine.Parameters(index = "0", description = "The directory where the site will be initiated")
    private File file;

    @Override
    public Integer call() throws Exception {
        try {
            if (!file.isAbsolute()) // Si l'utilisateur ne donne pas un chemin absolu, la commande crée le site dans un
                                    // nouveau fichier site à partir de l'endroit ou est executée la commande
                // récupère le path depuis lequel la commande est executée
                file = new File(Paths.get("").toAbsolutePath() + "\\site" + file.toString());

            if (!file.mkdirs()) {
                throw new RuntimeException("Could not create directory: " + file.toString());
            }

            // Affichage du chemin ou a été initialisé le site
            System.out.println("Created directory: " + file.getAbsolutePath());

            File config = new File(file.toString() + "\\config.yaml");
            File index = new File(file.toString() + "\\index.md");

            // Création du fichier de configuration
            if (config.createNewFile()) {
                System.out.println("Config file created");
                String content = "titre: titre du site" + CRLF
                               + "description: lorem ipsum" + CRLF
                               + "domain: example.com" + CRLF;
                FileUtils.writeStringToFile(config, content, "UTF-8");
            } else {
                System.out.println("Config file could not be created");
            }

            // Création du fichier index
            if (index.createNewFile()) {
                System.out.println("Index file created");
                String content = "---" + CRLF
                               + "titre: Mon premier article" + CRLF
                               + "auteur: John Doe" + CRLF
                               + "date: " + java.time.LocalDate.now() + CRLF
                               + "---" + CRLF
                               + "# Mon premier article" + CRLF
                               + "## Mon sous-titre" + CRLF
                               + "Le contenu de mon article." + CRLF;
                FileUtils.writeStringToFile(index, content, "UTF-8");
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
