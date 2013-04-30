/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interiores.presentation.terminal;

import interiores.business.controllers.DesignController;
import interiores.core.presentation.terminal.CommandGroup;

/**
 *
 * @author alvaro
 */
public class DesignCommands extends CommandGroup {
   private DesignController designController;
   
   public DesignCommands(DesignController designController) {
       this.designController = designController;
   }
   
   public void solve() {
       designController.solve();
       if (designController.hasSolution()) {
           println(designController.getDesign());
       }
       else {
           println("Solution not found");
       }
       
   }
    
}
