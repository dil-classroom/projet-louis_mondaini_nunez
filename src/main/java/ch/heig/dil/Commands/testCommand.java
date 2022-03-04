package ch.heig.dil.Commands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "test",
        description = "Test command"
)
public class testCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("you choose test command");
    }
}
