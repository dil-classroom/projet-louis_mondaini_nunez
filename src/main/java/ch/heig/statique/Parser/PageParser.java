package ch.heig.statique.Parser;

import ch.heig.statique.Site.Page;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.commonmark.*;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;
import org.yaml.snakeyaml.Yaml;

public class PageParser {

    public static String convertMdToHtml(String content) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content);

        HtmlRenderer renderer =
                HtmlRenderer.builder()
                        .attributeProviderFactory(
                                new AttributeProviderFactory() {
                                    @Override
                                    public AttributeProvider create(
                                            AttributeProviderContext context) {
                                        return new AttributeProvider() {
                                            @Override
                                            public void setAttributes(
                                                    Node node,
                                                    String tagName,
                                                    Map<String, String> attributes) {
                                                if (node instanceof Link) {
                                                    String file = attributes.get("href");
                                                    if (file.endsWith(".md")) {
                                                        attributes.replace(
                                                                "href",
                                                                file.replace(".md", ".html"));
                                                    }
                                                }
                                            }
                                        };
                                    }
                                })
                        .build();

        return renderer.render(document);
    }

    public static Page parseFile(File file) throws IOException {
        String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        return parseString(content);
    }

    public static Page parseString(String content) {
        Yaml yaml = new Yaml();

        String[] split = content.split("---");
        Map<String, Object> metadata = yaml.load(split[0]);
        if (metadata.containsKey("date")) {
            metadata.replace("date", new SimpleDateFormat("yyyy-MM-dd").format(metadata.get("date")));
        }

        return new Page(metadata, split[1]);
    }
}
