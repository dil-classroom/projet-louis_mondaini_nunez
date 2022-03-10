package ch.heig.dil.Commands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "clean",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Run the clean command"
)
public class cleanCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("clean command");
    }
}
