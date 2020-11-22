import java.util.Random;

/**
 * Enemy implements Runnable and contains methods to set this enemy's location, construct an Enemy object, get this enemy's description,
 * get this enemy's name, get this enemy's health, run threads to move each enemy to other rooms and helper methods to achieve that.
 * It contains the following data members: a name, health, a description, and the current location of this enemy. See the method headers
 * below for more information.
 * 
 * @author Michael Shriner
 */
public class Enemy implements Runnable {

	String aName;
	int enemyHealth;
	String aDescription;
	Room currentLocation;

	/**
	 * INTENT: Initialize class data members.
	 * 
	 * PRECONDITION 1 (health): health > 0 and health <= 100
	 * 
	 * PRE 2(location): There can be at most two enemies in one location.
	 * 
	 * POSTCONDITION 1: Class data members were initialized.
	 */
	public Enemy (String name, int health, String description, Room location) {
		aName = name;
		enemyHealth = health;
		aDescription = description;
		currentLocation = location;
	}

	/**
	 * INTENT: Sets the current location of this enemy.
	 * 
	 * POSTCONDITION 1: The current location of this enemy was assigned the parameter room, which was the room that contained 
	 * this enemy.
	 */
	public void setLocation(Room room) {
		currentLocation = room;
	}

	/**
	 * INTENT: Return a description of this enemy.
	 * 
	 * POSTCONDITION: A description of this enemy was returned.
	 */
	public String getDescription() {
		return aDescription;
	}

	/**
	 * INTENT: Return this enemy's name.
	 * 
	 * POSTCONDITION: This enemy's name was returned,
	 */
	public String getName() {
		return aName;
	}

	/**
	 * INTENT: Returns this enemy's health.
	 * 
	 * PRECONDITION 1 (integer): This enemy's health is represented by an integer in the interval [0,100].
	 * 
	 * POSTCONDITION 1: This enemy's health was returned.
	 */
	public int getHealth() {
		return enemyHealth;
	}

	/**
	 * INTENT: Runs a thread for each enemy that allows each enemy to move to other rooms.
	 * 
	 * PRECONDITION 1 (threads): A thread for each enemy was created.
	 * 
	 * PRE 2 (start): Each enemy thread was started.
	 * 
	 * PRE 3 (enemies per room): There can be at most two enemies in the same room.
	 * 
	 * PRE 4 (bounds): Enemies cannot move from the kitchen to the maze.
	 * 
	 * POSTCONDITION 1 (sleep): This thread slept for thirty seconds before POST 2.
	 * 
	 * POST 2 (command): Each enemy either moved south, west, east, north, XOR stayed in the current room.
	 * 
	 * POST 3 (complement): This thread was not interrupted.
	 */
	public void run() {
		/*
		 * SHORTHAND(P1): P1 denotes postcondition 1.
		 * 
		 * SHORTHAND(P2): P2 denotes post 2.
		 * 
		 * SHORTHAND (P3): P3 denotes post 3.
		 * 
		 * SHORTHAND (t1): Task t1 is this thread is asleep for 30 seconds.
		 * 
		 * SHORTHAND (t2): Task t2 is this enemy moves south, west, east, north, or stays in the current room.
		 */

		while (NightManor.subThreads.get(0).running.get()) {

			Random randNum = new Random();

			/*
			 * StateP1(sleep): Task t1 is started, which fulfills P1.
			 */
			try {
				Thread.sleep(30000);//30 seconds

				/*
				 * StateP1.5 (sleep complete): t1 is complete.
				 * 
				 * StateP2 (command): Task t2 is started, which fulfills P2.
				 */

				int command = randNum.nextInt(5); //Note: randomly chooses a number in the interval [0,4], where 0 results in 
				//the enemy staying in the current room, 1 implies move south, 2 implies move west, 3 implies move east, and 
				//4 implies move north.

				if(command == 0) {
					//do nothing
				}
				else {
					if (command == 1) {
						if (currentLocation.getRoomNumber() != 7) {
							enemyMove("south");
						}
					}
					else {
						if(command == 2) {
							enemyMove("west");
						}
						else {
							if(command == 3) {
								enemyMove("east");
							}
							else {
								if(command == 4) {
									enemyMove("north");
								}
							}
						}
					}
				}

				/*
				 * StateP2.5 (command complete): t2 is complete.
				 * 
				 * State P3 (complement): P3 is fulfilled.
				 */
			} catch (InterruptedException e) {

			}
		}
	}

