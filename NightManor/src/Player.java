import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Player contains methods to construct a Player, add health to this player's health, set this player's current location, get location, 
 * get health, move, return the number of enemies in this player's current location, open doors, close doors, lock doors, unlock
 * doors, sort inventory, and check inventory. For more details, see the method headers below.
 * 
 * @author Michael Shriner
 */
public class Player {

	Scanner aConsole = new Scanner (System.in);
	Room yourLocation;
	final int MAXHEALTH = 100;
	final int MINHEALTH = 1; 
	int yourHealth;

	/**
	 * INTENT: Constructs a Player.
	 * 
	 * PRECONDITION 1 (health): yourHealth = 100
	 * 
	 * PRE 2 (location): yourLocation is prison cell one
	 * 
	 * POSTCONDITION: The player's location and health were initialized.
	 */
	public Player(Room aLocation, int health) {		
		setLocation(aLocation);	
		yourHealth = health;
	}

	/**
	 * INTENT: Adds an integer to the player's health.
	 * 
	 * PRECONDITION (non-trivial): healthToAdd != 0
	 * 
	 * POSTCONDITION 1 (healthToAdd > 0): healthToAdd > 0 and yourHealth = yourHealth + healthToAdd, XOR healthToAdd < 0.
	 * 
	 * POST 2 (healthToAdd < 0): healthToAdd < 0 and yourHealth = yourHealth + healthToAdd
	 * 
	 * POST 3 (dead): If yourHealth - healthToAdd = 0, then this player was dead.
	 * 
	 * POST 4 (greater than max): If yourHealth + healthToAdd > MAXHEALTH, then yourHealth = MAXHEALTH.
	 */
	public void addToHealth(int healthToAdd) {

		/*
		 * State 1 (greater than max): If yourHealth + healthToAdd > MAXHEALTH, then yourHealth = MAXHEALTH.
		 */
		if ((yourHealth += healthToAdd) > MAXHEALTH) {
			yourHealth = MAXHEALTH;
		}
		else {
			/*
			 * State 2 (dead): If yourHealth - healthToAdd = 0, then this player is dead.
			 */
			if ((yourHealth += healthToAdd) < MINHEALTH) {
				//DEAD
			}
			/*
			 * State 3 (healthToAdd < 0 or healthToAdd > 0): yourHealth = yourHealth + healthToAdd
			 */
			else {
				yourHealth += healthToAdd;
			}
		}
	}

	/**
	 * INTENT: Sets this player's current location.
	 * 
	 * POSTCONDITION: This player's current location was assigned aLocation. 
	 */
	public void setLocation(Room aLocation) {
		yourLocation = aLocation;
	}

	/**
	 * INTENT: Returns this player's current location.
	 * 
	 * POSTCONDITION: This player's location was returned.
	 */
	public Room getLocation() {
		return yourLocation;
	}

	/**
	 * INTENT: Returns this player's health.
	 * 
	 * POSTCONDITION: This player's health was returned.
	 */
	public int getHealth() {
		return yourHealth;
	}

	/**
	 * INTENT: Move the player in the specified direction if there is an unlocked and open passage.
	 * 
	 * SHORTHAND (destination room): The destination room is the room in the direction of aCommand from this
	 * player's current room. 
	 * 
	 * PRECONDITION 1 (valid direction): aCommand equals "go north", "go south","go west", or "go east".
	 * 
	 * PRE 2 (valid move): This player can move in the direction aCommand if and only if there is not a locked door
	 * or a closed door and there is a passage between this player's current room and the destination room.
	 * 
	 * PRE 3 (multiple passages): This player's current location could have multiple passages or one passage.
	 * 
	 * POSTCONDITION 1 (maze): If this player was in the room maze and entered "go north", then this player entered the 
	 * start position of the maze.
	 * 
	 * POST 2 (destination room): Not POSTCONDITION 1, and the index of the edge with start room as this player's
	 * current location and direction aCommand was returned if it existed. If it did not exist, a number greater than 
	 * or equal to the size of the edgeList was returned.
	 * 
	 * POST 3 (moved): This player's current location was assigned the destination room, a description of the
	 * destination room was printed, and the number of enemies (if any) in the destination room was determined
	 * if and only if the index of the edge that was found was less than the size of the edgeList, a passage for that 
	 * edge existed, the edge was unlocked and open.
	 * 
	 * POST 4 (cannot move): The index of the edge that was found was greater than or equal to the size of the edgeList XOR
	 * there wasn't a passage for that edge, XOR the passage for that edge was locked or closed, and a statement was printed to
	 * indicate which of those was the case.  
	 */
	public void move(String aCommand) {	
		/*
		 * State 1 (maze): This player is in the room Maze, entered go north, and runMaze was called in Maze.java.
		 */
		if(yourLocation.getRoomNumber() == NightManor.maze.getRoomNumber() && aCommand.equalsIgnoreCase("go north")) {
			NightManor.theMaze.runMaze();
			return;
		}

		if (yourLocation.getRoomNumber()== NightManor.darkHall.getRoomNumber() && aCommand.equalsIgnoreCase("go west")) {
			NightManor.sWeb.spiderTrap();
			yourLocation = NightManor.spiderWeb;
			return;
		}

		if (yourLocation.getRoomNumber() == NightManor.spiderWeb.getRoomNumber() && aCommand.equalsIgnoreCase("go west")) {
			System.out.println("As you cross into the next room, the spiders wake up, and the walls start to tremble. Next thing you know, ");
			System.out.println("the ceiling crashes down between you and the spiders. There is no going back.");
			NightManor.edgeList.get(56).closePassage(false);
			yourLocation = NightManor.batCave;
			System.out.println(yourLocation.getRoomDescription());
			enemiesInRoom();		
			return;
		}

		if (yourLocation.getRoomNumber() == NightManor.batCave.getRoomNumber() && aCommand.equalsIgnoreCase("climb down")) {
			yourLocation = NightManor.tunnelRoom0;
			System.out.println(yourLocation.getRoomDescription());
			enemiesInRoom();
			return;
		}

		/*
		 * State 2 (destination room): i is the index of the edge with starting room as this player's location and direction
		 * as aCommand, and i is in the interval [0, edgeList.size()), XOR i >= edgeList.size().
		 */
		int i = getEdgeMultPassages(aCommand.substring(3));

		/*
		 * State 3 (moved): When i < edgeList.size(), there is a passage for that edge, that edge is unlocked and opened, 
		 * this player's current location is assigned the destination room, a description of the room is printed, and 
		 * the number of enemies in the destination room is determined.
		 */
		if (i < NightManor.edgeList.size() && NightManor.edgeList.get(i).passage) {

			if(!NightManor.edgeList.get(i).isLocked) {

				if(NightManor.edgeList.get(i).isOpen) {

					yourLocation = NightManor.edgeList.get(i).destRoom;
					System.out.println(yourLocation.getRoomDescription());
					enemiesInRoom();		
				}
				else {
					//State 4 (closed): If the passage for this edge has a closed door, then this statement is printed to 
					//indicate that to this player.
					System.out.println("closed");
				}
			}
			else {
				//State 5 (locked): If the passage for this edge has a locked door, then this statement is printed to 
				//indicate that to this player.
				System.out.println("locked");
			}
		}
		else {
			//State 6 (no passage): If i >= edgeList.size() or there isn't a passage for the edge at index i, then 
			//a statement is printed that indicates that there is no passage in that direction.
			System.out.println("no passage that way");
		}
	}

