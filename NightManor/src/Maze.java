import java.util.LinkedList; 
import java.util.Scanner;

/**
 * Maze contains methods to start the maze, process and respond to player commands, ensure valid moves, solve the maze, and 
 * print the solution to the maze. The maze is represented by a 5x5 two dimensional array that contains 0s and 1s. If a position contains a 1, 
 * you can move to that position. If a position contains a 0, you cannot move to that position. The player enters the maze at row 0, column 0 and 
 * finishes the maze at row 4, column 4. The player can move north, south, west, and east. Those movements are detailed below, where (row, column) 
 * is a position in theMaze.
 * move east = (row, column-1)
 * move west = (row, column+1)
 * move north = (row+1, column)
 * move south = (row -1, column)
 * 
 * @author Michael Shriner
 */
public class Maze {

	/**	theMaze is a 5X5 two-dimensional array that contains 1s and 0s.*/
	int [][]theMaze = {{1,1,1,1,1},{1,0,1,1,1}, {1,1,0,0,1}, {1,1,1,1,0}, {1,0,0,1,1}}; 

	/** visited is a 5X5 two-dimensional array that contains boolean values. The indices in visited correspond to the indices in theMaze.
	 * An index value of true implies that the same index value in theMaze was visited. Otherwise, the value is false. */
	boolean [][] visited = {{false, false, false, false, false},{false, false, false, false, false},
			{false, false, false, false, false},{false, false, false, false, false},
			{false, false, false, false, false}};

	int playerRow = 0;
	int playerColumn = 0;
	boolean inMaze=true;
	boolean done = false;
	LinkedList<String> aStack = new LinkedList<String>();

	/**
	 * INTENT: Receive commands from the player while the player is in this maze and call the moveInMaze method to respond to each command.
	 * 
	 * PRECONDITION: The current location of the player is the Room maze, and the player tried to move north before entering the runMaze method.
	 * 
	 * POSTCONDITION: inMaze equaled true, and the player didn't solve this maze, XOR inMaze equaled false, and the player solved this maze.
	 */
	public void runMaze() {		

		Scanner aConsole = new Scanner (System.in);

		System.out.println("You are now in the first room of the maze. As you stare at the tall, bleak, grey walls surrounding you, "
				+ "you hear a loud grating noise.\nYou spin around and see that a stone wall blocks the path back to the prison.");

		while(inMaze) {
			System.out.print(">");
			String a_command = aConsole.nextLine();
			moveInMaze(a_command);
		}
	}

