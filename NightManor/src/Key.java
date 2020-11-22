/**
 * Key inherits from Objects and contains methods to get this key's description, determine whether this key is in inventory, 
 * and take this key. See the method headers below for more details.
 * 
 * @author Michael Shriner
 */
public class Key extends Objects {

	/**
	 * INTENT: Calls the parent class constructor.
	 * 
	 * POSTCONDITION: The parent class constructor was called.
	 */
	public Key (String aName, Room aRoom, boolean inventory) {
		super(aName, aRoom, true, inventory);
	}

	/**
	 * INTENT: Returns a description of this key.
	 * 
	 * PRECONDITION: Descriptions of keys are in the hash map objectDescriptions, and the key word for each description is 
	 * the key's name.
	 * 
	 * POSTCONDITION: The description of this key was returned.
	 */
	public String getDescription(String keyName) {
		return Messages.objectDescriptions.get(keyName);
	}

	/**
	 * INTENT: Returns true if this key is in inventory; otherwise, it returns false.
	 * 
	 * POSTCONDITION: The player took this key and true was returned, XOR the player did not take this
	 * key and false was returned.
	 */
	public boolean isInInventory() {
		return inInventory;
	}

	/**
	 * INTENT: Take the key in this player's current room if such a key exists.
	 * 
	 * PRECONDITION (one per room): There is only one key per room.
	 * 
	 * POSTCONDITION 1 (key found): There was a key in this player's current room, and that key was added to inventory.
	 * 
	 * POST 2 (key not found): There was not a key in this player's current room, and a statement was printed that
	 * indicated that to this player.
	 */
	public static void takeKey() {

		Room currentRoom = NightManor.thePlayer.getLocation();
		boolean done = false;
		int i = 0;

		while (i < NightManor.objectsInRooms.size() && !done) {	

			Objects obj = NightManor.objectsInRooms.get(i);		

			if (obj instanceof Key) {

				if(obj.aRoom.getRoomNumber()==currentRoom.getRoomNumber()) {
					/*
					 * State 1 (key found): i < objectsInRooms.size(), the Objects in objectsInRooms at index i is 
					 * a Key, the room number of that Key is this player's current room number, and that Key is
					 * added to inventory.
					 */
					obj.addToInventory();
					done = true;
				}
			}
			i++;
		}

		if (!done) {
			/*
			 * State 2 (key not found): i >= objectsInRooms.size(), done is false, and a statement is 
			 * printed that indicates that there isn't a key in this player's current room.
			 */
			System.out.println("no key in " + currentRoom.getRoomName());
		}	
	}

}