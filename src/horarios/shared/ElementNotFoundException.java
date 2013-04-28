package horarios.shared;

/**
 * ElementNotFoundException represents an exception generated when the Catalog doesn't contain
 * the asked IdObject
 * @author Jaume
 */
public class ElementNotFoundException extends Exception{
    
    /**
     * Constructor only with the identifier
     * @param id Identifier
     */
    public ElementNotFoundException(String id) {
        super("The element: " + id + " is not into the Catalog");
    }
    
    
    /**
     * Constructor with the identifier and a cause
     * @param b_id Bad identifier
     */
    public ElementNotFoundException(String id, Throwable cause) {
        super("The element: " + id + " is not into the Catalog", cause);
    }
}

