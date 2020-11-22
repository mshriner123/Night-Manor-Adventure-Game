import java.util.ArrayList;
/**
 * Treasure contains methods to construct a Treasure object, set the capacity data member to the capacity of the player's largest bag, 
 * add a Treasure object to inventory, remove a Treasure object from inventory, print a list of the Treasure in the treasure room,
 * print a list of the treasure in the player's bag, get the total value of the treasure in the player's bag, determine the larger of 
 * two integers sent in as parameters and return the largest, and determine the possible maximum value of the treasure in a bag of a
 * given capacity.
 * 
 * @author Michael Shriner
 */
public class Treasure {

	String aName;
	boolean inInventory;
	int aValue;
	int aWeight;
	static int totalVal =0;
	static int totalWeight = 0;
	static int capacity = -1;
	static ArrayList <Treasure> treasure = new ArrayList <Treasure> ();			

	/**
	 * INTENT: Constructs a Treasure object. 
	 * 
	 * PRECONDITION 1 (inventory): inInventory is true if and only if the player successfully takes this Treasure.
	 * 
	 * PRE 2 (value and weight): aValue > 0 and aWeight > 0.
	 * 
	 * POSTCONDITION 1: A Treasure object with a name, inventory status, a value, and a weight was created and added to ArrayList treasure.
	 */
	public Treasure(String name, boolean inventory, int value, int weight) {
		aName = name;
		inInventory = inventory;
		aValue = value;
		aWeight = weight;
		treasure.add(this);
	}

	/**
	 * INTENT: Assigns the capacity of the largest treasure bag that the player has in inventory to the capacity data member of this
	 * class.
	 * 
	 * EXAMPLE: capacity = 200 if and only if the player has the treasure bag with capacity 200 and does not have a treasure bag with a greater
	 * capacity.
	 * 
	 * PRECONDITION (no treasure bag): Capacity is 0 if and only if the player does not have a treasure bag in inventory.
	 * 
	 * POSTCONDITION: The capacity of the largest treasure bag that the player has in inventory was assigned to the capacity 
	 * data member of this class.
	 */
	public static void setCapacity() {
		capacity = Bag.getLargestCapacity();
	}

	/**
	 * INTENT: Adds the specified Treasure object to inventory if it isn't already in inventory, there is enough capacity, and the player
	 * is in the treasure room.
	 * 
	 *SHORTHAND(enough capacity): There is enough capacity to take a Treasure object if the current capacity minus
	 *the weight of the Treasure object is greater than or equal to 0.
	 *
	 *PRECONDITION 1 (valid name): aTreasure is the name of a Treasure object. 
	 *
	 *PRE 2 (can take): Once the specified Treasure object is identified, it can be taken if and only if it is not in inventory 
	 * and there is enough capacity (see shorthand).
	 *
	 *PRE 3 (taken): A Treasure was taken if and only if it was added to inventory, capacity was reduced by the weight of the Treasure,
	 *the player's total value taken was increased by the value of the Treasure, and the total weight of the player's bag was 
	 *increased by the weight of the Treasure.
	 * 
	 * POSTCONDITION: The specified Treasure was identified, it was not in inventory, there was enough capacity, the player was in
	 * the treasure room, and it was taken, XOR aTreasure was not taken.
	 */
	public static void take(String aTreasure) {

		if (NightManor.thePlayer.yourLocation.getRoomNumber() == NightManor.treasureRoom.getRoomNumber()) {

			for (int i = 0; i < treasure.size(); i++) {

				if (treasure.get(i).aName.equalsIgnoreCase(aTreasure)) {

					if (!treasure.get(i).inInventory) {

						if((capacity - treasure.get(i).aWeight) >= 0 ) {
							/*
							 * State 1 (taken): The player was in the treasure room, the specified treasure was identified, 
							 * it was not in inventory, there was enough capacity, and it is taken.
							 */
							treasure.get(i).inInventory = true;
							capacity = capacity - treasure.get(i).aWeight;
							totalVal += treasure.get(i).aValue;
							totalWeight += treasure.get(i).aWeight;

							System.out.println(treasure.get(i).aName + " taken");
							System.out.println("bag capacity: " + capacity);
							System.out.println("total loot value: " + totalVal);
						}
						else {
							if (capacity == -1) {
								/*
								 * State 2 (no bag): If capacity is -1, then the player doesn't have a treasure bag, and a statement is printed
								 * to indicate that the player needs a treasure bag to take treasure.
								 */
								System.out.println("You need a treasure bag first.");
							}
							else {
								/*
								 * State 3 (bag empty): If there is not enough capacity (see shorthand in header) to take the specified 
								 * Treasure, and the player has a treasure bag, then a statement is printed to indicate that there isn't 
								 * enough capacity to take that Treasure object.
								 */
								System.out.println("You don't have room for that item.");
							}
						}
					}
					else {
						/*
						 * State 4 (already in inventory): If the player tries to take a Treasure that is in inventory, then a statement
						 * is printed that indicates that the player already took that Treasure.
						 */
						System.out.println("You already took " + aTreasure);
					}
				}
			}
		}
		else {
			/*
			 * State 5 (wrong room): The player is not in the treasure room, and a statement is printed to indicate 
			 * that there is no treasure in this room.
			 */
			System.out.println("No treasure here. Try a different room.");
		}
	}

