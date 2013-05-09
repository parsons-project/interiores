/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.exceptions;

import interiores.core.business.BusinessException;

/**
 * Represents a exception thrown when the user tries to remove a type of furniture from
 * the wishlist, and it pertains to the mandatory list of a room type, and thus it cannot
 * be removed from that room.
 * @author larribas
 */
public class MandatoryFurniture
    extends BusinessException {
    
    public MandatoryFurniture(String fname, String rtype) {
        super(fname + " is a mandatory furniture for " + rtype + ", and thus it cannot be unselected.");
    }
    
}