	/**
	 * INTENT: Determines the number of enemies in this player's current room and prints out that number and 
	 * a description of each enemy.
	 * 
	 * PRECONDITION 1 (max per room): There are at most two enemies per room.
	 * 
	 * POSTCONDITION 1 (no enemies): If there were no enemies in this player's current location, then this method returned 
	 * without doing anything else.
	 * 
	 * POST 2 (one enemy): If there was one enemy in this player's current location, then a statement was printed to indicate that
	 * and describe the enemy.
	 * 
	 * POST 3 (two enemies): If there were two enemies in this player's current location, then a statement was printed to indicate
	 * that and describe those enemies. 
	 */
	public void enemiesInRoom() {

		/*
		 * State 1 (no enemies): If there are no enemies in this player's current room, then this method returns without 
		 * doing anything else.
		 */
		if(yourLocation.numberOfEnemies != 0) {
			/*
			 * State 2 (one enemy): There is one enemy in this player's current location and a description of that enemy is 
			 * printed, XOR there are two enemies in this player's current location.
			 */
			if(yourLocation.numberOfEnemies == 1) {
				System.out.println("There is " + yourLocation.getNumberOfEnemies() + " enemy in this room!");
				System.out.println(yourLocation.enemiesInRoom.get(0).getDescription());
			}
			else {
				/*
				 * State 3 (two enemies): There are two enemies in this player's current location and descriptions of those
				 * enemies are printed.
				 */
				System.out.println("There are " + yourLocation.getNumberOfEnemies() + " enemies in this room!");
				for(int x = 0; x < yourLocation.enemiesInRoom.size();x++) {
					System.out.println(yourLocation.enemiesInRoom.get(x).getDescription());
				}
			}

		}
	}

	/**
	 * INTENT: Unlocks this door if this door is locked and this player has a key for this door.
	 * 
	 * PRECONDITION 1 (multiple passages): This player's current location has multiple passages or one passage.
	 * 
	 * POSTCONDITION 1 (multiple passages, edge index): An index for an edge in edgeList was found with this player's current 
	 * location as the starting room and the direction of the door as the edge direction, XOR the index of the edge was greater than 
	 * or equal to edgeList.size() if and only if there were multiple passages that lead out of this 
	 * player's current location.
	 * 
	 * POST 2 (unlocked): This edge was unlocked, its reverse edge was unlocked, and
	 * a statement was printed that indicated that to this player if and only if the index of the edge that was found was 
	 * less than the size of the edgeList, that edge was locked, and this player had the key that unlocked that edge.
	 * 
	 * POST 3 (unlocked or no key): If the index of the edge that was found was less than the size of the edgeList, then 
	 * a statement was printed that indicated that the door was already unlocked if and only if the edge was unlocked XOR
	 * a statement was printed that indicated that this player didn't have the key that unlocked this door if and only if 
	 * this player didn't have the key in inventory that unlocked this edge.
	 * 
	 * POST 4 (no door): A statement was printed that indicated that there was no door if and only if the index of the edge
	 * that was found was greater than or equal to the size of edgeList.
	 * 
	 * POST 5 (one passage): If this player's current room did not have multiple passages, then a method was called to handle the case
	 * where there was only one passage.
	 */
	public void unlockDoor() {

		if(yourLocation.multiplePassages) {
			/*
			 * State 1 (multiple passages): There are multiple passages, and this player is asked to enter the direction 
			 * of the passage that this player wants to unlock.
			 */
			System.out.println("Which door would you like to unlock? Enter its direction.");
			System.out.print(">");
			String answer = aConsole.nextLine();

			/*
			 * State 2 (edge index): The index of the edge with this player's current location as starting room and 
			 * direction as the direction that the player specified is found, XOR that index is greater than or equal to the size
			 * of the edgeList.
			 */
			int i = getEdgeMultPassages(answer);	

			if (i < NightManor.edgeList.size()) {

				if(NightManor.edgeList.get(i).isLocked) {

					if(NightManor.edgeList.get(i).aKey.inInventory) {
						/*
						 * State 3 (unlocked): The index of the edge that was found is less than the size of the 
						 * edgeList, that edge is locked, this player has the key that unlocks that edge, and that 
						 * edge and its reverse is unlocked.
						 */
						NightManor.edgeList.get(i).setLocked(false);
						NightManor.edgeList.get(i).getReverse().setLocked(false);
						System.out.println("unlocked");
					}
					else {
						/*
						 * State 4 (no key): This player doesn't have the key that unlocks the edge at the index that
						 * was found.
						 */
						System.out.println("need key first");
					}
				}
				else {
					/*
					 * State 5 (unlocked already): The edge at the index that was found was already unlocked.
					 */
					System.out.println("door not locked");
				}
			}
			else {
				/*
				 * State 6 (no door): There isn't a passage in the specified direction and a statement is printed to indicate that there 
				 * is no door in the specified direction.
				 */
				System.out.println("no door");
			}
		}
		else {
			/*
			 * State 7 (one passage): This player's current location only has one passage, and a method is called
			 * to handle that case.
			 */
			oneDoorToUnlock();		
		}
	}

