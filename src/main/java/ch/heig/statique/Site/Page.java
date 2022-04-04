package ch.heig.statique.Site;

import java.util.List;
import java.util.Map;

public class Page {
    private final Map<String, List<String>> metadata;
    private final String html;

    public Page(Map<String, List<String>> metadata, String html) {
        this.metadata = metadata;
        this.html = html;
    }

    public Map<String, List<String>> getMetadata() {
        return metadata;
    }

    public String getHtml() {
        return html;
    }
}
