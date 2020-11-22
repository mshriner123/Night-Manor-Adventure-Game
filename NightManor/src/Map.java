import java.util.PriorityQueue;
/**
 * Map extends Objects and contains methods to construct a Map, take a Map, find the shortest path 
 * from the player's current room to every other room on "Night Manor map", 
 * and help the previous function. See the method headers for more details.
 * 
 * @author Michael Shriner
 */
public class Map extends Objects {

	/**
	 * INTENT: Constructs a Map.
	 * 
	 * PRECONDITION (take): take is always true
	 * 
	 * POSTCONDITION: The parent constructor was called to initialize the data members of this Map.
	 */
	public Map (String name, Room room, boolean take, boolean inventory) {
		super(name, room, take, inventory);
	}

	/**
	 * INTENT: Takes a map and adds it to inventory.
	 * 
	 * PRECONDITION 1 (two maps): There is a map called "Map of the Tunnels" and a map called "Night Manor map". "Map of the Tunnels"
	 * is in the drinking room, and "Night Manor map" is in the master bedroom.
	 * 
	 * PRE 2 (taken): The player can only take a map if the player is in the same room as the map, the map is not in an unopened cabinet,
	 * and the map is not already in inventory.
	 * 
	 * POSTCONDITION 1 (Night Manor map): The player was in the master bedroom, the master bedroom cabinet was open, and the map called "Night Manor map"  
	 * was added to the player's inventory, XOR the map called "Night Manor map" was not added to inventory.
	 * 
	 * POST 2 (Map of the Tunnels): The player was in the drinking room, and "Map of the Tunnels" was added to inventory, XOR "Map of the Tunnels"
	 * was not added to inventory.
	 */
	public static void take() {

		/*
		 * State 1 (Night Manor map): The player is in the master bedroom, the master bedroom cabinet is open, and "Night Manor map" 
		 * is in inventory, XOR "Night Manor map" is not in inventory.
		 */
		if(NightManor.thePlayer.getLocation().getRoomNumber() == NightManor.masterBedroom.getRoomNumber()) {

			if (NightManor.masterBedCabinet.isOpen) {

				Objects map = NightManor.aMap;
				map.addToInventory();
			}	
		}
		/*
		 * State 2 (Map of the Tunnels): The player is in the drinking room, and "Map of the Tunnels" is in inventory, XOR
		 * "Map of the Tunnels" is not in inventory.
		 */
		else {
			if(NightManor.thePlayer.getLocation().getRoomNumber() == NightManor.drinkingRoom.getRoomNumber()) {
				Objects map = NightManor.tunnelMap;
				map.addToInventory();
			}
			else {
				System.out.println("There isn't a map in this room.");
			}
		}

	}

	/**
	 * INTENT: Returns the shortest paths from the player's current room to each room in "Night Manor map".
	 * 
	 * SHORTHAND (shortest path): The shortest path is the path with the least cost. 
	 * 
	 * SHORTHAND (visited): A room was visited if and only if it was added to returnPaths.
	 * 
	 * PRECONDITION 1 (non-negative): The weights of the edges are non-negative.
	 * 
	 * PRE 2 (Night Manor map): The tunnel rooms and the greenhouse are not on "Night Manor map" and cannot be seen using dijkstras() 
	 * because dijkstras() is only used with "Night Manor map".
	 * 
	 * POSTCONDITION 1 (shortest path): The shortest path to each room was appended to returnPaths.
	 * 
	 * POST 2 (visited): Each room was visited.
	 * 
	 * POST 3 (complement): returnPaths was returned.
	 */
	public String dijkstras() {

		String returnPaths = "";

		int theStartRoom = NightManor.thePlayer.getLocation().getRoomNumber();

		int [] distance = new int [Room.rooms.size()];

		PriorityQueue <ShortestPath> thePriorityQ = new PriorityQueue <ShortestPath>();

		makeAllUnvisited();

		/*
		 * State 1 (max distance): All distances from the start room to the room at index i are initialized to the maximum value of an 
		 * integer. The indices in the distance array correspond to the indices in the ArrayList rooms.
		 */
		for (int i = 0; i < distance.length; i++) {
			distance[i]= Integer.MAX_VALUE;
		}

		distance[theStartRoom] = 0;

		int aLength = distance[theStartRoom];
		int aRoomNum = theStartRoom;
		String aName = Room.rooms.get(theStartRoom).getRoomName();
		String aPath = Room.rooms.get(theStartRoom).getRoomName();

		ShortestPath sp = new ShortestPath(aName, aLength, aPath, aRoomNum);

		/*
		 * State 2 (start room): The ShortestPath object containing the shortest path from the start room to the start room is 
		 * added to the priority queue.
		 */
		thePriorityQ.add(sp);

		while(!thePriorityQ.isEmpty()) {

			/*
			 * State 3 (removed): aDQRoom contains the shortest path from the start room to aDQRoom in the priority queue, and 
			 * aDQRoom is removed from the priority queue.
			 */
			ShortestPath aDQRoom = thePriorityQ.remove();

			if (Room.rooms.get(aDQRoom.getRoomNumber()).isUnvisited()) {
				/*
				 * State 4 (unvisited): aDQRoom is added to returnPaths if and only if it is unvisited.
				 */		

				aName = aDQRoom.getName();
				aPath = aDQRoom.getPath();
				aRoomNum = aDQRoom.getRoomNumber();

				returnPaths += "Shortest path from " + Room.rooms.get(theStartRoom).getRoomName() + " to " + aName + ": " + aPath + "\n";

				/*
				 * State 5 (visited set): aDQRoom is set to visited.
				 */
				Room.rooms.get(aRoomNum).setVisited();		
			}

			/*
			 * State 6 (visited) aDQRoom is visited, and dijkstrasUnvisitedAdj is called to find the shortest paths from the start room to 
			 * the unvisited, adjacent rooms to aDQRoom.
			 */
			dijkstrasUnvisitedAdj(aDQRoom, distance, thePriorityQ);
		}

		/*
		 * State 7 (complement): returnPaths is returned, and all rooms are visited.
		 */
		return returnPaths;

	}

