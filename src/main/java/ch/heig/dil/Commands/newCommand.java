package ch.heig.dil.Commands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "new",
        description = "Run the new command"
)
public class newCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("run command");
    }
}
