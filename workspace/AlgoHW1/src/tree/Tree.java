package tree;

// SingleTone
public class Tree {
	/*
	 * Result (inner class)
	 * - result of tree searching
	 */
	public static class Result {
		public Boolean isFind;		// indicate whether key is found or not.
		public Boolean isEmpty;		// indicate whether node is empty(null) or not.
		public String path;			// if key is found, indicate for key's path. if not, "-1". if indicate nothing, "-2".
		public int index;			// indicate for expression's searching index. if errored, -2.
		
		// constructor
		Result(Boolean isFind, Boolean isEmpty, String path, int index) {
			this.isFind = isFind;
			this.isEmpty = isEmpty;
			this.path = path;
			this.index = index;
		}
	}
	
	// constants(work as enumerate)
	public static final int VER_ROOT = 2;
	public static final int VER_LEFT = 0;
	public static final int VER_RIGHT = 1;
	
	// whether print testing on the console or not
	private static final Boolean turnOnTheConsoleTesting = false;
	
	// root node
	private static Node root;
	
	// delete tree instance (+ child nodes)
	public static void deleteInstance() {
		if(root != null) root.deleteAll();
		root = null;
	}
	
	public static void consolePathPrinting(final int keyToFind, String expression, String path) {
		if(turnOnTheConsoleTesting) System.out.println(expression + "<" + keyToFind + ">" + "::" + path);
	}
	
