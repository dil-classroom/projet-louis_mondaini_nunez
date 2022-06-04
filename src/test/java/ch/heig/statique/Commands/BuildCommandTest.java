package ch.heig.statique.Commands;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOut;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.heig.statique.Utils.Utils;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import org.junit.jupiter.api.*;
import picocli.CommandLine;

public class BuildCommandTest {

    @Test
    public void testBuildCommandWithMissingFolder() throws Exception {
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
    public void testBuildCommandWithRelativePath() throws Exception {
        String outText =
                tapSystemErrAndOut(
                        () -> {
                            new CommandLine(new Build()).execute("abc");
                        });
        assertEquals("Please use an absolute path", outText.trim());
    }

    @Test
    public void testBuildCommand() throws Exception {
        new CommandLine(new Init())
                .execute(System.getProperty("user.dir") + Utils.SEPARATOR + "abc");
        new CommandLine(new Build())
                .execute(System.getProperty("user.dir") + Utils.SEPARATOR + "abc");
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
