package interiores.core.presentation.swing.terminal;

import interiores.core.Debug;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Deque;
import java.util.LinkedList;
import javax.swing.JTextPane;

/**
 *
 * @author hector
 */
public class ConsoleInputStream
    extends InputStream
{
    Deque<Character> contents;
    boolean isConfirmed = true;

    public ConsoleInputStream(final JTextPane textPane) {
        contents = new LinkedList();
        final ConsoleInputStream me = this;
        
        textPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e)
            {
                isConfirmed = false;
                
                char c = e.getKeyChar();
                
                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    contents.pollLast();
                else if(c == '\n' || !e.isActionKey())
                    contents.add(c);
                
                if(c == '\n'){
                    synchronized (me) {
                        me.notify();
                    }
                }
            }
        });
    }

    @Override
    synchronized public int read() throws IOException {
        while(contents.isEmpty()) {
            if(! isConfirmed) {
                isConfirmed = true;
                return -1; // End of current stream
            }
            
            try {
                this.wait(); // Wait until input
            } catch(InterruptedException e) {
                if(Debug.isEnabled())
                    e.printStackTrace();
            }
        }
        
        return contents.poll();
    }
}
