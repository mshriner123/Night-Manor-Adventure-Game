/**
 * Food extends Objects and contains methods to construct a Food object, get this Food object's health benefit, and
 * take a Food object. See the method headers for more details. 
 * 
 * @author Michael Shriner
 */
public class Food extends Objects {

	int healthBenefit;

	/**
	 * INTENT: Constructs a Food object.
	 * 
	 * PRECONDITION: healthBenefit > 0.
	 * 
	 * POSTCONDITION: The parent constructor was called, and the data member of this class was initialized.
	 */
	public Food (String aName, Room aRoom, boolean canTake, boolean inInventory, int health) {
		super(aName, aRoom, canTake, inInventory);	
		healthBenefit = health;
	}

	/**
	 * INTENT: Returns this Food object's health benefit.
	 * 
	 *	POSTCONDITION: This Food object's health benefit was returned.
	 */
	public int getHealthBenefit() {
		return healthBenefit;
	}

	/**
	 * INTENT: Takes the Food item specified by the parameter. 
	 * 
	 * PRECONDITION: objectsInRooms is non-empty.
	 * 
	 * POSTCONDITION 1 (Food object found): Index i was in the interval [0, objectsInRooms.size()), 
	 * the Objects at i in objectsInRooms was a Food object, this player's current room 
	 * contained that Food object, the parameter foodName equaled the name of the Food object at 
	 * i in objectsInRooms, and the desired Food object was found, XOR the desired Food object 
	 * was not found.
	 * 
	 * POST 2 (add to inventory): POSTCONDITION 1 was fulfilled, the Food object at index i in 
	 * objectsInRooms was added to inventory, and the loop was exited, XOR the desired Food object 
	 * was not found.
	 */
	public static void takeFood(String foodName) {
		Room currentRoom = NightManor.thePlayer.getLocation();
		boolean done = false;
		int i = 0;

		while (i < NightManor.objectsInRooms.size() && !done) {	
			Objects obj = NightManor.objectsInRooms.get(i);

			if (obj instanceof Food) {	
				/*
				 * State 1 (Food): Objects at index i in objectsInRooms is a Food object.
				 */

				if(obj.aRoom.getRoomNumber()==currentRoom.getRoomNumber()) {
					/*
					 * State 2 (current location): The player is in the same room as the Food at index i 
					 * in objectsInRooms.
					 */

					if (obj.getName().equals(foodName)) {
						/*
						 * State 3 (added): The Food at index i in objectsInRooms is the Food specified in
						 * the parameter, it is added to inventory if and only if it is not already in inventory, and
						 * done is assigned true.
						 */
						obj.addToInventory();
						done = true;
					}
				}
			}
			i++;
		}
		if (!done) {
			/*
			 * State 4 (not found): The specified Food is in the player's current room, XOR a statement is 
			 * printed to indicate that the specified Food is not in the player's current room.
			 */
			System.out.println(foodName + " was not in " + currentRoom.getRoomName());
		}
	}

}