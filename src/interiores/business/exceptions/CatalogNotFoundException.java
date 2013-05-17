package interiores.business.exceptions;

import interiores.core.business.BusinessException;

/**
 *
 * @author hector
 */
public class CatalogNotFoundException
    extends BusinessException
{
    public CatalogNotFoundException(String catalogName) {
        super("There is no catalog known as: " + catalogName);
    }
}
