/* (C)2022 */
package ch.heig.statique.Utils;

import java.nio.file.FileSystems;

/** A simple utility class to simplify redondant tasks */
public class Utils {

    public static String SEPARATOR = FileSystems.getDefault().getSeparator();

    /**
     * Get the extension from a file Source:
     * https://mkyong.com/java/how-to-get-file-extension-in-java/
     *
     * @param fileName the given name of the file
     * @return The file extension
     */
    public static String getExtensionFromString(String fileName) {
        String extension = "";

        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            extension = fileName.substring(index + 1);
        }

        return extension;
    }
}
