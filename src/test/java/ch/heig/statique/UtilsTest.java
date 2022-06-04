package ch.heig.statique;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.heig.statique.Commands.Init;
import ch.heig.statique.Utils.Utils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import org.junit.jupiter.api.*;
import picocli.CommandLine;

class UtilsTest {

    @Test
    void testGetExtensionFromString() {
        assertEquals("txt", Utils.getExtensionFromString("file.txt"));
        assertEquals("yaml", Utils.getExtensionFromString("/mon/chemin/file.yaml"));
        assertEquals("yaml", Utils.getExtensionFromString("C:\\file.yaml"));
    }

    @Test
    void testCopyDirectory() throws IOException {
        new CommandLine(new Init())
                .execute(System.getProperty("user.dir") + Utils.SEPARATOR + "abc");
        File file = new File(System.getProperty("user.dir") + Utils.SEPARATOR + "abc2");
        Utils.copyDirectory(
                System.getProperty("user.dir") + Utils.SEPARATOR + "abc", file.toString());
        assertTrue(Files.exists(file.toPath()));
        String subfolder =
                file
                        + Utils.SEPARATOR
                        + "site"
                        + Utils.SEPARATOR
                        + "template"
                        + Utils.SEPARATOR
                        + "menu.html";
        assertTrue(Files.exists(new File(subfolder).toPath()));
    }

    /**
     * Supprimer le dossier site/ créé par la commande init
     *
     * @throws Exception Throws exception if a file or folder cannot be opened
     */
    @AfterAll
    public static void cleanUp() throws Exception {
        Path path = Paths.get(System.getProperty("user.dir") + Utils.SEPARATOR + "abc");
        Path path2 = Paths.get(System.getProperty("user.dir") + Utils.SEPARATOR + "abc2");
        Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        Files.walk(path2).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    }
}
