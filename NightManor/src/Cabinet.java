import java.util.Random;
import java.util.Scanner;

/**
 * Cabinet extends Objects and contains methods to open this cabinet, unlock this cabinet, get a description of the inside of this cabinet, 
 * create a random lock combination to lock this cabinet, and determine whether a player's guess for a lock combination number was one of the 
 * possible lock combination values for that lock combination number.
 * 
 *@author Michael Shriner
 */
public class Cabinet extends Objects {

	boolean isLocked;
	boolean isOpen; 
	int firstNum;
	int secondNum;
	int thirdNum;
	int [] possibleNumsOne;
	int [] possibleNumsTwo;
	int [] possibleNumsThree;

	Scanner aConsole = new Scanner (System.in);

	/**
	 * INTENT: Constructs a Cabinet. 
	 * 
	 * PRECONDITION 1 (take): take is false.
	 * 
	 * PRE 2 (inventory): inventory is false.
	 * 
	 * PRE 3 (open): open is true if and only if locked is false.
	 * 
	 * POSTCONDITION: The data members were initialized to the parameter values and the parent class constructor was called.
	 */
	public Cabinet(String name, Room room, boolean take, boolean inventory, boolean locked, boolean open) {
		super(name, room, take, inventory);
		isLocked = locked;
		setOpen(open);
	}

	/**
	 * INTENT: Sets this cabinet to open if and only if this cabinet is not locked.
	 * 
	 * POSTCONDITION: This cabinet was unlocked, the parameter open had value true, and isOpen was assigned true, XOR
	 * isOpen was assigned false.
	 */
	public void setOpen(boolean open) {	
		if(open) {	
			if (!isLocked) {
				isOpen = open;
			}
		}
		else {
			isOpen = false;
			getDescription();
		}
	}

	/**
	 *INTENT: Prints a description of the inside of this cabinet.
	 *
	 * PRECONDITION: This cabinet is unlocked and open.
	 * 
	 * POSTCONDITION: A description of this cabinet was printed, XOR this cabinet was locked or closed and a 
	 * description of this cabinet was not printed. 
	 */
	public void getDescription() {	
		if(!isLocked && isOpen) {
			System.out.println(Messages.objectDescriptions.get(aName));
		}
	}


	/**
	 *INTENT: Sets the lock combination of this cabinet. 
	 *
	 *PRECONDITION 1 (length of combination): A lock combination contains three numbers. 
	 *
	 *PRE 2 (unique numbers): No two numbers in a lock combination are equivalent.
	 *
	 *POSTCONDITION 1 (first number): The lower bound of the first lock combination number was randomly chosen in the interval
	 *[1,11], the upper bound was the sum of the lower bound and 15, the first lock combination number was 
	 *randomly chosen in the interval [lowerBound, upperBound), and an array that contained the possible values of lock 
	 *combination number one was initialized.
	 *
	 *POST 2 (second number): The lower bound of the second lock combination number was the upper bound of the first lock combination, 
	 *the upper bound was the sum of the lower bound and 15, the second lock combination number was 
	 *randomly chosen in the interval [lowerBound, upperBound), and an array that contained the possible values of lock 
	 *combination number two was initialized.
	 *
	 *POST 3 (third number): The lower bound of the third lock combination number was the upper bound of the second lock combination, 
	 * the third lock combination number was randomly chosen in the interval [lowerBound, lowerBound + 15), and an array that contained 
	 * the possible values of lock combination number three was initialized.
	 *
	 */
	public void setLock() {

		Random rand = new Random(); 

		/*
		 * State 1 (first number): A random lower bound was generated in the interval [1,11], an upperBound was generated
		 * by multiplying the lowerBound by 2, the first lock combination number was randomly chosen from the interval 
		 * [lowerBound, upperBound), and an array that contained the possible values of lock combination number 
		 * one was initialized.
		 */
		int lowerBound = rand.nextInt(11) + 1; 
		int upperBound = lowerBound + 15;	
		firstNum = rand.nextInt(15) + lowerBound;
		possibleNumsOne = new int [upperBound];

		for (int i = lowerBound; i < upperBound; i++) {
			possibleNumsOne[i]=i;
		}

		/*
		 * State 2 (second number): The lower bound for the second lock combination number was the upperBound of lock combination number one, 
		 * the upper bound was the sum of the lower bound and 15,the second lock combination number was randomly chosen from the interval 
		 * [lowerBound, upperBound), and an array that contained the possible values of lock combination number two was initialized.
		 */	
		lowerBound = upperBound;
		upperBound = lowerBound + 15;
		secondNum = rand.nextInt(15) + lowerBound;
		possibleNumsTwo = new int [upperBound];

		for (int i = lowerBound; i < upperBound; i++) {
			possibleNumsTwo[i]=i;
		}

		/*
		 * State 3 (third number): The lower bound for the third lock combination number was the upperBound of lock combination number two,
		 * the third lock combination number was randomly chosen from the interval [lowerBound, lowerBound + 15), 
		 * and an array that contained the possible values of lock combination number three was initialized.
		 */
		lowerBound = upperBound;
		thirdNum = rand.nextInt(15) + lowerBound;
		possibleNumsThree = new int [lowerBound+15];

		for (int i = lowerBound; i < (lowerBound+15); i++) {
			possibleNumsThree[i]=i;
		}
	}

