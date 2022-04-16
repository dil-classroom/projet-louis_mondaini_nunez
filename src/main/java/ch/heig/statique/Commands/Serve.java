package ch.heig.statique.Commands;

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

    @Override
    public Integer call() throws Exception {
        final Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
        Server server = new Server(Integer.parseInt(properties.getProperty("serverport")));

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);

        String root = "site/build/";
        File f = new File(root);
        if (!f.exists() || !f.isDirectory()) {
            throw new RuntimeException("No site folder found. Please compile before");
        }

        resource_handler.setResourceBase(root);
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { resource_handler, new DefaultHandler() });
        server.setHandler(handlers);

        server.start();
        server.join();
        return 0;
    }
}