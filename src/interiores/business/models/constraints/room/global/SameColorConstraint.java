/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.business.models.constraints.room.global;

import interiores.business.models.backtracking.FurnitureConstant;
import interiores.business.models.backtracking.FurnitureValue;
import interiores.business.models.backtracking.FurnitureVariable;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.business.models.constraints.room.RoomBacktrackingTimeTrimmer;
import interiores.business.models.constraints.room.RoomPreliminarTrimmer;
import interiores.business.models.room.FurnitureModel;
import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a global constraint imposed over the color of all the elements in a room.
 * This constraint forces all the furniture to be the same color, regardless of which 
 * specific color it is
 * @author nmamano
 */
@XmlRootElement
public class SameColorConstraint 
    extends GlobalConstraint
    implements RoomPreliminarTrimmer, RoomBacktrackingTimeTrimmer
{
    public SameColorConstraint()
    { }

    /**
     * Finds a list of colors such that for each variable, at least one model
     * has it; then, eliminates all models of each variable whose color is not in the
     * list.
     * 
     * @param variables
     * @param fixedFurniture 
     */
    @Override
    public void preliminarTrim(List<FurnitureVariable> variables, List<FurnitureConstant> fixedFurniture) {
        
        //1) find colors available for each variable
        HashSet<Color>[] availableColors = new HashSet[variables.size()];
        int i = 0;
        for (FurnitureVariable variable : variables) {
            availableColors[i] = allColors(variable);
            i++;
        }
        
        //2) find colors available for all variables
        HashSet<Color> validColors = availableColors[0];
        for (i = 1; i < availableColors.length; ++i) {
            validColors = intersection(validColors, availableColors[i]);
        }
        
        //3) for each variable, eliminate models with an invalid color
        for (FurnitureVariable variable : variables) {
            HashSet<FurnitureModel> validModels = variable.getDomain().getModels(0);
            Iterator<FurnitureModel> it = validModels.iterator();
            while (it.hasNext()) {
                if (! validColors.contains(it.next().getColor()))
                    it.remove();
            }
            variable.eliminateExceptM(validModels);
        }
    }

    /**
     * Pre: all variables from the assignedVariable list and actual have a value
     * with the same color.
     * If the assignedVariable list is not empty, does nothing, because the
     * color is determined with the first assignement.
     * Otherwise, trims all models from unassigned variables that do not have
     * the same color as actual.
     * @param assignedVariables
     * @param unassignedVariables
     * @param fixedFurniture
     * @param actual 
     */
    @Override
    public void trim(List<FurnitureVariable> assignedVariables,
        List<FurnitureVariable> unassignedVariables,
        List<FurnitureConstant> fixedFurniture, FurnitureVariable actual) {
        if (assignedVariables.isEmpty()) {
            Color validColor = actual.getAssignedValue().getModel().getColor();
            for (FurnitureVariable variable : unassignedVariables)
                trimVariable(variable, validColor);
        }
    }

    /**
     * Pre: all variables from the assignedVariable list have the same color.
     * We only need to check whether actual has the same color as any variable
     * from said list.
     * @param assignedVariables
     * @param unassignedVariables
     * @param fixedFurniture
     * @param actual
     * @return 
     */
    @Override
    public boolean isSatisfied(List<FurnitureVariable> assignedVariables,
        List<FurnitureVariable> unassignedVariables,
        List<FurnitureConstant> fixedFurniture,
        FurnitureVariable actual)
    {
        if (assignedVariables.isEmpty())
            return true; //first assignment can't violate same-color restriction
        else
            return actual.getAssignedValue().getModel().getColor() ==
                    assignedVariables.get(0).getAssignedValue().getModel().getColor();
    }
    
    
    private void trimVariable(FurnitureVariable variable, Color validColor) {
         HashSet<FurnitureModel> validModels = new HashSet(variable.getDomain().getModels(1));
        //if assignedVariables is empty, we are at iteration 0, trimming
        //values of iteration 1
        Iterator<FurnitureModel> it = validModels.iterator();
        while (it.hasNext()) {
            if (! it.next().getColor().equals(validColor))
                it.remove();
        }

        variable.trimExceptM(validModels);
    }
    
    /**
     * Returns all colors available for a variable.
     * @param variable
     * @return 
     */
    private HashSet<Color> allColors(FurnitureVariable variable) {
        HashSet<Color> colors = new HashSet<Color>();
        for (FurnitureModel model : variable.getDomain().getModels(0))
            colors.add(model.getColor());
        return colors;
    }

    /**
     * Returns the intersection of 2 sets of colors.
     * @param set1
     * @param set2
     * @return 
     */
    private HashSet<Color> intersection(HashSet<Color> set1, HashSet<Color> set2) {
        Iterator<Color> it = set2.iterator();
        while (it.hasNext()) {
            Color color = it.next();
            if (! set1.contains(color))
                it.remove();
        }
        return set2;
    }

    @Override
    public void notifyStepBack(List<FurnitureVariable> assignedVariables,
        List<FurnitureVariable> unassignedVariables,
        List<FurnitureConstant> fixedFurniture,
        FurnitureVariable actual, FurnitureValue actualValue) {
        
    }
    
}
