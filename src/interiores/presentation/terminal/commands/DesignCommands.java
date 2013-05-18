/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.terminal.commands;

import interiores.business.controllers.DesignController;
import interiores.business.exceptions.ElementNotFoundBusinessException;
import interiores.business.exceptions.NoRoomCreatedException;
import interiores.business.exceptions.WantedElementNotFoundException;
import interiores.core.Options;
import interiores.core.business.BusinessException;
import interiores.core.presentation.terminal.AdvancedCommandGroup;
import interiores.core.presentation.terminal.annotation.Command;
import interiores.core.presentation.terminal.annotation.CommandOptions;
import interiores.core.presentation.terminal.annotation.CommandSubject;

/**
 *
 * @author alvaro
 */
@CommandSubject(name = "design", description = "Design related commands")
public class DesignCommands extends AdvancedCommandGroup {
   private DesignController designController;
   
   public DesignCommands(DesignController designController) {
       this.designController = designController;
   }
   
   @Command("Generate a valid design for the room")
   @CommandOptions("debug")
   public void solve(Options options)
           throws BusinessException
   {
       if(options.isEnabled("debug")) {
           println("Solving in debug mode");
           designController.debug();
       }
       else {
           designController.solve();
       }
       
       if (designController.hasSolution()) {
           println(designController.getDesign());
       }
       else {
           println("Solution not found");
       }
   }
    
}
