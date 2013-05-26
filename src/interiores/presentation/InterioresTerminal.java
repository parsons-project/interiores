package interiores.presentation;

import interiores.core.presentation.TerminalController;
import java.io.InputStream;
import java.io.PrintStream;

/**
 *
 * @author hector
 */
public class InterioresTerminal
    extends TerminalController
{
    public InterioresTerminal(InputStream istream, PrintStream ostream) {
        super("interiores.presentation.terminal.commands", istream, ostream);
        
        setWelcomeMessage("Welcome to Interior design! Use 'help' to see the available commands.");
    }
}