	/**
	 * INTENT: Unlocks this door if this door is locked and this player has a key for this door.
	 * 
	 * PRECONDITION 1 (one passage): This player's current room has one passage.
	 * 
	 * POSTCONDITION 1 (edge index): An index for an edge with this player's current 
	 * location as the starting room in the interval [0, edgeList.size()) was found, XOR the index that was found 
	 * was equal to -1. 
	 * 
	 * POST 2 (unlocked): This edge was unlocked, its reverse edge was unlocked, and
	 * a statement was printed to indicate that to this player if and only if the index of the edge that was found was 
	 * in the interval [0, edgeList.size()), the edge at that index had this player's current room as its starting room, 
	 * that edge was locked, and this player had the key that unlocked that edge.
	 * 
	 * POST 3 (didn't unlock): If the index of the edge that was found was in the interval [0, edgeList.size()), then 
	 * a statement was printed that indicated that the door was already unlocked if and only if the edge was unlocked XOR
	 * a statement was printed that indicated that this player didn't have the key that unlocked this door if and only if 
	 * this player didn't have the key in inventory that unlocked this edge. 
	 */
	public void oneDoorToUnlock() {

		/*
		 * State 1 (edge index): The index of the edge with this player's current location as starting room is found, and index i 
		 * is in the interval [0, edgeList.size()), XOR index i = -1.
		 */
		int i = getEdgeOnePassage();

		if (i != -1) {

			if(NightManor.edgeList.get(i).isLocked) {

				if(NightManor.edgeList.get(i).aKey.inInventory) {
					/*
					 * State 2 (unlocked): The index of the edge that was found was in the interval 
					 * [0, edgeList.size()), that edge was locked, this player had the key that unlocked 
					 * that edge, that edge is unlocked, that edge's reverse is unlocked, and a message is 
					 * printed to indicate that.
					 */
					NightManor.edgeList.get(i).setLocked(false);
					NightManor.edgeList.get(i).getReverse().setLocked(false);
					System.out.println("unlocked");
				}
				else {
					/*
					 * State 3 (no key): This player doesn't have the key in inventory that unlocks this edge and 
					 * a statement is printed to indicate that.
					 */
					System.out.println("need key first");
				}
			}
			else {
				/*
				 * State 4 (unlocked): The edge at index i is unlocked, and a statement is printed to indicate that the door is 
				 * already unlocked.
				 */
				System.out.println("door not locked");
			}
		}
	}

	/**
	 * INTENT: Opens this door if this door is unlocked and closed.
	 * 
	 * PRECONDITION 1 (multiple passages): This player's current location has multiple passages or one passage.
	 * 
	 * POSTCONDITION 1 (multiple passages, edge index): An index for an edge in edgeList was found with this player's current 
	 * location as the starting room and the direction of the door as the edge direction, XOR the index of the edge was greater than 
	 * or equal to edgeList.size() if and only if there were multiple passages that lead out of this 
	 * player's current location.
	 * 
	 * POST 2 (opened): This edge was opened, its reverse edge was opened, and
	 * a statement was printed to indicate that to this player if and only if the index of the edge that was found was 
	 * less than the size of the edgeList, that edge was unlocked, and that edge was not open.
	 * 
	 * POST 3 (open already or locked): If the index of the edge that was found was less than the size of the edgeList, then 
	 * a statement was printed that indicated that the door was already open if and only if the edge was open XOR
	 * a statement was printed that indicated that the door was locked if and only if the edge was locked.
	 * 
	 * POST 4 (no door): A statement was printed that indicated that there was no door if and only if the index of the edge
	 * that was found was greater than or equal to the size of edgeList.
	 * 
	 * POST 5 (one door): If this player's current room did not have multiple passages, then a method was called to handle the case
	 * where there was only one passage.
	 */
	public void openDoor() {

		if(yourLocation.multiplePassages) {
			/* 
			 * State 1 (multiple passages): There are multiple passages, and this player is asked to enter the direction 
			 * of the passage that this player wants to open.
			 */
			System.out.println("Which door would you like to open? Enter its direction.");
			System.out.print(">");
			String answer = aConsole.nextLine();

			/*
			 * State 2 (edge index): The index of the edge with this player's current location as starting room and 
			 * direction as the direction that the player specified is found, XOR that index is greater than or equal to the size
			 * of the edgeList.
			 */
			int i = getEdgeMultPassages(answer);	

			if (i < NightManor.edgeList.size()) {

				if(!NightManor.edgeList.get(i).isLocked) {

					if(!NightManor.edgeList.get(i).isOpen) {
						/*
						 * State 3 (opened): The index of the edge that was found was less than the size of the 
						 * edgeList, and that edge was unlocked and closed, and that edge and its reverse are opened and
						 * a statement is printed to indicate that to this player.
						 */
						NightManor.edgeList.get(i).setOpen(true);
						NightManor.edgeList.get(i).getReverse().setOpen(true);
						System.out.println("opened");
					}
					else {
						/*
						 * State 4 (open): This door was already open.
						 */
						System.out.println("door open already");
					}
				}
				else {
					/*
					 * State 5 (locked): The edge at the index that was found was locked.
					 */
					System.out.println("door locked");
				}
			}
			else {
				/*
				 * State 6 (no door): There isn't a passage in the specified direction and a statement is printed to indicate that there 
				 * is no door in the specified direction.
				 */
				System.out.println("no door");
			}
		}
		else {
			/*
			 * State 7 (one passage): This player's current location only has one passage, and a method is called
			 * to handle that case.
			 */
			oneDoorToOpen();
		}
	}

