import java.util.ArrayList;
/**
 * Bag extends Objects and contains methods to construct a Bag object, get the capacity of this Bag, add this Bag to inventory, 
 * and get the capacity of the largest bag in inventory. See the method headers below for more details.
 * 
 * @author Michael Shriner
 */
public class Bag extends Objects {

	int capacity;
	static ArrayList<Bag> bags = new ArrayList<Bag>();

	/**
	 * INTENT: Constructs a Bag.
	 * 
	 * PRECONDITION (capacity): capacity > 0.
	 * 
	 * POSTCONDITION: A Bag was constructed with a name, room, take status, inventory status, and capacity and was 
	 * appended to ArrayList bags.
	 */
	public Bag(String name, Room room, boolean take, boolean inventory, int c) {
		super(name, room, take, inventory);
		capacity = c;
		bags.add(this);
	}

	/**
	 * INTENT:Returns the capacity of this Bag.
	 * 
	 * POSTCONDITION: The capacity of this Bag was returned.
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * INTENT: Takes the specified bag if the bag is identified, the player is in the same room as the bag, and the 
	 * bag is not already in inventory.
	 * 
	 * PRECONDITION 1 (bag): The parameter bag is the name of a Bag.
	 * 
	 * PRE 2 (taken): The specified Bag was taken if and only if the Bag was identified, was not already in inventory,
	 * and the player was in the same room as the Bag.
	 * 
	 * POSTCONDITION: The specified Bag was identified, the player was in the same room as the Bag, the Bag was not already in inventory, 
	 * the Bag was added to inventory, and the capacity of the player's largest bag was assigned to the capacity data member in
	 * Treasure.java, XOR the Bag was not taken.
	 */
	public static void take(String bag) {

		boolean added = false;

		for (int i = 0; i < bags.size(); i++) {		

			if (bags.get(i).getName().equalsIgnoreCase(bag)) {
				/*
				 * State 1 (identified): The Bag that is specified in the parameter is identified.
				 */
				if (bags.get(i).getRoom().getRoomNumber() == NightManor.thePlayer.getLocation().getRoomNumber()) {	
					/*
					 * State 2 (current location): The player is in the same room as the Bag, and 
					 * the Bag is added to inventory if and only if the bag is not already in inventory.
					 */		
					bags.get(i).addToInventory();
					Treasure.setCapacity();
					added = true;
				}
			}
		}

		if (!added) {
			System.out.println("That bag wasn't found.");
		}
	}

	/**
	 * INTENT: Returns the capacity of the player's largest bag.
	 * 
	 * POSTCONDITION 1 (max): max equaled the capacity of the largest Bag that was in inventory, XOR max equaled -1
	 * if and only if there wasn't a bag in inventory.
	 * 
	 * POST 2 (complement): max was returned.
	 */
	public static int getLargestCapacity() {

		int max = -1;

		for (int i = 0; i < bags.size();i++) {

			if (bags.get(i).getCapacity() > max) {
				/*
				 * State 1 (max): The Bag at index i in bags has a greater capacity than max.
				 */
				if (bags.get(i).inInventory) {
					/*
					 * State 2 (inventory): The Bag at index i in bags is in inventory, and max is assigned the 
					 * capacity of the Bag at index i in bags.
					 */		
					max = bags.get(i).getCapacity();
				}
			}
		}

		/*
		 * State 3 (complement): Max is returned.
		 */
		return max;
	}
}