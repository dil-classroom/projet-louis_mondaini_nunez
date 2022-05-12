package ch.heig.statique;

import static org.junit.jupiter.api.Assertions.*;

import ch.heig.statique.Parser.PageParser;
import ch.heig.statique.Site.Page;
import org.junit.jupiter.api.Test;

/** Unit test for simple App. */
public class ParserTest {
    @Test
    public void parseFromString() {
        final String markdown =
                        "titre: Mon premier article\n"
                        + "auteur: Bertil Chapuis\n"
                        + "date: 2021-03-10\n"
                        + "---\n"
                        + "# Mon premier article\n"
                        + "## Mon sous-titre\n"
                        + "Le contenu de mon article.";
        Page page = PageParser.parseString(markdown);
        assertEquals(
                "<h1>Mon premier article</h1>\n"
                        + "<h2>Mon sous-titre</h2>\n"
                        + "<p>Le contenu de mon article.</p>\n",
                PageParser.convertMdToHtml(page.getContent()));
        assertEquals(page.getMetadata().get("titre"), "Mon premier article");
        assertEquals(page.getMetadata().get("auteur"), "Bertil Chapuis");
        assertEquals(page.getMetadata().get("date"), "2021-03-10");
    }
}
