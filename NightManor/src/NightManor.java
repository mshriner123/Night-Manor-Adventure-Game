import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * NightManor implements Runnable and contains methods to add enemies to rooms, start and create
 * threads for enemy movement, add objects to rooms, create edges between rooms, process commands, offer a deal given by a ghost, 
 * determine what to remove when the player asks to remove something and call the appropriate method, determine what to 
 * take when the player asks to take something and call the appropriate method, determine which map the player wants to use when the player
 * enters "use map" and call the appropriate method, and run a NightManor thread. See the method headers for more details.
 * 
 * Its data members include Room objects, an ArrayList of Edge objects, an ArrayList of objects in rooms, an ArrayList that contains 
 * Enemy objects, an ArrayList of sub-threads, a Player, a Maze, keys, food items, cabinets, Enemy objects, bags, treasure, maps, and 
 * a SpiderWeb.
 * 
 * @author Michael Shriner
 */
public class NightManor implements Runnable{

	static Room prisonCellOne = new Room ("Prison Cell One", 0, false);
	static Room prisonCellTwo = new Room ("Prison Cell Two",1, false);
	static Room prisonCellThree = new Room ("Prison Cell Three", 2, false);
	static Room centerPrisonHall = new Room ("Center Prison Hall", 3, true);
	static Room westPrisonHall = new Room ("West Prison Hall", 4, true);
	static Room eastPrisonHall = new Room ("East Prison Hall", 5, true);
	static Room maze = new Room ("Maze", 6, true);
	static Room kitchen = new Room ("Kitchen",7, true);
	static Room wineCellar = new Room ("Wine Cellar", 8, true);
	static Room northWineCellar = new Room ("North Wine Cellar", 9, true);
	static Room informalDiningRoom = new Room ("Informal Dining Room", 10, false);
	static Room southWineCellar = new Room ("South Wine Cellar", 11, false);
	static Room centerWineCellar = new Room ("Center Wine Cellar", 12, true);
	static Room diningRoom = new Room ("Dining Room", 13, true);
	static Room ballRoom = new Room ("Ballroom", 14, true);
	static Room drinkingRoom = new Room ("Drinking Room", 15, true);
	static Room sittingRoom = new Room ("Sitting Room", 16, true);
	static Room grandFoyer = new Room ("Grand Foyer", 17, true);
	static Room staircaseOne = new Room ("South Grand Foyer Staircase", 18, true);
	static Room southHall = new Room ("South Hall, Floor Two", 19, true);
	static Room centerHall = new Room ("Center Hall, Floor Two", 20, true);
	static Room northHall = new Room ("North Hall, Floor Two", 21, true);
	static Room library = new Room ("Library", 22, true);
	static Room staircaseThree = new Room ("Hidden Staircase", 23, true);
	static Room treasureRoom = new Room ("Treasure Room", 24, false);
	static Room masterBedroom = new Room ("Master Bedroom", 25, false);
	static Room darkHall = new Room ("Dark Hall", 26, true);
	static Room spiderWeb = new Room ("Spider Web", 27, true);
	static Room batCave = new Room ("Bat Cave", 28, false);
	static Room tunnelRoom0 = new Room("Tunnel Room 0", 29, true);
	static Room tunnelRoom1 = new Room("Tunnel Room 1", 30, true);
	static Room tunnelRoom2 = new Room("Tunnel Room 2", 31, true);
	static Room tunnelRoom3 = new Room("Tunnel Room 3", 32, true);
	static Room tunnelRoom4 = new Room("Tunnel Room 4", 33, true);
	static Room tunnelRoom5 = new Room("Tunnel Room 5", 34, true);
	static Room tunnelRoom6 = new Room("Tunnel Room 6", 35, true);
	static Room tunnelRoom7 = new Room("Tunnel Room 7", 36, true);
	static Room tunnelRoom8 = new Room("Tunnel Room 8", 37, true);
	static Room tunnelRoom9 = new Room("Tunnel Room 9", 38, true);
	static Room tunnelRoom10 = new Room("Tunnel Room 10", 39, true);
	static Room tunnelRoom11 = new Room("Tunnel Room 11", 40, true);	
	static Room tunnelRoom12 = new Room("Tunnel Room 12", 41, true);
	static Room tunnelRoom13 = new Room("Tunnel Room 13", 42, true);
	static Room tunnelRoom14 = new Room("Tunnel Room 14", 43, true);
	static Room tunnelRoom15 = new Room("Tunnel Room 15", 44, true);
	static Room tunnelRoom16 = new Room("Tunnel Room 16", 45, true);
	static Room tunnelRoom17 = new Room("Tunnel Room 17", 46, true);
	static Room frontOfNightManor = new Room ("Front of Night Manor", 47, false);
	static Room greenhouse = new Room("Greenhouse", 48, false);

	static ArrayList <Edge> edgeList = new ArrayList<Edge>();

	static ArrayList<Objects> objectsInRooms = new ArrayList<Objects>();

	static ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	static ArrayList <ManageSubThreads> subThreads = new ArrayList <ManageSubThreads>();

	static Player thePlayer = new Player(prisonCellOne, 100);

	static Maze theMaze = new Maze();

	static Key keyToCellThree = new Key ("prison cell three key", eastPrisonHall, false);
	static Key keyToCellOne = new Key ("prison cell one key", westPrisonHall, false);
	static Key keyToInformalDining = new Key ("informal dining room key", kitchen,false);
	static Key nightManorKey = new Key ("Night Manor key", tunnelRoom13, false);
	//the existence of frontYardKey is not indicated to the player at this stage in the game
	static Key frontYardKey = new Key ("Key to Grand Foyer Door", greenhouse, false);

	static Cabinet theKitchenCabinet = new Cabinet ("The Kitchen Cabinet", kitchen, false, false, true, false);
	static Cabinet masterBedCabinet = new Cabinet ("The Master Bedroom Cabinet", masterBedroom,false, false, true, false);

	static Food anApple = new Food ("apple", kitchen, true, false, 10);
	static Food aBanana = new Food ("banana", kitchen, true, false, 15);
	static Food bread = new Food ("bread", kitchen, true, false, 10);
	static Food aHand = new Food ("human hand", informalDiningRoom, true, false, -15);
	static Food aGlassOfWhiskey = new Food ("whiskey", drinkingRoom, true, false, 20);
	static Food bloodWine = new Food ("wine", centerWineCellar, true, false, 15);

