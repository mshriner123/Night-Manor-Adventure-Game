/**
 * Objects is an abstract parent class for Key.java, Food.java, Cabinet.java, Map.java, and Bag.java, and contains methods to return this 
 * object's name, this object's room, return true if the player can take this object, return false if the player cannot take this object, 
 * return a description of this object, add this object to inventory, and compare two objects. See the method headers below
 * for more details.
 * 
 * @author Michael Shriner
 */
public abstract class Objects {

	String aName;
	Room aRoom;
	boolean canTake;
	boolean inInventory;

	/**
	 * INTENT: Initializes the class data members.
	 * 
	 * PRECONDITION 1 (inventory): inInventory is true if and only if this object was taken; otherwise, inInventory is false. 
	 * 
	 * PRE 2 (aRoom): aRoom is the room that contains this object.
	 * 
	 * POSTCONDITION: aName, aRoom, canTake, and inInventory were initialized to the parameter values.
	 */
	public Objects(String name, Room room, boolean take, boolean inventory) {		
		aName = name;
		aRoom = room;	
		canTake = take;
		inInventory = inventory;
	}

	/**
	 * INTENT: Returns the name of this object.
	 * 
	 * POSTCONDITION: The name of this object was returned.
	 */
	public String getName() {
		return aName;
	}

	/**
	 * INTENT: Returns the room that contains this object.
	 * 
	 * POSTCONDITION: The Room that contains this object was returned.
	 */
	public Room getRoom() {
		return aRoom;
	}

	/**
	 * INTENT: Returns true if this object can be taken; otherwise, returns false.
	 * 
	 * POSTCONDITION: True was returned if and only if this player could take this object, XOR false was returned.
	 */
	public boolean canTake() {
		return canTake;
	}

	/**
	 * INTENT: Examines this object.
	 * 
	 * PRECONDITION: The HashMap objectDescriptions in the class Messages contains a description of this object, and the description 
	 * is associated with this object's name. 
	 * 
	 * POSTCONDITION: A description of this object was printed.
	 */
	public void examineObject() {
		System.out.println(Messages.objectDescriptions.get(aName));
	}

	/**
	 * INTENT: Adds this object to inventory.
	 * 
	 * PRECONDITION: The player cannot add an object to inventory twice.
	 * 
	 * POSTCONDITION 1 (in inventory): This object was in inventory, and a statement was printed that 
	 * indicated that to the player, XOR this object was not in inventory.
	 * 
	 * POST 2 (not in inventory): This object was not in inventory, was added to inventory, and a statement was printed that indicated 
	 * to the player that this object was in inventory, XOR this object was in inventory.
	 */
	public void addToInventory() {
		if(inInventory) {
			System.out.println(this.aName + " is already in your inventory.");
		}
		else {
			inInventory = true;
			System.out.println(this.aName + " is now in your inventory.");
		}
	}

	/**
	 * INTENT: Compares this Objects to the parameter Objects
	 * 
	 * EXAMPLE: If "apple" is being compared to "orange", then -1 is returned. If "orange" is being compared to "apple", then
	 * 1 is returned, and if "apple" is being compared to "apple", then 0 is returned. 
	 * 
	 * SHORTHAND (equal): This Objects equals the parameter Objects if and only if the name of this Objects is equal to
	 * the name of the parameter Objects, excluding differences due to capitalization.
	 * 
	 * SHORTHAND (greater than): This Objects is greater than the parameter Objects if and only if the character of this Objects 
	 * name has a greater ASCII value than the character of the parameter Objects name at the same index, and all characters that are 
	 * at a lower index than the character at the index that is being compared are the same in both names.
	 * (e.g., rat is greater than racecar because the ASCII value of t is 116 and the ASCII value of c is 99, both character are at index 
	 * 2, and the characters at indices in the interval [0,1] are the same in both words).
	 * 
	 * SHORTHAND (less than): This Objects is less than the parameter Objects if and only if the character of this Objects 
	 * name has a lower ASCII value than the character of the parameter Objects name at the same index, and all characters that are 
	 * at a lower index than the character at the index that is being compared are the same in both names.
	 * (e.g., banana is less than banner because the ASCII value of a is 97 and the ASCII value of n is 110, both characters are at index 3, 
	 * and the characters at indices in the interval [0,2] are the same in both words).
	 * 
	 * PRECONDITION 1 (ASCII): This method uses ASCII values to compare characters. 
	 * 
	 * PRE 2 (capitalization): If a name has an upper case character or characters, then those characters are set to lower case before 
	 * being compared because upper case characters have lower values than their corresponding lower case characters.
	 * 
	 * POSTCONDITION 1 (equal): 0 was returned if and only if this Objects equaled (see shorthand) the parameter Objects.
	 * 
	 * POST 2 (less than): -1 was returned if and only if this Objects was less than (see shorthand) the parameter Objects.
	 * 
	 * POST 3 (greater than): 1 was returned if and only if this Objects was greater than (see shorthand) the parameter Objects.
	 */
	public int compareTo (Objects obj) {

		String thisName = this.getName();
		String paramName = obj.getName();

		boolean done = false;
		int thisIdx =0;
		int paramIdx = 0;

		if(thisName.strip().equalsIgnoreCase(paramName.strip())) {
			/*
			 * State 1 (equal): The name of this Objects is equal to the name of the parameter Objects and 0 is returned. 
			 */
			done = true;
			return 0;
		}

		while (!done)	{

			if (thisName.toLowerCase().strip().charAt(thisIdx) < paramName.toLowerCase().strip().charAt(paramIdx)) {
				/*
				 * State 2 (less than): This Objects is less than the parameter Objects and -1 is 
				 * returned.
				 */
				done = true;
				return -1;
			}
			else {
				if(thisName.toLowerCase().strip().charAt(thisIdx) > paramName.toLowerCase().strip().charAt(paramIdx)) {
					/*
					 * State 3 (greater than): This Objects is greater than the parameter Objects and 1 is 
					 * returned.
					 */
					done = true;
					return 1;
				}
			}

			if (thisIdx < thisName.length()) {
				/*
				 * State 4 (this increment): thisIdx is less than the length of this Objects name, the character at thisIdx in this Objects name is 
				 * equal to the character at paramIdx in the parameter Objects name, and thidIdx was incremented.
				 */
				thisIdx++;
			}

			if(paramIdx < paramName.length()) {
				/*
				 * State 5 (parameter increment): paramIdx is less than the length of the parameter Objects name, 
				 * the character at paramIdx in the parameter Objects name is equal to the character at thisIdx (prior to being incremented) 
				 * in this Objects name, and paramIdx was incremented.
				 */
				paramIdx++;
			}

		}

		return -20;//will not reach here but satisfies this method's concerns that this method won't return anything
	}


}