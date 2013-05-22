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
public class CatalogAlreadyExistsException extends BusinessException {
    
    public CatalogAlreadyExistsException(String c) {
        super("The catalog " + c + " already exists.");
    }
    
}
