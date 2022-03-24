package ch.heig.dil.Parser;

import ch.heig.dil.Site.Page;
import org.commonmark.Extension;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.File;
import java.util.*;

public class PageParser {
    public static Page parseFromMarkdownFile(File file) {
        return null;
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
                .extensions(extensions)
                .build();

        String stringDocument = renderer.render(document);
        Map<String, List<String>> metadata = visitor.getData();

        return new Page(metadata, stringDocument);
    }
}
