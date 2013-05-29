package interiores.business.exceptions;

import interiores.core.Utils;
import interiores.core.business.BusinessException;
import interiores.utils.Functionality;
import java.util.Set;

/**
 *
 * @author hector
 */
public class RoomFunctionalitiesNotSatisfiedException
    extends BusinessException
{
    public RoomFunctionalitiesNotSatisfiedException(Set<Functionality> notSatisfied) {
        super("You room must satisfy this functionalities: " + Utils.commaSeparated(notSatisfied));
    }
}