	/**
	 * INTENT: Opens this door if this door is unlocked and closed.
	 * 
	 * PRECONDITION 1 (one passage): This player's current room has one passage.
	 * 
	 * POSTCONDITION 1 (edge index): An index for an edge with this player's current 
	 * location as the starting room in the interval [0, edgeList.size()) was found, XOR the index that was found 
	 * was equal to -1. 
	 * 
	 * POST 2 (opened): This edge was opened, its reverse edge was opened, and
	 * a statement was printed to indicate that to this player if and only if the index of the edge that was found was 
	 * in the interval [0, edgeList.size()), the edge at that index had this player's current room as its starting room, and
	 * that edge was unlocked and closed.
	 * 
	 * POST 3 (didn't open): If the index of the edge that was found was in the interval [0, edgeList.size()), then 
	 * a statement was printed that indicated that the door was already open if and only if the edge was open XOR
	 * a statement was printed that indicated that the door was locked if and only if the edge was locked.
	 * 
	 * POST 4 (invalid index): A statement was printed that indicated that there wasn't a passage if and only if 
	 * the index of the edge that was found was not in the interval [0, edgeList.size()).
	 */
	public void oneDoorToOpen() {

		/*
		 * State 1 (edge index): The index of the edge with this player's current location as starting room is found, and index i 
		 * is in the interval [0, edgeList.size()), XOR index i = -1.
		 */
		int i = getEdgeOnePassage();

		if (i!=-1) {
			if(!NightManor.edgeList.get(i).isLocked) {

				if(!NightManor.edgeList.get(i).isOpen) {
					/*
					 * State 2 (opened): The index of the edge that was found was in the interval 
					 * [0, edgeList.size()), that edge was unlocked and closed, and that edge and its reverse
					 * are opened and a message is printed to indicate that.
					 */
					NightManor.edgeList.get(i).setOpen(true);
					NightManor.edgeList.get(i).getReverse().setOpen(true);
					System.out.println("opened");
				}
				else {
					/*
					 * State 3 (open already): The edge is already open, and a message is printed to indicate that.
					 */
					System.out.println("door open already");
				}
			}
			else {
				/*
				 * State 4 (locked): The edge at index i was locked, and a statement was printed to indicate that the door was 
				 * locked.
				 */
				System.out.println("door locked");
			}
		}
	}


	/**
	 * INTENT: Closes this door if this door is open
	 * 
	 * PRECONDITION 1 (number of passages): This player's current location has multiple passages or one passage.
	 * 
	 * POSTCONDITION 1 (multiple passages, find edge index): An index for an edge in edgeList was found with this player's current 
	 * location as the starting room and the direction of the door as the edge direction, XOR the index of the edge was greater than 
	 * or equal to edgeList.size() if and only if there were multiple passages that lead out of this 
	 * player's current location.
	 * 
	 * POST 2 (closed): This edge was closed, its reverse edge was closed, and
	 * a statement was printed that indicated that to this player if and only if the index of the edge that was found was 
	 * less than the size of the edgeList, and that edge was open.
	 * 
	 * POST 3 (already closed): If the index of the edge that was found was less than the size of the edgeList, then 
	 * a statement was printed to indicate that the door was already closed if and only if the edge was closed.
	 * 
	 * POST 4 (no door): A statement was printed that indicated that there was no door if and only if the index of the edge
	 * that was found was greater than or equal to the size of edgeList.
	 * 
	 * POST 5 (one door): If this player's current room did not have multiple passages, then a method was called to handle the case
	 * where there was only one passage.
	 */
	public void closeDoor() {

		if(yourLocation.multiplePassages) {
			/* 
			 * State 1 (multiple passages): There are multiple passages, and this player is asked to enter the direction 
			 * of the passage that this player wants to close.
			 */
			System.out.println("Which door would you like to close? Enter its direction.");
			System.out.print(">");
			String answer = aConsole.nextLine();

			/*
			 * State 2 (edge index): The index of the edge with this player's current location as starting room and 
			 * direction as the direction that the player specified is found, XOR that index is greater than or equal to the size
			 * of the edgeList.
			 */
			int i = getEdgeMultPassages(answer);

			if (i < NightManor.edgeList.size()) {

				if(NightManor.edgeList.get(i).isOpen) {
					/*
					 * State 3 (closed): The index of the edge that was found was less than the size of the 
					 * edgeList, that edge was open, and that edge and its reverse are closed and
					 * a statement is printed to indicate that to this player.
					 */
					NightManor.edgeList.get(i).setOpen(false);
					NightManor.edgeList.get(i).getReverse().setOpen(false);
					System.out.println("closed");
				}
				else {
					/*
					 * State 4 (closed already): The edge is already closed, and a message is printed to indicate that.
					 */
					System.out.println("door is already closed");
				}
			}else {
				/*
				 * State 5 (no door): There isn't a passage in the specified direction and a statement is printed to indicate that there 
				 * is no door in the specified direction.
				 */
				System.out.println("no door");
			}	
		}
		else {
			/*
			 * State 6 (one passage): This player's current location only has one passage, and a method is called to handle that case.
			 */
			oneDoorToClose();
		}
	}

