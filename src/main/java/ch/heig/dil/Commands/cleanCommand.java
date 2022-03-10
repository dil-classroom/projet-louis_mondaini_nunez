package ch.heig.dil.Commands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "clean",
        description = "Run the clean command"
)
public class cleanCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("clean command");
    }
}
