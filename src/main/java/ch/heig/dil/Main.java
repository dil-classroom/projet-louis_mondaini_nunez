package ch.heig.dil;

import ch.heig.dil.Commands.DILCommand;
import picocli.CommandLine;

public class Main {

    public static void main( String[] args )
    {
        new CommandLine(new DILCommand()).execute(args);
    }
}
