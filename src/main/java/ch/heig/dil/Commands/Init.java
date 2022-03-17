package ch.heig.dil.Commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "new",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Init a static site"
)
public class Init implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
