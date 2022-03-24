package ch.heig.dil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ch.heig.dil.Parser.PageParser;
import ch.heig.dil.Site.Page;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    @Test
    public void parseRegularMarkdownYaml() {
        final String markdown =
                "---\n" +
                "titre: Mon premier article\n" +
                "auteur: Bertil Chapuis\n" +
                "date: 2021-03-10\n" +
                "---\n" +
                "# Mon premier article\n" +
                "## Mon sous-titre\n" +
                "Le contenu de mon article.";
        Page page = PageParser.parseFromString(markdown);
        assertEquals(
                "<h1>Mon premier article</h1>\n" +
                "<h2>Mon sous-titre</h2>\n" +
                "<p>Le contenu de mon article.</p>\n"
                , page.getHtml());
    }
}
