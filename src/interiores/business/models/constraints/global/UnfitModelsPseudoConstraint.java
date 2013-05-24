/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.global;

import interiores.business.models.FurnitureModel;
import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.FurnitureConstraints.PreliminarTrimmer;
import java.util.Iterator;

/**
 *
 * @author hector0193
 */
public class UnfitModelsPseudoConstraint
    implements PreliminarTrimmer
{
    @Override
    public void preliminarTrim(FurnitureConstant constants[], FurnitureVariable[] variables)
    {
        for(FurnitureVariable variable : variables)
            trimUnfitModels(variable);
    }
    
    private void trimUnfitModels(FurnitureVariable variable)
    {
        Iterator<FurnitureModel> evaluatedModelIterator = variable.getDomain().getModels(0).iterator();
        while (evaluatedModelIterator.hasNext()) {
            FurnitureModel evaluatedModel = evaluatedModelIterator.next();
            boolean hasBeenRemoved = false;
            Iterator<FurnitureModel> it = evaluatedModelIterator;
            while (! hasBeenRemoved && it.hasNext()) {
                FurnitureModel model = it.next();
                boolean evaluatedModelIsLessFit = (
                        evaluatedModel.getPrice() >= model.getPrice() &&
                        evaluatedModel.getSize().depth >= model.getSize().depth &&
                        evaluatedModel.getSize().width >= model.getSize().width);
                if (evaluatedModelIsLessFit) {
                    evaluatedModelIterator.remove();
                    hasBeenRemoved = true;
                }
            }
        }
    }
}
