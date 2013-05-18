/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.terminal.commands;

import interiores.business.controllers.FixedElementController;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.core.presentation.terminal.AdvancedCommandGroup;
import interiores.core.presentation.terminal.annotation.Command;
import interiores.core.presentation.terminal.annotation.CommandSubject;
import interiores.utils.Dimension;
import java.awt.Point;

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
    
    @Command("Add a fixed element to the room")
    public void add() throws NoRoomCreatedException {
        
        String typeName = readString("Enter the name of the type of the fixed element:");
        int x = readInt("Enter x coordinate:");
        int y = readInt("Enter y coordinate:");
        int width = readInt("Enter the width of the element in centimeters:");
        int depth = readInt("Enter the depth of the element in centimeters:");
        
        fixedElementController.add(typeName, new Point(x,y), new Dimension(width, depth));
    }
    
    @Command("List the current selected fixed types")
    public void selected() throws NoRoomCreatedException {
        print(fixedElementController.getFixedFurniture());
    }
}
