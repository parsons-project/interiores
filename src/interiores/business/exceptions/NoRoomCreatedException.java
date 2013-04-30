package interiores.business.exceptions;

import interiores.core.business.BusinessException;

/**
 *
 * @author hector
 */
public class NoRoomCreatedException
    extends BusinessException
{
    public NoRoomCreatedException() {
        super("There is no room created.");
    }
}
