package ch.heig.statique;

import ch.heig.statique.Commands.*;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Callable;
import picocli.CommandLine;

/** Classe principale du site Point d'entrée des différentes commandes de génération d'un site */
@CommandLine.Command(
        name = "statique",
        description = "A static site generator",
        subcommands = {
            Init.class,
            Clean.class,
            Build.class,
            Serve.class,
        },
        versionProvider = VersionProvider.class)
public class Statique implements Callable<Integer> {

    @CommandLine.Option(
            names = {"-V", "--version"},
            versionHelp = true,
            description = "Print version information and exit")
    boolean versionRequested;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Statique()).execute(args);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);
        return 0;
    }
}

/**
 * Implémentation de la commande "version" qui retourne la version du projet La version retournée
 * correspond est celle dans le fichier pom.xml
 */
class VersionProvider implements CommandLine.IVersionProvider {
    public String[] getVersion() throws IOException {

        final Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
        return new String[] {
            properties.getProperty("name") + " v" + properties.getProperty("version")
        };
    }
}