	/**
	 * INTENT: Adds the shortest paths from the start room to the unvisited, adjacent rooms to aDQRoom if such rooms exist.
	 * 
	 * SHORTHAND(shortest path): see dijkstras method header
	 * 
	 * SHORTHAND (destination room): A destination room is an unvisited, adjacent room to aDQRoom.
	 * 
	 * SHORTHAND (path length): The path length to a destination room is the sum of the distance to aDQRoom and 
	 * the distance between aDQRoom and the destination room (which is always 1).
	 * 
	 * PRECONDITION (aDQRoom): aDQRoom is visited.
	 * 
	 * POSTCONDITION 1 (added): The shortest path to each destination room was added to the priority queue.
	 * 
	 * POST 2 (complement): There were no destination rooms, and this method returned.
	 */
	private void dijkstrasUnvisitedAdj (ShortestPath aDQRoom, int [] distance, PriorityQueue<ShortestPath> thePriorityQ ) {

		ShortestPath sp1;
		int aRoomNum = aDQRoom.getRoomNumber();
		int aLength = aDQRoom.getPathLength();
		String aPath = aDQRoom.getPath();
		String locked;

		for (int i = 0; i < NightManor.edgeList.size(); i++) {

			if (NightManor.edgeList.get(i).startRoom.getRoomNumber() == aRoomNum) {

				if (NightManor.edgeList.get(i).destRoom.isUnvisited()) {

					/*
					 * State 1 (destination room): There is a destination room at index i in edgeList.
					 */

					if(distance[NightManor.edgeList.get(i).destRoom.getRoomNumber()] > (aLength + 1)) {

						/*
						 * State 2 (shortest path): The destination room at index i has a shorter path than it's current distance
						 * from the start room.
						 */

						if (NightManor.edgeList.get(i).isLocked) {
							locked = "(passage locked)";
						}
						else {
							if (!NightManor.edgeList.get(i).passage) {
								locked = "(no passage)";
							}
							else {
								locked = "";
							}
						}

						/*
						 * State 3 (added): The shortest path to the destination room is added to the priority queue.
						 */
						sp1 = new ShortestPath (NightManor.edgeList.get(i).destRoom.getRoomName(), 
								distance[NightManor.edgeList.get(i).destRoom.getRoomNumber()], 
								aPath + " -- " + locked + " " + NightManor.edgeList.get(i).destRoom.getRoomName(), 
								NightManor.edgeList.get(i).destRoom.getRoomNumber());

						thePriorityQ.add(sp1);	
					}
				}
			}	
		}
	}
	/*
	 * State 4 (complement): There is no destination room, and this method returns.
	 */

	/**
	 * INTENT: Sets all of the Room objects in ArrayList rooms to unvisited.
	 * 
	 * PRECONDITION (non-trivial): ArrayList rooms is greater than 0.
	 * 
	 * POSTCONDITION: Each Room object at an index i in the interval [0, rooms.length) in rooms was set to unvisited.
	 */
	private void makeAllUnvisited() {
		for (int i=0; i<Room.rooms.size(); i++) {
			Room.rooms.get(i).setUnvisited();
		}
	}

}
