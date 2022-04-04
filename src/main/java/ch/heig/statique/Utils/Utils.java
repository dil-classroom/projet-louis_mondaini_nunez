package ch.heig.statique.Utils;

/**
 * A simple utility class to simplify redondant tasks
 */
public class Utils {
    /**
     * Get the extension from a file
     * Source: https://mkyong.com/java/how-to-get-file-extension-in-java/
     * @param fileName the given name of the file
     * @return The file extension
     */
    static public String getExtensionFromString(String fileName) {
        String extension = "";

        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            extension = fileName.substring(index + 1);
        }

        return extension;
    }
}
