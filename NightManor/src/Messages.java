import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Messages contains methods to put a text file into a HashMap such that sentences are associated with keys like a dictionary.
 * 
 * @author Michael Shriner
 */
public class Messages {

	static final HashMap<String,String> roomDescriptions = new HashMap<String,String>();
	static final HashMap<String,String> introduction = new HashMap<String,String>();
	static final HashMap<String,String> novak = new HashMap<String,String>();
	static final HashMap<String,String> objectDescriptions = new HashMap<String,String>();
	static final HashMap<String,String> help = new HashMap<String,String>();

	/**
	 * INTENT: Put a text file into a HashMap and associate keys with statements.
	 * 
	 * EXAMPLE: "I have a dog" is associated with the key "pet owner", and I can locate and retrieve the statement 
	 * "I have a dog" using the key "pet owner".
	 * 
	 * SHORTHAND(entry): An entry refers to the key word and associated sentence(s).
	 * 
	 * PRECONDITION (text format): The text file is formatted such that the key word is on the line directly above the
	 * sentence(s) associated with that key word, and there is an empty line between separate entries.
	 * 
	 * POSTCONDITION: The parameter text file was read, each key associated with a description in that text file was 
	 * associated in the parameter HashMap, and the HashMap contained each key and associated description in the file, XOR
	 * the file sent in as a parameter was not found.
	 */
	public static void loadHashMap(String fileName, HashMap <String,String> hashMap) {

		Scanner anInputFile;

		/*
		 * State 1 (inside try brackets): the parameter text file was found, the text file had another line, 
		 * a key word in the parameter file was assigned to a key variable, the sentences associated with that 
		 * key were added to an ArrayList, a line in the text file was empty, and the sentences and key were put 
		 * (associated) in the HashMap, XOR the text file did not have another line
		 */
		try {
			anInputFile = new Scanner(new File(fileName));

			while (anInputFile.hasNextLine()) {

				String key = anInputFile.nextLine().strip();
				ArrayList<String> description = new ArrayList<String>();

				while (anInputFile.hasNextLine()) {
					String line = anInputFile.nextLine();

					//State 2 (break inner loop): The leading and trailing white space in the current line was removed,
					//the current line was empty, and the inner while loop was exited, XOR the leading and trailing white space
					//in the current line was removed, and the current line was not empty.
					if (line.strip().equals("")) {
						break;
					}
					description.add(line);
				}
				hashMap.put(key, String.join("\n", description));
			}

			anInputFile.close();

			//State 3 (inside catch brackets): the parameter text file was not found and a statement was 
			//printed that indicated that it was not found.
		} catch (FileNotFoundException fnfe) {
			System.out.println("Did not find the file: " + fileName);
		}

	}//end of loadHashMap method

	/**
	 * INTENT: Calls loadHashMap to load the text files into the related HashMaps.
	 * 
	 * POSTCONDITION: Each HashMap was sent to loadHashMap as a parameter with an associated text file, and that 
	 * text file was found.
	 */
	public static void loadHashMap() {

		loadHashMap("Night Manor Introduction.txt", Messages.introduction);
		loadHashMap("Room Descriptions.txt", Messages.roomDescriptions);
		loadHashMap("Novak.txt", Messages.novak);
		loadHashMap("Object Descriptions.txt", Messages.objectDescriptions);
		loadHashMap("Help.txt", Messages.help);		
	}

}
