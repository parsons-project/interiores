/**
* FurnitureValue takes the role of value in the context of the Constraint
* Satisfaction Problem for the room design. A value in this context involves
* a certain furniture model, placed in a certain position with a given
* orientation.
*/
public class FurnitureValue
	extends Value
{
	/* Represents the position of the top left corner of the furniture piece */
	private Position position;
	
	/* Represents the orientation of the furniture piece. It can be either 'n'
	* for north, 'w' for west, 's' for south or 'e' for east.
	*/
	private char orientation;
	
	private Model model;
	
	public Value (Position p, char orientation, Model m) {
		this->position = p;
		this->orientation = orientation;
		this->model = m;
	}
}