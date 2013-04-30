//UNFINISHED!! XD

package interiores.business.models.backtracking;

import interiores.core.terminal.IOStream;

/**
 *
 * @author nil.mamano
 */
public class FurnitureVariableSetDriver {

    private static IOStream iostream = new IOStream(System.in, System.out);
    String NEW_LINE = System.getProperty("line.separator");
    
    FurnitureVariableSet variableSet;
    
    public void main() {  
        introduction();
        variableSet = readFurnitureVariableSet();
        boolean exit = false;
        while (!exit) {
            menu();
            int option = iostream.readInt();
            switch (option) {
                case 0:  System.out.println(variableSet.toString());
                         break;
                case 1:  variableSet = readFurnitureVariableSet();
                         break;
                case 2:  setActualVariableTester();
                         break;
                case 3:  trimDomainsTester();
                         break;
                case 4:  undoTrimDomainsTester();
                         break;
                case 5:  allAssignedTester();
                         break;
                case 6:  canAssignToActualTester();
                         break;
                case 7:  preliminarTrimDomainsTester();
                         break;
                case 8:  solveTester();
                         break;
                case 9:  backtrackingTester();
                         break;
                case 10: noSolutionFoundTester();
                         break;
                case 11: exit = true;
                         break;
                default: System.out.println("Invalid option");
                         break;
            }
        }
    }

    
    private void introduction() {
        System.out.println("FurnitureVariableSet Driver" + NEW_LINE + NEW_LINE);
        System.out.println("Thorough the execution of this driver, we will have");
        System.out.println(" an object of the type FurnitureVariableSet which will");
        System.out.println(" be the target of all test funcition calls, and");
        System.out.println(" will evolve along with them. This is required to");
        System.out.println(" be able to test every possible state of a");
        System.out.println(" FurnitureVariable object, as some states can only");
        System.out.println(" be reached through a sequence of calls to private");
        System.out.println(" methods. There are options in the menu to display");
        System.out.println(" or set the state of this object." + NEW_LINE);
        System.out.println("If the preconditions of a method are not met, the");
        System.out.println(" result is unspecified." + NEW_LINE);
    }
    
    private void menu() {
        System.out.println("Options:" + NEW_LINE);
        System.out.println("0) See the state of the variable used in all tests" + NEW_LINE);
        System.out.println("1) Define the variable that will be used in all tests" + NEW_LINE);
        System.out.println("2) setActualVariable()" + NEW_LINE);
        System.out.println("3) trimDomains()" + NEW_LINE);
        System.out.println("4) undoTrimDomains(Value value)" + NEW_LINE);
        System.out.println("5) allAssigned()" + NEW_LINE);    
        System.out.println("6) canAssignToActual(Value value)" + NEW_LINE);    
        System.out.println("7) preliminarTrimDomains()" + NEW_LINE);
        System.out.println("8) solve()" + NEW_LINE);
        System.out.println("9) backtracking()" + NEW_LINE);
        System.out.println("10) noSolutionFound()" + NEW_LINE);
        System.out.println("11) exit" + NEW_LINE);
    }
    
    private void setActualVariableTester() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void trimDomainsTester() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void undoTrimDomainsTester() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void allAssignedTester() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void canAssignToActualTester() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void preliminarTrimDomainsTester() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void solveTester() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void backtrackingTester() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void noSolutionFoundTester() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
  
}


