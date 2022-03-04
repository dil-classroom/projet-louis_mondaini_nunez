package ch.heig.dil.Commands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "dil",
        description = "DIL Commands",
        subcommands = {
                testCommand.class
        })
public class DILCommand implements Runnable {

    @Override
    public void run() {
        System.out.println("This is a DIL command");
    }
}