	/**
	 * Intent: Process and respond to the player's commands while the player is in this maze. 
	 * 
	 * SHORTHAND((a row number, a column number)): (a row number, a column number) refers to the position at a row number and a column number in 
	 * theMaze.(e.g., (0,1) is the position at row 0, column 1).
	 * 
	 * PRECONDITION 1 (starting position): the player's starting position in theMaze is (0,0).
	 * 
	 * PRE 2 (solved): the maze is solved when the player is at (4,4) in theMaze.
	 * 
	 * PRE 3 (valid direction): aDirection is valid if and only if it equals "solve", "hint", "go north", "go south",
	 * "go west", or "go east".
	 * 
	 * PRE 4 (movement): move east = (currentRow, currentColumn-1) AND move west = (currentRow, currentColumn+1) AND
	 * move north = (currentRow+1, currentColumn) AND move south = (currentRow -1, currentColumn).
	 * 
	 * POSTCONDITION 1 (go north): aDirection equaled "go north", the player could move to position(playerRow+1, playerColumn), playerRow was 
	 * assigned playerRow+1, and a message that indicated that the player moved to the north was printed, XOR aDirection equaled "go north", the
	 * player couldn't move to position (playerRow+1, playerColumn), and a message that indicated that the player couldn't move north was 
	 * printed, XOR aDirection did not equal "go north".
	 * 
	 * POST 2 (go south): aDirection equaled "go south", the player could move to the position (playerRow-1, playerColumn), playerRow was 
	 * assigned playerRow-1, and a message that indicated that the player moved south was printed, XOR aDirection equaled "go south", the player 
	 * couldn't move to the position (playerRow-1, playerColumn), and a message that indicated that the player could not move south was printed, 
	 * XOR aDirection did not equal "go south".
	 * 
	 * POST 3 (go west): aDirection equaled "go west", the player could move to the position (playerRow, playerColumn+1), playerColumn was 
	 * assigned playerColumn+1, and a message that indicated that the player moved west was printed, XOR aDirection equaled "go west", the 
	 * player couldn't move to the position (playerRow, playerColumn+1), and a message that indicated that the player could not move west was 
	 * printed, XOR aDirection did not equal "go west".
	 * 
	 * POST 4(go east): aDirection equaled "go east", the player could move to the position (playerRow, playerColumn-1), playerColumn was 
	 * assigned playerColumn-1, and a message that indicated that the player moved east was printed, XOR aDirection equaled "go east", the 
	 * player couldn't move to the position (playerRow, playerColumn-1), and a message that indicated that the player could not move east was 
	 * printed, XOR aDirection did not equal "go east".
	 * 
	 * POST 5 (solve): aDirection equaled "solve", solveMaze was called, directions to solve the_maze were printed, each room was marked as 
	 * unvisited, and done was assigned false, XOR aDirection did not equal "solve".
	 * 
	 * POST 6 (hint): aDirection equaled "hint" and a hint was printed, XOR aDirection did not equal "hint".
	 * 
	 * POST 7 (complement): playerRow equaled 4, player column equaled 4, inMaze was assigned false, the player's location was set to the Room 
	 * kitchen, the passage of the edge with start room kitchen and destination room maze was closed, and a description 
	 * of the kitchen was printed.
	 */
	public void moveInMaze(String aDirection) {

		/*
		 * state: aDirection equaled "solve", a statement was printed, the maze was solved and the solution was printed, each position in
		 * the visited array was set to false, and done was set to false, XOR aDirection didn't equal "solve".
		 */
		if(aDirection.equalsIgnoreCase("solve")) {

			System.out.println("Suddenly, Novak appeared and walked through the nearest wall to find a path to the exit.");
			System.out.println("When Novak returned, he said,");

			solveMaze(playerRow, playerColumn);
			markAllUnvisited();
			done = false;
		}

		/*
		 * state: aDirection equaled "hint", and a hint was printed, XOR aDirection didn't equal "hint".
		 */
		if(aDirection.equalsIgnoreCase("hint")) {
			System.out.println("Always keep your right hand along a wall, and you will eventually find the exit.");
		}

		/*
		 * state: aDirection equaled "go north", the position (playerRow+1,playerColumn) in theMaze was unvisited and had value 1, playerRow
		 * was set to playerRow+1, and a statement was printed that indicated that the player moved north, XOR aDirection equaled 
		 * "go north" but the position (playerRow+1, playerColumn) was either not in theMaze XOR the position was visited or the value of 
		 *  (playerRow+1, playerColumn) in theMaze was 0, XOR aDirection didn't equal "go north".
		 */
		if(aDirection.equalsIgnoreCase("go north")) {

			if(canMove(playerRow+1, playerColumn)) {
				playerRow = playerRow+1;
				System.out.println("You moved into the room in front of you.");
			}
			else {
				System.out.println("You can't move north");
			}
		}		
		else {
			/*
			 * state: aDirection equaled "go south", the position (playerRow-1,playerColumn) in theMaze was unvisited and had value 1, playerRow
			 * was set to playerRow-1, and a statement was printed that indicated that the player moved south, XOR aDirection equaled 
			 * "go south" but the position (playerRow-1, playerColumn) was either not in theMaze XOR the position was visited or the value of 
			 *  (playerRow-1, playerColumn) in theMaze was 0 and a statement was printed that indicated that the player cannot move south, XOR 
			 *  aDirection didn't equal "go south".
			 */
			if(aDirection.equalsIgnoreCase("go south")) {

				if(canMove(playerRow-1,playerColumn)) {		
					playerRow = playerRow-1;
					System.out.println("You moved into the room behind you.");			
				}
				else {
					System.out.println("Your path is blocked. You can't move south.");
				}
			}
			else {
				/*
				 * state: aDirection equaled "go west", the position (playerRow,playerColumn+1) in theMaze was unvisited and had value 1,
				 * playerColumn was set to playerColumn + 1, and a statement was printed that indicated that the player moved west, XOR 
				 * aDirection equaled "go west" but the position (playerRow, playerColumn+1) was either not in theMaze XOR the position was 
				 * visited or the value of (playerRow, playerColumn+1) in theMaze was 0 and a statement was printed that indicated that the 
				 * player cannot move west, XOR aDirection didn't equal "go west".
				 */
				if(aDirection.equalsIgnoreCase("go west")) {

					if(canMove(playerRow, playerColumn+1)) {
						playerColumn = playerColumn+1;
						System.out.println("You moved into the room to the west.");			
					}
					else {
						System.out.println("There is a wall in your way. Try moving a different direction.");
					}			
				}
				else {
					/*
					 * state: aDirection equaled "go east", the position (playerRow,playerColumn-1) in theMaze was unvisited and had value 1,
					 * playerColumn was set to playerColumn-1, and a statement was printed that indicated that the player moved east, XOR 
					 * aDirection equaled "go east" but the position (playerRow, playerColumn-1) was either not in theMaze XOR the position was 
					 * visited or the value of (playerRow, playerColumn-1) in theMaze was 0 and a statement was printed that indicated that the 
					 * player cannot move east, XOR aDirection didn't equal "go east".
					 */
					if(aDirection.equalsIgnoreCase("go east")) {

						if(canMove(playerRow, playerColumn-1)) {
							System.out.println("You moved into the room to the east.");
							playerColumn = playerColumn-1;
						}
						else {
							System.out.println("You can't go that way.");
						}
					}
				}
			}
		}

		/*
		 * state: The player reached the end of theMaze (position (4,4)), inMaze was set to false, a statement was printed, 
		 * the player's current location was updated to the kitchen, the edge weight between the maze and kitchen was set to 4, 
		 * and a description of the kitchen was printed, XOR the player wasn't at position (4,4) in theMaze.
		 */
		if(playerRow == 4 && playerColumn == 4) {

			inMaze = false;
			System.out.println("You reached the end of the maze, and a wall slid across the passage behind you."
					+ "\nYou can't go back that way anymore. You must press on but be weary, move swiftly, and tread lightly."
					+ "\nbecause you are not supposed to be out of your cell. You are now in the kitchen.");

			NightManor.thePlayer.setLocation(NightManor.kitchen);
			NightManor.edgeList.get(13).closePassage(false);
			System.out.println(NightManor.kitchen.getRoomDescription());
		}

	}//end of moveInMaze method

