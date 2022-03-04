package ch.heig.dil.Commands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "serve",
        description = "Run the serve command"
)
public class serveCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("serve command");
    }
}
