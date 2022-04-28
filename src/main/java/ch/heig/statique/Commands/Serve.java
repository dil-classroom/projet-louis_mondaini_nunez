package ch.heig.statique.Commands;

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

@CommandLine.Command(
        name = "serve",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Serve a static site")
public class Serve implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", description = "The directory containing the site")
    private File file;

    @Override
    public Integer call() throws Exception {
        final Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
        Server server = new Server(Integer.parseInt(properties.getProperty("serverport")));

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);

        if (file.isAbsolute()) {
            file =
                    new File(
                            file.toString()
                                    + Utils.SEPARATOR
                                    + "site"
                                    + Utils.SEPARATOR
                                    + "build/");
        } else {
            throw new RuntimeException("Please use an aboslute path");
        }

        if (!file.exists() || !file.isDirectory()) {
            throw new RuntimeException("No site folder found. Please compile before");
        }

        resource_handler.setResourceBase(file.toString());
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {resource_handler, new DefaultHandler()});
        server.setHandler(handlers);

        server.start();
        server.join();
        return 0;
    }
}