	/**
	 * INTENT: Closes this door if this door is open.
	 * 
	 * PRECONDITION 1 (one passage): This player's current room has one passage.
	 * 
	 * POSTCONDITION 1 (edge index): An index for an edge with this player's current 
	 * location as the starting room was found in the interval [0, edgeList.size()), XOR the index that was found 
	 * was equal to -1. 
	 * 
	 * POST 2 (closed): This edge was closed, its reverse edge was closed, and
	 * a statement was printed to indicate that to this player if and only if the index of the edge that was found was 
	 * in the interval [0, edgeList.size()), the edge at that index had this player's current room as its starting room, and
	 * that edge was open.
	 * 
	 * POST 3 (already closed): If the index of the edge that was found was in the interval [0, edgeList.size()), then 
	 * a statement was printed that indicated that the door was already closed if and only if the edge was closed.
	 */
	public void oneDoorToClose() {
		/*
		 * State 1 (edge index): The index of the edge with this player's current location as starting room is found, and index i 
		 * is in the interval [0, edgeList.size()), XOR index i = -1.
		 */
		int i = getEdgeOnePassage();

		if (i!=-1) {
			if(NightManor.edgeList.get(i).isOpen) {
				/*
				 * State 2 (closed): The index of the edge that was found is in the interval 
				 * [0, edgeList.size()), that edge is open, and that edge and its reverse
				 * are closed and a message is printed to indicate that.
				 */
				NightManor.edgeList.get(i).setOpen(false);
				NightManor.edgeList.get(i).getReverse().setOpen(false);
				System.out.println("closed");
			}
			else {
				/*
				 * State 3 (closed already): The edge is already closed, and a message is printed to indicate that.
				 */
				System.out.println("door closed already");
			}
		}	
	}

	/**
	 * INTENT: Locks this door if this door is unlocked, this player has a key for this door, and this door is closed.
	 * 
	 * PRECONDITION 1 (multiple passages): This player's current location has multiple passages or one passage.
	 * 
	 * PRE 2 (key): The key that unlocks an edge can be used to lock that same edge.
	 * 
	 * POSTCONDITION 1 (multiple passages, edge index): An index for an edge in edgeList was found with this player's current 
	 * location as the starting room and the direction of the door as the edge direction, XOR the index of the edge was greater than 
	 * or equal to edgeList.size() if and only if there were multiple passages that lead out of this 
	 * player's current location.
	 * 
	 * POST 2 (locked): This edge was locked, its reverse edge was locked, and
	 * a statement was printed to indicate that to this player if and only if the index of the edge that was found was 
	 * less than the size of edgeList, that edge was unlocked, this player had the key that locked that edge, and 
	 * that edge was closed.
	 * 
	 * POST 3 (locked already, open, or no key): If the index of the edge that was found was less than the size of edgeList, then 
	 * a statement was printed that indicated that the door was already locked if and only if the edge was locked XOR
	 * a statement was printed that indicated that this player didn't have the key that locked this door if and only if 
	 * this player didn't have the key in inventory that locked this edge, XOR a statement was printed that indicated that 
	 * the door was open if and only if the edge was open. 
	 * 
	 * POST 4 (no door): A statement was printed that indicated that there was no door if and only if the index of the edge
	 * that was found was greater than or equal to the size of edgeList.
	 * 
	 * POST 5 (one passage): If this player's current room did not have multiple passages, then a method was called to handle the case
	 * where there was only one passage.
	 */
	public void lockDoor() {

		if(yourLocation.multiplePassages) {
			/*
			 * State 1 (multiple passages): There are multiple passages, and this player is asked to enter the direction 
			 * of the passage that this player wants to lock.
			 */
			System.out.println("Which door would you like to lock? Enter its direction.");
			System.out.print(">");
			String answer = aConsole.nextLine();

			/*
			 * State 2 (edge index): The index of the edge with this player's current location as starting room and 
			 * direction as the direction that the player specified is found, XOR that index is greater than or equal to the size
			 * of the edgeList.
			 */
			int i = getEdgeMultPassages(answer);

			if (i < NightManor.edgeList.size()) {

				if(!NightManor.edgeList.get(i).isLocked) {

					if(NightManor.edgeList.get(i).aKey.inInventory) {
						/*
						 * State 3 (locked): The index of the edge that was found is less than the size of the 
						 * edgeList, that edge is unlocked, this player has the key that locks that edge, and that 
						 * edge and its reverse edge is locked.
						 */
						if(!NightManor.edgeList.get(i).isOpen) {
							NightManor.edgeList.get(i).setLocked(true);
							NightManor.edgeList.get(i).getReverse().setLocked(true);
							System.out.println("locked");
						}
						else {
							/*
							 * State 4 (open): The edge is open, and a message is printed to indicate that.
							 */
							System.out.println("close door first");
						}
					}
					else {
						/*
						 * State 5 (no key): This player doesn't have the key that locks the edge at the index that
						 * was found, and a statement is printed to indicate that.
						 */
						System.out.println("need key first");
					}
				}
				else {
					/*
					 * State 6 (locked already): The edge is already locked, and a message is printed to indicate that.
					 */
					System.out.println("door already locked");
				}
			}
			else {
				/*
				 * State 6 (no door): There isn't a passage in the specified direction and a statement is printed to indicate that there 
				 * is no door in the specified direction.
				 */
				System.out.println("no door");
			}
		}
		else {
			/*
			 * State 7 (one passage): This player's current location only has one passage, and a method is called to handle that case.
			 */
			oneDoorToLock();		
		}
	}

