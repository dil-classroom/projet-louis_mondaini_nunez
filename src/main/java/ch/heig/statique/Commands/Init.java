package ch.heig.statique.Commands;

import picocli.CommandLine;
import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import org.apache.commons.io.FileUtils;

/**
 * Permet d'initialiser un site statique
 * Génère un fichier config.yaml contenant les configs du site
 * Génère un fichier index.md page racine du projet
 */
@CommandLine.Command(
        name = "init",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Init a static site"
)
public class Init implements Callable<Integer> {

    static String CRLF = "\r\n";

    @CommandLine.Parameters(index = "0", description = "The absolute path where the " +
            "root directory of the site will be initiated")
    private File file;

    @Override
    public Integer call() throws Exception {
        try {
            // Si l'utilisateur ne donne pas un chemin absolu, la commande crée le site dans un
            // nouveau fichier site à partir de l'endroit ou est executée la commande
            // sinon la commande crée le dossier site à partir du chemin absolu donné
            if (file.isAbsolute())  {
                file = new File(file.toString() + "\\site");
            } else {
                file = new File(Paths.get("").toAbsolutePath() + "\\" + file.toString() + "\\site");
            }


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
