package ch.heig.statique.Commands;

import ch.heig.statique.Statique;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatiqueTest {
    @Test
    public void testVersion() throws Exception {
        String outText = tapSystemErrAndOut(() -> {
            new CommandLine(new Statique()).execute("--version");
        });
        assertEquals("Statique v0.1", outText.trim());
    }

    @Test
    public void testStatique() throws Exception {
        String outText = tapSystemErrAndOut(() -> {
            new CommandLine(new Statique()).execute("");
        });
        assertTrue(outText.contains("Usage: statique [-V] [COMMAND]"));
    }
}
