
import interiores.shared.backtracking.VariableSet;
import interiores.shared.backtracking.Value;

// UNCONTINUED CLASS !!!!!!!!!!1

/**
* GeneralSolver deals with finding a solution to a Constraint
* Satisfaction Problem materialized by the set of variables it
* is initialized with . A solution has been found when each
* variable has been assigned a value from its domain , and no
* restriction is violated .
*/
public class GeneralSolver {

    private VariableSet variableSet;

    /**
    * Constructor .
    * @param variableSet The VariableSet where assign values.
    */
    public GeneralSolver (VariableSet variableSet) {
        this.variableSet = variableSet;
    }




    private void backtracking(int depth) throws NoSolutionException {
            if (variableSet.allAssigned()) return;

            else {
                    variableSet.setActualVariable();
                    while (variableSet.actualHasMoreValues()) {
                            Value value = variableSet.getNextActualDomainValue();
                            if (variableSet.canAssignToActual(value)) {
                                    variableSet.assignToActual(value);
                                    variableSet.trimDomains(); 		

                                    backtracking(depth+1);

                                    variableSet.undoAssignToActual();
                                    variableSet.undoTrimDomains(value);
                            }
                    }
            }
            if (depth == 0) throw NoSolutionException;
    }

}