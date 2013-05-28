/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.terminal.commands;

import interiores.business.controllers.DesignController;
import interiores.business.models.room.elements.WantedFurniture;
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
   @CommandOptions({"debug", "time"})
   public void solve(Options options) {
       if(options.isEnabled("debug"))
           println("Solving in debug mode");
       
       designController.solve(options.isEnabled("debug"), options.isEnabled("time"));
   }
   
   @Command("Show the last design")
   public void show() {
       if(designController.isSolving())
           throw new BusinessException("The solver is still trying to find a solution...");
       
       if(designController.isSolutionFound()) {
           for(WantedFurniture wf : designController.getDesignFurniture())
               println(wf.toString());
       }
       else
           println("No solution found :(");
   }
}
