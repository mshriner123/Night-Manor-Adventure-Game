/**
 * PrimNode implements Comparable and contains a compareTo method that defines the ordering of the
 * priority queue in SpiderWeb.java in primsMST(). PrimNode objects are used in primsMST() to find the
 * minimum spanning tree of a SpiderWeb.
 * 
 * @author Michael Shriner
 */
public class PrimNode implements Comparable <PrimNode> {

	int vNum;
	int keyVal;

	/**
	 * INTENT: Compares this PrimNode to the parameter PrimNode.
	 * 
	 * POSTCONDITION: Returns a negative integer, zero, or a positive integer if this PrimNode is less than the parameter PrimNode, equal to it,
	 * or greater than it, respectively.
	 */
	public int compareTo(PrimNode node) {
		return this.keyVal - node.keyVal;
	}
	
}
