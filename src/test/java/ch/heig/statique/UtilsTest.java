package ch.heig.statique;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.heig.statique.Utils.Utils;
import java.nio.file.FileSystems;
import org.junit.jupiter.api.Test;

class UtilsTest {

    @Test
    void testSeparator() {
        assertEquals(Utils.SEPARATOR, FileSystems.getDefault().getSeparator());
    }
}
