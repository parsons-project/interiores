/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.exceptions;

import interiores.core.business.BusinessException;

/**
 *
 * @author larribas
 */
public class ActiveCatalogRemovalException extends BusinessException {
    
    public ActiveCatalogRemovalException() {
        super("The currently active catalog cannot be removed");
    }
    
}
