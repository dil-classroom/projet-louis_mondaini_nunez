package ch.heig.dil.Commands;

import picocli.CommandLine;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "build",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Build a static site"
)
public class Build implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
