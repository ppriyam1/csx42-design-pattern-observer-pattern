package studentskills.mytree;

/**
 *
 * @implNote BST Implementation
 *
 * @see https://www.geeksforgeeks.org/binary-search-tree-set-1-search-and-insertion
 *      https://www.geeksforgeeks.org/search-a-node-in-binary-tree
 *
 *
 */
// TODO: put the reference in README
class StudentTree {

	protected final Integer id;
	protected StudentRecord root;

	public StudentTree() {
		id = Utils.getUniqueId();
		root = null;
	}

	/**
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
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
	 * @param node
	 * @param key
	 * @return
	 */
	private static boolean searchNode(StudentRecord node, StudentRecord key) {

		if (node == null)
			return false;

		if (node.bNumber == key.bNumber)
			return true;

		// then recur on left sutree /
		boolean res1 = searchNode(node.left, key);
		if (res1) // node found, no need to look further
			return true;

		// node is not found in left, so recur on right subtree /
		boolean res2 = searchNode(node.right, key);

		return res2;
	}

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

	public void insert(StudentRecord studentRecord) {
		this.root = insertNode(this.root, studentRecord);
		System.out.println("Added " + this.root.tree + ": " + studentRecord.toString());
	}

	public void update(StudentRecord studentRecord) {
		updateNode(this.root, studentRecord);
		System.out.println("Updated " + this.root.tree + ": " + studentRecord.toString());
	}

	public StudentRecord search(StudentRecord studentRecord) {
		boolean isExists = searchNode(this.root, studentRecord);
		return isExists ? findNode(studentRecord.bNumber, this.root) : null;
	}

	public void print() {
		printNode(this.root);
		System.out.println("");
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
