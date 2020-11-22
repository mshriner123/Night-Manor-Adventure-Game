import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Tunnels contains methods to construct a Tunnels graph, find and print the shortest path to each vertex in this Tunnels from a
 * start vertex, and initialize a Tunnels graph and call the shortest path function.
 * 
 * @author Michael Shriner
 */
public class Tunnels {

	int numV;
	int numE;
	int parent[];
	int distance[];

	ArrayList<TunnelRoom> edges = new ArrayList <TunnelRoom>();

	/**
	 * INTENT: Constructs a Tunnels object.
	 * 
	 * PRECONDITION (non-trivial): numV > 0 and numE > 0.
	 * 
	 * POSTCONDITION 1: The number of vertices and edges in this graph were initialized.
	 * 
	 * POST 2: The parent and distance arrays were initialized with length as the number of vertices in this graph.
	 */
	public Tunnels (int numV, int numE) {
		this.numV = numV;
		this.numE = numE;
		distance = new int[this.numV];
		parent = new int[this.numV];
	}

	/**
	 * INTENT: Determines the shortest path to each vertex in the graph from a start vertex.
	 * 
	 * SHORTHAND (numV): numV is the number of vertices in this Tunnels graph.
	 * 
	 * PRECONDITION 1(start): 0 <= start <= 17
	 * 
	 * PRE 2 (no negative cycles): This Tunnels graph does not contain negative cycles.
	 * 
	 * POSTCONDITION 1 (parent): parent[i] was the vertex directly before vertex i in the lowest cost path to vertex i from the start vertex 
	 * for each i in the interval [0, numV).
	 * 
	 * POST 2(distance): distance[i] was the lowest cost path from the start vertex to vertex i for each i in the interval [0,numV).
	 */
	public void bellmanFord (int start) {

		/*
		 * SHORTHAND (numVG): numVG is the number of vertices in G (see next shorthand).
		 * 
		 * SHORTHAND(G): G is a sub-graph of this Tunnels graph where distance[i] is the lowest cost path
		 * from the start vertex to vertex i for each i in the interval [0, numVG), and parent[i] is the vertex directly 
		 * before vertex i in the lowest cost path to vertex i from the start vertex for each i in the interval [0, numVG).
		 */

		for (int i = 0; i < this.numV; i++) {
			parent [i] = -1;
			distance [i] = Integer.MAX_VALUE;	
		}

		distance[start]=0;

		/*
		 * State 1 (cheapest): G consists of all shortest paths from start of length <= i where i is the number of 
		 * vertices in this Tunnels graph. The parent array contains the parent of each vertex in the shortest paths that
		 * G contains, and the distance array contains the costs of each of the shortest paths in G.
		 */
		for (int i = 1; i < this.numV;i++) {
			for (int x = 0; x < this.numE;x++) {

				int strt = edges.get(x).start;
				int dest = edges.get(x).dest;
				int weight = edges.get(x).weight;

				if (distance[dest] > (distance[strt]+ weight) && distance[strt]!=Integer.MAX_VALUE) {
					distance[dest] = (distance[strt] + weight);	
					parent[dest] = strt;
				}
			}
		}

		/*
		 * State 2 (greed used): All changes to G have been additive and optimizing based upon the data 
		 * available at the time.
		 *
		 * State 3 (numVG = numV): The number of vertices in G is the number of vertices in this Tunnels graph.
		 */

		print();		
	}

	/**
	 * INTENT: Prints the shortest path to each vertex in this Tunnels graph from the start vertex that was sent in as a parameter
	 * to bellmanFord.
	 * 
	 * PRECONDITION 1 (method call): This method is only called from bellmanFord.
	 * 
	 * POSTCONDITION 1: The path to each vertex in this Tunnels graph from the start vertex that was sent in as a parameter to bellmanFord 
	 * was printed.
	 */
	public void print () {

		/*
		 * SHORTHAND(P): P is the number of paths that have been printed.
		 * 
		 * SHORTHAND (numV): numV is the number of vertices in this Tunnels graph.
		 */

		/*
		 * State 1: 0 <= P <= vertex AND vertex < numV
		 */
		for (int vertex =0; vertex < this.numV; vertex++) {

			int destV = vertex;
			System.out.printf("Path to Tunnel Room %d: ", vertex);
			int parentOfVertex = parent[vertex];

			printPath(parentOfVertex);

			System.out.println("Tunnel Room " + destV);		
		}

		/*
		 * State 2: P = vertex AND vertex >= numV
		 */
	}

