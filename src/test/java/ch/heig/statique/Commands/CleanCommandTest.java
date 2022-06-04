package ch.heig.statique.Commands;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import ch.heig.statique.Utils.Utils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import org.junit.jupiter.api.*;
import picocli.CommandLine;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CleanCommandTest {

    /** Vérifie que la commande clean efface le dossier build */
    @Test
    @Order(2)
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

    @Test
    @Order(1)
    void testCleanCommandWithRelativePath() throws Exception {
        String outText =
                tapSystemErrAndOut(
                        () -> {
                            new CommandLine(new Clean()).execute("abc");
                        });
        assertEquals("Please use an absolute path", outText.trim());
    }

    @Test
    @Order(3)
    void testDeleteDirectory() throws Exception {
        String outText =
                tapSystemErrAndOut(
                        () -> {
                            new CommandLine(new Clean())
                                    .execute(
                                            System.getProperty("user.dir")
                                                    + Utils.SEPARATOR
                                                    + "abc");
                        });
        assertEquals(
                "File "
                        + System.getProperty("user.dir")
                        + Utils.SEPARATOR
                        + "abc"
                        + Utils.SEPARATOR
                        + "site"
                        + Utils.SEPARATOR
                        + "build does not exist",
                outText.trim());
    }

    /**
     * Supprimer le dossier site/ créé par la commande init
     *
     * @throws Exception Throws exception if a file or folder cannot be opened
     */
    @AfterAll
    public static void cleanUp() throws Exception {
        Path path = Paths.get(System.getProperty("user.dir") + Utils.SEPARATOR + "abc");
        Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    }
}
