package ch.heig.statique.Commands;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.heig.statique.Utils.Utils;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import org.junit.jupiter.api.*;
import picocli.CommandLine;

class BuildCommandTest {

    @Test
    void testBuildCommandWithMissingFolder() throws Exception {
        String outText =
                tapSystemErrAndOut(
                        () -> {
                            new CommandLine(new Build())
                                    .execute(
                                            System.getProperty("user.dir")
                                                    + Utils.SEPARATOR
                                                    + "abc");
                        });
        assertEquals(
                "File system element for parameter 'directory' does not exist: '"
                        + System.getProperty("user.dir")
                        + Utils.SEPARATOR
                        + "abc"
                        + Utils.SEPARATOR
                        + "site'",
                outText.trim());
    }

    @Test
    void testBuildCommandWithRelativePath() throws Exception {
        String outText =
                tapSystemErrAndOut(
                        () -> {
                            new CommandLine(new Build()).execute("abc");
                        });
        assertEquals("Please use an absolute path", outText.trim());
    }

    @Test
    void testBuildCommand() throws Exception {
        String outText =
                tapSystemErrAndOut(
                        () -> {
                            new CommandLine(new Init())
                                    .execute(
                                            System.getProperty("user.dir")
                                                    + Utils.SEPARATOR
                                                    + "abc");
                        });
        assertTrue(outText.trim().contains("Init successful"));

        outText =
                tapSystemErrAndOut(
                        () -> {
                            new CommandLine(new Build())
                                    .execute(
                                            System.getProperty("user.dir")
                                                    + Utils.SEPARATOR
                                                    + "abc");
                        });
        assertTrue(outText.contains("Build sucessful"));
    }

    /**
     * Supprimer le dossier site/ cr???? par la commande init
     *
     * @throws Exception Throws exception if a file or folder cannot be opened
     */
    @AfterAll
    public static void cleanUp() throws Exception {
        Path path = Paths.get(System.getProperty("user.dir") + Utils.SEPARATOR + "abc");
        Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    }
}
