package ch.heig.statique.Commands;

import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
        name = "serve",
        mixinStandardHelpOptions = true,
        version = "0.1",
        description = "Serve a static site")
public class Serve implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