	static Treasure necklace = new Treasure ("gold necklace", false, 50, 10);
	static Treasure ring = new Treasure ("four diamond encrusted gold rings",false, 180, 20);
	static Treasure watch = new Treasure ("watch",false, 30, 5);
	static Treasure diamonds = new Treasure ("three diamonds", false, 300, 60);
	static Treasure rubies = new Treasure ("ruby", false, 90, 18);
	static Treasure emerald = new Treasure ("emerald", false, 45, 8);
	static Treasure opal = new Treasure ("two opals",false, 190, 50);
	static Treasure goldBars = new Treasure ("three gold bars", false, 630, 120);
	static Treasure earrings = new Treasure ("diamond earrings", false, 90, 8);
	static Treasure cuffLinks = new Treasure ("gold cuff links", false, 65, 4);
	static Treasure fabergeEggs = new Treasure ("faberge egg", false, 250, 50);
	static Treasure goldCoins = new Treasure ("bag of gold coins", false, 450, 150);
	static Treasure tiara = new Treasure ("gold and diamond tiara",false, 200, 15);
	static Treasure scepter = new Treasure ("scepter", false,150, 45);
	static Treasure crown = new Treasure ("crown", false,180, 25);
	static Treasure medals = new Treasure ("three gold medals",false, 60, 30);

	static Bag smallBag = new Bag ("small treasure bag", treasureRoom, true, false, 80);
	static Bag mediumBag = new Bag ("medium treasure bag", masterBedroom, true, false, 200); 
	static Bag largeBag = new Bag ("large treasure bag", prisonCellThree, true, false, 350);

	static Enemy vamp1 = new Enemy ("Lucien", 100, "Lucien is tall with slick black hair, has a skinny figure, pale skin, and red eyes.", informalDiningRoom);
	static Enemy vamp2 = new Enemy ("Alexandra", 100, "Alexandra has curly black and grey hair and is wearing the type of dress you would see at a funeral.", informalDiningRoom);
	static Enemy ghost1 = new Enemy ("Camelia", 100, "Camelia is a mere outline of what was once a human. She is transparent and difficult to see.", ballRoom);
	static Enemy ghost2 = new Enemy ("Constantin", 100, "Constantin is a black shadow in the shape of someone you once knew.", grandFoyer);

	static Map aMap = new Map ("Night Manor map", masterBedroom, true, false);
	static Map tunnelMap = new Map ("Map of the Tunnels", drinkingRoom, true, false);

	static SpiderWeb sWeb = new SpiderWeb(14);

	/**
	 * INTENT: Adds enemies to rooms and the ArrayList enemies.
	 * 
	 * PRECONDITION 1 (ArrayList): ArrayList enemies was instantiated. 
	 * 
	 * PRE 2 (Room): Each Room object was instantiated. 
	 * 
	 * PRE 3 (Enemy): Each Enemy object was instantiated.
	 * 
	 * PRE 4 (number of enemies): There can be at most two enemies in a room.
	 * 
	 * POSTCONDITION 1 (added to room): Each Enemy object was added to a Room object.
	 * 
	 * POST 2 (added to ArrayList): Each Enemy that was added to a Room was also added to ArrayList enemies.
	 */
	public static void addEnemies() {
		/*
		 * State 1 (enemies added to rooms): Some Room objects contain Enemy objects.
		 */
		informalDiningRoom.addEnemy(vamp1);
		informalDiningRoom.addEnemy(vamp2);
		ballRoom.addEnemy(ghost1);
		grandFoyer.addEnemy(ghost2);

		/*
		 * State 2 (enemies added to ArrayList): ArrayList enemies contains all instantiated Enemy objects. 
		 */
		enemies.add(vamp1);
		enemies.add(vamp2);
		enemies.add(ghost1);
		enemies.add(ghost2);
	}

	/**
	 * INTENT: Creates a thread for each Enemy and starts that thread.
	 * 
	 * SHORTHAND (S): S is the size of the ArrayList enemies.
	 *
	 *PRECONDITION 1 (non-trivial): S > 0.
	 *
	 *PRE 2 (runnable): Enemy.java implements Runnable. 
	 *
	 *POSTCONDITION 1 (threads): For each index i in ArrayList enemies, a Thread was created for 
	 *the Enemy at i and started.
	 *
	 *POST 2 (complement): i = S
	 */
	public static void startEnemyMovement() {

		for(int i = 0; i < enemies.size(); i++) {

			Thread enemyMove = new Thread(enemies.get(i)); 
			subThreads.add(new ManageSubThreads (enemyMove));
			enemyMove.start(); 
		}
	}

	/**
	 * INTENT: Adds objects to rooms.
	 * 
	 * PRECONDITION 1 (ArrayList): ArrayList objectsInRooms of type Objects was initialized.
	 * 
	 * PRE 2(Objects): Each Objects was initialized.
	 * 
	 * POSTCONDITION: ArrayList objectsInRooms contains each Objects. 
	 */
	public static void addObjectsToRooms() {
		objectsInRooms.add(keyToCellThree);
		objectsInRooms.add(keyToInformalDining);
		objectsInRooms.add(keyToCellOne);
		objectsInRooms.add(anApple);
		objectsInRooms.add(aBanana);
		objectsInRooms.add(bread);
		objectsInRooms.add(aHand);
		objectsInRooms.add(aGlassOfWhiskey);
		objectsInRooms.add(bloodWine);
		objectsInRooms.add(smallBag);
		objectsInRooms.add(mediumBag);
		objectsInRooms.add(largeBag);
		objectsInRooms.add(aMap);
		objectsInRooms.add(nightManorKey);
		objectsInRooms.add(tunnelMap);
		objectsInRooms.add(frontYardKey);
	}

