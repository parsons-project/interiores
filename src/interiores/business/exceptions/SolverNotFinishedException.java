package interiores.business.exceptions;

import interiores.core.business.BusinessException;

/**
 *
 * @author hector
 */
public class SolverNotFinishedException
    extends BusinessException
{
    public SolverNotFinishedException()
    {
        super("There is already a search in progress!");
    }
}
