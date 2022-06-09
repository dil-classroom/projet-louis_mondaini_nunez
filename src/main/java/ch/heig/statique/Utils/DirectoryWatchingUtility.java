package ch.heig.statique.Utils;

import ch.heig.statique.Build.Builder;
import io.methvin.watcher.DirectoryWatcher;
import io.methvin.watcher.hashing.FileHasher;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Utility class to watch a directory for changes and hot-rebuild the site.
 */
public class DirectoryWatchingUtility {

    private final DirectoryWatcher watcher;

    /**
     * Constructor of a DirectoryWatchingUtility object from a site directory
     * and a folder to exclude (usually the build folder).
     * @param siteFolder the site directory
     * @param folderToExclude the folder to exclude
     * @throws IOException if an error occurs while creating the watcher
     */
    public DirectoryWatchingUtility(File siteFolder, File folderToExclude) throws IOException {
        Path directoryToWatch = siteFolder.toPath();
        this.watcher =
                DirectoryWatcher.builder()
                        .path(directoryToWatch)
                        .listener(
                                event -> {
                                    if (event.path()
                                                    .toFile()
                                                    .getCanonicalPath()
                                                    .contains(
                                                            folderToExclude.getCanonicalPath()
                                                                    + File.separator)
                                            || event.path().equals(folderToExclude.toPath())) {
                                        return;
                                    }

                                    Builder.buildSite(siteFolder);
                                })
                        .fileHasher(FileHasher.LAST_MODIFIED_TIME)
                        .build();
    }

    /**
     * Stop watching the site directory.
     * @throws IOException if an error occurs while stopping the watcher
     */
    public void stopWatching() throws IOException {
        watcher.close();
    }

    /**
     * Start watching the site directory.
     */
    public void watch() {
        watcher.watch();
    }
}
