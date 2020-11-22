import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * SpiderWeb contains methods to construct a SpiderWeb, add edges to this SpiderWeb, initiate the spider trap,
 * set the trap, run the trap, provide hints for escaping the trap, print the nodes, edges, and weights in this
 * SpiderWeb, attempt to escape the web, remove an edge, and find the minimum spanning tree of this SpiderWeb.
 * See the method headers below for more details.
 * 
 * @author Michael Shriner
 */
public class SpiderWeb {

	int numV;
	LinkedList<SpiderNode>[] adj;

	static Scanner aConsole = new Scanner (System.in);

	/**
	 * INTENT: Constructs a SpiderWeb.
	 * 
	 * PRECONDITION 1 (non-trivial): numV > 0
	 * 
	 * POSTCONDITION 1 (numV): numV was the number of vertices in this SpiderWeb.
	 * 
	 * POST 2 (adj): The LinkedList adj was size numV and initialized.
	 */
	@SuppressWarnings("unchecked")
	public SpiderWeb (int numV) {
		/*
		 * State 1 (numV): numV is the number of vertices in this SpiderWeb.
		 */
		this.numV = numV;

		/*
		 * State 2 (adj): adj is size numV and initialized.
		 */
		adj = new LinkedList[this.numV];

		for (int i = 0; i < numV; i++) {
			adj[i]= new LinkedList<>();
		}
	}

	/**
	 * INTENT: Adds undirected, weighted edges to this SpiderWeb.
	 * 
	 * POSTCONDITION 1 (SpiderNodes): Two SpiderNode objects were initialized where each SpiderNode was one 
	 * side of the edge.
	 * 
	 * POST 2 (adj): Both SpiderNode objects were added to the LinkedList adj.
	 */
	public void addEdge(int start, int dest, int weight, String name0, String name1) {
		SpiderNode node0 = new SpiderNode(dest, weight,name0, name1);
		SpiderNode node1 = new SpiderNode (start, weight, name1, name0);
		adj[start].add(node0);
		adj[dest].add(node1);	
	}

	/**
	 * INTENT: Prints out the instructions for escaping the spider web trap and calls methods to set up and 
	 * run the trap.
	 * 
	 * PRECONDITION 1 (location): The player was in the room "dark hall" and entered "go west".
	 * 
	 * POSTCONDITON 1 (instructions): The instructions to escape the spider web trap were printed to the console.
	 * 
	 * POST 2 (set trap): A method to add the edges to this SpiderWeb was called.
	 * 
	 * POST 3 (run trap): A method that allows the player to examine the graph, remove edges, get a hint, and 
	 * try to escape the trap was called.
	 */
	public void spiderTrap() {

		/*
		 * State 1 (introduction): An introduction to the trap prints to the console.
		 */
		System.out.println(Messages.roomDescriptions.get("Spider Web"));
		NightManor.edgeList.get(101).closePassage(false);

		boolean done = false;

		/*
		 * State 2 (instructions): Instructions to escape from the trap print to the console.
		 */
		while (!done) {
			System.out.print(">");
			String aCommand = aConsole.nextLine();

			if (aCommand.equalsIgnoreCase("more instructions")) {
				done = true; 
				System.out.println(Messages.help.get("spider trap"));
			}
		}	
		/*
		 * State 3 (set and run): The methods that set and run the trap are called.
		 */
		setTrap();
		runTrap();
	}

