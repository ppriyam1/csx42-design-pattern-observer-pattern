package studentskills.mytree;

import studentskills.exception.ResultException;
import studentskills.util.MyLogger;
import studentskills.util.Result;
import studentskills.util.Utils;

/**
 *
 * @implNote BST Implementation
 *
 * @see https://www.geeksforgeeks.org/binary-search-tree-set-1-search-and-insertion
 *      https://www.geeksforgeeks.org/search-a-node-in-binary-tree
 *
 *
 */

class StudentTree {

	protected final Integer id;
	protected StudentRecord root;

	protected String outputFileName;
	protected Result result;

	public StudentTree() {
		this.result = new Result();

		this.id = Utils.getUniqueId();
		this.root = null;
	}

	/**
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Method to
	 *
	 * @param prefix
	 * @param node
	 * @param isLeft
	 */
	public void showTree(String prefix, StudentRecord node, boolean isLeft) {
		if (node != null) {
			if (prefix.equals(""))
				System.out.println("P-->" + "(" + node.bNumber + ")");
			else
				System.out.println(prefix + (isLeft ? "L-->" : "R-->") + "(" + node.bNumber + ")");

			showTree(prefix + (isLeft ? "   " : "    "), node.left, true);
			showTree(prefix + (isLeft ? "   " : "    "), node.right, false);
		}
	}

	public void show() {
		System.out.println("\nTree[" + this.id + "] [P: Parent_Node] [L: Left_Node] [R: Right_Node]");
		showTree("", this.root, true);
	}

	/**
	 * Method to insert node in the tree.
	 *
	 * @param node
	 * @param key
	 * @return
	 */
	private static StudentRecord insertNode(StudentRecord node, StudentRecord key) {
		/* A recursive function to insert a new key in BST */

		/* If the tree is empty, return a new node */
		if (node == null) {
			node = key;
			return node;
		}

		/* Otherwise, recur down the tree */
		if (key.bNumber < node.bNumber)
			node.left = insertNode(node.left, key);
		else if (key.bNumber > node.bNumber)
			node.right = insertNode(node.right, key);

		/* return the (unchanged) node pointer */
		return node;
	}

	/**
	 * Method to update node in the tree.
	 *
	 * @param node
	 * @param key
	 * @return
	 */
	private static boolean updateNode(StudentRecord node, StudentRecord key) {

		if (node == null)
			return false;

		if (node.bNumber == key.bNumber) {
			node = key;
			return true;
		}

		boolean res1 = updateNode(node.left, key);
		if (res1) {// node found, no need to look further
			return true;
		}

		// node is not found in left, so recur on right subtree /
		boolean res2 = updateNode(node.right, key);

		return res2;
	}

	/**
	 * Method to search node record in a tree.
	 *
	 * @param node
	 * @param key
	 * @return
	 */
	private static boolean searchNode(StudentRecord node, StudentRecord key) {

		if (node == null)
			return false;

		if ((node.bNumber).equals(key.bNumber))
			return true;

		// then recur on left subtree /
		boolean res1 = searchNode(node.left, key);
		if (res1) // node found, no need to look further
			return true;

		// node is not found in left, so recur on right subtree /
		boolean res2 = searchNode(node.right, key);

		return res2;
	}

	/**
	 * @param bNumber
	 * @param node
	 * @return
	 */
	public StudentRecord findNode(final Integer bNumber, StudentRecord node) {
		if (node == null)
			return null;

		if (bNumber.equals(node.bNumber)) {
			return node;
		} else {
			StudentRecord found = findNode(bNumber, node.left); // left traversal

			if (found == null)
				found = findNode(bNumber, node.right); // right traversal

			return found;
		}

	}

	/**
	 * Method to print node.
	 *
	 * @param node
	 */
	public static void printNode(StudentRecord node) {
		if (node.left != null) {
			printNode(node.left);
		}

		/**
		 * Print b_number
		 */
		System.out.print("(" + node.bNumber + ")");

		if (node.right != null) {
			printNode(node.right);
		}
	}

	/**
	 * Method to call the BST insertNode function to insert node in the tree.
	 *
	 * @param studentRecord
	 * @throws ResultException
	 */
	public void insert(StudentRecord studentRecord) throws ResultException {
		this.root = insertNode(this.root, studentRecord);

		String message = "Added " + this.root.tree + ": " + studentRecord.toString();

		MyLogger.writeMessage(message, MyLogger.DebugLevel.STUDENT_TREE);

		result.add(message);
		result.printToFile(outputFileName);
	}

	/**
	 * Method to call the BST updateNode function to update node in a tree.
	 *
	 * @param studentRecord
	 * @throws ResultException
	 */
	public void update(StudentRecord studentRecord) throws ResultException {
		updateNode(this.root, studentRecord);

		String message = "Updated " + this.root.tree + ": " + studentRecord.toString();

		MyLogger.writeMessage(message, MyLogger.DebugLevel.STUDENT_TREE);

		result.add(message);
		result.printToFile(outputFileName);
	}

	/**
	 * Method to call the BST searchNode function to search a node record in a tree.
	 *
	 * @param studentRecord
	 * @return
	 */
	public StudentRecord search(StudentRecord studentRecord) {
		boolean isExists = searchNode(this.root, studentRecord);
		return isExists ? findNode(studentRecord.bNumber, this.root) : null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentTree other = (StudentTree) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tree [id=" + id + "]";
	}
}
