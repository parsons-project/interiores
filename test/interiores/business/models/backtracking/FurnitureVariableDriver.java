//UNFINISHED!! XD

package interiores.business.models.backtracking;

import interiores.shared.backtracking.Value;

/**
 *
 * @author nil.mamano
 */
public class FurnitureVariableDriver {

    FurnitureVariable variable;
    
    public void main() {  
        introduction();
        variable = readFurnitureVariable();
        boolean exit = false;
        while (!exit) {
            menu();
            int option;
            cin >> option);
            switch (option) {
                case 0:  System.out.println(variable.toString());
                
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
        System.out.println("FurnitureVariableDriver" , endl);
        System.out.println("Thorough the execution of this driver, we will have an object of the type FurnitureVariable which will be the target of all test funcition calls, and will evolve along with them. This is required to be able to test every possible state of a FurnitureVariable object, as some states can only be reached through a sequence of calls to their methods. There are options in the menu to display or set the state of this object" , endl);
    }
    
    private void menu() {
        System.out.println("Options:" , endl);
        System.out.println("0) See the state of the variable used in all tests"
        System.out.println("1) Define the variable that will be used in all tests" , endl);
        System.out.println("2) getNextDomainValue() and hasMoreValues() and resetIterators()" , endl);
        System.out.println("3) assignValue(Value value)" , endl);
        System.out.println("4) undoAssignValue()" , endl);
        System.out.println("5) trimDomain(Variable variable, int iteration)" , endl);    
        System.out.println("6) undoTrimDomain(Variable variable, Value value, int iteration)" , endl);    
        System.out.println("7) exit" , endl);
    }
    
    private void getNextDomainValueTester() {
        System.out.println("These 3 functions serve the same purpose: iterating through the domain values of the variable for the current iteration. Thus, they are tested together." , endl);
        System.out.println("The initial state of the variable is this: " , endl);
        System.out.println(variable.toString());
        System.out.println("We will check that the functions work properly by iterating through the domain with them and displaying all the values" , endl);
        
        variable.resetIterators();
        while (variable.hasMoreValues()) {
            Value value = variable.getNextDomainValue();
            System.out.println(value.toString());
        }
    }
                
    private void assignValueTester() {
        System.out.println("The initial state of the variable is this: " , endl);
        variable.toString();
        System.out.println("Introduce the value to be assigned to the variable" , endl);
        Value value = readFurnitureValue();
        System.out.println("Calling method assignValue(" , value , ")" , endl);
        variable.assignValue(value);
        System.out.println("The final state of the variable is this: " , endl);
        variable.toString();
    }
        
    private void undoAssignValueTester() {
        System.out.println("The initial state of the variable is this: " , endl);
        variable.toString());
        System.out.println("Calling method undoAssignValue()" , endl);
        variable.undoAssignValue();
        System.out.println("The final state of the variable is this: " , endl);
        variable.toString());
    }
    
    private void trimDomainTester() {
        System.out.println("Introduce the FurnitureVariable that will be passed as parameter to the trimDomain call" , endl);
        FurnitureVariable parameterVariable = readFurnitureVariable();
        System.out.println("Now introduce the value that this variable should have assigned" , endl);
        FurnitureValue value = readFurnitureValue();
        System.out.println("Now introduce the \"iteration\" parameter. Remember that the iteration parameter has to fulfill the following precondition: \"if trimDomain or undoTrimDomain has already been called once, \"iteration\" value must be related to the value of \"iteration\" of the previous call (+1 if it was a trimDomain or equal if it was a undoTrimDomain). Otherwise, it must be 0." , endl);
        int iteration;
        cin >> iteration);
        System.out.println("The initial state of the variable is this: " , endl);
        variable.toString());
        System.out.println("Now we make the trimDomain(furnitureVariable, iteration) call" , endl);
        variable.trimDomain(parameterVariable, iteration);
        System.out.println("The final state of the variable is this: " , endl);
        variable.toString());
    }
    
    private void undoTrimDomainTester() {
        //TO DO
    }    
  
}