	/**
	 * INTENT: Locks this door if this door is unlocked, closed, and this player has the key to lock this door.
	 * 
	 * PRECONDITION 1 (one passage): This player's current room has one passage.
	 * 
	 * PRE 2 (key): The key that unlocks an edge can be used to lock that same edge.
	 * 
	 * POSTCONDITION 1 (edge index): An index for an edge with this player's current 
	 * location as the starting room was found in the interval [0, edgeList.size()), XOR the index that was found 
	 * was equal to -1. 
	 * 
	 * POST 2 (locked): This edge was locked, its reverse edge was locked, and
	 * a statement was printed to indicate that to this player if and only if the index of the edge that was found was 
	 * in the interval [0, edgeList.size()), the edge at that index had this player's current room as its starting room,
	 * that edge was closed and unlocked, and this player had the key that locked this door.
	 * 
	 * POST 3 (already locked or open or no key): If the index of the edge that was found was in the interval [0, edgeList.size()), then 
	 * a statement was printed that indicated that the door was already locked if and only if the edge was locked, XOR
	 * a statement was printed that indicated that this player didn't have the key that locked this door if and only if 
	 * this player didn't have the key in inventory that locked this edge, XOR a statement was printed that indicated that 
	 * the door was open if and only if the edge was open. 
	 */
	public void oneDoorToLock() {

		/*
		 * State 1 (edge index): The index of the edge with this player's current location as starting room is found, and index i 
		 * is in the interval [0, edgeList.size()), XOR index i = -1.
		 */
		int i = getEdgeOnePassage();

		if (i != -1) {

			if(!NightManor.edgeList.get(i).isLocked) {

				if(NightManor.edgeList.get(i).aKey.inInventory) {

					if(!NightManor.edgeList.get(i).isOpen) {
						/*
						 * State 2 (locked): The index of the edge that was found is in the interval 
						 * [0, edgeList.size()), that edge is closed, this player has the key that locks that edge,
						 * and that edge and its reverse are locked and a message is printed to indicate that.
						 */	
						NightManor.edgeList.get(i).setLocked(true);
						NightManor.edgeList.get(i).getReverse().setLocked(true);
						System.out.println("locked");
					}
					else {
						/*
						 * State 4 (open): The edge is open, and a message is printed to indicate that.
						 */
						System.out.println("close door first");
					}
				}
				else {
					/*
					 * State 5 (no key): This player doesn't have the key that locks the edge at the index that
					 * was found, and a statement is printed to indicate that.
					 */
					System.out.println("need key first");
				}
			}
			else {
				/*
				 * State 6 (locked already): The edge is already locked, and a message is printed to indicate that.
				 */
				System.out.println("door already locked");
			}
		}
	}

	/**
	 * INTENT: Returns the index of the edge with this player's current room as the starting room and direction as the parameter
	 * aDirection if this player's current room has multiple passages or this player entered "go north", 
	 * "go south", "go west", or "go east".
	 * 
	 * PRECONDITION 1 (valid direction): aDirection equals "north", "south", "east", or "west".
	 * 
	 * PRE 2 (multiple passages): This player's current room has multiple passages or this player entered "go north", 
	 * "go south", "go west", or "go east".
	 * 
	 * POSTCONDITION 1 (valid index found): An index i was found in the interval [0,edgeList.size()) where the edge at that
	 * index had its starting room as this player's current room and direction as aDirection and was returned, XOR 
	 * i >= edgeList.size().
	 * 
	 * POST 2 (valid index not found): Index i >= edgeLise.size() and i was returned.
	 */
	public int getEdgeMultPassages(String aDirection) {

		int i=0;
		boolean done = false;

		while (i < NightManor.edgeList.size() && !done) {
			/*
			 * State 1: i < edgeList.size(), and the edge at index i has its starting room as this player's current room,
			 * direction as aDirection, and done is assigned true to end this loop, XOR such an edge was not found in the interval 
			 * [0, edgeList.size()).
			 */
			if (NightManor.edgeList.get(i).startRoom.getRoomNumber()== yourLocation.getRoomNumber()
					&& NightManor.edgeList.get(i).getDirection().substring(3).equalsIgnoreCase(aDirection)) {
				done = true; 
				i--;
			}
			i++;
		}

		/*
		 * State 2 (return i): i < edgeList.size(), done is true, and i is returned, XOR i >= edgeList.size(), 
		 * done is false, and i is returned
		 */
		return i;
	}

