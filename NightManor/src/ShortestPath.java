/**
 * ShortestPath implements Comparable and represents the shortest path from a start room to an end room. ShortestPath objects are 
 * used in the implementation of dijkstras algorithm in Map.java. ShortestPath contains set and get methods for its data members, which are 
 * the name of the room that this path leads to, the length of this path, a String representation of this path, and the room
 * number of the room that this path leads to. It also contains a compareTo method that defines the order of the priority queue that 
 * contains ShortestPath objects in Map.java. More details about the methods in this class can be found in their method headers.
 * 
 * @author Michael Shriner
 */
public class ShortestPath implements Comparable <ShortestPath>{

	private String aName;
	private int pathLength;
	private String aPath = "";
	private int aRoomNum;


	/**
	 * INTENT: Calls set methods to initialize the data members of this class.
	 * 
	 * SHORTHAND(RS): RS equals rooms.size() (an ArrayList in Room.java), which is the number of Room objects.
	 * 
	 * PRECONDITION 1 (valid path length): pathL >= 0 AND pathL < RS.
	 * 
	 * PRE 2 (valid room number): roomNum >=0 AND roomNum < RS.
	 * 
	 * PRE 3 (valid name): name is the name of a Room object in ArrayList rooms in Room.java.
	 * 
	 * POSTCONDITION: The set methods were called to initialize the data member to the parameter values.
	 */
	public ShortestPath(String name, int pathL, String path, int roomNum) {
		setName(name);
		setPathLength(pathL);
		setPathString(path);	
		setRoomNumber(roomNum);
	}

	/**
	 * INTENT: Sets the name of the room that this path leads to to the parameter value.
	 * 
	 * PRECONDITION: aName is the name of a Room object.
	 * 
	 * POSTCONDITION: The name of the room that this path leads to was set to the parameter value.
	 */
	public void setName(String name) {
		aName = name;
	}

	/**
	 * INTENT: Sets the length of this path to the parameter value.
	 * 
	 * SHORTHAND(RS): RS equals rooms.size() (an ArrayList in Room.java), which is the number of Room objects.
	 * 
	 * PRECONDITION: pathL >= 0 AND pathL < RS.
	 * 
	 * POSTCONDITION: The length of this path was set to the parameter value.
	 */
	public void setPathLength(int pathL) {		
		pathLength = pathL;
	}

	/**
	 * INTENT: Appends rooms to aPath to form a String representation of this path.
	 * 
	 * POSTCONDITION: The parameter path was appended to aPath.
	 */
	public void setPathString (String path) {		
		aPath += path;
	}

	/**
	 * INTENT: Sets the room number of the room that this path leads to.
	 * 
	 * SHORTHAND(RS): RS equals rooms.size()(an ArrayList in Room.java), which is the number of Room objects.
	 * 
	 * PRECONDITION: roomNum >=0 AND roomNum < RS.
	 * 
	 * POSTCONDITION: aRoomNum was set to the parameter roomNum.
	 */
	public void setRoomNumber (int roomNum) {
		aRoomNum = roomNum;
	}

	/**
	 * INTENT: Returns the name of the room that this path leads to.
	 * 
	 * POSTCONDITION: aName was returned.
	 */
	public String getName() {
		return aName;
	}

	/**
	 * INTENT: Returns the length of this path.
	 * 
	 * POSTCONDITION: pathLength was returned.
	 */
	public int getPathLength() {
		return pathLength;
	}

	/**
	 * INTENT: Returns this path.
	 * 
	 * POSTCONDITION: aPath was returned.
	 */
	public String getPath() {
		return aPath;
	}

	/**
	 * INTENT: Returns the room number of the room that this path leads to.
	 * 
	 * POSTCONDITION: aRoomNum was returned.
	 */
	public int getRoomNumber() {
		return aRoomNum;
	}

	/**
	 * INTENT: Compares two ShortestPath objects.
	 * 
	 * SHORTHAND (greater than): This ShortestPath is greater than the parameter ShortestPath if and only if 
	 * the path length of this ShortestPath is greater than the path length of the parameter ShortestPath.
	 * 
	 * SHORTHAND (less than): This ShortestPath is less than the parameter ShortestPath if and only if 
	 * the path length of this ShortestPath is less than the path length of the parameter ShortestPath.
	 * 
	 * POSTCONDITION: This ShortestPath was less than the parameter ShortestPath, and -1 was returned, XOR
	 * this ShortestPath was greater than the parameter ShortestPath, and 1 was returned, XOR 0 was returned.
	 */
	public int compareTo (ShortestPath obj) {

		if (this.pathLength < obj.pathLength) {
			/*
			 * State 1 (less than): This ShortestPath is less than the parameter ShortestPath, and -1 is returned, XOR
			 * this ShortestPath is not less than the parameter ShortestPath.
			 */
			return -1;
		}
		else {
			if (this.pathLength > obj.pathLength) {
				/*
				 * State 2 (greater than): This ShortestPath is greater than the parameter ShortestPath, and 1 is returned, XOR
				 * this ShortestPath is equal to the parameter ShortestPath.
				 */
				return 1;
			}
			else {
				/*
				 * State 3 (equal): 0 is returned.
				 */
				return 0;
			}
		}	
	}//end of compareTo method

}