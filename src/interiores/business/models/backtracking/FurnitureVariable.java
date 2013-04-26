/**
* FurnitureVariable takes the role of variable in the context of the Constraint
* Satisfaction Problem (CSP) for the room design. A variable in this context
* involves a domain of values and, optionally, an assigned value.
*/
public class FurnitureVariable
	extends Variable
{
	private Value assignedValue;
	
	/**
	* This vector of lists contains all models available for this furniture type.
	* The vector's size is the amount of variables in the CSP.
	* Each vector position corresponds to an iteration of the algorithm.
	* For an iteration level i,
	* all models available at this stage are listed in DomainModels[i].
	* Consequent positions of the vector are empty lists. DomainModels[j],
	* with j < i, contain all models which were available up to algorithm
	* iteration j.
	*/
	List<Model>[] DomainModels;
	
	/**
	* This vector contains, for each iteration of the algorithm,
	* a map indicating for all discrete position of the room if they are
	* banned or allowed for this specific variable.
	*/
	BitMap[] PositionMap;
	
	/**
	* Orientation: not included so far
	* 
	*/
}
	
	
	
	