	/**
	 * INTENT: Returns the index of the edge with this player's current room as the starting room 
	 * if this player's current room doesn't have multiple passages.
	 * 
	 * PRECONDITION 1 (one passage): This player's current room has one passage.
	 * 
	 * POSTCONDITION 1 (valid index): Index i < edgeList.size(), the edge at index i had this player's 
	 * current room as its starting room, and i was returned, XOR i >= edgeList.size().
	 * 
	 * POST 2 (invalid index): Index i >= edgeList.size() and -1 was returned.
	 */
	public int getEdgeOnePassage() {
		int i = 0;
		boolean done = false;

		while (i < NightManor.edgeList.size() && !done) {
			/*
			 * State 1 (valid index): i < edgeList.size(), the edge at index i has this player's current room as its starting room, 
			 * and done is assigned true, XOR such an edge was not found in the interval [0, edgeList.size()).
			 */
			if (NightManor.edgeList.get(i).startRoom.getRoomNumber()== yourLocation.getRoomNumber()) {
				done = true; 
				i--;
			}
			i++;
		}

		/*
		 * State 2 (done): return i if and only if i is the index of an edge where that edge's starting room is this player's current room.
		 */
		if (done) {
			return i;
		}

		/*
		 * State 3 (!done): i >= edgeList.size(), done is false, and an edge where its starting room is this player's current room wasn't found 
		 * in the interval [0, edgeList.size()).
		 */
		return -1;	
	}

	/**
	 * INTENT: Prints a menu for the player to select what he wants to see from inventory.
	 * 
	 * SHORTHAND (in inventory): An object is in this player's inventory if and only if this player took that object, and that object's data member
	 * that indicates whether it is in inventory is set to true.
	 * 
	 * SHORTHAND (objects in inventory): Food objects and Key objects can be stored in inventory.
	 * 
	 * PRECONDITION 1 (response): This player's input does not include the number or the white space before the word(s) presented 
	 * in the menu.
	 * 
	 * POSTCONDITION 1 (menu printed): A menu was printed that showed this player's options to check inventory, and this player 
	 * entered the option this player wanted printed.
	 * 
	 * POST 2 (selected inventory printed): The option that this player selected was printed in alphabetical order. 
	 */
	public void checkInventory() {
		/*
		 * State 1 (menu): A menu that shows this player's inventory options is printed, and this player's selection 
		 * is recorded.  
		 */
		System.out.println("Inventory: ");
		System.out.println("all items");
		System.out.println("food");
		System.out.print(">");
		String answer = aConsole.nextLine();

		if(answer.equalsIgnoreCase("all items")) {
			/*
			 * State 2 (all items): This player selected "all items", and a method that prints 
			 * all inventory items in alphabetical order is called. 
			 */
			printAllItems();
		}
		else {
			if(answer.equalsIgnoreCase("food")) {
				/*
				 * State 3 (food): This player selected "food", and a method that prints all food items in inventory 
				 * in alphabetical order is called.
				 */
				printInventoryFood();
			}
		}	
	}

	/**
	 * INTENT: Prints all of the food items in this player's inventory in alphabetical order.
	 * 
	 * SHORTHAND (food items): Food items (also called Food objects) are instances of Food.java.
	 * 
	 * POSTCONDITION 1 (sorted): The food items that were in this player's inventory were sorted in alphabetical order. 
	 * 
	 * POST 2 (printed): The food items that were in this player's inventory were printed in alphabetical order with their health benefit.
	 * 
	 * POST 3 (empty): A statement was printed that indicated that this player didn't have any food items if and only if 
	 * this player didn't have any food items in inventory.
	 */
	public static void printInventoryFood() {

		boolean empty = true;

		/*
		 * State 1 (sorted): A method is called to sort the food items in this player's inventory in alphabetical order. 
		 */
		sort(NightManor.objectsInRooms,0, NightManor.objectsInRooms.size()-1);

		for (int i = 0; i < NightManor.objectsInRooms.size();i++) {

			if(NightManor.objectsInRooms.get(i) instanceof Food) {

				if(NightManor.objectsInRooms.get(i).inInventory) {
					/*
					 * State 2 (printed): 0 <= i < objectsInRooms.size(), the Objects at the index i in objectsInRooms is 
					 * a Food object that is in inventory, and that Food object is printed with its health benefit. 
					 */
					empty =false;
					Food food = (Food) NightManor.objectsInRooms.get(i);
					System.out.println(food.aName + " -- health benefit: " + food.healthBenefit);
				}
			}
		}

		if (empty) {
			/*
			 * State 3 (empty): i >= objectsInRooms.size() and empty is true (i.e., no Food objects were found in inventory), and 
			 * a statement is printed that indicates that to this player. 
			 */
			System.out.println("You don't have any food.");
		}
	}

	/**
	 * INTENT: Prints all of the items in inventory in alphabetical order. 
	 * 
	 * SHORTHAND (item): An item in inventory is a Key object or Food object in inventory. 
	 * 
	 * POSTCONDITION 1 (sorted): All items in this player's inventory were sorted in alphabetical order.
	 * 
	 * POST 2 (printed): All items that were in this player's inventory were printed in alphabetical order.
	 * 
	 * POST 3 (empty): A statement was printed that indicated that this player didn't have items if and only if 
	 * this player didn't have any items in inventory.
	 */
	public static void printAllItems() {
		boolean empty = true;

		/*
		 * State 1 (sorted): A method is called to sort the items in this player's inventory in alphabetical order. 
		 */
		sort(NightManor.objectsInRooms, 0, NightManor.objectsInRooms.size()-1);

		for (int i = 0; i < NightManor.objectsInRooms.size(); i++) {

			if(NightManor.objectsInRooms.get(i).inInventory) {
				/*
				 * State 2 (printed): 0 <= i < objectsInRooms.size(), the Objects at the index i in objectsInRooms is 
				 * in inventory, and that Objects name is printed. 
				 */
				empty = false;
				System.out.println(NightManor.objectsInRooms.get(i).aName);
			}
		}

		if (empty) {
			/*
			 * State 3 (empty): i >= objectsInRooms.size() and empty is true (i.e., no Key objects and no Food objects were found in inventory), and 
			 * a statement is printed that indicates that to this player. 
			 */
			System.out.println("You don't have anything in inventory.");
		}
	}

