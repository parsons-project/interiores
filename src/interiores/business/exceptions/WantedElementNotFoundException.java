package interiores.business.exceptions;

import interiores.core.business.BusinessException;

/**
 *
 * @author hector
 */
public class WantedElementNotFoundException
    extends BusinessException
{
    public WantedElementNotFoundException(String elementType, String elementId) {
        super("There is no " + elementType + " in you wish list identified as " + elementId);
    }
}
