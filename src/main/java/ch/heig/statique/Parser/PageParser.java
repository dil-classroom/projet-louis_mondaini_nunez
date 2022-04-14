package ch.heig.statique.Parser;

import ch.heig.statique.Site.Page;
import ch.heig.statique.Utils.Utils;
import org.commonmark.Extension;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.*;

public class PageParser {
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

    public static Page parseFromString(String data) {
        List<Extension> extensions = Collections.singletonList(YamlFrontMatterExtension.create());
        Parser parser = Parser.builder()
                .extensions(extensions)
                .build();

        YamlFrontMatterVisitor visitor = new YamlFrontMatterVisitor();

        Node document = parser.parse(data);
        document.accept(visitor);

        HtmlRenderer renderer = HtmlRenderer.builder()
                .attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext context) {
                        return new AttributeProvider() {
                            @Override
                            public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
                                if (node instanceof Link) {
                                    System.out.println(node);
                                }
                            }
                        };
                    }
                })
                .extensions(extensions)
                .build();

        String stringDocument = renderer.render(document);
        Map<String, List<String>> metadata = visitor.getData();

        return new Page(metadata, stringDocument);
    }
}
