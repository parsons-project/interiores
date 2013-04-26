/**
* BitMap represents a map indicating for all discrete position of the room if they are
* banned or allowed for a specific variable.
* A banned position means that if the variable took a value with this position, a restriction would be violated.
* The banned subset of positions is exhaustive, i.e., an allowed position might violate a restriction as well.
*/
public class BitMap
{

	int RoomWidth;
	int RoomDepth;
	char bitMask[8]={1, 2, 4, 8, 16, 32, 64, 128};
	/**
	* This vector contains
	* a map indicating for all discrete position of the room if they are
	* banned (1) or not (0) for this specific variable.
	* For optimization purposes, it is done on a bit level.
	*/
	private char[] bitMap;
	
	/**
	* Constructor
	*/
	public BitMap(int RoomWidth, int RoomDepth) {
		this->RoomWidth = RoomWidth;
		this->RoomDepth = RoomDepth;
		int size = (RoomWidth*RoomDepth)/8;
		if (RoomWidth*RoomDepth%8 != 0) ++size; //for rounding purposes
		bitMap = new char[size];
	}
	
	/**
	* Returns whether the position (x,y) in the room is allowed (i.e. not banned)
	*/
	public boolean isAllowed(int x, int y) {
		int index = x*RoomWidth+y;
		int byteNumber = index/8;
		int bitNumber = index%8;
		return bitMap[byteNumber] & bitMask[bitNumber];
	}
	
	/**
	* Sets a certain position banned or allowed
	* if allowed is true, the position (x,y) is set as allowed
	* otherwise, (x,y) is set as banned
	*/
	public void setAllowed(boolean allowed, int x, int y) {
		int index = x*RoomWidth+y;
		int byteNumber = index/8;
		int bitNumber = index%8;
		if (allowed) bitMap[byteNumber] |= bitMask[bitNumber];
		else {
			char negatedBitMask = 255-bitMask[bitNumber];
			bitMap[byteNumber] &= bitMask[bitNumber];
		}
	}
}