	/**
	 * INTENT: Moves the enemy in the specified direction if possible.
	 * 
	 * SHORTHAND(S): S is the size of the edgeList in NightManor.java.
	 * 
	 * SHORTHAND(Idx): Idx denotes the index of the edge in the edgeList that connects this enemy's current room to the destination room that is in 
	 * the specified direction to move.
	 * 
	 * PRECONDITION 1 (direction): aDirection equals "south", "west", "east", or "north".
	 * 
	 * POST 1: Idx exists and was found in the interval [0, S), XOR Idx >= S
	 * 
	 * POST 2 (valid move): Idx < S, there are fewer than two enemies in the destination room, and the enemy was moved to the destination room,
	 * XOR those conditions were not met and the method ended.
	 * 
	 * POST 3 (player's current room): If this enemy moved into the player's current room, then a 
	 * message was printed to indicate to the player that an enemy walked into the room.
	 */
	public void enemyMove(String aDirection) {

		/*
		 * State 1 (edge): Idx (see shorthand in method header) exists and was found in the interval [0,S), XOR Idx >= S
		 */
		int i;
		i = getDest(aDirection);

		/*
		 * State 2 (valid move): i < S, there are fewer than two enemies in the destination room, and the enemy was moved to the 
		 * destination room, XOR those conditions were not met and the method ended.
		 */
		if (i < NightManor.edgeList.size()) {

			if(NightManor.edgeList.get(i).destRoom.numberOfEnemies < 2) {

				this.currentLocation.removeEnemy(this);
				this.setLocation(NightManor.edgeList.get(i).destRoom);
				NightManor.edgeList.get(i).destRoom.addEnemy(this);

				/*
				 * State 3 (player's current room): If this enemy is in the same room as the player, then 
				 * a statement is printed that tells the player that an enemy just walked into the room.
				 */
				if(NightManor.thePlayer.yourLocation.getRoomNumber()== this.currentLocation.getRoomNumber()) {
					System.out.println();
					System.out.println("An enemy just walked into the " + NightManor.thePlayer.yourLocation.getRoomName() + "!");
					System.out.print(">");
				}
			}
		}
	}

	/**
	 * INTENT: Returns the index of the edge that contains the current room of the enemy and the direction that the enemy wants to move in.
	 * 
	 * EXAMPLE: If the direction is "north", and the enemy's current room is the kitchen, then this method would return the index of the edge
	 * that has starting room kitchen, direction north, and destination room dining room.
	 * 
	 * PRECONDITION 1 (direction): direction equals "south", "west", "east", or "north".
	 * 
	 * PRE 2 (edgeList): edgeList is non-empty.
	 * 
	 * POST 1 (edge found): An edge in edgeList was found with the starting room of this enemy and the direction that this enemy wants to move in
	 * and the index of that edge was returned, XOR such an edge was not found and an integer greater than or equal to edgeList.size() was returned.
	 */
	public int getDest(String direction) {

		int i=0;
		boolean done = false;

		while (i < NightManor.edgeList.size() && !done) {

			if (NightManor.edgeList.get(i).startRoom.getRoomNumber()== currentLocation.getRoomNumber()
					&& NightManor.edgeList.get(i).getDirection().substring(3).equalsIgnoreCase(direction)) {
				/*
				 * State 1 (edge index): An edge in edgeList with the starting room of this enemy and the direction that this enemy wants to move in 
				 * was found at index i and done is assigned true.
				 */

				done = true; 
				i--;
			}
			i++;
		}
		/*
		 * State 2 (complement): Done is true, and an index i in the interval [0, edgeList.size()) is returned, XOR 
		 * done is false, and an integer greater than or equal to edgeList.size() is returned.
		 */
		return i;
	}

}