	/**
	 * INTENT: Creates edges between rooms.
	 * 
	 * SHORTHAND(connected rooms): Connected rooms are rooms that have an edge between them.
	 * 
	 * SHORTHAND(reverse edge): For each Edge (denoted Edge1 here), there exists a reverse Edge, which is an Edge where its direction of travel is the 
	 * opposite of Edge1's direction of travel. For example, if Edge1 has direction north and leads from prison cell one to west prison hall, its reverse 
	 * Edge has direction south and leads from west prison hall to prison cell one. 
	 * 
	 * SHORTHAND (locked edge): A locked Edge is an Edge where a key is required to move from the starting room to the 
	 * destination room of that Edge.
	 * 
	 * PRECONDITION 1 (passage): Connected rooms have a passage between them.
	 * 
	 * PRE 2 (number of edges): There are at least two edges for each room. One edge to leave the room
	 * and one edge to enter the room. 
	 * 
	 * PRE 2(locked edges): There are locked edges, and keys exist that unlock those edges.
	 * 
	 * POSTCONDITION 1 (edges initialized): For each passage leading out of a Room, an Edge object was initialized such that
	 * its starting Room was the Room that the passage lead out from, its destination Room was the room that the Edge lead
	 * to, its direction was the direction (north, south, west, or east) from the starting Room to the destination Room, 
	 * and it had boolean values that indicated whether it was open or locked.
	 * 
	 * POST 2 (set reverse): Each Edge had a reverse Edge.
	 * 
	 * POST 3(keys): Each locked Edge had a key.
	 * 
	 * POST 4 (ArrayList): ArrayList edgeList contained each Edge object.
	 */
	public static void createEdges() {

		/*
		 * State 1 (edges initialized): For each passage leading out of a Room, an Edge is initialized.
		 */
		Edge edgeOne = new Edge(prisonCellOne, westPrisonHall, true, false, "go north");
		Edge edgeTwo = new Edge(westPrisonHall, prisonCellOne, true, false, "go south");
		Edge edgeThree = new Edge(westPrisonHall, centerPrisonHall, false, true, "go east");
		Edge edgeFour = new Edge(centerPrisonHall, westPrisonHall, false, true, "go west");
		Edge edgeFive = new Edge(centerPrisonHall, prisonCellTwo, false, false, "go south");
		Edge edgeSix = new Edge(prisonCellTwo, centerPrisonHall, false, false, "go north");
		Edge edgeSeven = new Edge(centerPrisonHall, eastPrisonHall, false, true, "go east");
		Edge edgeEight = new Edge(eastPrisonHall, centerPrisonHall, false, true, "go west");
		Edge edgeNine = new Edge(centerPrisonHall, maze, false, true, "go north");
		Edge edgeTen = new Edge(maze, centerPrisonHall, false, true, "go south");
		Edge edgeEleven = new Edge(eastPrisonHall, prisonCellThree, true, false, "go south");
		Edge edge12 = new Edge(prisonCellThree, eastPrisonHall, true, false, "go north");
		Edge edge13 = new Edge (maze, kitchen, false, true,"go north");
		Edge edge14 = new Edge(kitchen, maze, false, true, "go south");
		Edge edge15 = new Edge (kitchen, wineCellar, false, false, "go west");
		Edge edge16 = new Edge (wineCellar, kitchen, false, false, "go east");
		Edge edge17 = new Edge (wineCellar, centerWineCellar, false, true, "go west");
		Edge edge18 = new Edge (centerWineCellar, wineCellar, false, true, "go east");
		Edge edge19 = new Edge (centerWineCellar, southWineCellar, false, true, "go south");
		Edge edge20 = new Edge (southWineCellar, centerWineCellar, false, true, "go north");
		Edge edge21 = new Edge (centerWineCellar, northWineCellar, false, true, "go north");
		Edge edge22 = new Edge (northWineCellar, centerWineCellar, false, true, "go south");
		Edge edge23 = new Edge (northWineCellar, informalDiningRoom, true, false, "go north");
		Edge edge24 = new Edge (informalDiningRoom, northWineCellar, true, false, "go south");	
		Edge edge25 = new Edge (kitchen, sittingRoom, false, false, "go east");
		Edge edge26 = new Edge (sittingRoom, kitchen, false, false, "go west");	
		Edge edge27 = new Edge (sittingRoom, grandFoyer, false, false, "go east");
		Edge edge28 = new Edge (grandFoyer, sittingRoom, false, false, "go west");	
		Edge edge29 = new Edge (sittingRoom, drinkingRoom, false, false, "go north");
		Edge edge30 = new Edge (drinkingRoom, sittingRoom, false, false, "go south");	
		Edge edge31 = new Edge (kitchen, diningRoom, false, false, "go north");
		Edge edge32 = new Edge (diningRoom, kitchen, false, false, "go south");	
		Edge edge33 = new Edge (diningRoom, drinkingRoom, false, false, "go east");
		Edge edge34 = new Edge (drinkingRoom, diningRoom, false, false, "go west");
		Edge edge35 = new Edge (diningRoom, ballRoom, false, false, "go west");
		Edge edge36 = new Edge (ballRoom, diningRoom, false, false, "go east");
		Edge edge37 = new Edge (grandFoyer, staircaseOne, false, true, "go south");
		Edge edge38 = new Edge (staircaseOne, grandFoyer, false, true, "go north");
		Edge edge39 = new Edge (staircaseOne, southHall, false, true, "go west");
		Edge edge40 = new Edge (southHall, staircaseOne, false, true, "go east");
		Edge edge41 = new Edge (southHall, centerHall, false, true, "go north");
		Edge edge42 = new Edge (centerHall, southHall, false, true, "go south");
		Edge edge43 = new Edge (centerHall, northHall, false, true, "go north");
		Edge edge44 = new Edge (northHall, centerHall, false, true, "go south");
		Edge edge45 = new Edge (northHall, library, false, false, "go west");
		Edge edge46 = new Edge (library, northHall, false, false, "go east");
		Edge edge47 = new Edge (library, staircaseThree, false, false, "go west");
		Edge edge48 = new Edge (staircaseThree, library, false, true, "go east");
		Edge edge49 = new Edge (staircaseThree, treasureRoom, false, true, "go west");
		Edge edge50 = new Edge (treasureRoom, staircaseThree, false, true, "go east");
		Edge edge51 = new Edge(staircaseThree, masterBedroom, false, false, "go south");
		Edge edge52 = new Edge(masterBedroom, staircaseThree, false, false, "go north");
		Edge edge53 = new Edge (southHall, darkHall, false, false, "go west");
		Edge edge54 = new Edge (darkHall, southHall, false, false, "go east");
		Edge edge55 = new Edge (darkHall, spiderWeb, false, true, "go west");	
		Edge edge56 = new Edge (spiderWeb, batCave, false, true, "go west");
		Edge edge57 = new Edge (batCave, spiderWeb, false, true, "go east");
		Edge edge58 = new Edge (tunnelRoom0, tunnelRoom1, false, true, "go west");
		Edge edge59 = new Edge (tunnelRoom1, tunnelRoom0, false, true, "go east");
		Edge edge60 = new Edge (tunnelRoom1, tunnelRoom4, false, true, "go west");
		Edge edge61 = new Edge (tunnelRoom4, tunnelRoom1, false, true, "go east");
		Edge edge62 = new Edge (tunnelRoom4, tunnelRoom5, false, true, "go south");
		Edge edge63 = new Edge (tunnelRoom5, tunnelRoom4, false, true, "go north");
		Edge edge64 = new Edge (tunnelRoom5, tunnelRoom6, false, true, "go east");
		Edge edge65 = new Edge (tunnelRoom6, tunnelRoom5, false, true, "go west");
		Edge edge66 = new Edge (tunnelRoom5, tunnelRoom7, false, true, "go south");
		Edge edge67 = new Edge (tunnelRoom7, tunnelRoom5, false, true, "go north");
		Edge edge68 = new Edge (tunnelRoom6, tunnelRoom8, false, true, "go south");
		Edge edge69 = new Edge (tunnelRoom8, tunnelRoom6, false, true, "go north");
		Edge edge70 = new Edge (tunnelRoom7, tunnelRoom8, false, true, "go east");	
		Edge edge71 = new Edge (tunnelRoom8, tunnelRoom7, false, true, "go west");
		Edge edge72 = new Edge (tunnelRoom8, tunnelRoom9, false, true, "go east");
		Edge edge73 = new Edge (tunnelRoom9, tunnelRoom8, false, true, "go west");
		Edge edge74 = new Edge (tunnelRoom9, tunnelRoom10, false, true, "go south");
		Edge edge75 = new Edge (tunnelRoom10, tunnelRoom9, false, true, "go north");
		Edge edge76 = new Edge (tunnelRoom10, tunnelRoom11, false, true, "go west");
		Edge edge77 = new Edge (tunnelRoom11, tunnelRoom10, false, true, "go east");	
		Edge edge78 = new Edge (tunnelRoom7, tunnelRoom12, false, true, "go south");
		Edge edge79 = new Edge (tunnelRoom12, tunnelRoom7, false, true, "go north");
		Edge edge80 = new Edge (tunnelRoom11, tunnelRoom12, false, true, "go west");
		Edge edge81 = new Edge (tunnelRoom12, tunnelRoom11, false, true, "go east");
		Edge edge82 = new Edge (tunnelRoom12, tunnelRoom13, false, true, "go south");
		Edge edge83 = new Edge (tunnelRoom13, tunnelRoom12, false, true, "go north");
		Edge edge84 = new Edge (tunnelRoom0, tunnelRoom2, false, true, "go east");
		Edge edge85 = new Edge (tunnelRoom2, tunnelRoom0, false, true, "go west");
		Edge edge86 = new Edge (tunnelRoom2, tunnelRoom3, false, true, "go east");
		Edge edge87 = new Edge (tunnelRoom3, tunnelRoom2, false, true, "go west");
		Edge edge88 = new Edge (tunnelRoom3, tunnelRoom15, false, true, "go south");
		Edge edge89 = new Edge (tunnelRoom15, tunnelRoom3, false, true, "go north");
		Edge edge90 = new Edge (tunnelRoom2, tunnelRoom14, false, true, "go south");
		Edge edge91 = new Edge (tunnelRoom14, tunnelRoom2, false, true, "go north");
		Edge edge92 = new Edge (tunnelRoom14, tunnelRoom16, false, true, "go south");
		Edge edge93 = new Edge (tunnelRoom16, tunnelRoom14, false, true, "go north");
		Edge edge94 = new Edge (tunnelRoom14, tunnelRoom15, false, true, "go east");
		Edge edge95 = new Edge (tunnelRoom15, tunnelRoom14, false, true, "go west");
		Edge edge96 = new Edge (tunnelRoom16, tunnelRoom17, false, true, "go east");
		Edge edge97 = new Edge (tunnelRoom17, tunnelRoom16, false, true, "go west");
		Edge edge98 = new Edge (tunnelRoom17, tunnelRoom15, false, true, "go north");
		Edge edge99 = new Edge (tunnelRoom15, tunnelRoom17, false, true, "go south");
		Edge edge100 = new Edge (grandFoyer,frontOfNightManor, true, false, "go east");
		Edge edge101 = new Edge (frontOfNightManor,grandFoyer, true, false, "go west");
		Edge edge102 = new Edge (spiderWeb, darkHall, false, true, "go east");	
		Edge edge103=new Edge(tunnelRoom1, tunnelRoom6, false, true, "go south");
		Edge edge104=new Edge(tunnelRoom6, tunnelRoom1, false, true, "go north");
		Edge edge105 = new Edge(tunnelRoom13, greenhouse, true, false, "go south");
		Edge edge106 = new Edge (greenhouse,tunnelRoom13, true, false, "go north");

		/*
		 * State 2(set reverse): Each Edge that was initialized has a reverse edge. 
		 */
		edgeOne.setReverse(edgeTwo);
		edgeTwo.setReverse(edgeOne);
		edgeThree.setReverse(edgeFour);
		edgeFour.setReverse(edgeThree);
		edgeFive.setReverse(edgeSix);
		edgeSix.setReverse(edgeFive);
		edgeSeven.setReverse(edgeEight);
		edgeEight.setReverse(edgeSeven);
		edgeNine.setReverse(edgeTen);
		edgeTen.setReverse(edgeNine);
		edgeEleven.setReverse(edge12);
		edge12.setReverse(edgeEleven);
		edge13.setReverse(edge14);
		edge14.setReverse(edge13);
		edge15.setReverse(edge16);
		edge16.setReverse(edge15);
		edge17.setReverse(edge18);
		edge18.setReverse(edge17);
		edge19.setReverse(edge20);
		edge20.setReverse(edge19);
		edge21.setReverse(edge22);
		edge22.setReverse(edge21);
		edge23.setReverse(edge24);
		edge24.setReverse(edge23);	
		edge25.setReverse(edge26);
		edge26.setReverse(edge25);
		edge27.setReverse(edge28);
		edge28.setReverse(edge27);
		edge29.setReverse(edge30);
		edge30.setReverse(edge29);
		edge31.setReverse(edge32);
		edge32.setReverse(edge31);
		edge33.setReverse(edge34);
		edge34.setReverse(edge33);
		edge35.setReverse(edge36);
		edge36.setReverse(edge35);
		edge37.setReverse(edge38);
		edge38.setReverse(edge37);
		edge39.setReverse(edge40);
		edge40.setReverse(edge39);
		edge41.setReverse(edge42);
		edge42.setReverse(edge41);
		edge43.setReverse(edge44);
		edge44.setReverse(edge43);
		edge45.setReverse(edge46);
		edge46.setReverse(edge45);
		edge47.setReverse(edge48);
		edge48.setReverse(edge47);
		edge49.setReverse(edge50);
		edge50.setReverse(edge49);
		edge51.setReverse(edge52);
		edge52.setReverse(edge51);
		edge53.setReverse(edge54);
		edge54.setReverse(edge53);		
		edge56.setReverse(edge57);
		edge57.setReverse(edge56);
		edge58.setReverse(edge59);
		edge59.setReverse(edge58);
		edge60.setReverse(edge61);
		edge61.setReverse(edge60);	
		edge62.setReverse(edge63);
		edge63.setReverse(edge62);
		edge64.setReverse(edge65);
		edge65.setReverse(edge64);
		edge66.setReverse(edge67);
		edge67.setReverse(edge66);
		edge68.setReverse(edge69);
		edge69.setReverse(edge68);
		edge70.setReverse(edge71);
		edge71.setReverse(edge70);
		edge72.setReverse(edge73);
		edge73.setReverse(edge72);
		edge74.setReverse(edge75);
		edge75.setReverse(edge74);	
		edge76.setReverse(edge77);
		edge77.setReverse(edge76);
		edge78.setReverse(edge79);
		edge79.setReverse(edge78);
		edge80.setReverse(edge81);
		edge81.setReverse(edge80);
		edge82.setReverse(edge83);
		edge83.setReverse(edge82);
		edge84.setReverse(edge85);
		edge85.setReverse(edge84);
		edge86.setReverse(edge87);
		edge87.setReverse(edge86);		
		edge88.setReverse(edge89);
		edge89.setReverse(edge88);
		edge90.setReverse(edge91);
		edge91.setReverse(edge90);
		edge92.setReverse(edge93);
		edge93.setReverse(edge92);
		edge94.setReverse(edge95);
		edge95.setReverse(edge94);
		edge96.setReverse(edge97);
		edge97.setReverse(edge96);
		edge98.setReverse(edge99);
		edge99.setReverse(edge98);
		edge100.setReverse(edge101);
		edge101.setReverse(edge100);
		edge102.setReverse(edge55);
		edge55.setReverse(edge102);
		edge103.setReverse(edge104);
		edge104.setReverse(edge103);
		edge105.setReverse(edge106);
		edge106.setReverse(edge105);


		/*
		 * State 3 (set key): Each locked edge has a key that unlocks it.
		 */
		edgeOne.setKey(keyToCellOne);
		edgeOne.getReverse().setKey(keyToCellOne);
		edgeEleven.setKey(keyToCellThree);
		edgeEleven.getReverse().setKey(keyToCellThree);
		edge23.setKey(keyToInformalDining);
		edge23.getReverse().setKey(keyToInformalDining);
		edge105.setKey(nightManorKey);
		edge105.getReverse().setKey(nightManorKey);
		edge100.setKey(frontYardKey);
		edge100.getReverse().setKey(frontYardKey);

		/*
		 * State 4 (add to list): Each Edge that was initialized is added to ArrayList edgeList.
		 */
		edgeList.add(edgeOne);
		edgeList.add(edgeTwo);
		edgeList.add(edgeThree);
		edgeList.add(edgeFour);
		edgeList.add(edgeFive);
		edgeList.add(edgeSix);
		edgeList.add(edgeSeven);
		edgeList.add(edgeEight);
		edgeList.add(edgeNine);
		edgeList.add(edgeTen);
		edgeList.add(edgeEleven);
		edgeList.add(edge12);
		edgeList.add(edge13);
		edgeList.add(edge14);
		edgeList.add(edge15);
		edgeList.add(edge16);
		edgeList.add(edge17);
		edgeList.add(edge18);
		edgeList.add(edge19);
		edgeList.add(edge20);
		edgeList.add(edge21);
		edgeList.add(edge22);
		edgeList.add(edge23);
		edgeList.add(edge24);
		edgeList.add(edge25);
		edgeList.add(edge26);
		edgeList.add(edge27);
		edgeList.add(edge28);
		edgeList.add(edge29);
		edgeList.add(edge30);
		edgeList.add(edge31);
		edgeList.add(edge32);
		edgeList.add(edge33);
		edgeList.add(edge34);
		edgeList.add(edge35);
		edgeList.add(edge36);	
		edgeList.add(edge37);	
		edgeList.add(edge38);	
		edgeList.add(edge39);	
		edgeList.add(edge40);	
		edgeList.add(edge41);	
		edgeList.add(edge42);	
		edgeList.add(edge43);	
		edgeList.add(edge44);
		edgeList.add(edge45);
		edgeList.add(edge46);
		edgeList.add(edge47);
		edgeList.add(edge48);
		edgeList.add(edge49);
		edgeList.add(edge50);
		edgeList.add(edge51);
		edgeList.add(edge52);	
		edgeList.add(edge53);	
		edgeList.add(edge54);	
		edgeList.add(edge55);	
		edgeList.add(edge56);	
		edgeList.add(edge57);		
		edgeList.add(edge58);
		edgeList.add(edge59);
		edgeList.add(edge60);
		edgeList.add(edge61);
		edgeList.add(edge62);
		edgeList.add(edge63);
		edgeList.add(edge64);
		edgeList.add(edge65);
		edgeList.add(edge66);
		edgeList.add(edge67);
		edgeList.add(edge68);
		edgeList.add(edge69);
		edgeList.add(edge70);
		edgeList.add(edge71);
		edgeList.add(edge72);
		edgeList.add(edge73);
		edgeList.add(edge74);
		edgeList.add(edge75);
		edgeList.add(edge76);
		edgeList.add(edge77);
		edgeList.add(edge78);
		edgeList.add(edge79);
		edgeList.add(edge80);
		edgeList.add(edge81);
		edgeList.add(edge82);
		edgeList.add(edge83);
		edgeList.add(edge84);
		edgeList.add(edge85);
		edgeList.add(edge86);
		edgeList.add(edge87);
		edgeList.add(edge88);
		edgeList.add(edge89);
		edgeList.add(edge90);
		edgeList.add(edge91);
		edgeList.add(edge92);	
		edgeList.add(edge93);
		edgeList.add(edge94);
		edgeList.add(edge95);
		edgeList.add(edge96);
		edgeList.add(edge97);
		edgeList.add(edge98);
		edgeList.add(edge99);
		edgeList.add(edge100);
		edgeList.add(edge101);	
		edgeList.add(edge102);
		edgeList.add(edge103);
		edgeList.add(edge104);	
		edgeList.add(edge105);
		edgeList.add(edge106);
	}

