package interiores.presentation.terminal.commands;

import interiores.business.controllers.GlobalConstraintController;
import interiores.business.models.constraints.room.GlobalConstraint;
import interiores.core.Utils;
import interiores.core.presentation.terminal.AdvancedCommandGroup;
import interiores.core.presentation.terminal.annotation.Command;
import interiores.core.presentation.terminal.annotation.CommandSubject;
import interiores.utils.CoolColor;
import interiores.utils.Material;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 *
 * @author alvaro
 */
@CommandSubject(name = "gc", description = "Global-constraint related commands")
public class GlobalConstraintCommands
    extends AdvancedCommandGroup
{        
    private GlobalConstraintController constraintController;
    
    public GlobalConstraintCommands(GlobalConstraintController constraintController) {
        this.constraintController = constraintController;
    }
    
    @Command("Add a global constraint to the room")
    public void add()
            throws Throwable
    {
        String type = readChoice("Specify the alias of the constraint you want to add",
                constraintController.getAvailableConstraints());
        
        if (type.equals("budget")) addBudgetConstraint();
        else {
            String[] parts = type.split("-", 2);
            String methodName = "add" + Utils.capitalize(parts[0]) + Utils.capitalize(parts[1]) + "Constraint";
            
            try {
                getClass().getMethod(methodName).invoke(this);
            }
            catch(InvocationTargetException e) {
                throw e.getCause();
            }
        }
    }
    
    public void addBudgetConstraint() {
        float maxPrice = readFloat("Enter the maximum price of the design");
        constraintController.addBudgetConstraint(maxPrice);
    }
    
    public void addSameColorConstraint() {
        constraintController.addSameColorConstraint();
    }
    
    public void addSameMaterialConstraint() {
        constraintController.addSameMaterialConstraint();
    }
    
    public void addSpecificColorConstraint() {
        String color = readString("Enter the color that all the elements should have\n"+
                                  CoolColor.getNames());
        constraintController.addSpecificColorConstraint(CoolColor.valueOf(color).getColor());
    }
    
    public void addSpecificMaterialConstraint() {
        String material = readString("Enter the material that all the elements should have\n"+
                                     Material.getNames());
        constraintController.addSpecificMaterialConstraint(Material.valueOf(material));
    }
    
    @Command("Remove a given global constraint")
    public void remove() {
        String alias = readChoice("Specify the alias of the constraint you want to remove:",
                constraintController.getAvailableConstraints());
        
        constraintController.removeGlobalConstraint(alias);
    }
    
    @Command("List all the global constraints applied to the room")
    public void list() { 
        
        Collection<GlobalConstraint> constraints = constraintController.getConstraints();

        
        if(constraints.isEmpty())
            println("There are no global constraints defined for this design");
        else {
            println("List of constraints defined for this design");
            print(constraintController.getConstraints());
        }
    }   
}
