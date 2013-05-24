/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.core.presentation.terminal;

import java.awt.Color;
import java.io.PrintStream;
import javax.swing.text.JTextComponent;

/**
 *
 * @author hector0193
 */
public class ConsolePrintStream
        extends PrintStream
{
    private ConsoleOutputStream consoleOutput;
    
    public ConsolePrintStream(PrintStream printStream) {
        this(new ConsoleOutputStream(Color.black, printStream, true));
    }
    
    public ConsolePrintStream(ConsoleOutputStream consoleOutput) {
        super(consoleOutput, true);
        
        this.consoleOutput = consoleOutput;
    }
    
    public void setTextComponent(JTextComponent textComponent) {
        consoleOutput.setTextComponent(textComponent);
    }
    
    public void setAsDefault() {
        System.setOut(this);
    }
}
