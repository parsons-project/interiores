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
    
    public void add() throws ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        String type = readString("Specify the kind of constraint you want to add");
        
        List<Object> parameters = new ArrayList();
        if (type.equals("color") || type.equals("material") || type.equals("orientation"))
            parameters.add(readString("Choose the " + type + " you want for this furniture"));
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
            parameters.add(specific);
            if (specific.equals("at")) { parameters.add(readInt("")); parameters.add(readInt("")); }
            else if (specific.equals("range")) {
                parameters.add(readInt("")); parameters.add(readInt(""));
                parameters.add(readInt("")); parameters.add(readInt(""));
            }
            else if (specific.equals("walls")) parameters.add(readString(""));
        }
        
        String furnitureID = readString("Select the furniture to which you want to apply the constraint");
        
        constraintController.add(type,parameters,furnitureID);
        
    }
    
    public void remove() {
        String ctype = readString("Specify the kind of constraint you want to remove");
        String furnitureID = readString("Select the furniture from which you want to remove the constraint");
        
        constraintController.remove(ctype,furnitureID);
    }
    
    public void list() {
        String furn = readString("Select the furniture whose constraints you want to show");
        Collection constraints = constraintController.getConstraints(furn);
        
        println("List of constraints defined for " + furn);
        print(constraints);
        if(constraints.isEmpty())
            println("There are no constraints defined for " + furn);
    }

}
