package interiores.core.presentation.swing.terminal;

import interiores.core.Debug;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author hector
 */
public class ConsoleInputStream
    extends InputStream
{
    TerminalFilter terminalFilter;
    AbstractDocument document;
    Queue<Character> contents;
    boolean isConfirmed = true;

    public ConsoleInputStream(final JTextPane textPane) {
        terminalFilter = new TerminalFilter(this);
        document = (AbstractDocument) textPane.getDocument();
        document.setDocumentFilter(terminalFilter);
        
        contents = new LinkedList();
    }

    @Override
    synchronized public int read() throws IOException {
        while(contents.isEmpty()) {
            if(! isConfirmed) {
                isConfirmed = true;
                return -1; // End of current stream
            }
            
            try {
                terminalFilter.unlock();
                this.wait(); // Wait until input
                contents = terminalFilter.lock(document);
            }
            catch(InterruptedException e) {
                if(Debug.isEnabled())
                    e.printStackTrace();
            }
        }
        
        isConfirmed = false;
        
        return contents.poll();
    }
}
