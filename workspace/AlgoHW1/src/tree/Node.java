package tree;

class Node {
	// variables
	public Boolean isLeaf;		// (only node's)		indicate whether node is leaf or not
	int number;					// (only node's)		number which node has saved
	String path;				// (depending parent)	path from root to node
	int sumFromRoot;			// (depending parent)	sum from root to node
	private Node parent;		// (depending parent)	node's parent
	private Node left;			// (only node's)		node's left child
	private Node right;			// (only node's)		node's right child
	
	// default constructor
	Node() {
		isLeaf = false;
		number = -1;
		path = "";
		sumFromRoot = -1;
		parent = null;
		left = null;
		right = null;
	}
	// constructor only for root
	Node(int rootNumber) {
		this.number = rootNumber;
		this.path = "0";	// '0' is left or root
		
		isLeaf = false;
		sumFromRoot = rootNumber;
		parent = null;
		left = null;
		right = null;
	}
	// constructor for node
	Node(int number, Node parent, String newPath) {
		this.number = number;
		this.parent = parent;
		this.path = parent.path + newPath;
		
		isLeaf = false;
		sumFromRoot = parent.sumFromRoot + number;
		left = null;
		right = null;
	}
	
	// setter only for a parent node to link their children tree with them
	void setChildToLeft(Node child) {
		left = child;
	}
	void setChildToRight(Node child) {
		right = child;
	}
	
	Boolean isLeftChild(Node child) {
		if(left == child) return true;
		else return false;
	}
	Boolean isRightChild(Node child) {
		if(right == child) return true;
		else return false;
	}
	
	void releaseLeftChild() {
		left = null;
	}
	void releaseRightChild() {
		right = null;
	}
	
	// request to terminate node's child trees to the GC (Garbage Collector)
	void deleteAll() {
		if(this.left != null) {
			this.left.deleteAll();
			this.left = null;
		}
		if(this.right != null) {
			this.right.deleteAll();
			this.right = null;
		}
	}
}
