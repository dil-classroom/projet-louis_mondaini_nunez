package ch.heig.statique.Site;

import java.util.Map;

/** Representation of a web page with an HTML content and metadata */
public class Page {
    private final Map<String, Object> metadata;
    private final String content;

    public Page(Map<String, Object> metadata, String html) {
        this.metadata = metadata;
        this.content = html;
    }

    /**
     * Metadata getter
     *
     * @return metadata
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * Content getter
     *
     * @return content
     */
    public String getContent() {
        return content;
    }
}
