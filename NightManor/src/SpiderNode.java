import java.util.ArrayList;

/**
 * SpiderNode contains methods to construct a SpiderNode, get the weight of this SpiderNode, get the destination of this
 * SpiderNode, get the start name of this SpiderNode, get the destination name of this SpiderNode, and remove this SpiderNode.
 * 
 * @author Michael Shriner
 */
public class SpiderNode {

	int dest;
	int weight;
	String name;
	String destName;
	static ArrayList<SpiderNode> spiders = new ArrayList <SpiderNode>();
	boolean removed = false;

	/**
	 * INTENT: Constructs a SpiderNode.
	 * 
	 * PRECONDITION 1: weight > 0
	 * 
	 * POSTCONDITION 1: A SpiderNode object with a dest, weight, name, and destName was created and appended to
	 * the ArrayList spiders.
	 */
	public SpiderNode(int d, int w, String n, String destN) {
		dest = d;
		weight = w;
		name = n;
		destName = destN;
		spiders.add(this);
	}
	/**
	 * INTENT: Returns the weight of this SpiderNode.
	 * 
	 * POSTCONDITION: The weight of this SpiderNode was returned.
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * INTENT: Returns the destination of this SpiderNode.
	 * 
	 * POSTCONDITION: The destination of this SpiderNode was returned.
	 */
	public int getDest() {
		return dest;
	}

	/**
	 * INTENT: Returns the start name of this SpiderNode.
	 * 
	 * POSTCONDITION: The start name of this SpiderNode was returned.
	 */
	public String getName() {
		return name;
	}

	/**
	 * INTENT: Returns the destination name of this SpiderNode.
	 * 
	 * POSTCONDITION: The destination name of this SpiderNode was returned.
	 */
	public String getDestName() {
		return destName;
	}

	/**
	 * INTENT: Removes this SpiderNode if it hasn't been removed already.
	 * 
	 * PRECONDITION: A SpiderNode was removed if and only if removed = true.
	 * 
	 * POSTCONDITION: this.removed = true if and only if this SpiderNode wasn't removed prior to this
	 * method call.
	 */
	public void remove() {

		if (removed) {
			System.out.println("You already removed that thread.");
		}
		else {
			removed = true;
			System.out.println("You removed that thread.");
		}
	}

}