	public static Result searchTree(final int keyToFind, String expression, int index, int version) {
		return searchTree(keyToFind, expression, index, version, root);
	}
	// overloading
	public static Result searchTree(final int keyToFind, String expression, int index, int version, Node node) {
		if(!(version == VER_ROOT || version == VER_LEFT || version == VER_RIGHT))	return new Result(false, false, "-2", -2); // error catching
		
		int leafCaseTest = 0;	// test for that node is leaf node. If this become '2', node is leaf node.
		Node child;
		
		// first starting must be '('
		if(expression.charAt(index) == '(') {
			index++;	// go to the next case
			/*
			 * if next case is ')',	root version		-> there is no tree in expression. so return "-1" path.
			 * 						left,right version	-> this node is empty node. so return 'empty'.	
			 */
			if (expression.charAt(index) == ')')	return (version==VER_ROOT) ? new Result(false, false, "-1", -1) : new Result(false,true,"-2", index+1);
			// if next case is number
			else {
				// parse the number from expression string
				String number = "";
				while(expression.charAt(index) != '(') number += expression.charAt(index++);
				
				/*
				 * root version			-> create node 'root'
				 * left,right version	-> create node 'child' and set node's parent.
				 */
				child = (version==VER_ROOT) ? new Node(Integer.parseInt(number)) : new Node(Integer.parseInt(number), node, Integer.toString(version));
				if(version==VER_ROOT)	root = child;
				Tree.consolePathPrinting(keyToFind, expression, child.path);
				
				/*
				 * if child node's sum from root is over the key to find.
				 * root version			-> return "-1" path.
				 * left,right version	-> jump to other sibling tree.
				 */
				if(child.sumFromRoot > keyToFind) {
					Tree.consolePathPrinting(keyToFind, expression, "over sum is captured");
					if(version != VER_ROOT) {
						int findEndOfChildTree = 0;				// jump to index that locate to other sibling tree.
						while(findEndOfChildTree >= 0) {
							switch(expression.charAt(index)) {
							case '(':
								findEndOfChildTree++;
								break;
							case ')':
								findEndOfChildTree--;
								break;
							default:
								break;
							}
							index++;
						}
					}
					return (version==VER_ROOT) ? new Result(false, false, "-1", index) : new Result(false, false, "-2", index);
				}
				
				// search left child tree recursively. 
				Result leftResult = searchTree(keyToFind, expression, index, VER_LEFT, child);
				if(leftResult.isFind)	return leftResult;	// if find the key, return directly
				if(leftResult.isEmpty)	leafCaseTest++;		// if empty, check this node is leaf case.
				index = leftResult.index;
				// left child tree doesn't have a result. Let's searching right child tree recursively.
				Result rightResult = searchTree(keyToFind, expression, index, VER_RIGHT, child);
				if(rightResult.isFind)	return rightResult;	// if find the key, return directly
				if(rightResult.isEmpty)	leafCaseTest++;		// if empty, this node is leaf case.
				index = rightResult.index;
				
				// if this node is leaf, check node's sumFromRoot is same to the key.
				if(leafCaseTest == 2) {
					child.isLeaf = true;
					// finally, success in finding the key!!
					if(child.sumFromRoot == keyToFind) {
						Tree.consolePathPrinting(keyToFind, expression, "Success in finding the key!!");
						return new Result(true, false, child.path, index+1);
					}
					// failed to find the key, return path "-1" with index.
					else {
						return new Result(false, false, "-1", index+1);
					}
				}
				// if this node isn't leaf and couldn't find the key, return path "-1".
				return new Result(false, false, "-1", index+1);
			}
		}
		
		// error case
		else return new Result(false, false, "-2", -2);
	}
		
		
		
	
	// garbage comments
	/*
		public static Result searchTree(final int keyToFind, String expression, int index, int version, Node node) {
		int leafCaseTest = 0;	// test for that node is leaf node. If this become '2', node is leaf node.
		
		if(version == VER_ROOT) {
			if(expression.charAt(index) == '(') {
				index++; 
				if (expression.charAt(index) == ')') {
					return new Result(false, false, "-1", -1);
				}
				else {
					String number = "";
					while(expression.charAt(index) != '(') number += expression.charAt(index++);
					node = new Node(Integer.parseInt(number)); // root
					root = node;
					Tree.consolePathPrinting(keyToFind, expression, node.path);
					if(root.sumFromRoot > keyToFind) {
						Tree.consolePathPrinting(keyToFind, expression, "over sum is captured");
						return new Result(false, false, "-1", index);
					}
					
					Result leftResult = searchTree(keyToFind, expression, index, VER_LEFT, node);
					if(leftResult.isFind)	return leftResult;
					if(leftResult.isEmpty)	leafCaseTest++;
					index = leftResult.index;
					// left child tree hasn't found result. Let's checking right child tree.
					Result rightResult = searchTree(keyToFind, expression, index, VER_RIGHT, node);
					if(rightResult.isFind)	return rightResult;
					if(rightResult.isEmpty)	leafCaseTest++;
					index = rightResult.index;
					
					if(leafCaseTest == 2) {
						node.isLeaf = true;
						// finally, success in finding the key!!
						if(node.sumFromRoot == keyToFind) return new Result(true, false, node.path, index+1);
						// failed to find the key, return -1 with index.
						else return new Result(false, false, "-1", index+1);
					}
					// else
					return new Result(false, false, "-1", -1);
				}
			}
			else return new Result(false, false, "-2", -2);
		}
		
		// FIXIT: - Think algorithm, again 
		else if(version == VER_LEFT || version == VER_RIGHT) {
			Node childTree;
			if(expression.charAt(index) == '(') {
				index++;
				// leaf case test
				if (expression.charAt(index) == ')') {	return new Result(false, true, "-2", index+1);	}
				else {
					String number = "";
					while(expression.charAt(index) != '(') { number += expression.charAt(index++); }
					childTree = new Node(Integer.parseInt(number), node, Integer.toString(version));
					if(version==VER_LEFT)	node.setChildToLeft(childTree);
					else					node.setChildToRight(childTree);
					Tree.consolePathPrinting(keyToFind, expression, childTree.path);
					
					
					// if child node's sum from root is over key to find, jump to other tree.
					if(childTree.sumFromRoot > keyToFind) {
						Tree.consolePathPrinting(keyToFind, expression, "over sum captured");
						int findEndOfChildTree = 0;
						while(findEndOfChildTree >= 0) {
							switch(expression.charAt(index)) {
							case '(':
								findEndOfChildTree++;
								break;
							case ')':
								findEndOfChildTree--;
								break;
							default:
								break;
							}
							index++;
						}
						return new Result(false, false, "-2", index);
					}
					
					Result leftResult = searchTree(keyToFind, expression, index, VER_LEFT, childTree);
					if (leftResult.isFind)		return leftResult;
					if (leftResult.isEmpty)		leafCaseTest++;
					index = leftResult.index;
					Result rightResult = searchTree(keyToFind, expression, index, VER_RIGHT, childTree);
					if (rightResult.isFind)		return rightResult;
					if (rightResult.isEmpty)	leafCaseTest++;
					index = rightResult.index;
					
					if(leafCaseTest == 2) {
						childTree.isLeaf = true;
						// finally, success in finding the key!!
						if(childTree.sumFromRoot == keyToFind) {
							Tree.consolePathPrinting(keyToFind, expression, "Success in finding the key!!");
							return new Result(true, false, childTree.path, index+1);
						}
						// failed to find the key, return -1 with index.
						else return new Result(false, false, "-1", index+1);
					}
					else {
						childTree.isLeaf = false;
						return new Result(false, false, "-1", index+1);
					}
				}
			}
			else return new Result(false, false, "-2", -2);
		}

		
		else {
			// error!
			return new Result(false, false, "-2", -2);
		}
	}
	*/
	
	/*
	 	public static void test(String number) {
		Node test = null;
		if(root != null && (root.left != null || root.right != null)) {
			if(root.left != null) {
				test = root.left;
				System.out.println(number + "::" + "left is testing");
			}
			else {
				test = root.right;
				System.out.println(number + "::" + "right is testing");
			}
		}
		Tree.deleteInstance();
		if(test == null) {
			System.out.println(number + "::" + "dead");
		} else {
			System.out.println(number + "::" + "alive");
		}
	}
	*/


}
