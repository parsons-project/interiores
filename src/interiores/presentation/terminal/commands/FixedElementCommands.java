/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.terminal.commands;

import interiores.business.controllers.FixedElementController;
import interiores.business.exceptions.MandatoryFurnitureException;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.core.business.BusinessException;
import interiores.core.presentation.terminal.AdvancedCommandGroup;
import interiores.core.presentation.terminal.annotation.Command;
import interiores.core.presentation.terminal.annotation.CommandSubject;
import interiores.utils.Dimension;
import java.awt.Point;
import java.util.Collection;

/**
 *
 * @author alvaro
 */
@CommandSubject(name = "fe", description = "Fixed Elements related commands")
public class FixedElementCommands extends AdvancedCommandGroup {
    private FixedElementController fixedElementController;
    
    public FixedElementCommands(FixedElementController fixedElementController) {
        super();
        
        this.fixedElementController = fixedElementController;
    }
    
    @Command("Add a fixed element to the current selection")
    public void add() throws NoRoomCreatedException, BusinessException {
        
        String typeName = readString("Enter the name of the type of the fixed element:");
        int x = readInt("Enter x coordinate:");
        int y = readInt("Enter y coordinate:");
        int width = readInt("Enter the width of the element in centimeters:");
        int depth = readInt("Enter the depth of the element in centimeters:");
        
        fixedElementController.add(typeName, new Point(x,y), new Dimension(width, depth));
    }
    
    @Command("Removes a fixed element from the current selection")
    public void remove() 
            throws NoRoomCreatedException, MandatoryFurnitureException {
        String name = readString("Enter the name of the element you want to remove:");
        fixedElementController.remove(name);
    }
    
    @Command("List the current selected fixed types")
    public void selected() throws NoRoomCreatedException {
        
        Collection selected = fixedElementController.getFixedFurniture();
        
        if(selected.isEmpty())
            println("You have not selected any fixed element yet");
        else {
            println("You have selected this fixed elements:");
            print(selected);
        }
    }
    
    @Command("List the possible fixed elements to be added")
    public void list() {
        Collection types = fixedElementController.getSelectable();
        print(types);
        
    }
}