	/**
	 * INTENT: Allows the player to call functions to examine this SpiderWeb, escape this SpiderWeb, 
	 * remove an edge, get a hint, and see the instructions again.
	 * 
	 * PRECONDITION 1 (setTrap): setTrap was called before this method was called.
	 * 
	 * POSTCONDITION 1 (examine web): printWeb() was called if and only if the player entered "examine web" AND inTrap = true.
	 * 
	 * POST 2 (see instructions): The instructions to escape the spider web trap were printed if and only if the player
	 * entered "see instructions" AND inTrap = true.
	 * 
	 * POST 3 (remove thread): removeWhichThread() was called if and only if the player entered "remove thread" AND inTrap = true.
	 * 
	 * POST 4 (escape): escape() was called if and only if the player entered "escape" AND inTrap was true before the player
	 * entered "escape."
	 * 
	 * POST 5 (hint): hint(hintNum) was called if and only if the player entered hint AND inTrap = true
	 */
	public void runTrap() {
		boolean inTrap = true; 
		int hintNum = 0;
		/*
		 * State 1: inTrap = true and POSTCONDITION 1 through POST 5 are attained.
		 */
		while (inTrap) {
			System.out.print(">");
			String aCommand = aConsole.nextLine();

			if (aCommand.equalsIgnoreCase("see instructions")) {
				System.out.println(Messages.help.get("spider trap"));
			}
			else {
				if (aCommand.equalsIgnoreCase("examine web")) {
					printWeb();
				}
				else {
					if (aCommand.equalsIgnoreCase("remove thread")) {
						removeWhichThread();
					}
					else
					{
						if (aCommand.equalsIgnoreCase("escape")) {
							inTrap = false;
							escape();
						}
						else {
							if(aCommand.equalsIgnoreCase("hint")) {
								hintNum++;
								hint(hintNum);
							}
						}
					}
				}
			}	
		}

		/*
		 * State 2: inTrap = false
		 */
	}

	/**
	 * INTENT: Gives a hint to the player if and only if hintNum <= 3 and hintNum >= 1.
	 * 
	 * SHORTHAND (hint): A hint is a piece of advice for escaping the spider web trap.
	 * 
	 * PRECONDITION 1 (hintNum): hintNum >= 1.
	 * 
	 * POSTCONDITION 1 (first hint): The first hint was printed to the console if and only if hintNum = 1
	 * 
	 * POST 2 (second hint): The second hint was printed to the console if and only if hintNum = 2 
	 * 
	 * POST 3 (third hint): the third hint was printed to the console if and only if hintNum = 3
	 * 
	 * POST 4 (no hint): no hint was printed if and only if hintNum > 3, XOR precondition 1 was not attained. 
	 */
	public void hint(int hintNum) {

		/*
		 * State 1 (first hint): hintNum == 1 AND the first hint is printed, XOR hintNum > 1
		 */
		if (hintNum == 1) {
			System.out.println("Hint #1:");
			int lowestSum = primsMST();
			System.out.println("When you try to escape, the total likelihood of removing the remaining threads");
			System.out.println("in the spider web should be " + lowestSum);
		}
		else {
			/*
			 * State 2 (second hint): hintNum == 2 AND the second hint is printed, XOR hintNum > 2
			 */
			if (hintNum == 2) {
				System.out.println("Hint #2:");
				System.out.println("Remove the thread that starts at Spider 7 and ends at Spider 10.");
			}
			else {
				/*
				 * State 3 (third hint): hintNum == 3 AND the third hint is printed, XOR hintNum > 3
				 */
				if (hintNum == 3) {
					System.out.println("Hint #3:");
					System.out.println("Remove the thread that starts at Spider 4 and ends at Spider 8.");
				}
				else {
					/*
					 * State 4 (no hint): hintNum > 3 and no hint is printed
					 */
					System.out.println("You are out of hints.");
				}
			}
		}
	}

	/**
	 * INTENT: Determines whether the player successfully escaped the spider web trap and responds accordingly. 
	 * 
	 * POSTCONDITION 1 (lowestSum): lowestSum equaled the sum of the weights of the minimum spanning tree of this SpiderWeb.
	 * 
	 * POST 2 (playerSum): playerSum equaled the sum of the edge weights of this SpiderWeb that have not been removed. 
	 * An edge was removed if and only if its corresponding SpiderNode had the value true for the removed data member.
	 * 
	 * POST 3 (comparison): playerSum = lowestSum, and the player escaped, XOR playerSum != lowestSum, all threads were
	 * terminated, and the game ended.
	 */
	public void escape() {
		/*
		 * State 1 (lowestSum): lowestSum = the sum of the weights of the minimum spanning tree of this SpiderWeb
		 */
		int lowestSum = primsMST();
		int playerSum = 0;

		/*
		 * State 2 (playerSum): playerSum = the sum of the edge weights of this SpiderWeb that have not been removed.
		 */
		for (int i = 0; i < SpiderNode.spiders.size(); i+=2) {

			if (SpiderNode.spiders.get(i).removed == false) {
				int weight = SpiderNode.spiders.get(i).getWeight();
				playerSum+= weight;
			}
		}

		/*
		 * State 3 (comparison): playerSum = lowestSum, and the player isn't stuck in the trap anymore, XOR 
		 * playerSum != lowestSum, all threads are terminated, and the game ends.
		 */
		if (lowestSum == playerSum) {
			System.out.println("You escaped! Go west to leave this room before the spiders realize you're gone.");
		}
		else {
			System.out.println("As you crept away, you woke the spiders.");	
			System.out.println("Game over.");
			ManageSubThreads.interruptAll();
		}
	}