	/**
	 * INTENT: Sort an ArrayList that contains Objects into alphabetical order using quick sort. 
	 * 
	 * EXAMPLE: unsorted list: apple, orange, bread, banana    sorted list: apple, banana, bread, orange.
	 * 
	 * INV (Content Conserved): arr[low, high] is a fixed multiset. 
	 * 
	 * SHORTHAND(pivot): The pivot is an element in ArrayList arr that splits the sub-array into a left partition and a right partition. 
	 * 
	 * SHORTHAND(left partition): The left partition is arr[low, the pivot index -1] where low is the first index of the sub-array.
	 * 
	 * SHORTHAND(right partition): The right partition is arr[the pivot index + 1, high] where high is the last index of the sub-array.
	 * 
	 * PRECONDITION 1 (Objects): The elements that ArrayList arr contains are Objects. 
	 * 
	 * PRE 2(non-trivial): arr.size() > 1
	 * 
	 * PRE 3 (valid indices): 0 <= low <= pivotIdx <= high < arr.size()
	 * 
	 * POSTCONDITION 1 (left partition sorted): For all indices i and x in arr[0, pivotIdx] where i < x, arr[i] <= arr[x]  
	 * 
	 * POST 2 (right partition sorted): For all indices i and x in arr[pivotIdx, arr.size()-1] where i < x, arr[i] <= arr[x]
	 * 
	 * POST 3 (arr sorted): All indices i and x in arr[0, arr.size()-1] where i < x, arr[i] <= arr[x]
	 */
	public static void sort(ArrayList<Objects> arr, int low, int high) {

		//State 1 (return): There was one element or no elements in the sub-array arr[low, high].
		if (low >= high) {
			return;
		}

		//State 2 (partition): For each index i in arr[low, pivotIdx-1] and each index x in arr[pivotIdx + 1, high], 
		//arr[i] <= pivotIdx AND arr[x] >= pivotIdx.
		int pivotIdx = partition(arr, low, high);

		//State 3 (sort left): sort was called on arr[low, pivotIdx-1], which was the left partition of the sub-array arr[low, high].
		sort(arr, low, pivotIdx-1);

		//State 4 (sort right): sort was called on arr[pivotIdx+1, high], which was the right partition of the sub-array arr[low,high]
		sort(arr, pivotIdx+1, high);	
	}

	/**
	 * INTENT: Divides the sub-array into a left partition and a right partition where all elements in
	 * the left partition are less than or equal to the pivot and all elements in the right partition are 
	 * greater than or equal to the pivot.
	 * 
	 * INV (content conserved): arr[low, high] is a fixed multiset
	 * 
	 * PRECONDITION 1 (valid indices): 0 <= low <= pivotIdx <= high < arr.size()
	 * 
	 * PRE 2 (ArrayList arr): arr > 1 AND arr contains Objects
	 * 
	 * PRE 3 (names): All Objects have a name, and some names have capital letters.
	 * 
	 * PRE 4 (pivot index): The pivot index is the rightmost index in arr[low, high].
	 * 
	 * POSTCONDITION 1 (left partition): For each index i in arr[low, pivotIdx-1], arr[i] <= pivotIdx 
	 * 
	 * POST 2 (right partition): For each index x in arr[pivotIdx + 1, high], arr[x] >= pivotIdx
	 * 
	 * POST 3 (complement): low >= high, and the index of the pivot was returned.
	 */
	public static int partition(ArrayList<Objects> arr, int low, int high) {

		int pivotIdx = high;
		Objects pivot = arr.get(high);

		boolean done= false;
		boolean loop = true;

		while (loop) {

			//State 1 (organize left): low >= high or arr[low]>arr[pivotIdx] XOR low < high AND low is incremented
			while(arr.get(low).compareTo(pivot) != 1 && !done) {
				low++;
				if (low >= high) {
					done = true;
				}
			}

			done = false;

			//State 2 (organize right): low >= high or arr[high] < arr[pivotIdx] XOR low < high AND high is decremented
			while(arr.get(high).compareTo(pivot) != -1  && !done) {
				high--;
				if(low >= high) {
					done = true;
				}
			}

			done = false;

			if (low >= high) {
				//State 3 (exit loop): There is one element or no elements in the sub-array arr[low, high], and loop is assigned false.
				loop = false;
			}

			if (loop) {
				//State 4 (swapped): low < high, arr[low] > arr[pivotIdx], arr[high] < arr[pivotIdx], and arr[low] is swapped with arr[high].
				Collections.swap(arr, low, high); 
			}
		}

		//State 5 (pivot swapped): low >= high and arr[low] is swapped with arr[pivotdIdx]
		Collections.swap(arr, low, pivotIdx);

		//State 6 (pivot index returned): low is the index of the pivot, and low is returned.
		return low;
	}


}//end of Player class 
