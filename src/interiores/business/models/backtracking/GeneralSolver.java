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
* @param variableSet The VariableSet where assign values .
*/
public GeneralSolver ( VariableSet variableSet ) {
	this.variableSet = variableSet;
}

/**
* Tries to find a solution , that is , a value for each
* variable such that no restriction is violated .
* @throws NoSolutionException if not found a solution .
*/
public void solve ( void ) throws NoSolutionException {
	backtracking(0);
}


private void backtracking(int profundidad) throws NoSolutionException {
	if (variableSet.allAssigned()) return;

	else {
		Variable variable = variableSet.next();
		variable.sortDomainValues();//////////////
		while (variable.hasMoreValues()) {
			Value value = variable.getNextDomainValue();
			if (variableSet.canPlace(value)) {
				var.valorAsignado = val;
				graella.place(val); 			//colocalo en la graella
				cjtVar.trimDomains(val); 		//dado la nueva asignacion, reStringir todo lo posible
											//los dominios de las variables que quedan por asignar
													
				backtracking(profundidad+1);	//llamada recursiva para intentar asignar las variables restantes
			
											//si llega aqui, es que asignando val a var, no hay solucion
				cjtVar.undoTrimDomains(val);	//deshacer cambios
				graella.remove(val);			//deshacer cambios
				var.unsetValue();	//deshacer cambios
			}
		}
	}

	if (profundidad == 0) throw NoSolutionException;			//si ningun valor del dominio de la primera variable lleva a una solucion, no hay
}

}