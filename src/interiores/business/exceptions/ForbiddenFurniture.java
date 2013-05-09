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
public class ForbiddenFurniture 
    extends BusinessException {
    
    public ForbiddenFurniture(String fname, String rtype) {
        super(fname + " cannot belong to a " + rtype + ".");
    }
    
}
