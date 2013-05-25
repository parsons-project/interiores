package interiores.core.presentation.swing.terminal;

import java.awt.Color;
import java.io.InputStream;
import java.io.PrintStream;
import javax.swing.JTextPane;

/**
 *
 * @author hector
 */
public class SwingTerminalStream {
    private ConsoleInputStream istream;
    private ConsolePrintStream ostream;
    
    public SwingTerminalStream(JTextPane textPane, Color color) {
        istream = new ConsoleInputStream(textPane);
        ostream = new ConsolePrintStream(textPane, Color.white);
    }
    
    public InputStream getInputStream() {
        return istream;
    }
    
    public PrintStream getPrintStream() {
        return ostream;
    }
}