	/**
	 * INTENT: Removes the specified Treasure object from inventory if it's in inventory. 
	 *
	 *PRECONDITION 1 (valid name): aTreasure is the name of a Treasure object. 
	 *
	 *PRE 2 (can remove): Once the specified Treasure object is identified, it can be removed if and only if it's in inventory.
	 *
	 *PRE 3 (removed): A Treasure was removed if and only if it was removed from inventory, capacity was increased by the weight of the Treasure,
	 * the player's total value taken was decreased by the value of the Treasure, and the player's bag's total weight was 
	 * decreased by the weight of the Treasure.
	 * 
	 * POSTCONDITION: The player was in the treasure room, the specified Treasure was identified, it was in inventory, and it was removed, XOR 
	 * the Treasure was not removed.
	 */
	public static void remove(String aTreasure) {

		if (NightManor.thePlayer.yourLocation.getRoomNumber() == NightManor.treasureRoom.getRoomNumber()) {

			for (int i = 0; i < treasure.size(); i++) {

				if (treasure.get(i).aName.equalsIgnoreCase(aTreasure)) {

					if(treasure.get(i).inInventory) {
						/*
						 * State 1 (removed): The player was in the treasure room, the specified Treasure object was identified, 
						 * was in inventory, and is removed (see PRE 3 (removed) for more details).
						 */
						capacity = capacity + treasure.get(i).aWeight;
						totalVal = totalVal - treasure.get(i).aValue;
						totalWeight = totalWeight - treasure.get(i).aWeight;
						treasure.get(i).inInventory = false;

						System.out.println(treasure.get(i).aName + " removed");
						System.out.println("bag capacity: " + capacity);
						System.out.println("total loot value: " + totalVal);			
					}
					else {
						/*
						 * State 2 (not in inventory): If the specified Treasure is not in inventory, then a 
						 * statement is printed to indicate that it cannot be removed until it is taken.
						 */
						System.out.println("You have to take " + aTreasure + " before removing it.");
					}
				}
			}
		}
		else {
			/*
			 * State 3 (wrong room): The player is not in the treasure room, and a statement is printed to indicate 
			 * that there is no treasure in this room.
			 */
			System.out.println("No treasure here. Try a different room.");
		}
	}

	/**
	 * INTENT:Prints a list of the treasure in the treasure room if the player is in the treasure room.
	 * 
	 * POSTCONDITION: A list of each Treasure in the treasure room with its weight and value was printed, XOR
	 * the player was not in the treasure room.
	 */
	public static void printTreasure() {

		if (NightManor.thePlayer.yourLocation.getRoomNumber() == NightManor.treasureRoom.getRoomNumber()) {	

			for (int i = 0; i < treasure.size(); i++) {

				Treasure anItem = treasure.get(i);
				if(!anItem.inInventory) {
					/*
					 * State 1 (printed): The name, weight, and value of the Treasure object at index i in treasure is printed,
					 * the Treasure object at index i is not in inventory, and the player is in the treasure room.
					 */
					System.out.println(anItem.aName + ", weight: " + anItem.aWeight + ", value: " + anItem.aValue);
				}
			}
		}
		else {
			/*
			 * State 2 (wrong room): The player is not in the treasure room, and a statement is printed to indicate 
			 * that there is no treasure in this room.
			 */
			System.out.println("No treasure here. Try a different room.");
		}
	}

