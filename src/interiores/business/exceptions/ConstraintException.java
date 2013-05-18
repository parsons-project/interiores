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
public class ConstraintException extends BusinessException {

    public ConstraintException(String msg) {
        super(msg);
    }
    
}