	/**
	 * INTENT: Prints the path from the start vertex that was sent in as a parameter to bellmanFord to 
	 * the vertex with parent parentOfVertex. 
	 * 
	 * SHORTHAND (parent): The parent of a vertex v is the vertex that comes directly before it in the shortest path 
	 * to v. 
	 * 
	 * PRECONDITION 1 (method call): This method is only called from the print method.
	 * 
	 * POSTCONDITION 1: The path from the start vertex that was sent in as a parameter to bellmanFord to the vertex with
	 * parent parentOfVertex was printed.
	 * 
	 */
	public void printPath(int parentOfVertex) {

		/*
		 * State 1 (base case): parentOfVertex is the parent of the start vertex and this method call returns, XOR 
		 * this method is called again with the parent of parentOfVertex.
		 */
		if (parentOfVertex == -1) {
			return;
		}

		/*
		 * State 2 (recursive call): This method is called again with the parent of parentOfVertex.
		 */
		printPath(parent[parentOfVertex]);

		/*
		 * State 3: The vertex parentOfVertex is printed.
		 */
		System.out.printf("Tunnel Room %d -- ", parentOfVertex);
	}

	/**
	 * INTENT: Initializes a graph and calls bellmanFord to print the shortest paths to each vertex in the graph from a start vertex.
	 * 
	 * SHORTHAND(tunnel system): The tunnel system contains tunnel rooms 0 through 17, and those tunnel rooms have room numbers 
	 * 29 through 46.
	 * 
	 * PRECONDITION (map): The Map with the name "Map of the Tunnels" is in the player's inventory.
	 * 
	 * POSTCONDITION 1 (graph initialized): An undirected, weighted graph was initialized with TunnelRoom vertices.
	 * 
	 * POST 2: bellmanFord was called with the player's current location in the tunnel system as the start vertex.
	 */
	public static void useMap() {

		Scanner aConsole = new Scanner (System.in);

		/*
		 * State 1 (edges): The two dimensional array edges contains edges that connect TunnelRoom vertices.
		 */
		int edges[][]= {{1, 0, 1}, {1,1,0},{1,0,2}, {1,2,0} ,{1,1, 4},{1,4,1} ,{1, 2, 3}, {1,3,2} ,{1, 4, 5}, {1,5,4},
				{1,1,6},{1,6,1}, {1,5,7}, {1,7,5}, {1,7,8}, {1,8,7}, {1,6,8}, {1,8,6}, {1,8,9},{1,9,8},{1,9,10},{1,10,9}, {1,10,11},
				{1,11,10},{1,11,12},{1,12,11},{1,12,13},{1,13,12},{1,7,12}, {1,12,7} ,{1,2,14},{1,14,2}, {1,14,16},{1,16,14}, {1,16,17},{1,17,16} ,
				{1,17,15},{1,15,17}, {1,3,15}, {1,15,3}, {1,15,14},{1,14,15}};

		/*
		 * State 2 (Tunnels): A Tunnels graph contains 18 TunnelRoom vertices and the same number of edges as the size of
		 * the edges array.
		 */
		Tunnels graph = new Tunnels(18, edges.length);

		for (int[] edge : edges ) {
			graph.edges.add(new TunnelRoom(edge[0], edge[1], edge[2]));
		}

		/*
		 * State 3 (bellmanFord): startInt is the player's current location within the tunnel system (see shorthand), and bellmanFord is called
		 * with startInt as a parameter, XOR the player is not in the tunnel system.
		 */

		int playerLocation = NightManor.thePlayer.getLocation().getRoomNumber();

		if (playerLocation >= 29 && playerLocation <= 46) {

			String startName = NightManor.thePlayer.getLocation().getRoomName();
			int startInt = Integer.parseInt(startName.substring(12));

			graph.bellmanFord(startInt);
		}
		else {
			System.out.println("Only the tunnels are labeled on this map. Try a different map.");
		}
	}

}