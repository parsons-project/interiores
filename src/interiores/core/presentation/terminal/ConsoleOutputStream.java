/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.core.presentation.terminal;

import com.apple.crypto.provider.Debug;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author hector0193
 */
public class ConsoleOutputStream
        extends ByteArrayOutputStream
{
    private JTextComponent textComponent;
    private Document document;
    private SimpleAttributeSet attributes;
    private PrintStream printStream;
    private StringBuffer buffer = new StringBuffer(80);
    private boolean isFirstLine;
    private boolean isAppend;

    /*
     *  Specify the option text color and PrintStream
     */
    public ConsoleOutputStream(Color textColor, PrintStream printStream, boolean isAppend) {
        textComponent = null;
        document = null;

        if (textColor != null) {
            attributes = new SimpleAttributeSet();
            StyleConstants.setForeground(attributes, textColor);
        }

        this.printStream = printStream;
        this.isAppend = isAppend;

        if (isAppend) {
            isFirstLine = true;
        }
    }

    public void setTextComponent(JTextComponent textComponent) {
        this.textComponent = textComponent;
        document = textComponent.getDocument();
    }

    @Override
    public void flush() {
        String message = toString();

        if (message.length() == 0) {
            return;
        }

        if (isAppend) {
            handleAppend(message);
        } else {
            handleInsert(message);
        }

        reset();
    }

    private void handleAppend(String message) {
        if (message.endsWith("\r")
                || message.endsWith("\n")) {
            buffer.append(message);
        } else {
            buffer.append(message);
            clearBuffer();
        }
    }

    private void handleInsert(String message) {
        buffer.append(message);

        if (message.endsWith("\r")
                || message.endsWith("\n")) {
            clearBuffer();
        }
    }

    private void clearBuffer() {
        String line = buffer.toString();
        buffer.setLength(0);
        
        if (printStream != null)
            printStream.print(line);
        
        if (textComponent == null)
            return;
        
        if (isFirstLine && document.getLength() != 0)
            buffer.insert(0, "\n");

        isFirstLine = false;
        
        try {
            if (isAppend) {
                int offset = document.getLength();
                document.insertString(offset, line, attributes);
                textComponent.setCaretPosition(document.getLength());
            } else {
                document.insertString(0, line, attributes);
                textComponent.setCaretPosition(0);
            }
        } catch (BadLocationException ble) {
            // ?
        }
    }
}
