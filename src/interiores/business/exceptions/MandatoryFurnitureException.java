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
public class MandatoryFurnitureException
    extends BusinessException {
    
    public MandatoryFurnitureException(String fname, String rtype) {
        super("A " + rtype + " must contain at least one " + fname + ".");
    }
    
    public MandatoryFurnitureException(String fname) {
        super(fname + " is in the list of mandatory furniture.");
    }
    
}