	/**
	 * INTENT: Prints a list of the treasure in inventory (in the player's treasure bag) and the bag's total weight and value.
	 * 
	 * POSTCONDITION: A list of each Treasure in inventory with its weight and value was printed, and the bag's total weight
	 * and total value was printed.
	 */
	public static void printItemsInBag() {
		boolean empty = true;

		for (int i = 0; i < treasure.size(); i++) {
			if (treasure.get(i).inInventory) {
				/*
				 * State 1 (in inventory): The name, weight, and value of the Treasure object at index i in treasure is printed if
				 * and only if the Treasure object at index i is in inventory.
				 */
				empty = false;
				Treasure anItem = treasure.get(i);
				System.out.println(anItem.aName + ", weight: " + anItem.aWeight + ", value: " + anItem.aValue);
			}
		}

		if (empty) {
			/*
			 * State 2 (empty): If the player's treasure bag is empty, or the player does not have a treasure bag,
			 * then a statement is printed to indicate that there is no treasure in the player's treasure bag.
			 */
			System.out.println("No treasure in bag.");
		}
		else {
			/*
			 * State 3 (non-empty): The total value and total weight of the player's treasure bag is printed if and only if 
			 * the player has a treasure bag, and that treasure bag is not empty.
			 */
			System.out.println("Total Value: " + totalVal);
			System.out.println("Total Weight: " + totalWeight);
		}
	}

	/**
	 * INTENT: Returns the larger of the two parameter values or the second parameter value if they are equal.
	 *
	 * POSTCONDITION: The larger of the two parameter values was returned, or the second parameter 
	 * value was returned.
	 */
	public static int max (int value1, int value2) {

		return (value1 > value2)? value1 : value2;
	}

	/**
	 *INTENT: Returns the maximum total value of treasure that can be put into a treasure bag of a limited capacity.
	 *
	 *EXAMPLE: Suppose you have a bag with weight capacity 80 and three objects to choose from. The first object has value 50 and weight 40. 
	 *The second object has value 100 and weight 40, and the third object has value 20 and weight 20. The maximum total value 
	 *is 150. You would choose the first and second objects to maximize the value in the bag.
	 *
	 *SHORTHAND (first case): The first case is the value of the nth item added to the maximum total value of n-1 items, and 
	 *the bag weight capacity reduced by the weight of the nth item. In this case, we include the nth item in the treasure bag.
	 *
	 *SHORTHAND (second case): The second case is the maximum value of n-1 Treasure objects and the weight capacity. In this case, we
	 *exclude the nth item from the treasure bag.
	 *
	 *SHORTHAND (knownS): knownS contains solutions for instances of the problem with simpler parameters. The problem is finding the 
	 *maximum total value of n Treasure objects that can be put into a treasure bag of capacity c. An instance of the problem with
	 *simpler parameters is the maximum total value of i Treasure objects for 0<= i < n that can be put into a treasure bag of capacity w 
	 *where 0 <= w < c.
	 *
	 *PRECONDITION 1 (treasure): Each Treasure object has a weight and a value and is contained by ArrayList treasure.
	 *
	 *PRE 2 (capacity and items): c >= 0 and n >= 0
	 *
	 *PRE 3 (0-1): Each Treasure object has the 0-1 property (i.e., no Treasure object can be split into separate parts -- either you
	 *include it in the treasure bag or exclude it from the treasure bag).
	 *
	 *POSTCONDITION 1 (sub-solutions, part 1): The maximum value of 0 Treasure objects or 0 capacity equaled 0 and was stored.
	 *
	 *POST 2 (sub-solutions, part 2): The larger value of the first case and the second case was stored for i Treasure objects 
	 *for each i in the interval [1, n) and each bag capacity w in the interval [1, c).
	 *
	 *POST 3 (enough known): The larger value of the first case and the second case was stored for n treasure objects and capacity 
	 *c.
	 *
	 *POST 4 (complement): The maximum total value of n Treasure objects and capacity c was returned. 
	 */
	public static int treasureBag (int c, int n ) {

		if (c < 0 || n < 0) {
			return Integer.MAX_VALUE;
		}

		int treasureBag [][] = new int [n+1][c+1];

		for (int i = 0; i <= n; i++) {

			for (int w =0; w <= c; w++) {

				if (i == 0 || w==0) {
					/*
					 * State 1 (0 items or 0 capacity): The maximum total value for 0 Treasure objects or 
					 * 0 capacity equals 0 and is stored.
					 */
					treasureBag[i][w] = 0;
				}
				else {
					if (treasure.get(i-1).aWeight <= w) {
						/*
						 * State 2 (max): The larger value that is obtained by including the ith item or excluding the ith item 
						 * is stored in treasureBag.
						 */
						treasureBag [i][w] = max(treasure.get(i-1).aValue + treasureBag[i-1][w - treasure.get(i-1).aWeight], 
								treasureBag[i-1][w]);
					}
					else {
						/*
						 * State 3 (exceeded capacity): If the weight of the ith item is greater than the capacity w, then 
						 * the ith item is excluded.
						 */
						treasureBag[i][w] = treasureBag [i-1][w];
					}
				}		 
			}
		}

		/*
		 * State 4 (complement): The maximum total value of n Treasure objects and capacity c is returned. 
		 */
		return treasureBag[n][c];	
	}

