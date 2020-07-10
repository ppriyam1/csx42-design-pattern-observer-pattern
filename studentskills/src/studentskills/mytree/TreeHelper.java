package studentskills.mytree;

import java.util.LinkedList;
import studentskills.exceptions.StudentSkillsException;
import studentskills.mytree.StudentRecord.Operation;
import studentskills.exceptions.ErrorCode;
import java.util.Queue;
import java.util.Set;

public class TreeHelper{

	/**
	 * @param data
	 */
	
	// Method to insert node in a tree recursively
	public StudentRecord insertStudentRecord(StudentRecord data,StudentRecord root) {
		if (root == null) {
			root = data;
		} else {
			
			StudentRecord copyOfRoot = root;
			while (copyOfRoot != null) {
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
			copyOfRoot.height = Math.max(height(copyOfRoot.left), height(copyOfRoot.right)) + 1;
		}
		root.height = Math.max(height(root.left), height(root.right)) + 1;

		int balancingFactor = balancingFactor(root);
		// System.out.println(balancingFactor+" "+ data+" root:"+root.data);
		// Left to Right Rotation
		if (balancingFactor > 1 && data.bNumber < root.left.bNumber) {
			root = rightRotation(root);
		}

		// right to Left Rotation
		if (balancingFactor < -1 && data.bNumber > root.right.bNumber) {
			root = leftRotation(root);
		}

		// Left rotation then left to right Rotation
		if (balancingFactor > 1 && data.bNumber > root.left.bNumber) {
			root.left = leftRotation(root.left);
			root = rightRotation(root);
		}
		// Right rotation then right to Left Rotation
		if (balancingFactor < -1 && data.bNumber < root.right.bNumber) {
			root.right = rightRotation(root.right);
			root = leftRotation(root);
		}
		return root;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public int height(StudentRecord node) {
		if (node == null) {
			return 0;
		} else
			return node.height;
	}

	/**
	 * @param node
	 * @return
	 */
	public int balancingFactor(StudentRecord node) {

		if (node == null) {
			return 0;
		}
		return height(node.left) - height(node.right);
	}

	/**
	 * 
	 */
	public void treePrint(StudentRecord root) {

		if (root == null) {
			System.out.println("No records added");
			return;
		}
		printInorder(root);
		/*
		StudentRecord copyOfRoot = root;
		Queue<StudentRecord> queue = new LinkedList<StudentRecord>();
		StudentRecord temp;
		queue.add(copyOfRoot);
		System.out.println(copyOfRoot.toString());
		while (queue.isEmpty() == false) {
			temp = queue.poll();
			if (temp.left != null) {
				System.out.println(temp.left.toString());
				queue.add(temp.left);
			}
			if (temp.right != null) {
				System.out.println(temp.right.toString());
				queue.add(temp.right);
			}

		}*/
	}
	public void printInorder(StudentRecord root) 
	    { 
	        if (root == null) 
	            return; 
	        printInorder(root.left); 
	        System.out.println(root.toString()); 
	        printInorder(root.right); 
	    } 
	

	/**
	 * if
	 * 
	 * @param data
	 * @return
	 */
	public boolean modifyIfDuplicate(StudentRecord data,StudentRecord root) {
		StudentRecord copyOfRoot = root;
		if (copyOfRoot == null) {
			return false;
		} else {
			while (copyOfRoot != null) {
				if (data.bNumber < copyOfRoot.bNumber) {
					if (copyOfRoot.left == null) {
						break;
					}
					copyOfRoot = copyOfRoot.left;
				} else if (data.bNumber > copyOfRoot.bNumber) {
					if (copyOfRoot.right == null) {
						break;
					}
					copyOfRoot = copyOfRoot.right;
				} else {
					copyOfRoot.setFirstName(data.getFirstName());
					copyOfRoot.setLastName(data.getLastName());
					copyOfRoot.setMajor(data.getMajor());
					copyOfRoot.setGpa(data.getGpa());
					Set<String> skills = copyOfRoot.getSkills();
					skills.addAll(data.getSkills());
					copyOfRoot.setSkills(skills);
					copyOfRoot.notifyAll(Operation.MODIFY);
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * calculation rightRotation
	 * 
	 * @param root
	 * @return
	 */
	public StudentRecord rightRotation(StudentRecord root) {

		// setting the current location of the left node to the root to be rotated
		StudentRecord newRoot = root.left;
		StudentRecord newRootRight = newRoot.right;

		// Rotate the root and left node to right
		newRoot.right = root;
		root.left = newRootRight;

		// updating the height of new right node to the new root and new root itself
		root.height = Math.max(height(root.left), height(root.right)) + 1;
		newRoot.height = Math.max(height(newRoot.left), height(newRoot.right)) + 1;

		// returning new root
		return newRoot;
	}

	/**
	 * calculation leftRotation
	 * 
	 * @param root
	 * @return
	 */
	public StudentRecord leftRotation(StudentRecord root) {

		// setting the current location of the right node to the root to be rotated
		StudentRecord newRoot = root.right;
		StudentRecord newRootLeft = newRoot.left;

		// Rotate the root and right node to left
		newRoot.left = root;
		root.right = newRootLeft;

		// updating the height of new left node to new root and new root itself
		root.height = Math.max(height(root.left), height(root.right)) + 1;
		newRoot.height = Math.max(height(newRoot.left), height(newRoot.right)) + 1;

		// returning new root
		return newRoot;
	}
	public void modify(StudentRecord root, int bNumber, String oldValue, String newValue) throws StudentSkillsException {
		StudentRecord modifiedNode = modifyValues(root,bNumber,oldValue,newValue);
		if(modifiedNode != null) {
			modifiedNode.notifyAll(Operation.MODIFY);
		}
		else {
			System.out.println("cannot find bNumber: "+bNumber+" with value "+oldValue);
			//throw new StudentSkillsException(ErrorCode.CANNOT_MODIFY,"Cannot Find Vlaues");
		}
		
		
	}
	public StudentRecord modifyValues(StudentRecord root, int bNumber, String oldValue, String newValue)  {
		StudentRecord copyOfRoot = root;
		if (copyOfRoot == null) {
			return null;
		} else {
			while (copyOfRoot != null) {
				
				if (bNumber < copyOfRoot.bNumber) {
					if (copyOfRoot.left == null) {
						break;
					}
					copyOfRoot = copyOfRoot.left;
				} else if (bNumber > copyOfRoot.bNumber) {
					if (copyOfRoot.right == null) {
						break;
					}
					copyOfRoot = copyOfRoot.right;
				} else {
					
					if(copyOfRoot.getFirstName().equals(oldValue)){
						copyOfRoot.setFirstName(newValue);
						return copyOfRoot;
						
					}else if(copyOfRoot.getLastName().equals(oldValue)) {
						copyOfRoot.setLastName(newValue);
						return copyOfRoot;
						
					}else if(copyOfRoot.getMajor().equals(oldValue)) {
						copyOfRoot.setMajor(newValue);
						return copyOfRoot;
						
					}else if(copyOfRoot.getSkills().contains(oldValue)) {
						Set<String> skills = copyOfRoot.getSkills();
						skills.remove(oldValue);
						skills.add(newValue);
						return copyOfRoot;
					}
					return null;
				}
			}
			return null;
		
		
	}
	}
}