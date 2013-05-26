package interiores.business.models;

import interiores.core.business.BusinessException;


/**
 * This enumeration class encapsulates orientations. It has 4 possible values
 * N (North), S (South), E (East), W (West).
 * 
 * @author alvaro
 */
public enum Orientation {
    
    N(0), E(1), S(2), W(3);
    
    private final int direction;

    private Orientation(int direction) {
        this.direction = direction;
    }

    /**
     * Returns the immediate orientation to the left of the caller.
     * @param None
     * @return The orientation of the left of the implicit parameter
     */
    public Orientation rotateLeft() {
	// Java modulus doesn't work as math defines it
	int newdir = (this.direction - 1 % 4);
	if (newdir < 0) newdir += 4;
        return Orientation.values()[newdir];
    }
    
    /**
     * Returns the immediate orientation to the right of the caller.
     * @param None
     * @return The orientation of the right of the implicit parameter
     */
    public Orientation rotateRight() {
        return Orientation.values()[(this.direction + 1) % 4];
    }

    /**
     * Returns the number of turns to the right that we must do to be facing
     * orientation.
     * @param orientation Other orientation
     * @return An int representing the number of turns to the right.
     */
    public int distanceRight(Orientation orientation) {
        // We get the number of left turns we must do to reach orientation
        int rightTurns = orientation.direction - this.direction;
        return rightTurns < 0 ? rightTurns + 4 : rightTurns;
    }
    
    /**
     * Returns the number of turns to the left that we must do to be facing
     * orientation.
     * @param orientation Other orientation
     * @return An int representing the number of turns to the left.
     */
    public int distanceLeft(Orientation orientation) {
        int leftTurns = this.direction - orientation.direction;
        return leftTurns < 0 ? leftTurns + 4 : leftTurns;
    }
    
    /**
     * Returns the opposite orientation of the caller.
     * @param None
     * @return The orientation in front of the caller.
     */
    public Orientation complementary() {
        return Orientation.values()[(this.direction + 2) % 4];
    }
    
    @Override
    public String toString() {
        return this.toString(false);
    }
    
    public String toString(boolean longName) {
        switch(this) {
            case N:
                return longName ? "North": "N";
            case E:
                return longName ? "East": "E";
            case S:
                return longName ? "South": "S";
            case W:
                return longName ? "West": "W";
            default:
                return "Error"; // This should never happen.
        }
    }
    
    public static Orientation getEnum(String name) {
        for(Orientation o : values())
            if(o.name().equalsIgnoreCase(name))
                return o;
        
        throw new BusinessException("There is no orientation known as: " + name);
    }

}