	/**
	 * INTENT: Determines which command the player entered and calls the appropriate method in the appropriate class to 
	 * take action based upon the command entered.  
	 * 
	 * EXAMPLE: If the player entered "go north", then move was called in Player.java to move the player to the north.
	 * 
	 * SHORTHAND(aCommand): aCommand is an instruction that is given by the player and tells the program what to do next.
	 * 
	 * SHORTHAND(appropriate method): An appropriate method in this context is the method that fulfills the command that was entered.
	 * 
	 * PRECONDITION 1: The player (program user) entered aCommand.
	 * 
	 * PRE 2: The player is not solving the maze (the player is solving the maze if the player is in Room maze and entered "go north", and the
	 * player solved the maze if the player was or is in the kitchen).
	 * 
	 * POSTCONDITION 1 (aCommand identified): If aCommand was identified, then the program called the appropriate method (see shorthand)
	 * to respond to aCommand.
	 * 
	 * POST 2 (complement): True was returned if and only if the game was over, and false was returned if and only if the game was
	 * not over.
	 */
	public static boolean processCommands(String aCommand) {

		int currentRoomNum = thePlayer.getLocation().getRoomNumber();

		//State 1 (quit): If aCommand equals "quit", then all active threads are terminated.
		if(aCommand.equalsIgnoreCase("quit")) {
			System.out.println("Game Over.");
			ManageSubThreads.interruptAll();
			return true;
		}

		//State 2 (help): If aCommand equals "help", a menu of help options is printed. 
		if (aCommand.equalsIgnoreCase("help")){		
			System.out.println(Messages.help.get("help"));
			return false;
		}

		//State 3 (help basics): If aCommand equals "help basics", then a list of basic commands is printed to the console.
		if (aCommand.equalsIgnoreCase("help basics")) {
			System.out.println(Messages.help.get("help basics"));	
			return false;
		}

		//State 4 (help commands): If aCommand equals "help commands", then a list of commands that is longer than the basic list of 
		//commands is printed to the console.
		if(aCommand.equalsIgnoreCase("help commands")){
			System.out.println(Messages.help.get("help commands"));
			return false;
		}

		//State 5 (a direction): If aCommand equals "go north" or "go south" or "go west" or "go east", then move in Player.java
		//is called to move the player in that direction.
		if (aCommand.equalsIgnoreCase("go north")||aCommand.equalsIgnoreCase("go south")
				|| aCommand.equalsIgnoreCase("go west")|| aCommand.equalsIgnoreCase("go east") ||
				aCommand.equalsIgnoreCase("climb down")) {	
			thePlayer.move(aCommand);	
			return false;
		}

		//State 6 (look): If aCommand equals "look", then a description of the player's current location is printed to the console. 
		if (aCommand.equalsIgnoreCase("look")) {
			System.out.println(thePlayer.getLocation().getRoomDescription());
			return false;
		}

		//State 7 (unlock): If aCommand equals "unlock door" or "unlock the door", then unlockDoor in Player.java is called to 
		//unlock the door.
		if (aCommand.equalsIgnoreCase("unlock door")||aCommand.equalsIgnoreCase("unlock the door")){		
			thePlayer.unlockDoor();
			return false;
		}

		//State 8 (lock): If aCommand equals "lock door" or "lock the door", then lockDoor in Player.java is called to 
		//lock the door.
		if (aCommand.equalsIgnoreCase("lock door")||aCommand.equalsIgnoreCase("lock the door")){
			thePlayer.lockDoor();
			return false;
		}

		//State 9 (open door): If aCommand equals "open door" or "open the door", then openDoor is called in Player.java
		//to open the door.
		if (aCommand.equalsIgnoreCase("open door")||aCommand.equalsIgnoreCase("open the door")) {
			thePlayer.openDoor();	
			return false;
		}

		//State 10 (close door): If aCommand equals "close door" or "close the door", then closeDoor is called in Player.java
		//to close the door.
		if (aCommand.equalsIgnoreCase("close door")||aCommand.equalsIgnoreCase("close the door")) {
			thePlayer.closeDoor();	
			return false;
		}

		//State 11 (take): If the substring of aCommand in the interval [0,4) equals "take", then takeWhat is called in NightManor.java 
		//to determine what the player wants to take before calling the appropriate take method for that object.
		if (aCommand.length() >= 4 && aCommand.substring(0, 4).equalsIgnoreCase("take")) {
			takeWhat(aCommand);
			return false;
		}

		//State 12 (check inventory): If aCommand equals "check inventory", then checkInventory is called in Player.java.
		if (aCommand.equalsIgnoreCase("check inventory")) {
			thePlayer.checkInventory();
			return false;
		}

		//State 13 (ghost, yes): If aCommand equals "ghost, yes", then offerDeal in NightManor.java is called with boolean value true to indicate 
		//that the player agreed to hear the ghost's deal, and the boolean value that offerDeal returned is returned to the method call in
		//PlayGame.java.
		if(aCommand.equalsIgnoreCase("ghost, yes")) {
			return offerDeal(true);
		}

		//State 14 (ghost, no): If aCommand equals "ghost, no", then offerDeal in NightManor.java is called with boolean value false to indicate that the 
		//player refused to hear the ghost's deal, and all active threads are terminated.
		if(aCommand.equalsIgnoreCase("ghost, no")) {
			return offerDeal(false);
		}

		//State 15 (open cabinet): If aCommand equals "open cabinet" or "open the cabinet", and the player's current room is the kitchen or the 
		//master bedroom, then the openCabinet method is called in Cabinet.java to unlock the cabinet.
		if (aCommand.equalsIgnoreCase("open cabinet") || aCommand.equalsIgnoreCase("open the cabinet")) {
			if (currentRoomNum == 7) {
				theKitchenCabinet.openCabinet();
			}
			else {
				if(currentRoomNum == 25) {
					masterBedCabinet.openCabinet();
				}
			}
			return false;
		}

		//State 16 (check treasure in bag): If aCommand equals "check treasure in bag", printItemsInBag in Treasure.java is called to
		//print the items in the player's treasure bag.
		if (aCommand.equalsIgnoreCase("check treasure in bag")) {
			Treasure.printItemsInBag();
			return false;
		}

		//State 17 (check treasure in room):If aCommand equals "check treasure in room", printTreasure in Treasure.java is called to
		//print the items in the treasure room.
		if (aCommand.equalsIgnoreCase("check treasure in room")) {
			Treasure.printTreasure();
			return false;
		}

		//State 18 (remove): If the substring of aCommand in the interval [0,6) equals "remove", then removeWhat is called in NightManor.java 
		//to determine what the player wants to remove before calling the appropriate remove method for that object.
		if (aCommand.length() >= 6 && aCommand.substring(0,6).equalsIgnoreCase("remove")) {
			removeWhat(aCommand);
			return false;
		}

		//State 19 (check loot success): If aCommand equals "check loot success", then checkSuccess is called in Treasure.java to 
		//determine whether the player has the maximum possible value in the player's largest treasure bag.
		if(aCommand.equalsIgnoreCase("check loot success")) {
			Treasure.checkSuccess();
			return false;
		}

		//State 20 (use map): If aCommand equals "use map", then whichMap() is called
		if(aCommand.equalsIgnoreCase("use map")) {	
			whichMap();	
			return false;
		}

		return false;
	}//end of processCommands method

