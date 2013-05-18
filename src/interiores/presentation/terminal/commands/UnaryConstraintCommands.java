package interiores.presentation.terminal.commands;

import interiores.business.controllers.UnaryConstraintController;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.exceptions.WantedElementNotFoundException;
import interiores.business.models.Orientation;
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
@CommandSubject(name = "uc", description = "Unary-constraint related commands")
public class UnaryConstraintCommands extends AdvancedCommandGroup {
    private static final String PATTERN_SIZE_TYPE = "^(width|depth)$";
    private static final String PATTERN_SIMPLE_TYPE = "^(color|material|model|orientation)$";
    
    private UnaryConstraintController constraintController;
    
    public UnaryConstraintCommands(UnaryConstraintController constraintController) {
        this.constraintController = constraintController;
    }
    
    @Command("Add a constraint to selected furniture")
    public void add()
            throws Throwable
    {
        String type = readChoice("Specify the alias of the constraint you want to add:",
                constraintController.getAvailableConstraints());
        
        try {
            if(type.matches(PATTERN_SIMPLE_TYPE))
                addSimpleConstraint(type);

            else if(type.matches(PATTERN_SIZE_TYPE))
                addSizeConstraint(type);

            else
                getClass().getMethod("add" + Utils.capitalize(type) + "Constraint").invoke(this);
        }
        catch(InvocationTargetException e) {
            throw e.getCause();
        }
    }
    
    public void addSimpleConstraint(String type)
            throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException
    {
        String parameter = readString("Choose the " + type + " you want for this furniture");
        String furnitureId = askFurnitureId();
        
        // Using reflection to avoid duplicated ugly code
        constraintController.getClass().getMethod("add" + Utils.capitalize(type) + "Constraint",
                    String.class, String.class).invoke(constraintController, furnitureId, parameter);
    }
    
    public void addSizeConstraint(String type)
            throws NoRoomCreatedException, WantedElementNotFoundException
    {
        int min = readInt("Enter the minimum " + type + " for this furniture:");
        int max = readInt("Enter the maximum " + type + " for this furniture:");
            
        String furnitureId = askFurnitureId();
        
        if(type.equals("width"))
            constraintController.addWidthConstraint(furnitureId, min, max);
        else
            constraintController.addDepthConstraint(furnitureId, min, max);
    }
    
    
    public void addPriceConstraint()
            throws NoRoomCreatedException, WantedElementNotFoundException
    {
        float maxPrice = readFloat("Enter the maximum price you want to pay:");
        
        constraintController.addPriceConstraint(askFurnitureId(), maxPrice);
    }
    
    public void addPositionConstraint()
            throws NoRoomCreatedException, WantedElementNotFoundException
    {
        String specific = readString("Enter <at> followed by two coordinates to indicate a certain position."
                + " Enter <range> followed by four coordinates to indicate a range of positions."
                + " Enter <walls> followed by <all> or a cardinal point to cling this furniture to any"
                + " wall or to a specific one.");
        
        if(specific.equals("at")) {
            int x = readInt("");
            int y = readInt("");

            constraintController.addPositionAtConstraint(askFurnitureId(), x, y);
        }
        else if(specific.equals("range")) {
            int x1 = readInt("");
            int y1 = readInt("");
            int x2 = readInt("");
            int y2 = readInt("");
            
            constraintController.addPositionRangeConstraint(askFurnitureId(), x1, y1, x2, y2);
        }
        else if(specific.equals("walls")) {
            String whichWall = readString("");
            Orientation[] walls;
            
            if(whichWall.equals("all"))
                walls = Orientation.values();
            else
                walls = new Orientation[]{ Orientation.valueOf(whichWall) };
            
            constraintController.addWallConstraint(askFurnitureId(), walls);
        }
    }
    
    private String askFurnitureId() {
        return readString("Select the furniture to which you want to apply the constraint");
    }
    
    @Command("Remove an applied constraint")
    public void remove()
            throws NoRoomCreatedException, BusinessException
    {
        String ctype = readChoice("Specify the alias of the constraint you want to remove:",
                constraintController.getAvailableConstraints());
        String furn = readString("Select the furniture piece from which to remove the constraint");
        
        constraintController.remove(ctype, furn);
    }
    
    @Command("List constraints applied to some selected furniture")
    public void list()
            throws NoRoomCreatedException, WantedElementNotFoundException
    {
        String furn = readString("Select the furniture whose constraints you want to show");
        Collection constraints = constraintController.getConstraints(furn);
        
        println("List of constraints defined for " + furn);
        print(constraints);
        
        if(constraints.isEmpty())
            println("There are no constraints defined for " + furn);
    }
}
