/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.utils;

import interiores.business.models.constraints.BinaryConstraint;

/**
 *
 * @author larribas
 */
public class BinaryConstraintAssociation {

    public String furniture1;
    public String furniture2;
    public BinaryConstraint constraint;

    @Override
    public String toString() {
        return constraint + " defined between " + furniture1 + " and " + furniture2;
    }
}