	/**
	 * INTENT: Determines which thread in this SpiderWeb that the player wants to remove and calls a method to remove it.
	 *
	 * POSTCONDITION 1 (thread specified): spider0 is the name of the start node of an edge and spider1 is the 
	 * destination node of an edge.
	 * 
	 * POST 2 (thread removed): The specified thread was removed XOR the specified thread didn't exist XOR the specified 
	 * thread was already removed.
	 */
	public void removeWhichThread() {
		/*
		 * State 1 (thread specified): spider0 is the name of the start node of an edge and spider1 is the 
		 * destination node of an edge.
		 */
		System.out.println("Enter the name of the first spider connected to the thread that you want to remove (e.g., spider 2)");
		System.out.print(">");
		String spider0 = aConsole.nextLine();

		System.out.println("Enter the name of the second spider connected to the thread that you want to remove.");
		System.out.print(">");
		String spider1 = aConsole.nextLine();

		boolean edgeRemoved = false;
		int i = 0;

		/*
		 * State 2 (thread removed): The thread was removed, XOR it was already removed, XOR it wasn't found.
		 */
		while (!edgeRemoved && i < SpiderNode.spiders.size()) {

			SpiderNode node = SpiderNode.spiders.get(i);

			if (node.getName().equalsIgnoreCase(spider0)&& node.getDestName().equalsIgnoreCase(spider1)) {
				node.remove();
				edgeRemoved = true;
			}

			i+=2;
		}

		if (!edgeRemoved) {
			System.out.println("That thread doesn't exist.");
		}
	}

	/**
	 * INTENT: Prints the nodes, edges, and edge weights in this SpiderWeb.
	 * 
	 * EXAMPLE: The following is what the output of this method looks like.
	 * spider 1 -- 8 --spider 2
	 * spider 1 -- 45 --spider 3
	 * spider 3 -- 12 --spider 5
	 * 
	 * SHORTHAND(removed): A SpiderNode is removed if and only if that SpiderNode's removed data member is true.
	 * 
	 * SHORTHAND (S): S is the length of the ArrayList that contains each SpiderNode.
	 * 
	 * PRECONDITION 1 (S is even): S is even because each edge is undirected. Whenever a SpiderNode with start s, destination
	 * d, and weight w is added to this SpiderWeb, a SpiderNode with start d, destination s, and weight w is also added.
	 * 
	 * POSTCONDITION 1 (printed): Each SpiderNode i where i is in the interval [0,S), and i is a multiple of 2 or 0 was printed
	 * if and only if SpiderNode i was not removed.
	 * 
	 * Note: We printed every second SpiderNode and the SpiderNode at index 0 to avoid printing each SpiderNode twice because
	 * they are undirected.
	 */
	public void printWeb() {

		for (int i = 0; i < SpiderNode.spiders.size();i += 2) {

			if (SpiderNode.spiders.get(i).removed == false) {
				String startName = SpiderNode.spiders.get(i).getName();
				String destName = SpiderNode.spiders.get(i).getDestName();
				int weight = SpiderNode.spiders.get(i).getWeight();

				System.out.println(startName + " -- " + weight + " --" + destName);
			}
		}
	}

