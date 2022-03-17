package ch.heig.dil;

import ch.heig.dil.Commands.*;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "statique",
        description = "A static site generator",
        subcommands = {
                Init.class,
                Clean.class,
                Build.class,
                Serve.class,
        })
public class Statique implements Callable<Integer> {

    public static void main( String[] args ) {
        int exitCode = new CommandLine(new Statique()).execute(args);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);
        return 0;
    }
}
