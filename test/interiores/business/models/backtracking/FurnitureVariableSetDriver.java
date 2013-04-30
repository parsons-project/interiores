
package interiores.business.models.backtracking;

import drivers.AbstractDriver;
import interiores.core.presentation.terminal.IOStream;
import interiores.shared.backtracking.NoSolutionException;

/**
 *
 * @author nil.mamano
 */
public class FurnitureVariableSetDriver extends AbstractDriver {

    private static IOStream iostream = new IOStream(System.in, System.out);
    
    private static FurnitureVariableSet variableSet;
    
    public static void main(String[] args) {  
        introduction();
        boolean exit = false;
        while (!exit) {
            menu();
            int option = iostream.readInt();
            switch (option) {
                case 0:  iostream.println(variableSet.toString());
                         break;
                case 1:  variableSet = readFurnitureVariableSet();
                         break;
                case 2: 
                    try{
                        solveTester();
                    }
                    catch (NoSolutionException nse) {
                        iostream.println("No solution Found");
                    }
                    break;
                case 3: exit = true;
                         break;
                default: iostream.println("Invalid option");
                         break;
            }
        }
    }

    
    private static void introduction() {
        iostream.println("FurnitureVariableSet Driver");
        iostream.println("");
        iostream.println("Define the VariableSet object:");
        variableSet = readFurnitureVariableSet();
    }
    
    private static void menu() {
        iostream.println("Options:");
        iostream.println("");
        iostream.println("0) See the VariableSet");
        iostream.println("1) Define a new VariableSet");
        iostream.println("2) solve()");
        iostream.println("3) exit");
        iostream.println(">> ");
    }
    
    private static void solveTester() throws NoSolutionException {
        iostream.println("Calling solve() method...");
        variableSet.solve();
        iostream.println("Done. You can check if a solution was found, and ");
        iostream.println("which, with the appropiate menu option (0)");
    }

}


