import java.io.Serializable;
import java.util.ArrayList;

public class Node implements Serializable{

	public int attribute = -1; // for internal nodes holds the attribute used for the node
	public int label = -1; // for leaf nodes label holds the class label
	public ArrayList<Node> children = new ArrayList<Node>();

	public Node() {
		children = new ArrayList<Node>();
	}

	public boolean isLeaf() {
		if (children.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
