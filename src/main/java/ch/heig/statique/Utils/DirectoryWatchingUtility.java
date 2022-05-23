package ch.heig.statique.Utils;

import ch.heig.statique.Build.Builder;
import io.methvin.watcher.DirectoryWatcher;
import io.methvin.watcher.hashing.FileHasher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class DirectoryWatchingUtility {

    private final DirectoryWatcher watcher;

    public DirectoryWatchingUtility(File siteFolder, File folderToExclude) throws IOException {
        Path directoryToWatch = siteFolder.toPath();
        this.watcher = DirectoryWatcher.builder()
                .path(directoryToWatch)
                .listener(event -> {
                    if (event.path().toFile().getCanonicalPath().contains(folderToExclude.getCanonicalPath() + File.separator)
                    || event.path().equals(folderToExclude.toPath())) {
                        return;
                    }

                    Builder.buildSite(siteFolder);

                })
                .fileHasher(FileHasher.LAST_MODIFIED_TIME)
                .build();
    }

    public void stopWatching() throws IOException {
        watcher.close();
    }

    public void watch() {
        // you can also use watcher.watch() to block the current thread
        watcher.watch();
    }
}