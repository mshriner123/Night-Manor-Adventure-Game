/**
 * Edge contains methods to construct an Edge, set this edge's reverse edge, close this edge's passage, set locked, set open, 
 * set the key that unlocks and locks this edge, get the direction of this edge, and get this edge's reverse edge. See 
 * the method headers below for more details.
 * 
 * @author Michael Shriner
 */
public class Edge {

	boolean isLocked;
	Room startRoom;
	Room destRoom;
	boolean isOpen;
	String aDirection = "";
	Key aKey;
	Edge theReverse;
	boolean passage;

	/**
	 *INTENT: Constructs an Edge.
	 *
	 *SHORTHAND (start room): The start room is the room that this player moves from to arrive at the destination room.
	 *
	 *SHORTHAND (destination room): The destination room is the room that this player arrived at by moving in the direction of the 
	 *edge from the start room.
	 *
	 *SHORTHAND (locked status): Locked status is true if and only if this edge is locked; otherwise, it is false.
	 *
	 *SHORTHAND (open status): Open status is true if and only if this edge is open; otherwise, it is false.
	 *
	 *SHORTHAND (passage status): Passage status is true if and only if this edge offers a passage to this player; otherwise,
	 *it is false.
	 *
	 *PRECONDITION 1 (one direction): Each edge has one direction of travel (e.g., a start room, end room, and direction from
	 *start to end).
	 *
	 *PRE 2 (locked or open): An edge cannot be both locked and open. 
	 *
	 *PRE 3 (direction): aDirection equals "go north", "go south", "go east", or "go west".
	 *
	 *POSTCONDITION 1 (constructed): An Edge was constructed with a start room, destination room, direction, locked status, 
	 * open status, and passage status.
	 */
	public Edge (Room room, Room secondRoom, boolean locked, boolean open, String direction) {	
		setLocked(locked);
		startRoom = room;
		destRoom = secondRoom;
		setOpen(open);
		aDirection = direction;	
		passage = true;
	}

	/**
	 * INTENT: Sets the reverse edge.
	 * 
	 * SHORTHAND (reverse edge): A reverse edge is the edge that runs from this edge's destination room to start room
	 * (e.g. the reverse of an edge that has start room1, destination room2, and direction north is an edge that has 
	 * start room2, destination room1, and direction south).
	 * 
	 * POSTCONDITION: theReverse was assigned anEdge.
	 */
	public void setReverse(Edge anEdge) {
		theReverse = anEdge;
	}

	/**
	 * INTENT: Closes this edge's passage.
	 * 
	 * PRECONDITION (no passage): There is no passage for this edge if and only if this player
	 * cannot travel from the start room of this edge to the destination room of this edge.
	 * 
	 * POSTCONDITION: passage was assigned closed.
	 */
	public void closePassage(boolean closed) {
		passage = closed;
	}

	/**
	 * INTENT: Sets the locked status of this edge.
	 * 
	 * PRECONDITION (locked): An edge is locked if and only if a key is required to open this edge.
	 * 	 
	 * POSTCONDITION: isLocked was assigned locked.
	 */
	public void setLocked(boolean locked) {
		isLocked = locked;
	}

	/**
	 * INTENT: Sets the open status of this edge. 
	 * 
	 * PRECONDITION (opened): An edge is open if and only if it is not locked.
	 * 
	 * POSTCONDITION: isOpen was assigned open.
	 */
	public void setOpen(boolean open) {
		isOpen = open;
	}

	/**
	 * INTENT: Sets the key for this edge.
	 * 
	 * SHORTHAND (key): This edge's key is the key that is required to unlock this edge.
	 * 
	 * PRECONDITION (key required): A key is required to use an edge as a passage if and only if that edge is locked. 
	 * 
	 * POSTCONDITION: aKey was assigned key.
	 */
	public void setKey(Key key) {
		aKey = key;
	}

	/**
	 * INTENT: Returns the direction of this edge.
	 * 
	 * PRECONDITION: aDirection equals "go north", "go south", "go east", or "go west".
	 * 
	 * POSTCONDITION: aDirection was returned.
	 */
	public String getDirection() {
		return aDirection;
	}

	/**
	 *INTENT: Returns this edge's reverse edge.
	 *
	 * SHORTHAND (reverse edge): A reverse edge is the edge that runs from this edge's destination room to start room
	 * (e.g. the reverse of an edge that has start room1, destination room2, and direction north is an edge that has 
	 * start room2, destination room1, and direction south).
	 *
	 *POSTCONDITION: theReverse was returned.
	 */
	public Edge getReverse() {
		return theReverse;
	}
}
