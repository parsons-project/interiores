/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.room.global;

import interiores.business.models.FurnitureModel;
import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.furniture.PreliminarTrimmer;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.constraints.room.RoomPreliminarTrimmer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * This pseudo-constraint eliminates models that do not necessarily violate
 * any restriction, but that there exists other models that fulfil any
 * constraint that such model would fulfil. Thus, we reduce the size of the
 * domains without compromising correctness.
 * 
 * This preliminar trimmer has an important singularity: it has to be called
 * after all others preliminar trimmers. For example, this method could trim
 * a furniture model such when there is a constraint that says that the user
 * wants that particular model.
 * 
 * In order to be efficient, it needs information about what other constraints
 * will use.
 * 
 * @author hector0193
 */
public class UnfitModelsPseudoConstraint
    extends GlobalConstraint
    implements RoomPreliminarTrimmer {

    private boolean sameColorConstraintActive;
    private boolean sameMaterialConstraintActive;
    private boolean budgetConstraintActive;

    public UnfitModelsPseudoConstraint(boolean sameColorConstraintActive,
            boolean sameMaterialConstraintActive, boolean budgetConstraintActive) {
        this.sameColorConstraintActive = sameColorConstraintActive;
        this.sameMaterialConstraintActive = sameMaterialConstraintActive;
        this.budgetConstraintActive = budgetConstraintActive;
    }
    
    
    @Override
    public void preliminarTrim(List<FurnitureVariable> variables, List<FurnitureConstant> fixedFurniture) {
        for(FurnitureVariable variable : variables)
            trimUnfitModels(variable);
    }
    
    private void trimUnfitModels(FurnitureVariable variable) {
        HashSet<FurnitureModel> validModels = variable.getDomain().getModels(0);
        Iterator<FurnitureModel> evaluatedModelIterator = validModels.iterator();
        while (evaluatedModelIterator.hasNext()) {
            FurnitureModel evaluatedModel = evaluatedModelIterator.next();
            boolean hasBeenRemoved = false;
            Iterator<FurnitureModel> it = evaluatedModelIterator;
            while (! hasBeenRemoved && it.hasNext()) {
                FurnitureModel model = it.next();
                boolean evaluatedModelIsLessFit = (
                        (evaluatedModel.getColor() == model.getColor() ||
                        ! sameColorConstraintActive) &&
                        (evaluatedModel.getMaterial().equals(model.getMaterial()) ||
                        ! sameMaterialConstraintActive) &&
                        (evaluatedModel.getPrice() >= model.getPrice() ||
                        ! budgetConstraintActive) &&
                        evaluatedModel.getSize().depth >= model.getSize().depth &&
                        evaluatedModel.getSize().width >= model.getSize().width);
                if (evaluatedModelIsLessFit) {
                    evaluatedModelIterator.remove();
                    hasBeenRemoved = true;
                }
            }
        }
        variable.eliminateExceptM(validModels);
    }
}
