package ch.heig.statique;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.heig.statique.Commands.Clean;
import ch.heig.statique.Commands.Init;
import ch.heig.statique.Utils.Utils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import org.junit.jupiter.api.*;
import picocli.CommandLine;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommandsTest {

    /** Vérifie que la commande execute puisse être exécutée */
    @Test
    @Order(1)
    void testInitCommand() throws Exception {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(output));
            new CommandLine(new Init())
                    .execute(System.getProperty("user.dir") + Utils.SEPARATOR + "abc");
            assertTrue((output.toString().contains("Config file created")));
        }
    }

    /** Vérifie que la commande execute crée la structure de dossiers de base */
    @Test
    @Order(2)
    void testInitFolderIsCreated() {
        Path path =
                Paths.get(
                        System.getProperty("user.dir")
                                + Utils.SEPARATOR
                                + "abc"
                                + Utils.SEPARATOR
                                + "site");
        assertTrue(Files.exists(path));
    }

    /** Vérifie que la commande execute crée les fichiers par défaut */
    @Test
    @Order(3)
    void testInitFolderConfigFiles() {
        Path path =
                Paths.get(
                        System.getProperty("user.dir")
                                + Utils.SEPARATOR
                                + "abc"
                                + Utils.SEPARATOR
                                + "site"
                                + Utils.SEPARATOR
                                + "config.yaml");
        assertTrue(Files.exists(path));
        path =
                Paths.get(
                        System.getProperty("user.dir")
                                + Utils.SEPARATOR
                                + "abc"
                                + Utils.SEPARATOR
                                + "site"
                                + Utils.SEPARATOR
                                + "index.md");
        assertTrue(Files.exists(path));
    }

    /** Vérifie que la commande clean efface le dossier build */
    @Test
    @Order(4)
    void testCleanCommand() throws Exception {
        // create build folder
        File file =
                new File(
                        System.getProperty("user.dir")
                                + Utils.SEPARATOR
                                + "abc"
                                + Utils.SEPARATOR
                                + "site"
                                + Utils.SEPARATOR
                                + "build");
        file.mkdirs();

        // execute clean command
        File file2 = new File(System.getProperty("user.dir") + Utils.SEPARATOR + "abc");
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(output));
            new CommandLine(new Clean()).execute(file2.toString());
        }

        // verify build folder doesn't exist
        Path path = Paths.get(file.toString());
        assertFalse(Files.exists(path));
    }

    /** Supprimer le dossier site/ créé par la commande init */
    @AfterAll
    public static void cleanUp() throws IOException {
        Path path = Paths.get(System.getProperty("user.dir") + Utils.SEPARATOR + "abc");
        Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    }
}
