import java.util.ArrayList;

/**
 * TunnelRoom contains a method to construct a TunnelRoom with a weight, dest, start, startName, and destName.
 * TunnelRoom objects are used in Tunnels.java to find the shortest path in a graph.
 * 
 * @author Michael Shriner
 */
public class TunnelRoom {

	int weight;
	int dest;
	int start;
	String startName;
	String destName;

	/**
	 * INTENT: Constructs a TunnelRoom.
	 * 
	 * POSTCONDITION: weight, dest, and start were assigned the parameter values, and startName and destName were
	 * assigned the names "Tunnel Room" with the start or the dest plus one appended to it.
	 */
	public TunnelRoom (int weight, int start, int dest) {
		this.weight = weight;
		this.dest = dest;
		this.start = start;
		startName = String.format("Tunnel Room %d", start + 1);
		destName = String.format("Tunnel Room %d", dest + 1);
	}

}
