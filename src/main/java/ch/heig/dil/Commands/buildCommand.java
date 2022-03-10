package ch.heig.dil.Commands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "build",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Run the build command"
)
public class buildCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("build command");
    }
}
