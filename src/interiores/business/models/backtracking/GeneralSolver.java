//
//import interiores.shared.Variable;
//
///**
//* GeneralSolver deals with finding a solution to a Constraint
//* Satisfaction Problem materialized by the set of variables it
//* is initialized with . A solution has been found when each
//* variable has been assigned a value from its domain , and no
//* restriction is violated .
//*/
//public class GeneralSolver {
//
//    private VariableSet variableSet;
//
//    /**
//    * Constructor .
//    * @param variableSet The VariableSet where assign values .
//    */
//    public GeneralSolver ( VariableSet variableSet ) {
//            this.variableSet = variableSet;
//    }
//
//    /**
//    * Tries to find a solution , that is , a value for each
//    * variable such that no restriction is violated .
//    * @throws NoSolutionException if not found a solution .
//    */
//    public void solve ( void ) throws NoSolutionException {
//            backtracking(0);
//    }
//
//
//    private void backtracking(int depth) throws NoSolutionException {
//            if (variableSet.allAssigned()) return;
//
//            else {
//                    Variable variable = variableSet.next();
//                    while (variable.hasMoreValues()) {
//                            Value value = variable.getNextDomainValue();
//                            if (variableSet.canAssign(variable, value)) {
//                                    variable.setAssignedValue(value);
//                                    variableSet.trimDomains(variable); 		
//
//                                    backtracking(depth+1);
//
//                                    variable.unsetValue();
//                                    variableSet.undoTrimDomains(variable, value);
//                            }
//                    }
//            }
//            if (profundidad == 0) throw NoSolutionException;
//    }
//
//}