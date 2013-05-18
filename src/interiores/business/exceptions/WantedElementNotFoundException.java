package interiores.business.exceptions;

import interiores.core.business.BusinessException;

/**
 *
 * @author hector
 */
public class WantedElementNotFoundException
    extends BusinessException
{
    public WantedElementNotFoundException(String wantedElementId) {
        super("There is no element in your wish list identified as " + wantedElementId);
    }
}
