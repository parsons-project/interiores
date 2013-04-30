package interiores.business.exceptions;

import interiores.core.business.BusinessException;

/**
 *
 * @author hector
 */
public class DefaultCatalogOverwriteException
    extends BusinessException
{
    public DefaultCatalogOverwriteException() {
        super("The default catalog cannot be overwritten.");
    }
}