	/**
	 * INTENT: Opens this cabinet.
	 * 
	 * PRECONDITION: This cabinet is opened if and only if this cabinet is unlocked and if and only if the player is in the same room 
	 * as this cabinet.
	 * 
	 * POSTCONDITION: The player's current location was the room that contains this cabinet, this cabinet was locked, this cabinet's lock was set, 
	 * and the player was asked to enter the combination to this lock, XOR  this cabinet was unlocked, this cabinet was opened, and a statement was 
	 * printed to indicate that this cabinet was opened, XOR the player's current location was not the room that contains this cabinet and a 
	 * statement was printed to indicate that this cabinet was not in the player's room.
	 */
	public void openCabinet() {

		if(NightManor.thePlayer.getLocation().getRoomNumber()== this.aRoom.getRoomNumber()) {
			if (isLocked) {
				setLock();
				enterCombination();
			}
			else {
				isOpen = true;
				System.out.println(aName + " is open.");
				getDescription();
			}
		}
		else {
			System.out.println(aName + " is not in " + NightManor.thePlayer.getLocation().getRoomName());
		}
	}


	/**
	 * INTENT: Asks the user if they want to unlock this cabinet.
	 * 
	 * PRECONDITION: This cabinet is locked by a combination lock.
	 * 
	 * POSTCONDITION: A statement was printed that asked for user input, and the user entered "yes" or "yeah" (not case sensitive), and
	 * guessCombination was called, XOR a statement was printed that asked for user input, and the user did not enter "yes" or "yeah", and
	 * this cabinet was not unlocked. 
	 */
	public void enterCombination() {

		System.out.println(this.aName + " is locked. It requires a 3 number combination to unlock.");
		System.out.println("Do you want to try to unlock this cabinet?");
		System.out.print(">");
		String answer = aConsole.nextLine();

		if (answer.equalsIgnoreCase("yes")||answer.equalsIgnoreCase("yeah")) {
			guessCombination();
		}	
	}

