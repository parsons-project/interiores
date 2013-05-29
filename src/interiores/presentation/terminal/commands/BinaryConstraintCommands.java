package interiores.presentation.terminal.commands;

import interiores.business.controllers.BinaryConstraintController;
import interiores.core.Utils;
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
public class BinaryConstraintCommands
    extends AdvancedCommandGroup
{        
    private BinaryConstraintController constraintController;
    
    public BinaryConstraintCommands(BinaryConstraintController constraintController) {
        this.constraintController = constraintController;
    }
    
    @Command("Add a binary constraint to a pair of furniture")
    public void add()
            throws Throwable
    {
        String type = readChoice("Specify the alias of the constraint you want to add",
                constraintController.getAvailableConstraints());
        
        String[] parts = type.split(constraintController.getConstraintTypeSeparator(), 2);
        String methodName = "add" + Utils.capitalize(parts[1]) + Utils.capitalize(parts[0]) + "Constraint";
        
        try {
            getClass().getMethod(methodName).invoke(this);
        }
        catch(InvocationTargetException e) {
            throw e.getCause();
        }
    }
    
    public void addMaxDistanceConstraint() {        
        int distance = readInt("Enter the maximum distance measured in cm");
        String[] furniture = selectFurniturePair();
        
        constraintController.addMaxDistanceConstraint(furniture[0], furniture[1], distance);
    }
    
     public void addMinDistanceConstraint() {        
        int distance = readInt("Enter the minimum distance measured in cm");
        String[] furniture = selectFurniturePair();
        
        constraintController.addMinDistanceConstraint(furniture[0], furniture[1], distance);
    }
    
    public void addPartialFacingConstraint() {
        String[] furniture = selectFurniturePair();
        
        constraintController.addPartialFacingConstraint(furniture[0], furniture[1]);
    }
    
    public void addStraightFacingConstraint() {
        String[] furniture = selectFurniturePair();
        
        constraintController.addStraightFacingConstraint(furniture[0], furniture[1]);
    }
    
    private String[] selectFurniturePair() {
        String[] furn = new String[2];
        
        furn[0] = readString("Enter the two pieces of furniture of the constraint:");
        furn[1] = readString("");
        
        return furn;
    }
    
    @Command("Remove an applied constraint")
    public void remove() {
        String alias = readChoice("Specify the alias of the constraint you want to remove:",
                constraintController.getAvailableConstraints());
        
        String[] furniture = selectFurniturePair();
        
        constraintController.removeConstraint(furniture[0], furniture[1], alias);
    }
    
    @Command("List binary constraints applied to a piece of furniture")
    public void list() {
        String furn = readString("Select the furniture whose constraints you want to show");
        Collection constraints = constraintController.getConstraints(furn);
        
        println("List of constraints defined for " + furn);
        print(constraints);
        
        if(constraints.isEmpty())
            println("There are no constraints defined for " + furn);
    }
}
