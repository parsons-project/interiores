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
public class InvalidValueException extends BusinessException {
    
    public InvalidValueException(String msg) {
        super(msg);
    }
    
}
