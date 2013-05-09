/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.backtracking.trimmers;

import interiores.business.models.backtracking.FurnitureVariable;

/**
 *
 * @author hector0193
 */
public interface PreliminarTrimmer
{
    public void trim(FurnitureVariable[] variables);
}
