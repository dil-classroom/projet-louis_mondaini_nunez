package ch.heig.statique.Utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Utils {
    /**
     * https://mkyong.com/java/how-to-get-file-extension-in-java/
     */
    static public String getExtensionFromString(String fileName) {
        String extension = "";

        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            extension = fileName.substring(index + 1);
        }

        return extension;
    }

    /**
     * https://www.baeldung.com/java-copy-directory
     */
    public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
        File sourceDirectory = new File(sourceDirectoryLocation);
        File destinationDirectory = new File(destinationDirectoryLocation);
        FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
    }
}
