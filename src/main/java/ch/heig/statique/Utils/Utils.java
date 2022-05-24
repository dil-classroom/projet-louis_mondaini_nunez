package ch.heig.statique.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import org.apache.commons.io.FileUtils;

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

    /**
     * https://www.baeldung.com/java-copy-directory
     *
     * @throws IOException if the copy fails
     * @param sourceDirectoryLocation the source directory
     * @param destinationDirectoryLocation the destination directory
     */
    public static void copyDirectory(
            String sourceDirectoryLocation, String destinationDirectoryLocation)
            throws IOException {
        File sourceDirectory = new File(sourceDirectoryLocation);
        File destinationDirectory = new File(destinationDirectoryLocation);
        FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
    }
}
