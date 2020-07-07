package studentskills.mytree;

import java.util.LinkedList;
import java.util.Queue;


public class TreeHelper{
	static StudentRecord root;
	
	public static void insertStudentRecord(StudentRecord data) {

	if (root == null) {
		root = data;
	} else {
		StudentRecord copyOfRoot = root;
		while (copyOfRoot != null) {
			// System.out.print(copyRoot.data +" ");
			if (data.bNumber < copyOfRoot.bNumber) {
				if (copyOfRoot.left == null) {
					break;
				}
				copyOfRoot = copyOfRoot.left;
			} else {
				if (copyOfRoot.right == null) {
					break;
				}
				copyOfRoot = copyOfRoot.right;
			}
		}
		if (data.bNumber < copyOfRoot.bNumber) {
			copyOfRoot.left = data;

		} else {
			copyOfRoot.right = data;

		}
	}
}
	
public void bfsPrint() {
		
	if(root == null) {
		System.out.println("No records added");
		return;
	}
		StudentRecord copyRoot = root;
		Queue<StudentRecord> queue = new LinkedList<StudentRecord>();
		StudentRecord temp;
		queue.add(copyRoot);
		System.out.println(copyRoot.bNumber);
		while (queue.isEmpty() == false) {
			temp = queue.poll();
			if (temp.left != null) {
				System.out.println(temp.left.bNumber);
				queue.add(temp.left);
			}
			if (temp.right != null) {
				System.out.println(temp.right.bNumber);
				queue.add(temp.right);
			}

		}
	}
	
}