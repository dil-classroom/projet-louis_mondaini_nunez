package ch.heig.dil.Commands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "dil",
        description = "DIL Commands",
        subcommands = {
                newCommand.class,
                cleanCommand.class,
                buildCommand.class,
                serveCommand.class,
        })
public class DILCommand implements Runnable {

    @Override
    public void run() {
        System.out.println("This is a DIL command");
    }
}
