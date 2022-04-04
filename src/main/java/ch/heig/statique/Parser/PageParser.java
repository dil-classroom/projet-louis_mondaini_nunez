package ch.heig.statique.Parser;

import ch.heig.statique.Site.Page;
import ch.heig.statique.Utils.Utils;
import org.commonmark.Extension;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.*;

/**
 *  A utility class allowing to convert markdown files with yaml front matter
 *  metadata to HTML.
 */
public class PageParser {
    /**
     * Convert markdown files with yaml front matter metadata to HTML
     * @param file the given file to convert
     * @return The parsed html page
     */
    public static Page parseFromMarkdownFile(File file) {
        if (!Utils.getExtensionFromString(file.getName()).equals("md")) {
            throw new InvalidParameterException("Cannot parse markdown from a non-markdown file.");
        }

        StringBuilder data = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(file, StandardCharsets.UTF_8)
        )) {

            int c;
            while ((c = bufferedReader.read()) != -1) {
                data.append((char) c);
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return parseFromString(data.toString());
    }

    /**
     * Convert markdown string with yaml front matter metadata to HTML
     * @param data the given markdown string to convert
     * @return The parsed html page
     */
    public static Page parseFromString(String data) {
        List<Extension> extensions = Collections.singletonList(YamlFrontMatterExtension.create());
        Parser parser = Parser.builder()
                .extensions(extensions)
                .build();

        YamlFrontMatterVisitor visitor = new YamlFrontMatterVisitor();

        Node document = parser.parse(data);
        document.accept(visitor);

        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();

        String stringDocument = renderer.render(document);
        Map<String, List<String>> metadata = visitor.getData();

        return new Page(metadata, stringDocument);
    }
}
