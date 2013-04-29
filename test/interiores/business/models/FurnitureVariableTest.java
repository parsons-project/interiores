//UNFINISHED!! XD

package interiores.business.models;

import interiores.business.models.backtracking.FurnitureVariable;
import interiores.shared.backtracking.Value;

/**
 *
 * @author nil.mamano
 */
public class FurnitureVariableTest {

    FurnitureVariable variable;
    
    void main() {  
        boolean final = false;
        introduction();
        variable = readFurnitureVariable();
        int i;
        while (!final) {
            menu();
            cin >> i;
            switch (i) {
                case 0:  cout << variable.toString();
                
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
                case 7:  cout << "It's just a getter, doesn't need testing" << endl;
                         break;
                case 8:  cout << "It's just a getter, doesn't need testing" << endl;
                         break;
                case 9: final = true;
                         break;
                default: cout << "Invalid option";
                         break;
            }
        }
    }

    
    private void introduction() {
        cout << "FurnitureVariableDriver" << endl;
        cout << "Thorough the execution of this driver, we will have an object of the type FurnitureVariable which will be the target of all test funcition calls, and will evolve along with them. This is required to be able to test every possible state of a FurnitureVariable object, as some states can only be reached through a sequence of calls to their methods. There are options in the menu to display or set the state of this object" << endl;
    }
    
    private void menu() {
        cout << "Options:" << endl;
        cout << "0) See the state of the variable used in all tests"
        cout << "1) Define the variable that will be used in all tests" << endl;
        cout << "2) getNextDomainValue() and hasMoreValues() and resetIterators()" << endl;
        cout << "3) assignValue(Value value)" << endl;
        cout << "4) undoAssignValue()" << endl;
        cout << "5) trimDomain(Variable variable, int iteration)" << endl;    
        cout << "6) undoTrimDomain(Variable variable, Value value, int iteration)" << endl;    
        cout << "7) isAssigned()" << endl;
        cout << "8) getAssignedValue()" << endl;
        cout << "9) exit" << endl;
    }
    
    private void getNextDomainValueTester() {
        cout << "These 3 functions serve the same purpose: iterating through the domain values of the variable for the current iteration. Thus, they are tested together." << endl;
        cout << "The initial state of the variable is this: " << endl;
        cout << variable.toString();
        cout << "We will check that the functions work properly by iterating through the domain with them and displaying all the values" << endl;
        
        variable.resetIterators();
        while (variable.hasMoreValues()) {
            Value value = variable.getNextDomainValue();
            cout << value.toString();
        }
    }
                
    private void assignValueTester() {
        cout << "The initial state of the variable is this: " << endl;
        variable.toString();
        cout << "Introduce the value to be assigned to the variable" << endl;
        Value value = readFurnitureValue();
        cout << "Calling method assignValue(" << value << ")" << endl;
        variable.assignValue(value);
        cout << "The final state of the variable is this: " << endl;
        variable.toString();
    }
        
    private void undoAssignValueTester() {
        cout << "The initial state of the variable is this: " << endl;
        variable.toString();
        cout << "Calling method undoAssignValue()" << endl;
        variable.undoAssignValue();
        cout << "The final state of the variable is this: " << endl;
        variable.toString();
    }
    
    private void trimDomainTester() {
        cout << "Introduce the FurnitureVariable that will be passed as parameter to the trimDomain call" << endl;
        FurnitureVariable parameterVariable = readFurnitureVariable();
        cout << "Now introduce the value that this variable should have assigned" << endl;
        FurnitureValue value = readFurnitureValue();
        cout << "Now introduce the \"iteration\" parameter. Remember that the iteration parameter has to fulfill the following precondition: \"if trimDomain or undoTrimDomain has already been called once, \"iteration\" value must be related to the value of \"iteration\" of the previous call (+1 if it was a trimDomain or equal if it was a undoTrimDomain). Otherwise, it must be 0." << endl;
        int iteration;
        cin >> iteration;
        cout << "The initial state of the variable is this: " << endl;
        variable.toString();
        cout << "Now we make the trimDomain(furnitureVariable, iteration) call" << endl;
        variable.trimDomain(parameterVariable, iteration);
        cout << "The final state of the variable is this: " << endl;
        variable.toString();
    }
    
    private void undoTrimDomainTester() {
        //TO DO
    }    
  
}


