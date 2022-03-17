package ch.heig.dil.Commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "clean",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Clean a static site"
)
public class Clean implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
