/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.core.presentation.swing.terminal;

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
    public ConsolePrintStream(JTextComponent textComponent, Color color) {
        this(new ConsoleOutputStream(textComponent, color));
    }
    
    public ConsolePrintStream(ConsoleOutputStream consoleOutput) {
        super(consoleOutput, true);
    }
}