	/**
	 * INTENT: Adds edges to this SpiderWeb.
	 * 
	 * POSTCONDITION 1: Undirected, weighted edges were added to this SpiderWeb.
	 */
	public void setTrap() {
		this.addEdge(0, 1, 8, "spider 1", "spider 2");
		this.addEdge(0, 2, 45, "spider 1", "spider 3");
		this.addEdge(2, 4, 12, "spider 3", "spider 5");
		this.addEdge(2, 13, 50, "spider 3", "spider 14");
		this.addEdge(13, 11, 65, "spider 14","spider 12");
		this.addEdge(13, 5, 75, "spider 14","spider 6");
		this.addEdge(11, 12, 7, "spider 12","spider 13");
		this.addEdge(5, 8, 5, "spider 6","spider 9");
		this.addEdge(5, 7, 6, "spider 6","spider 8");
		this.addEdge(5, 6, 25, "spider 6","spider 7");
		this.addEdge(6, 9, 90, "spider 7","spider 10");
		this.addEdge(6, 10, 40, "spider 7","spider 11");
		this.addEdge(4, 3, 16, "spider 5","spider 4");
		this.addEdge(1, 11, 40, "spider 2","spider 12");
		this.addEdge(12, 5, 15, "spider 13","spider 6");
		this.addEdge(9, 10, 30, "spider 10","spider 11");
		this.addEdge(3, 7, 65, "spider 4","spider 8");
		this.addEdge(3, 5, 8, "spider 4","spider 6");
		this.addEdge(12, 13, 25,"spider 13","spider 14");
		this.addEdge(8, 6, 20, "spider 9","spider 7");
	}


	/**
	 * INTENT: Returns the sum of the minimum spanning tree of this SpiderWeb.
	 * 
	 * SHORTHAND (mst): mst represents a minimum spanning tree. If mist[i] is true, then the vertex 
	 * i is in mst, and if mst[i] is false, then the vertex i is not in mst for each i in the interval
	 * [0, numV).
	 * 
	 * SHORTHAND(partMST): partMST contains the vertices that are in mst (as defined above).
	 * 
	 * PRECONDITION 1: This SpiderWeb is undirected, weighted, and connected.
	 * 
	 * PRE 2 (non-trivial): This SpiderWeb is non-empty.
	 * 
	 * POSTCONDITION 1 (partMST.length <= numV): The length of partMST <= numV where numV was the number of vertices in this SpiderWeb.
	 * 
	 * POST 2 (connected and no cycles): partMST was connected and contained no cycles
	 * 
	 * POST 3 (partMST.length = numV): The length of partMST = numV.
	 * 
	 * POST 4 (complement): The sum of the weights in partMST was returned.
	 */
	public int primsMST () {

		int sum = 0;
		Boolean[] mst = new Boolean [this.numV];
		PrimNode [] node = new PrimNode [this.numV];

		for (int i = 0; i < mst.length;i++) {
			node[i] = new PrimNode();
			mst[i]=false;
			node[i].keyVal = Integer.MAX_VALUE;
			node[i].vNum=i;
		}

		mst[0] = true;
		node[0].keyVal=0;

		PriorityQueue <PrimNode> pq = new PriorityQueue <PrimNode>();

		for (int i = 0; i < this.numV; i++) {
			pq.add(node[i]);
		}

		/*
		 * State 1 (partMST): partMST (see shorthand) <= numV
		 */
		while (!pq.isEmpty()) {

			PrimNode aMinNode = pq.remove();
			sum+=aMinNode.keyVal;

			mst[aMinNode.vNum] = true;

			for (SpiderNode aNode : this.adj[aMinNode.vNum]) {

				if (mst[aNode.dest] == false) {

					if (node[aNode.dest].keyVal > aNode.weight) {

						pq.remove(node[aNode.dest]);
						node[aNode.dest].keyVal = aNode.weight;
						pq.add(node[aNode.dest]);					
					}
				}	
			}	
		}

		/*
		 * State 2 (Greed used): Every change to partMST was the addition of the cheapest edge emanating from it.
		 * 
		 * State 3 (numV): partMST = numV
		 * 
		 * State 4 (complement): sum equals the sum of the edge weights in partMST and is returned.
		 */

		return sum;
	}

}
