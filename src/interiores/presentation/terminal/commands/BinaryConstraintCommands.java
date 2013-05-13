/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.terminal.commands;

import interiores.business.controllers.BinaryConstraintController;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.core.Utils;
import interiores.core.business.BusinessException;
import interiores.core.presentation.terminal.AdvancedCommandGroup;
import interiores.core.presentation.terminal.annotation.Command;
import interiores.core.presentation.terminal.annotation.CommandSubject;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 *
 * @author larribas
 */
@CommandSubject(name = "bc", description = "Binary-constraint related commands")
public class BinaryConstraintCommands extends AdvancedCommandGroup {
        
    private BinaryConstraintController constraintController;
    
    public BinaryConstraintCommands (BinaryConstraintController constraintController) {
        this.constraintController = constraintController;
    }
    
    @Command("Add a binary constraint to a pair of furniture")
    public void add()
            throws Throwable
    {
        String type = readString("Specify the kind of constraint you want to add");
        
        try {
            getClass().getMethod("add" + Utils.capitalize(type) + "Constraint").invoke(this);
        }
        catch(InvocationTargetException e) {
            throw e.getCause();
        }
    }
    
    public void addDistanceConstraint()
            throws BusinessException
    {        
        String parameter = readString("Enter <max> to set a maximum distance between two pieces of furniture."
                        + " Enter <min> to set a minimum distance.");

        int distance = readInt("Enter the distance measured in cm");
        
        String[] furniture = selectFurniturePair();
        constraintController.addDistanceConstraint(parameter,distance, furniture);
    }
    
    public void addFaceConstraint() throws BusinessException {
        String parameter = readString("Enter the type of facing (partial or straight)");
        String[] furniture = selectFurniturePair();
        constraintController.addFaceConstraint(parameter, furniture);
    }
    
    private String[] selectFurniturePair() {
        String[] furn = new String[2];
        furn[0] = readString("Select the two pieces of furniture you want to apply the constraint to");
        furn[1] = readString("");
        return furn;
    }
    
    @Command("Remove an applied constraint")
    public void remove()
            throws NoRoomCreatedException, BusinessException
    {
        String kind = readString("Specify the kind of constraint you want to remove");
        
        String[] furniture = selectFurniturePair();
        constraintController.remove(kind, furniture);
        
    }
    
    @Command("List binary constraints applied to a piece of furniture")
    public void list()
            throws NoRoomCreatedException
    {
        String furn = readString("Select the furniture whose constraints you want to show");
        Collection constraints = constraintController.getConstraints(furn);
        
        println("List of constraints defined for " + furn);
        print(constraints);
        
        if(constraints.isEmpty())
            println("There are no constraints defined for " + furn);
    }
    
    
}
