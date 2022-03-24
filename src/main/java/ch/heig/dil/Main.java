package ch.heig.dil;

import ch.heig.dil.Commands.*;
import picocli.CommandLine;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "dil",
        description = "DIL Commands",
        subcommands = {
                newCommand.class,
                cleanCommand.class,
                buildCommand.class,
                serveCommand.class,
        },
        versionProvider = VersionProvider.class
)

public class Main implements Callable<Integer> {

    @CommandLine.Option(names = { "-V", "--version" }, versionHelp = true,
            description = "Print version information and exit")
    boolean versionRequested;

    public static void main( String[] args )
    {
        CommandLine cmd = new CommandLine(new Main());
        cmd.execute(args);
    }

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);
        return 0;
    }
}

class VersionProvider implements CommandLine.IVersionProvider {
    public String[] getVersion() throws IOException {

        final Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
        return new String[] {
                properties.getProperty("name") + " v" + properties.getProperty("version")
        };
    }
}
