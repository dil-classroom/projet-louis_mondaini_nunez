package ch.heig.statique.Site;

import java.util.List;
import java.util.Map;

/** Representation of a web page with an HTML content and metadata */
public class Page {
    private final Map<String, List<String>> metadata;
    private final String html;

    public Page(Map<String, List<String>> metadata, String html) {
        this.metadata = metadata;
        this.html = html;
    }

    /**
     * Metadata getter
     *
     * @return metadata
     */
    public Map<String, List<String>> getMetadata() {
        return metadata;
    }

    /**
     * HTML getter
     *
     * @return HTML
     */
    public String getHtml() {
        return html;
    }
}