	/**
	 * INTENT: Unlocks the cabinet if the player guesses the lock combination.
	 * 
	 * PRECONDITION 1: This cabinet is locked by a combination lock.
	 * 
	 * PRE 2: Each combination number in this lock is at least 1.
	 * 
	 * POSTCONDITION 1 (first lock number): The player guessed the first lock combination number, XOR the player gave up, 
	 * XOR the player's guess for the first lock combination number was incorrect, the player guessed again, the
	 * player's guess was in the list of possible lock combination numbers for lock combination number one, XOR
	 * a statement was printed that indicated that the player's guess was too small, XOR a statement was printed that 
	 * indicated that the player's guess was too large. 
	 * 
	 * POST 2: (second lock number): The player guessed the second lock combination number, XOR the player gave up, 
	 * and the player didn't give up on guessing the first lock number, XOR the player's guess for the second lock 
	 * combination number was incorrect, the player guessed again, the player's guess was in the list of possible lock 
	 * combination numbers for lock combination number two, XOR a statement was printed that indicated that the player's 
	 * guess was too small, XOR a statement was printed that indicated that the player's guess was too large. 
	 * 
	 * POST 3 (third lock number): The player guessed the third lock combination number, XOR the player gave up,
	 * and the player did not give up on guessing the second lock number or the first lock number,  XOR the player's guess 
	 * for the third lock combination number was incorrect, the player guessed again, the player's guess was in the list of possible 
	 * lock combination numbers for lock combination number three, XOR a statement was printed that indicated that the player's guess was 
	 * too small, XOR a statement was printed that indicated that the player's guess was too large. 
	 * 
	 * POST 4 (complement): The player guessed all three lock combination numbers, and this cabinet was unlocked.
	 */
	public void guessCombination() {

		boolean possible = false;
		int guessNum = -1;
		String guess;

		System.out.println("As you examine the lock, you notice that each lock combination number is greater than the last.");
		System.out.println("Enter your guess for the first combination number or enter give up to stop.");

		/*
		 * State 1 (first guess): The player's guess did not equal the first lock combination number, the player entered another guess, 
		 * binarySearch was called and determined whether the player's guess was a possible lock combination number for the 
		 * first combination number and statement was printed that indicated that the value was too large or too small if and only if 
		 * the player's guess was not a possible combination number, XOR the player's guess equaled the first lock combination
		 * number, and a statement was printed to indicate that the guessed number was correct, XOR the player entered 
		 * "give up" and the method ended.
		 * 
		 */
		while(guessNum != firstNum) {

			System.out.print(">");
			guess = aConsole.nextLine();

			if(guess.equalsIgnoreCase("give up")) {
				return;
			}

			guessNum = Integer.parseInt(guess);
			possible = binarySearch(possibleNumsOne, guessNum, 0, possibleNumsOne.length-1);

			if (!possible) {

				if(guessNum > possibleNumsOne[possibleNumsOne.length-1]) {	
					System.out.println("Try a different number. " + guessNum + " is too large to be a combination number");	
				}
				else {

					System.out.println("Try a different number. " + guessNum + " is too small to be a combination number.");	

				}	
			}
		}

		System.out.println("Congratulations! You found the first combination. It was " + guessNum);
		System.out.println("Enter your guess for the second combinaton number or enter give up to stop.");

		/*
		 * State 2 (second guess): The player's guess did not equal the second lock combination number, the player entered another guess, 
		 * binarySearch was called and determined whether the player's guess was a possible lock combination number for the 
		 * second combination number and a statement was printed that indicated that the value was too large or too small if and only if 
		 * the player's guess was not a possible combination number, XOR the player's guess equaled the second lock combination
		 * number, and a statement was printed to indicate that the guessed number was correct, XOR the player entered 
		 * "give up", and the method ended.
		 */
		while(guessNum!= secondNum) {

			System.out.print(">");
			guess = aConsole.nextLine();

			if(guess.equalsIgnoreCase("give up")) {
				return;
			}

			guessNum = Integer.parseInt(guess);
			possible = binarySearch(possibleNumsTwo, guessNum, 0, possibleNumsTwo.length-1);

			if (!possible) {

				if(guessNum > possibleNumsTwo[possibleNumsTwo.length-1]) {	
					System.out.println("Try a different number. " + guessNum + " is too large to be a combination number");	
				}
				else {
					System.out.println("Try a different number. " + guessNum + " is too small to be a combination number.");	
				}
			}
		}

		System.out.println("Congratulations! You found the second combination. It was " + guessNum);
		System.out.println("Enter your guess for the third combinaton number or enter give up to stop.");

		/*
		 * State 3 (third guess): The player's guess did not equal the third lock combination number, the player entered another guess, 
		 * binarySearch was called and determined whether the player's guess was a possible lock combination number for the 
		 * third combination number and a statement was printed that indicated that the value was too large or too small if and only if 
		 * the player's guess was not a possible combination number, XOR the player's guess equaled the third lock combination
		 * number, and a statement was printed to indicate that the guessed number was correct, XOR the player entered 
		 * "give up" and the method ended.
		 */
		while (guessNum != thirdNum) {

			System.out.print(">");
			guess = aConsole.nextLine();

			if(guess.equalsIgnoreCase("give up")) {
				return;
			}

			guessNum = Integer.parseInt(guess);
			possible = binarySearch(possibleNumsThree, guessNum, 0, possibleNumsThree.length-1);

			if (!possible) {
				if(guessNum > possibleNumsThree[possibleNumsThree.length-1]) {	
					System.out.println("Try a different number. " + guessNum + " is too large to be a combination number.");	
				}
				else {
					System.out.println("Try a different number. " + guessNum + " is too small to be a combination number.");	
				}
			}

		}

		//State 4 (unlocked): The player guessed all three lock combination numbers, and this cabinet was unlocked.
		System.out.println("Congratulations! You found the third combination. It was " + guessNum);
		System.out.println(this.aName + " is now unlocked.");
		isLocked = false;

	}

