//UNFINISHED!! XD

package interiores.business.models.backtracking;

import interiores.core.terminal.IOStream;
import interiores.shared.backtracking.Value;

/**
 *
 * @author nil.mamano
 */
public class FurnitureVariableDriver {

    private static IOStream iostream = new IOStream(System.in, System.out);
    String NEW_LINE = System.getProperty("line.separator");
    
    FurnitureVariable variable;
    
    public void main() {  
        introduction();
        variable = readFurnitureVariable();
        boolean exit = false;
        while (!exit) {
            menu();
            int option = iostream.readInt();
            switch (option) {
                case 0:  System.out.println(variable.toString());
                         break;
                case 1:  variable = readFurnitureVariable();
                         break;
                case 2:  getNextDomainValueTester();
                         break;
                case 3:  assignValueTester();
                         break;
                case 4:  undoAssignValueTester();
                         break;
                case 5:  trimDomainTester();
                         break;
                case 6:  undoTrimDomainTester();
                         break;
                case 7: exit = true;
                         break;
                default: System.out.println("Invalid option");
                         break;
            }
        }
    }

    
    private void introduction() {
        System.out.println("FurnitureVariable Driver" + NEW_LINE + NEW_LINE);
        System.out.println("Thorough the execution of this driver, we will have");
        System.out.println(" an object of the type FurnitureVariable which will");
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
        System.out.println("2) getNextDomainValue() and hasMoreValues() and resetIterators()" + NEW_LINE);
        System.out.println("3) assignValue(Value value)" + NEW_LINE);
        System.out.println("4) undoAssignValue()" + NEW_LINE);
        System.out.println("5) trimDomain(Variable variable, int iteration)" + NEW_LINE);    
        System.out.println("6) undoTrimDomain(Variable variable, Value value, int iteration)" + NEW_LINE);    
        System.out.println("7) exit" + NEW_LINE);
    }
    
    private void getNextDomainValueTester() {
        System.out.println("These 3 functions serve the same purpose: iterating through the domain values of the variable for the current iteration. Thus, they are tested together." + NEW_LINE);
        System.out.println("The initial state of the variable is this: " + NEW_LINE);
        System.out.println(variable.toString());
        System.out.println("We will check that the functions work properly by iterating through the domain with them and displaying all the values" + NEW_LINE);
        
        variable.resetIterators();
        while (variable.hasMoreValues()) {
            Value value = variable.getNextDomainValue();
            System.out.println(value.toString());
        }
    }
                
    private void assignValueTester() {
        System.out.println("The initial state of the variable is this: " + NEW_LINE);
        System.out.println(variable.toString());
        System.out.println("Introduce the value to be assigned to the variable" + NEW_LINE);
        Value value = readFurnitureValue();
        System.out.println("Calling method assignValue(value)" + NEW_LINE);
        variable.assignValue(value);
        System.out.println("The final state of the variable is this: " + NEW_LINE);
        System.out.println(variable.toString());
    }
        
    private void undoAssignValueTester() {
        System.out.println("The initial state of the variable is this: " + NEW_LINE);
        System.out.println(variable.toString());
        System.out.println("Calling method undoAssignValue()" + NEW_LINE);
        variable.undoAssignValue();
        System.out.println("The final state of the variable is this: " + NEW_LINE);
        System.out.println(variable.toString());
    }
    
    private void trimDomainTester() {
        System.out.println("Introduce the FurnitureVariable that will be passed as parameter to the trimDomain call" + NEW_LINE);
        FurnitureVariable parameterVariable = readFurnitureVariable();
        System.out.println("Now introduce the value that this variable should have assigned" + NEW_LINE);
        FurnitureValue value = readFurnitureValue();
        System.out.println("Now introduce the \"iteration\" parameter. Remember that the iteration parameter has to fulfill the following precondition: \"if trimDomain or undoTrimDomain has already been called once, \"iteration\" value must be related to the value of \"iteration\" of the previous call (+1 if it was a trimDomain or equal if it was a undoTrimDomain). Otherwise, it must be 0.\"" + NEW_LINE);
        int iteration = iostream.readInt();
        System.out.println("The initial state of the variable is this: " + NEW_LINE);
        System.out.println(variable.toString());
        System.out.println("Now we make the trimDomain(furnitureVariable, iteration) call" + NEW_LINE);
        variable.trimDomain(parameterVariable, iteration);
        System.out.println("The final state of the variable is this: " + NEW_LINE);
        System.out.println(variable.toString());
    }
    
    private void undoTrimDomainTester() {
        System.out.println("Introduce the FurnitureVariable that will be passed as parameter to the undoTrimDomain call" + NEW_LINE);
        FurnitureVariable parameterVariable = readFurnitureVariable();
        System.out.println("Now introduce the value parameter" + NEW_LINE);
        FurnitureValue value = readFurnitureValue();
        System.out.println("Now introduce the \"iteration\" parameter. Remember that the iteration parameter has to fulfill the following precondition: \"trimDomain has already been called once. \"iteration\" value must be related to the value of \"iteration\" of the previous call to trimDomain or undoTrimDomain (equal if it was trimDomain or -1 if it was undoTrimDomain).\"" + NEW_LINE);
        int iteration = iostream.readInt();
        System.out.println("The initial state of the variable is this: " + NEW_LINE);
        System.out.println(variable.toString());
        System.out.println("Now we make the trimDomain(furnitureVariable, iteration) call" + NEW_LINE);
        variable.undoTrimDomain(parameterVariable, value, iteration);
        System.out.println("The final state of the variable is this: " + NEW_LINE);
        System.out.println(variable.toString());
    }    
  
}