	/**
	 * INTENT: Marks all indices in the visited array as false.
	 * 
	 * PRECONDITION (Non-trivial): The visited array is non-empty.
	 * 
	 * POSTCONDITION: Each index x in the interval [0,visited.length) was paired with every index y in the interval [0, visited.length) such that 
	 * visited[x][y] = false. 
	 */
	public void markAllUnvisited() {

		for(int x = 0; x < visited.length; x++) {//row
			for(int y = 0; y < visited.length; y++) {//column
				visited[x][y] = false;
			}
		}		
	}

	/**
	 * INTENT: Returns true if the position (destRow, destColumn) is a valid position to move to. Otherwise, it returns false.
	 * 
	 * SHORTHAND((a row number, a column number)): (a row number, a column number) refers to the position at a row number and a column number in theMaze
	 * or visited (e.g., (0,1) is the position at row 0, column 1 in theMaze or visited).
	 * 
	 * PRECONDITION (valid indices): destRow >= 0 AND destRow < 5 AND destColumn >= 0 AND destColumn < 5.
	 * 
	 * POSTCONDITION: destRow < 0 or destRow > 4 or destColumn < 0 or destColumn > 4 and false was returned, XOR 
	 * the value at position (destRow, destColumn) was 0 and false was returned, XOR the position (destRow,destColumn) in 
	 * visited was true and false was returned, XOR true was returned and (destRow, destColumn) was a valid position to move to in theMaze.
	 */
	public boolean canMove(int destRow, int destColumn ) {

		if (destRow < 0 || destRow > 4 || destColumn < 0 || destColumn > 4) {
			return false;
		}
		else {
			if(theMaze[destRow][destColumn]==0) {
				return false;
			}
			else {
				if(visited[destRow][destColumn]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * INTENT: Find a path to the end of the maze from your current position, which is (currentRow, currentColumn).
	 * 
	 * PRECONDITION (valid start position): currentRow >= 0 AND currentRow <= 4 AND currentColumn >= 0 AND currentColumn <= 4.
	 * 
	 * POSTCONDITION 1 (end position): currentRow = 4 AND currentColumn = 4 AND the position (currentRow, currentColumn) in theMaze had value 1.
	 * 
	 * POST 2 (directions): aStack contained directions to get the player from the position (currentRow, currentColumn) of the initial method call to 
	 * the position specified in POSTCONDITION 1 when the start position was not the end position AND for each unvisited adjacent position to 
	 * (currentRow, currentColumn) where currentRow != 4 and currentColumn != 4 and moving to that position was valid, a direction was pushed onto 
	 * the stack, AND directions to positions in theMaze that didn't have an unvisited, adjacent position and were not the end position (4,4) were 
	 * popped off the stack.  
	 * 
	 * POST 3 (method call): currentRow and currentColumn were set to an unvisited, adjacent position to (currentRow, currentColumn) when the unvisited, 
	 * adjacent position was a valid position to move to and a direction to that position was pushed onto the stack and solveMaze was called XOR 
	 * currentRow = 4 AND currentColumn = 4 AND the position (currentRow, currentColumn) in theMaze had value 1.
	 * 
	 * POST 4 (complement): printSolution was called to recursively print the directions from the start position to the end position in theMaze.
	 */
	public void solveMaze(int currentRow, int currentColumn) {

		/*
		 * state(of base case): currentRow equals 4, currentColumn equals 4, it is valid to move to that position, 
		 * done is assigned true, "the end of the maze" is pushed onto the stack, and a method to print the maze solution was called,
		 * XOR currentRow does not equal 4 or currentColumn does not equal 4 or the position at (currentRow, currentColumn) 
		 * in theMaze contains the value 0
		 */
		if (currentRow == 4 && currentColumn == 4 && theMaze[currentRow][currentColumn]==1) {//immediately satisfy all postconditions if true
			done = true;
			aStack.push("the end of the maze");
			printSolution(aStack.pop());
		}

		visited[currentRow][currentColumn] = true;//The current position was visited.

		/*
		 * state: the position (currentRow+1, currentColumn) was in theMaze, was unvisited, 
		 * contained the value 1, the direction to that position was pushed onto the stack, and 
		 * solveMaze was called to solve the maze from (currentRow+1,currentColumn), XOR the position (currentRow+1, currentColumn) 
		 * was not in theMaze, XOR that position was visited or contained the value 0.
		 */
		if(canMove(currentRow+1, currentColumn)&&!done) {
			aStack.push("go north");
			solveMaze(currentRow+1, currentColumn);
		}

		/*
		 * state: the position (currentRow-1, currentColumn) was in theMaze, was unvisited, 
		 * contained the value 1, the direction to that position was pushed onto the stack, and 
		 * solveMaze was called to solve the maze from (currentRow-1,currentColumn), XOR the position 
		 * (currentRow-1, currentColumn) was not in theMaze, XOR that position was visited or contained the value 0.
		 */
		if(canMove(currentRow-1,currentColumn)&&!done) {
			aStack.push("go south");
			solveMaze(currentRow-1, currentColumn);
		}	

		/*
		 * state: the position (currentRow, currentColumn+1) was in theMaze, was unvisited, 
		 * contained the value 1, the direction to that position was pushed onto the stack, and 
		 * solveMaze was called to solve the maze from (currentRow,currentColumn+1), XOR the position 
		 * (currentRow, currentColumn+1) was not in theMaze, XOR that position was visited or contained the value 0.
		 */
		if(canMove(currentRow,currentColumn+1)&&!done) {
			aStack.push("go west");
			solveMaze(currentRow, currentColumn+1);
		}

		/*
		 * state: the position (currentRow, currentColumn-1) was in theMaze, was unvisited, 
		 * contained the value 1, the direction to that position was pushed onto the stack, and 
		 * solveMaze was called to solve the maze from (currentRow,currentColumn-1), XOR the position 
		 * (currentRow, currentColumn-1) was not in theMaze, XOR that position was visited or contained the value 0.
		 */
		if(canMove(currentRow,currentColumn-1)&&!done) {
			aStack.push("go east");
			solveMaze(currentRow, currentColumn-1);
		}	

		/*
		 * state: We haven't reached the end of the maze, and the current position had no unvisited, adjacent rooms with 
		 * the value 1 in theMaze, a direction was popped off aStack, and we returned to the previous method call, XOR 
		 * we reached the end of the maze and done equals true, XOR solveMaze was called to solve the maze from an unvisited, 
		 * adjacent room that contained the value 1 in theMaze.
		 */
		if(!done) {
			aStack.pop();
		}

	}//end of solve maze method

	/**
	 * INTENT: Prints the directions from the position when solveMaze was called to the end position of theMaze.
	 * 
	 * EXAMPLE: If the start position was (0,0), then the following would print:
	 * go north
	 * go north
	 * go north
	 * go west
	 * go west
	 * go west
	 * go north
	 * go west
	 * the end of the maze
	 * 
	 * PRECONDITION: aStack is non-empty.
	 * 
	 * POSTCONDITION 1 (empty): aStack was empty, aStackElement was printed, and the method returned to the previous method call.
	 * 
	 * POST 2 (non-empty): aStack was not empty and aStack popped off an element and printSolution was called with that popped element.
	 * 
	 * POST 3 (complement): The elements of the stack were printed from the bottom to the top of the stack.
	 */
	public void printSolution(String aStackElement) {

		if(aStack.size()!=0) {
			printSolution(aStack.pop());
		}
		System.out.println(aStackElement);	
	}


}//end of Maze class