package ch.heig.dil;

import ch.heig.dil.Commands.*;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "dil",
        description = "DIL Commands",
        subcommands = {
                newCommand.class,
                cleanCommand.class,
                buildCommand.class,
                serveCommand.class,
        })
public class Main implements Callable<Integer> {

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
