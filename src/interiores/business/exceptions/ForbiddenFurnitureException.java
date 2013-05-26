/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.exceptions;

import interiores.core.business.BusinessException;

/**
 * Represents a exception thrown when the user tries to select a type of furniture that
 * pertains to the forbidden list of a room type, and thus cannot be placed in that room.
 * @author larribas
 */
public class ForbiddenFurnitureException 
    extends BusinessException {
    
    public ForbiddenFurnitureException(String fname, String rtype) {
        super(fname + " is in " + rtype + "'s forbidden furniture list.");
    }
    
}