	/**
	 * INTENT: Determines which map the player wants to use and calls the appropriate method to use that map.
	 * 
	 * PRECONDITION 1: whichMap() was called from processCommands(String aCommand)
	 * 
	 * PRE 2: The player cannot use "Night Manor map" in a tunnel room or the greenhouse AND the player cannot use "Map of the Tunnels"
	 * outside of the tunnels, but the second conjunct is assured in useMap() in Tunnels.java.
	 * 
	 * POSTCONDITION 1 (answer): answer equaled "Night Manor map" XOR "Map of the Tunnels"
	 * 
	 * POST 2 (Night Manor map): answer equaled "Night Manor map", the player's current location's room number 
	 * was less than 29 or greater than to 46, "Night Manor map" was in the player's inventory, and dijkstras() in
	 * Map.java was called to use the map, XOR the player's current location's room number was in the interval
	 * [29, 46], XOR "Night Manor map" was not in inventory, XOR answer did not equal "Night Manor map".
	 * 
	 * POST 3 (Map of the Tunnels): answer equaled "Map of the Tunnels", "Map of the Tunnels" was in inventory, and 
	 * useMap() in Tunnels.java was called, XOR answer did not equal "Map of the Tunnels", XOR "Map of the Tunnels" was not
	 * in inventory.
	 */
	public static void whichMap() {
		Scanner theConsole = new Scanner (System.in);

		/*
		 * State 1: answer equals "Night Manor map" XOR "Map of the Tunnels"
		 */
		System.out.println("Which map do you want to use?");
		System.out.println("Map of the Tunnels or Night Manor map?");
		System.out.print(">");
		String answer = theConsole.nextLine();

		/*
		 * State 2: POST 2 attained
		 */
		if (answer.equalsIgnoreCase("Night Manor map")) {
			if (thePlayer.yourLocation.getRoomNumber() >= 29 && thePlayer.yourLocation.getRoomNumber() <= 46) {

				System.out.println("The tunnels aren't labeled on that map.");
			}else {
				if (aMap.inInventory) {
					System.out.println(aMap.dijkstras());
				}
				else {
					System.out.println("You don't have that map yet.");
				}
			}
		}
		/*
		 * State 3: POST 3 attained
		 */
		else {
			if (answer.equalsIgnoreCase("Map of the Tunnels")) {
				if (tunnelMap.inInventory) {
					Tunnels.useMap();
				}
			}
		}
	}

