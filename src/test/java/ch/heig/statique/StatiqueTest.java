package ch.heig.statique;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StatiqueTest {

    @Test
    void testExitCode() {
        int exitCode = new CommandLine(new Statique()).execute();
        assertEquals(exitCode, 0);
    }

    @Test
    void testStatiqueCommand() throws Exception {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            System.setOut(new PrintStream(output));
            new CommandLine(new Statique()).execute();
            assertTrue((output.toString().contains("A static site generator")));
        }
    }
}
