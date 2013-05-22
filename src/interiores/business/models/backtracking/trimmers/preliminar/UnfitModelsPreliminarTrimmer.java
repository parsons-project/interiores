/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.backtracking.trimmers.preliminar;

import interiores.business.models.room.FurnitureModel;
import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.backtracking.trimmers.PreliminarTrimmer;
import java.util.Iterator;

/**
 *
 * @author hector0193
 */
public class UnfitModelsPreliminarTrimmer
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