	/**
	 * INTENT: Offer Novak's deal and ask whether the player accepts his deal.
	 * 
	 * PRECONDITION 1 (deal input): The player agreed to hear Novak's deal or refused to hear Novak's deal.
	 * 
	 * POSTCONDITION 1 (yes deal and agree to hear): If the player agreed to hear Novak's deal and agreed to Novak's deal, then the key to prison cell 
	 * one was added to inventory, and false was returned.
	 * 
	 * POST 2 (no deal or refusal to hear): If the player did not agree to Novak's deal XOR did not agree to hear Novak's deal, 
	 * then all active threads were terminated.
	 */
	public static boolean offerDeal(boolean deal) {

		Scanner theConsole = new Scanner (System.in);

		/*
		 * State 1 (agree to hear deal): If the player agreed to hear Novak's deal, then Novak's deal is printed, and the player
		 * is asked to agree to or refuse to agree to his deal.
		 */
		if (deal) {
			System.out.println(Messages.novak.get("deal"));

			System.out.print(">");
			String answer = theConsole.nextLine();

			/*
			 * State 2 (yes deal): If the player agreed to Novak's deal, then the key to cell one is added to inventory and false is returned.
			 */
			if (answer.equalsIgnoreCase("yes")|| answer.equalsIgnoreCase("ghost, yes")) {
				System.out.println("Here is the key that unlocks your prison cell. If you need help navigating "
						+ "this monstrous mansion, \njust ask for me. My name was Novak. You can call me that.");
				keyToCellOne.addToInventory();	
				return false;
			}		
		}

		/*
		 * State 3 (refused to hear deal or no deal): If the player refused to hear Novak's deal, XOR the player disagreed to Novak's deal, then 
		 * all active threads are terminated.
		 */
		System.out.println(Messages.novak.get("no deal"));
		ManageSubThreads.interruptAll();
		return true;

	}

