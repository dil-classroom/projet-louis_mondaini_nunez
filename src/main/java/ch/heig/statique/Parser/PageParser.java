package ch.heig.statique.Parser;

import ch.heig.statique.Site.Page;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;
import org.yaml.snakeyaml.Yaml;

/**
 * Parser class allowing to parse a file from a file or string its representation
 * as a Page object.
 */
public class PageParser {

    /**
     * Convert the markdown string to HTML. The method also converts links
     * to .md files to links to .html files.
     * @param content the markdown string
     * @return the HTML string
     */
    public static String convertMdToHtml(String content) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content);

        HtmlRenderer renderer =
                HtmlRenderer.builder()
                        .attributeProviderFactory(
                                context -> (node, tagName, attributes) -> {
                                    if (node instanceof Link) {
                                        String file = attributes.get("href");
                                        if (file.endsWith(".md")) {
                                            attributes.replace(
                                                    "href",
                                                    file.replace(".md", ".html"));
                                        }
                                    }
                                })
                        .build();

        return renderer.render(document);
    }

    /**
     * Parse the given file and return its representation as a Page object.
     * @param file the file to parse
     * @return the Page object
     * @throws IOException if an error occurs while reading the file
     */
    public static Page parseFile(File file) throws IOException {
        String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        return parseString(content);
    }

    /**
     * Parse the given string and return its representation as a Page object.
     * @param content the string to parse
     * @return the Page object
     */
    public static Page parseString(String content) {
        Yaml yaml = new Yaml();

        String[] split = content.split("---");
        Map<String, Object> metadata = yaml.load(split[0]);
        if (metadata.containsKey("date")) {
            metadata.replace(
                    "date", new SimpleDateFormat("yyyy-MM-dd").format(metadata.get("date")));
        }

        return new Page(metadata, split[1]);
    }
}
