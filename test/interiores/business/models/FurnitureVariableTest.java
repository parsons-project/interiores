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
        menu();            
        int i;
        while (true) {
            cin >> i;
            switch (i) {
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
                case 6:  monthString = "June";
                         break;
                case 7:  monthString = "July";
                         break;
                case 8:  monthString = "August";
                         break;
                case 9:  monthString = "September";
                         break;
                case 10: monthString = "October";
                         break;
                case 11: monthString = "November";
                         break;
                case 12: monthString = "December";
                         break;
                default: monthString = "Invalid month";
                         break;
            }
        }
    }

    private void menu() {
        cout << "FurnitureVariableDriver" << endl;
        cout << "Options:" << endl;
        cout << "1) Define the variable that will be used in tests" << endl;
        cout << "2) getNextDomainValue() and hasMoreValues()" << endl;
        cout << "3) assignValue(Value value)" << endl;
        cout << "4) undoAssignValue()" << endl;
        cout << "5) trimDomain(Variable variable, int iteration)" << endl;    
        cout << "6) undoTrimDomain(Variable variable, Value value, int iteration)" << endl;    
        cout << "7) isAssigned()" << endl;
        cout << "8) getAssignedValue()" << endl;
        cout << "9) resetIterators(int iteration)" << endl;
        cout << "10) exit" << endl;
    }
    
    private void getNextDomainValueTester() {
        cout << "The initial state of the variable is this: " << endl;
        variable.toString();
        cout << "We will check that hasMoreValues() and getNextDomainValue work properly by iterating through the domain with them and displaying all the values" << endl;
        
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
        
    }
    
    
    
    
    public class ModelStub extends FurnitureModel {
        public String name;
    }
    
    public class PointStub extends Point {
        public int x;
        public int y;
    }}
}


