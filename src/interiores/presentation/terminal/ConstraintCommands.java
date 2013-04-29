/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.terminal;

import interiores.business.controllers.ConstraintController;
import interiores.core.presentation.terminal.CommandGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author larribas
 */
public class ConstraintCommands extends CommandGroup {
    
    private ConstraintController constraintController;
    
    public ConstraintCommands(ConstraintController constraintController) {
        this.constraintController = constraintController;
    }
    
    public void add() {
        String type = readString("Specify the kind of constraint you want to add");
        
        List<Object> parameters = new ArrayList();
        if (type.equals("color")) parameters.add(readString("Choose the color you want for this furniture"));
        else if (type.equals("material")) parameters.add(readString("Choose the material you want for this furniture"));
        else if (type.equals("orientation"))
            parameters.add(readString("Choose the direction towards which this furniture will be oriented"));
        else if (type.equals("price")) parameters.add(readInt("Enter the maximum price you want to pay"));
        else if (type.equals("width") || type.equals("depth")) {
            parameters.add(readInt("Enter a range of " + type + "s for this furniture"));
            parameters.add(readInt(""));
        }
        else if (type.equals("position")) {
            String specific = readString("Enter <at> followed by two coordinates to indicate a certain position."
                    + " Enter <range> followed by four coordinates to indicate a range of positions."
                    + " Enter <walls> followed by <all> or a cardinal point to cling this furniture to any wall"
                    + " or to a specific one.");
            
            if (specific.equals("at")) { parameters.add(readInt("")); parameters.add(readInt("")); }
            else if (specific.equals("range")) {
                parameters.add(readInt("")); parameters.add(readInt(""));
                parameters.add(readInt("")); parameters.add(readInt(""));
            }
            else if (specific.equals("wall")) parameters.add(readString(""));
        }
        
        String variable = readString("Select the variable to which you want to apply the constraint");
        
        constraintController.add(type,parameters,variable);
        
    }
    
    public void list() {
        String variable = readString("Select the variable whose constraints you want to show");
        Collection constraints = constraintController.getConstraints(variable);
        
        println("List of constraints defined for " + variable);
        print(constraints);
        if(constraints.isEmpty())
            println("There are no constraints defined for that variable");
    }

}
