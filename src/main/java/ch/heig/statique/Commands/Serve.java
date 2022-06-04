package ch.heig.statique.Commands;

import ch.heig.statique.Utils.DirectoryWatchingUtility;
import ch.heig.statique.Utils.Utils;
import java.io.File;
import java.util.Properties;
import java.util.concurrent.Callable;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import picocli.CommandLine;

/** Démarre un serveur web afin de pouvoir accéder aux site statique. */
@CommandLine.Command(
        name = "serve",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Serve a static site")
public class Serve implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", description = "The directory containing the site")
    private File siteFolder;

    @CommandLine.Option(
            names = {"--watch"},
            description = "Watch site directory for changes and hot rebuild")
    private boolean watch = false;

    @Override
    public Integer call() throws Exception {
        final Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
        Server server = new Server(Integer.parseInt(properties.getProperty("serverport")));

        File buildFolder;

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);

        if (siteFolder.isAbsolute()) {
            buildFolder =
                    new File(
                            siteFolder.toString()
                                    + Utils.SEPARATOR
                                    + "site"
                                    + Utils.SEPARATOR
                                    + "build/");
        } else {
            System.err.println("Please use an absolute path");
            return 1;
        }

        if (!buildFolder.exists() || !buildFolder.isDirectory()) {
            System.err.println("No build folder found. Please compile before");
            return 1;
        }

        resource_handler.setResourceBase(buildFolder.toString());
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {resource_handler, new DefaultHandler()});
        server.setHandler(handlers);

        server.start();

        if (watch) {
            DirectoryWatchingUtility directoryWatchingUtility =
                    new DirectoryWatchingUtility(siteFolder, buildFolder);
            directoryWatchingUtility.watch();
        }

        server.join();
        return 0;
    }
}
