/* (C)2022 */
package ch.heig.statique;

import static org.junit.jupiter.api.Assertions.*;

import ch.heig.statique.Parser.PageParser;
import ch.heig.statique.Site.Page;
import ch.heig.statique.Utils.Utils;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

/** Unit test for simple App. */
public class ParserTest {
    /** Rigorous Test :-) */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void parseRegularMarkdownYaml() {
        final String markdown =
                "---\n"
                        + "titre: Mon premier article\n"
                        + "auteur: Bertil Chapuis\n"
                        + "date: 2021-03-10\n"
                        + "---\n"
                        + "# Mon premier article\n"
                        + "## Mon sous-titre\n"
                        + "Le contenu de mon article.";
        Page page = PageParser.parseFromString(markdown);
        assertEquals(
                "<h1>Mon premier article</h1>\n"
                        + "<h2>Mon sous-titre</h2>\n"
                        + "<p>Le contenu de mon article.</p>\n",
                page.getHtml());
        assertEquals(page.getMetadata().get("titre").get(0), "Mon premier article");
        assertEquals(page.getMetadata().get("auteur").get(0), "Bertil Chapuis");
        assertEquals(page.getMetadata().get("date").get(0), "2021-03-10");
    }

    @Test
    public void parseFromFileMarkdownYaml() {
        Path path = Paths.get("src", "test", "resources").toAbsolutePath();
        final File file = new File(path + Utils.SEPARATOR + "test.md");
        Page page = PageParser.parseFromMarkdownFile(file);
        assertEquals(
                "<h1>Mon premier article</h1>\n"
                        + "<h2>Mon sous-titre</h2>\n"
                        + "<p>Le contenu de mon article.</p>\n",
                page.getHtml());
        assertEquals(page.getMetadata().get("titre").get(0), "Mon premier article");
        assertEquals(page.getMetadata().get("auteur").get(0), "Bertil Chapuis");
        assertEquals(page.getMetadata().get("date").get(0), "2021-03-10");
    }
}
