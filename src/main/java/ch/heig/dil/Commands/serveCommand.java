package ch.heig.dil.Commands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "serve",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Run the serve command"
)
public class serveCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("serve command");
    }
}
