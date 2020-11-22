import java.util.ArrayList;
/**
 * Room contains methods to construct a Room, set the room number, set the room name, set the room's flag to unvisited, set the room's flag
 * to visited, determine whether this room's flag is visited or unvisited, add enemies, remove enemies, get the number of enemies in this room, 
 * get the room name, get the room number, and get this room's description. For more details, see the method headers below. 
 * 
 * @author Michael Shriner
 */
public class Room {

	String aRoomName;
	int aRoomNum;
	boolean multiplePassages;
	ArrayList<Enemy> enemiesInRoom;
	int numberOfEnemies = 0;
	static ArrayList <Room> rooms = new ArrayList <Room>();
	FlagValue flag;

	/**
	 * INTENT: Constructs a Room object.
	 * 
	 * PRECONDITION 1 (room number): roomNumber >= 0, and each room number corresponds to only one Room object (e.g., Room1 cannot have room
	 * number 2 if Room 2 has room number 2).
	 * 
	 * PRE 2 (passages): multiplePassages is assigned true if and only if there is more than one edge leaving this room.
	 * 
	 * PRE 3 (name): Each Room has a different room name. 
	 * 
	 * POSTCONDITION 1 (data initialized): aRoomName, aRoomNum, multiplePassages, and enemiesInRoom were initialized, and this Room 
	 * was added to rooms.
	 * 
	 * POST 2 (complement): A Room object was constructed.
	 */
	public Room (String roomName, int roomNumber, boolean passages) {
		setRoomName(roomName);
		setRoomNumber(roomNumber);
		multiplePassages = passages;
		enemiesInRoom = new ArrayList <Enemy>();
		rooms.add(this);
	}

	/**
	 * INTENT: Sets this room's room number.
	 * 
	 * PRECONDITION 1 (valid room number): aRoomNum >=0, and each room number corresponds to only one Room object  (e.g., Room1 cannot have room
	 * number 2 if Room 2 has room number 2).
	 * 
	 * POSTCONDITION 1: aRoomNum was assigned roomNumber
	 */
	public void setRoomNumber(int roomNumber) {
		aRoomNum = roomNumber;
	}

	/**
	 * INTENT: Sets this room's name. 
	 * 
	 * PRECONDITION 1: Each Room has a different name.
	 * 
	 * POSTCONDITION 1: aRoomName was assigned roomName.
	 */
	public void setRoomName(String roomName) {
		aRoomName = roomName;	
	}

	/**
	 * INTENT: Sets this room's flag to visited.
	 * 
	 * POSTCONDITION: This room's flag was set to visited.
	 */
	public void setVisited() {
		flag = FlagValue.VISITED;
	}

	/**
	 * INTENT: Sets this room's flag to unvisited.
	 * 
	 * POSTCONDITION: This room's flag was set to unvisited.
	 */
	public void setUnvisited() {
		flag = FlagValue.UNVISITED;
	}

	/**
	 * INTENT: Returns true if this Room's flag is unvisited. Otherwise, it returns false. 
	 * 
	 * POSTCONDITION: True was returned if and only if this Room's flag was unvisited, XOR false was returned.
	 */
	public boolean isUnvisited() {
		if (flag == FlagValue.UNVISITED) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * INTENT: Adds an Enemy to this Room where applicable.
	 * 
	 * PRECONDITION 1 (enemies per room): There can be at most two enemies in a room.
	 * 
	 * POSTCONDITION 1(enemy added): The Enemy sent in as a parameter was added to ArrayList enemiesInRoom and numberOfEnemies was incremented if 
	 * and only if the number of enemies in this room was less than 2.
	 */
	public void addEnemy(Enemy toAdd) {	

		if(numberOfEnemies < 2) {//max two enemies in a room
			enemiesInRoom.add(toAdd);
			numberOfEnemies++;
		}
	}

	/**
	 * INTENT: Removes an Enemy from this Room.
	 * 
	 * PRECONDITION 1 (enemies per room): There is at least one Enemy in this Room.
	 * 
	 * POSTCONDITION 1(enemy added): The Enemy sent in as a parameter was removed from ArrayList enemiesInRoom and numberOfEnemies was 
	 * decremented if and only if the number of enemies in this room was at least 1.
	 */
	public void removeEnemy(Enemy toRemove) {
		if (numberOfEnemies > 0) {
			enemiesInRoom.remove(toRemove);
			numberOfEnemies--;
		}
	}

	/**
	 * INTENT: Returns the number of enemies in this room.
	 * 
	 * POSTCONDITION: The number of enemies in this room was returned. 
	 */
	public int getNumberOfEnemies() {
		return numberOfEnemies;
	}

	/**
	 * INTENT: Returns this room's name.
	 * 
	 * POSTCONDITION: This room's name was returned.
	 */
	public String getRoomName() {
		return aRoomName;
	}

	/**
	 * INTENT: Returns this room's room number.
	 * 
	 * POSTCONDITION: This room's room number was returned.
	 */
	public int getRoomNumber() {
		return aRoomNum;
	}

	/**
	 * INTENT: Returns this room's description.
	 * 
	 * PRECONDITION (description): The text file "Room Descriptions.txt" was put in the hash map roomDescriptions with each room name as a key word
	 * for that room's description.
	 * 
	 * POSTCONDITION: This room's description was returned.
	 */
	public String getRoomDescription() {
		return Messages.roomDescriptions.get(aRoomName);
	}

} 