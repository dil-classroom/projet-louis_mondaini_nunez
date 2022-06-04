package ch.heig.statique;

import ch.heig.statique.Commands.Init;
import ch.heig.statique.Utils.Utils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.*;

public class InitCommandTest {

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

    @Order(2)
    @Test
    void testInitCommandWithRelativePath() throws Exception {
        String outText = tapSystemErrAndOut(() -> {
            new CommandLine(new Init()).execute("abc");
        });
        assertEquals("Please use an aboslute path", outText.trim());
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
