package ch.heig.statique.Site;

import java.util.Map;

/**
 * Representation of a website page with its content and metadata.
 */
public class Page {
    private final Map<String, Object> metadata;
    private final String content;

    /**
     * Constructor of a Page object from its content and metadata.
     * @param metadata the metadata of the page
     * @param content the content of the page
     */
    public Page(Map<String, Object> metadata, String content) {
        this.metadata = metadata;
        this.content = content;
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
