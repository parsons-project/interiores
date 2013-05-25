package interiores.core.presentation.swing.terminal;

import interiores.core.Debug;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author hector
 */
public class TerminalFilter
    extends DocumentFilter
{
    private final InputStream istream;
    private boolean isLocked;
    private int lockOffset;
    private int lineLength;
    
    public TerminalFilter(InputStream istream) {
        this.istream = istream;
        lockOffset = 0;
        lineLength = 0;
        
        isLocked = true;
    }
    
    public Queue<Character> lock(AbstractDocument document) {
        isLocked = true;

        String readString = "";
        
        try {
            readString = document.getText(lockOffset, lineLength);
            
            lockOffset += lineLength;
            lineLength = 0;
        }
        catch(BadLocationException e) {
            if(Debug.isEnabled())
                e.printStackTrace();
        }
        
        Queue<Character> read = new LinkedList();
        
        for(char c : readString.toCharArray())
            read.add(c);
        
        return read;
    }
    
    public void unlock() {
        isLocked = false;
    }
    
    @Override
    public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
        if(lockOffset > offset)
            return;
        
        if(lineLength < length)
            return;
        
        lineLength -= length;
        
        super.remove(fb, offset, length);
    }
    
    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException
    {
        int totalOffset = offset + string.length();
        
        if(isLocked) {
            if(totalOffset > lockOffset)
                lockOffset = totalOffset;
        }
        else if(lockOffset <= offset)
            lineLength += string.length();
        else
            return;
            
        
        super.insertString(fb, offset, string, attr);
        
        if(! isLocked && string.endsWith("\n")) {
            synchronized (istream) {
                istream.notify();
            }
        }
    }
    
    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException
    {
        insertString(fb, offset, text, attrs);
    }
}
