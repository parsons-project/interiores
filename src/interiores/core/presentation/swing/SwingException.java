package interiores.core.presentation.swing;

/**
 *
 * @author hector
 */
public class SwingException
    extends Exception
{
    public SwingException(String msg) {
        super(msg);
    }
    
    public SwingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
