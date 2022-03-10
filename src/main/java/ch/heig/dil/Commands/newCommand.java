package ch.heig.dil.Commands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "new",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Run the new command"
)
public class newCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("run command");
    }
}
