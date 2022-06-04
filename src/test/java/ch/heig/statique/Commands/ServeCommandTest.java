package ch.heig.statique.Commands;

import ch.heig.statique.Commands.Build;
import ch.heig.statique.Commands.Init;
import ch.heig.statique.Commands.Serve;
import ch.heig.statique.Utils.Utils;
import org.junit.jupiter.api.*;
import picocli.CommandLine;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServeCommandTest {

    @Test
    @Order(1)
    public void testServeWithoutBuildCommand() throws Exception {
        String outText = tapSystemErrAndOut(() -> {
            new CommandLine(new Serve()).execute(System.getProperty("user.dir") + Utils.SEPARATOR + "abc");
        });
        assertEquals("No build folder found. Please compile before", outText.trim());
    }

    @Test
    @Order(2)
    public void testServeWithRelativePath() throws Exception {
        String outText = tapSystemErrAndOut(() -> {
            new CommandLine(new Serve()).execute("abc");
        });
        assertEquals("Please use an absolute path", outText.trim());
    }

    @Test
    @Order(3)
    public void testServe() throws Exception {
        new CommandLine(new Init()).execute(System.getProperty("user.dir") + Utils.SEPARATOR + "abc");
        new CommandLine(new Build()).execute(System.getProperty("user.dir") + Utils.SEPARATOR + "abc");
        String outText = tapSystemErrAndOut(() -> {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    new CommandLine(new Serve()).execute(System.getProperty("user.dir") + Utils.SEPARATOR + "abc");
                }
            });
            thread.start();
            Thread.sleep(1000);
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8085")).build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertTrue(response.statusCode() == 302 || response.statusCode() == 200);

        });

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
