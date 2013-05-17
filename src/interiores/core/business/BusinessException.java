package interiores.core.business;

/**
 * A BusinessException is an Exception that has occurred because some application logic has been compromised.
 * @author hector
 */
public class BusinessException
    extends Exception
{
    /**
     * Constructs a BusinessException with a given message.
     * @param msg The message of the exception
     */
    public BusinessException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs a BusinessException with a given message and a cause.
     * @param msg The message of the exception
     * @param cause The cause of the exception
     */
    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