	/**
	 * INTENT: Checks if the player took the total maximum value of treasure given the size of the player's largest treasure bag and 
	 * the number of treasure items.
	 * 
	 * SHORTHAND (total value): The total value is the value of the treasure in the player's treasure bag.
	 * 
	 * EXAMPLE: If the total maximum value of 5 treasure objects for a bag of capacity 80 is 250, the player's largest bag has capacity 80, 
	 * and the player has 5 treasure objects to choose from, then the player's loot was successful if and only if the total value of the treasure 
	 * in the player's treasure bag equals 250.
	 * 
	 * PRECONDITION (successful loot): The loot is successful if and only if the total value of the treasure in the player's treasure bag equals
	 * the total maximum value of treasure given the size of the player's largest treasure bag and the number of treasure items that the player
	 * had to choose from, XOR the loot is unsuccessful. 
	 * 
	 * POSTCONDITION 1 (max): max equaled the total maximum value of treasure given the number of treasure objects in ArrayList treasure and 
	 * the capacity of the player's largest treasure bag. 
	 * 
	 * POST 2 (total value): total value < max (loot unsuccessful), XOR total value = max (loot successful).
	 * 
	 * POST 3 (complement): A statement was printed that indicated whether the loot was successful or unsuccessful.
	 */
	public static void checkSuccess() {

		/*
		 * State 1 (max): max equals the total maximum value of treasure given the number of treasure objects in ArrayList treasure and 
		 * the capacity of the player's largest treasure bag. 
		 */
		int max = treasureBag(Bag.getLargestCapacity(), treasure.size());

		/*
		 * State 2 (total value): total value = max, XOR total value < max
		 */
		if (max == totalVal &&  totalVal !=0) {
			/*
			 * State 3 (complement1): Loot successful message is printed. 
			 */
			System.out.println("Loot successful: Maximum total value achieved.");
		}
		else {
			if(totalVal == 0) {
				/*
				 * State 4 (complement2): Loot unsuccessful message is printed. 
				 */
				System.out.println("You have the minimum total value: 0.");
			}
			else {
				/*
				 * State 5 (complement3): Loot unsuccessful message is printed.
				 */
				int difference = max - totalVal;
				System.out.println("Loot unsuccessful: you are " + difference + " away from the possible maximum value looted.");
			}
		}
	}

}
