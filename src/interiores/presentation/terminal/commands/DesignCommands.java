/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.terminal.commands;

import interiores.business.controllers.DesignController;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.core.presentation.terminal.CommandGroup;
import interiores.core.presentation.terminal.annotation.CommandSubject;

/**
 *
 * @author alvaro
 */
@CommandSubject(name = "design", description = "Design related commands")
public class DesignCommands extends CommandGroup {
   private DesignController designController;
   
   public DesignCommands(DesignController designController) {
       this.designController = designController;
   }
   
   public void solve() throws NoRoomCreatedException {
       designController.solve();
       
       if (designController.hasSolution()) {
           println(designController.getDesign());
       }
       else {
           println("Solution not found");
       }
       
   }
    
}
