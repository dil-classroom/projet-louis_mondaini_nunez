package ch.heig.statique.Commands;

import ch.heig.statique.Utils.Utils;
import java.io.File;
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

    static String CRLF = "\r\n";

    @CommandLine.Parameters(
            index = "0",
            description =
                    "The absolute path where the " + "root directory of the site will be initiated")
    private File file;

    @Override
    public Integer call() throws Exception {
        try {
            if (file.isAbsolute()) {
                file = new File(file.toString() + Utils.SEPARATOR + "site");
            } else {
                throw new RuntimeException("Please use an aboslute path");
            }

            if (!file.mkdirs()) {
                throw new RuntimeException("Could not create directory: " + file.toString());
            }

            // Affichage du chemin ou a été initialisé le site
            System.out.println("Created directory: " + file.getAbsolutePath());

            File config = new File(file.toString() + Utils.SEPARATOR + "config.yaml");
            File index = new File(file.toString() + Utils.SEPARATOR + "index.md");

            // Création du fichier de configuration
            if (config.createNewFile()) {
                System.out.println("Config file created");
                String content =
                        "titre: titre du site"
                                + CRLF
                                + "description: lorem ipsum"
                                + CRLF
                                + "domain: example.com"
                                + CRLF;
                FileUtils.writeStringToFile(config, content, "UTF-8");
            } else {
                System.out.println("Config file could not be created");
            }

            // Création du fichier index
            if (index.createNewFile()) {
                System.out.println("Index file created");
                String content =
                        "---"
                                + CRLF
                                + "titre: Mon premier article"
                                + CRLF
                                + "auteur: John Doe"
                                + CRLF
                                + "date: "
                                + java.time.LocalDate.now()
                                + CRLF
                                + "---"
                                + CRLF
                                + "# Mon premier article"
                                + CRLF
                                + "## Mon sous-titre"
                                + CRLF
                                + "Le contenu de mon article."
                                + CRLF;
                FileUtils.writeStringToFile(index, content, "UTF-8");
            } else {
                System.out.println("Index file could not be created");
            }

            File templateFolder = new File(file.toString() + Utils.SEPARATOR + "template");
            File menuTemplate = new File(templateFolder + Utils.SEPARATOR + "menu.html");
            File layoutTemplate = new File(templateFolder + Utils.SEPARATOR + "layout.html");

            if (templateFolder.mkdirs()) {
                System.out.println("Template folder created");
            } else {
                System.out.println("Template folder could not be created");
            }

            if (menuTemplate.createNewFile()) {
                System.out.println("Menu template file created");
                String content = "<ul></ul>";
                FileUtils.writeStringToFile(menuTemplate, content, "UTF-8");
            } else {
                System.out.println("Menu template file could not be created");
            }

            if (layoutTemplate.createNewFile()) {
                System.out.println("Layout template file created");
                String content =
                        "<!DOCTYPE html>" + CRLF +
                        "<html>" + CRLF +
                        "<head>" + CRLF +
                            "<meta charset=\"UTF-8\">" + CRLF +
                            "<title>{{ site.titre }} | {{ page.titre }}</title>" + CRLF +
                        "</head>" + CRLF +
                        "<body>" + CRLF +
                            "{% include menu.html }" + CRLF +
                            "{{ content }}" + CRLF +
                        "</body>" + CRLF +
                        "</html>" + CRLF;
                FileUtils.writeStringToFile(layoutTemplate, content, "UTF-8");
            } else {
                System.out.println("Layout template file could not be created");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        }
        return 0;
    }
}
