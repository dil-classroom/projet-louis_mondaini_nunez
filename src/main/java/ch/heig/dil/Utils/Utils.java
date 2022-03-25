package ch.heig.dil.Utils;

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
}