	/**
	 * INTENT: Determine what the player wants to remove and call the appropriate method to remove it.
	 * 
	 * EXAMPLE: If the player entered "remove gold necklace", then this method was called from processCommands and determined that 
	 * the player wanted to remove the gold necklace, and called remove in Treasure.java with "gold necklace" as a parameter.
	 * 
	 *PRECONDITION (input): The player entered "remove some object", and processCommands was called.
	 * 
	 *POSTCONDITION 1: The object that the player wanted to remove was identified, and the method that removes that 
	 *object was called, XOR the object that the player wanted to remove was not identified.
	 */
	public static void removeWhat(String aCommand) {

		if (aCommand.substring(7).strip().equalsIgnoreCase("gold necklace")||
				aCommand.substring(7).strip().equalsIgnoreCase("four diamond encrusted gold rings")||
				aCommand.substring(7).strip().equalsIgnoreCase("watch") ||
				aCommand.substring(7).strip().equalsIgnoreCase("three diamonds")||
				aCommand.substring(7).strip().equalsIgnoreCase("ruby")||
				aCommand.substring(7).strip().equalsIgnoreCase("emerald")||
				aCommand.substring(7).strip().equalsIgnoreCase("two opals")||
				aCommand.substring(7).strip().equalsIgnoreCase("three gold bars")||
				aCommand.substring(7).strip().equalsIgnoreCase("diamond earrings") ||
				aCommand.substring(7).strip().equalsIgnoreCase("gold cuff links")||
				aCommand.substring(7).strip().equalsIgnoreCase("faberge egg")||
				aCommand.substring(7).strip().equalsIgnoreCase("bag of gold coins")||
				aCommand.substring(7).strip().equalsIgnoreCase("gold and diamond tiara")||
				aCommand.substring(7).strip().equalsIgnoreCase("scepter")||
				aCommand.substring(7).strip().equalsIgnoreCase("crown")||
				aCommand.substring(7).strip().equalsIgnoreCase("three gold medals")){
			Treasure.remove(aCommand.substring(7));
		}

	}