	/**
	 * INTENT: Returns true if the player's guess for the lock combination number is a possible lock combination number.
	 * 
	 * SHORTHAND(key): key is the player's guess for the lock combination number.
	 * 
	 * PREDCONDITION 1 (non-trivial): arr is non-empty.
	 * 
	 * PRE 2 (sorted): arr is sorted in non-decreasing order.
	 * 
	 * PRE 3: arr contains the possible lock combination values for the lock combination number that the player is trying to guess.
	 * 
	 * PRE 3 (low): low has the value 0 at the first method call.
	 * 
	 * PRE 4 (high): high has the value arr.length -1 at the first method call.
	 * 
	 * POSTCONDITION (found): low was less than or equal to high, mid was (low+high)/2, the value at index mid in arr was 
	 * equal to the key, and true was returned, XOR low was greater than high and false was returned, XOR the value at index mid in arr 
	 * was not equal to the key.  
	 * 
	 * POST 2 (searched right of mid): low was less than or equal to high, mid was (low + high)/2, the value at index mid in arr was less 
	 * than the key, and binarySearch was called to search for the key to the right of mid, where low was mid+1, XOR low was greater than 
	 * high, and false was returned, XOR the value at index mid in arr was greater than or equal to the key.
	 * 
	 * POST 3 (searched left of mid): low was less than or equal to high, mid was (low+high)/2, the value at index mid in arr was greater 
	 * than the key, and binarySearch was called to search for the key to the left of mid, where high was mid-1, XOR low was greater than 
	 * high and false was returned, XOR the value at index mid in arr was less than or equal to the key.
	 * 
	 * POST 4 (key not found): low was greater than high, the key was not found, and false was returned, XOR true was returned, and the 
	 * key was found.
	 */
	public static boolean binarySearch(int []arr, int key, int low, int high) {

		if (low <= high) {

			/*
			 * State 1 (search): low is less than or equal to high, and the middle of this sub-array is calculated.
			 */

			int mid = (low + high)/2;

			if (arr[mid]==key) {
				/*
				 * State 2 (found): The value at index mid in arr equals the key, and true is returned.
				 */
				return true;
			}
			else {
				if(arr[mid] < key) {
					/*
					 * State 3 (search right): The value at index mid in arr is 
					 * less than the key, and binarySearch is called to search for the 
					 * key to the right of mid.
					 */
					return binarySearch(arr, key, mid+1, high);
				}
				else {
					/*
					 * State 4 (search left): the value at index mid in 
					 * arr is greater than the key, and binarySearch is called to 
					 * search for the key to the left of mid.
					 */
					return binarySearch(arr, key, low, mid -1);
				}	
			}

		}

		// State 5 (not found): key was not found, and false is returned.
		return false;
	}

}
