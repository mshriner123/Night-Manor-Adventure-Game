import java.util.Scanner;

/**
 * PlayGame sets up and starts the game by calling methods in NightManor.java, Messages.java, and ManageSubThreads.java.
 * See below for more details.
 * 
 * @author Michael Shriner
 */
public class PlayGame  {

	/**
	 * INTENT: Sets up and runs the game.
	 * 
	 * PRECONDITION (methods): The methods that this main method calls exist and fulfill their intended functions.
	 * 
	 * POSTCONDITION 1 (set up): Edges between rooms were created, objects were added to rooms, enemies were added to rooms, 
	 * the threads that allow the enemies to move were started, the necessary text files were put in the 
	 * appropriate hash maps, and a ManageSubThreads object that refers to the main thread was created and 
	 * added to a list of sub-threads.
	 * 
	 * POST 2 (introduction): The game introduction was printed to the console from Night Manor Introduction.txt, 
	 * and a new thread was started in Night Manor to print a message to the console after three minutes passed.
	 * 
	 * POST 3 (player input): While the game is not over, the Scanner object in this main method takes a command from 
	 * the player and processes that command. If the processCommands method returns false, the game continues.
	 * If the processCommands method returns true, the game is over. 
	 */
	public static void main(String[] args) {

		/*
		 * State 1 (set up): Edges between rooms exist, objects are in rooms, enemies are in rooms, the threads in 
		 * Enemy.java are active, the necessary text files are in the appropriate hash maps, and a ManageSubThreads 
		 * object that refers to the main thread exists and is contained in a list of sub-threads.
		 */

		Thread mainThread = Thread.currentThread();
		NightManor.subThreads.add(new ManageSubThreads(mainThread));

		NightManor.createEdges();
		NightManor.addObjectsToRooms();
		NightManor.addEnemies();
		NightManor.startEnemyMovement();
		Messages.loadHashMap();

		/*
		 * State 2 (introduction): The game introduction was printed from the hash map introduction, and a thread was started in 
		 * NightManor.java to print a message after three minutes.
		 */
		System.out.println(Messages.introduction.get("game introduction"));

		Thread introThread = new Thread(new NightManor());
		NightManor.subThreads.add(new ManageSubThreads (introThread));
		introThread.start();

		Scanner theConsole = new Scanner(System.in);
		boolean done = false;

		/*
		 * State 3 (player input): While the game is not done, the player is asked to enter input, and that input is 
		 * processed in NightManor.java.
		 */
		while(!done && NightManor.subThreads.get(0).running.get()) {	
			System.out.print(">");
			String aCommand = theConsole.nextLine();
			done = NightManor.processCommands(aCommand);	
		}

		theConsole.close();
	}

}//end of PlayGame