	/**
	 * INTENT: Determine what the player wants to take and call the appropriate method to take it.
	 * 
	 * EXAMPLE: If the player entered "take apple", then this method was called from processCommands and determined that 
	 * the player wanted to take an apple, and called takeFood in Food.java with "apple" as a parameter.
	 * 
	 *PRECONDITION (input): The player entered "take some object", and processCommands was called.
	 * 
	 *POSTCONDITION 1: The object that the player wanted to take was identified, and the method that takes that 
	 *object was called, XOR the object that the player wanted to take was not identified.
	 */
	public static void takeWhat(String aCommand) {

		/*
		 * State 1 (key): If aCommand equals "take key" or "take the key", then takeKey in Key.java is called 
		 * with the name of the Key sent in as a parameter to take the key.
		 */
		if (aCommand.substring(5).strip().equalsIgnoreCase("key") || aCommand.substring(4).strip().equalsIgnoreCase("thekey")) {
			Key.takeKey();
		}

		/*
		 * State 2 (food): If aCommand equals "take name of Food object", then takeFood in Food.java is called with the 
		 * name of the Food sent in as a parameter to take that Food object.
		 */
		if (aCommand.substring(5).strip().equalsIgnoreCase("apple")||
				aCommand.substring(5).strip().equalsIgnoreCase("banana")||
				aCommand.substring(5).strip().equalsIgnoreCase("whiskey") ||
				aCommand.substring(5).strip().equalsIgnoreCase("human hand")||
				aCommand.substring(5).strip().equalsIgnoreCase("wine")||
				aCommand.substring(5).strip().equalsIgnoreCase("bread")){
			Food.takeFood(aCommand.substring(5).strip());
		}

		/*
		 * State 3 (treasure): If aCommand equals "take name of treasure object", then take in Treasure.java is 
		 * called with the name of the Treasure sent in as a parameter to take that Treasure object.
		 */
		if (aCommand.substring(5).strip().equalsIgnoreCase("gold necklace")||
				aCommand.substring(5).strip().equalsIgnoreCase("four diamond encrusted gold rings")||
				aCommand.substring(5).strip().equalsIgnoreCase("watch") ||
				aCommand.substring(5).strip().equalsIgnoreCase("three diamonds")||
				aCommand.substring(5).strip().equalsIgnoreCase("ruby")||
				aCommand.substring(5).strip().equalsIgnoreCase("emerald")||
				aCommand.substring(5).strip().equalsIgnoreCase("two opals")||
				aCommand.substring(5).strip().equalsIgnoreCase("three gold bars")||
				aCommand.substring(5).strip().equalsIgnoreCase("diamond earrings") ||
				aCommand.substring(5).strip().equalsIgnoreCase("gold cuff links")||
				aCommand.substring(5).strip().equalsIgnoreCase("faberge egg")||
				aCommand.substring(5).strip().equalsIgnoreCase("bag of gold coins")||
				aCommand.substring(5).strip().equalsIgnoreCase("gold and diamond tiara")||
				aCommand.substring(5).strip().equalsIgnoreCase("scepter")||
				aCommand.substring(5).strip().equalsIgnoreCase("crown")||
				aCommand.substring(5).strip().equalsIgnoreCase("three gold medals")){
			Treasure.take(aCommand.substring(5).strip());
		}

		/*
		 * State 4 (bag): If aCommand equals "take name of Bag", then take in Bag.java is called with the name of the Bag sent in as a parameter 
		 * to take that Bag object.
		 */
		if (aCommand.substring(5).strip().equalsIgnoreCase("small treasure bag")|| 
				aCommand.substring(5).strip().equalsIgnoreCase("medium treasure bag")||
				aCommand.substring(5).strip().equalsIgnoreCase("large treasure bag")){
			Bag.take(aCommand.substring(5).strip());
		}

		/*
		 * State 5 (map): If aCommand equals "take map", then take in Map.java is called to take the Map.
		 */
		if(aCommand.substring(5).strip().equalsIgnoreCase("map")){
			Map.take();
		}
	}

	/**
	 * INTENT: Print a statement that offers a deal given by a ghost 3 minutes (180,000 milliseconds) after starting a new thread.
	 * 
	 * PRECONDITION 1: NightManor implements the Runnable interface.
	 * 
	 * PRE 2: A NightManor thread was created and started in PlayGame.java.
	 * 
	 * POSTCONDITION 1 (delay): 3 minutes passed since this thread was started.
	 * 
	 * POST 2 (deal): A statement was printed to the console that offered the player a deal, which was given by a ghost.
	 * 
	 * POST 3 (complement): This thread was not interrupted.
	 */
	public void run() { 
		/*
		 * SHORTHAND(P1): P1 denotes postcondition 1.
		 * 
		 * SHORTHAND(P2): P2 denotes post 2.
		 * 
		 * SHORTHAND (P3) P3 denotes post 3.
		 * 
		 * SHORTHAND (t1): Task t1 is that this thread is asleep for 3 minutes.
		 * 
		 * SHORTHAND (t2): Task t2 is that a statement is printed to the console that offers the player a deal.
		 */

		try {
			/*
			 * StateP1 (delay): Task t1 is started, which fulfills P1.
			 */
			Thread.sleep(180000);

			/*
			 * State P1.5 (t1 complete): t1 is complete..
			 */

			/*
			 * State P2 (deal): t2 is started, which fulfills P2.
			 */
			System.out.println();
			System.out.print(Messages.novak.get("first appearance"));		

			/*
			 * State P2.5 (t2 complete): t2 is complete.
			 * 
			 * State P3 (complement): P3 is fulfilled.
			 */

		} catch (InterruptedException e) {	

		}
	}

}//end of NightManor class
