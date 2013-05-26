package interiores.business.exceptions;

import horarios.shared.ElementNotFoundException;
import interiores.core.business.BusinessException;

/**
 *
 * @author hector
 */
public class ElementNotFoundBusinessException
    extends BusinessException
{
    public ElementNotFoundBusinessException(String catalogName, String elementName,
            ElementNotFoundException cause) {
        super("The " + catalogName + " catalog has no element named: " + elementName);
        initCause(cause);
    }
    
    public ElementNotFoundBusinessException(
            ElementNotFoundException cause) {
        super("The requested element couldn't be found");
        initCause(cause);
    }
}
