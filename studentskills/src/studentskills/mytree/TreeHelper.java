package studentskills.mytree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TreeHelper {

	public static final Map<Integer, StudentTree> TreeMap = new HashMap<>();

	/**
	 * @param count
	 */
	public TreeHelper() {
	}

	public final void init(Integer countOfReplicas) {
		Integer counter = 0;

		while (counter < countOfReplicas) {
			TreeMap.put(counter, new StudentTree());
			counter++;
		}

		System.out.println(TreeMap.size() + " tree has been intialized"); // TODO: logger
	}

	public void display() {
		TreeMap.forEach((k, v) -> {
			v.print();
		});
	}

	// method to build tree recursively
	public void buildTree(StudentRecord node, Integer counter, List<StudentRecord> list) {

		StudentRecord currentNode = node;
		StudentRecord nextNode = null;

		if ((node != null) && counter < TreeMap.size()) { // to break recursion
			StudentTree tree = TreeMap.get(counter);

			currentNode.tree = tree;

			list.add(currentNode);

			StudentRecord replicaNode = null;

			if (list.size() < TreeMap.size()) {
				try {
					replicaNode = currentNode.cloneStudentRecord();
				} catch (CloneNotSupportedException e) {
					// TODO: throw from CustomStudentRecordException and use logger to print
				}
			}

			nextNode = replicaNode;

			buildTree(nextNode, ++counter, list);

			tree.insert(node);// 0 1 2 -> 2 1 0
			//TODO output = tree.insert(node);

		}
	}

	public void registerListeners(List<StudentRecord> observers) {
		System.out.println("Registering the observers"); // TODO: logger
		observers.forEach(k -> {
			List<StudentRecord> temp = new ArrayList<>();

			temp = observers.stream().filter(j -> j.tree.getId() != k.tree.getId()).collect(Collectors.toList());
			k.registerObservers(temp);
		});
	}

	public void build(String input) {
		StudentRecord node = new StudentRecord().formatter(input);

		StudentRecord ifStudentRecordExists = TreeMap.get(0).search(node);

		if (ifStudentRecordExists == null) { // flow: Create
			List<StudentRecord> observers = new ArrayList<>();
			Integer counter = 0;

			buildTree(node, counter, observers);
			registerListeners(observers);
		} else {
			StudentRecord replicaNode = ifStudentRecordExists;

			replicaNode.setFirstName(node.getFirstName());
			replicaNode.setLastName(node.getLastName());
			replicaNode.setMajor(node.getMajor());
			replicaNode.setGradePointAverage(node.getGradePointAverage());

			node.getSkills().forEach(skill -> {
				if (!replicaNode.getSkills().contains(skill))
					replicaNode.getSkills().add(skill);
			});

			// tree.update(replicaNode);
			System.out.println("\nUpdated Tree [id=" + replicaNode.tree.getId() + "]: " + replicaNode.toString());

			replicaNode.notifyAll(Operation.INSERT);
		}

	}

	public void modify(String input) {
		StudentRecord node = new StudentRecord().modifyFormatter(input);

		TreeMap.forEach((k, v) -> {

			if (node.treeId == v.getId()) {
				StudentRecord ifExistRecord = v.search(node);

				StudentRecord replicaNode = ifExistRecord;

				if (replicaNode.getFirstName().equals(node.originalValue)) {
					replicaNode.setFirstName(node.newValue);
				} else if (replicaNode.getLastName().equals(node.originalValue)) {
					replicaNode.setLastName(node.newValue);
				} else if (replicaNode.getMajor().equals(node.originalValue)) {
					replicaNode.setMajor(node.newValue);
				} else if (replicaNode.getSkills().contains(node.originalValue)) {
					Set<String> skills = replicaNode.getSkills();

					skills.remove(node.originalValue);
					skills.add(node.newValue);
					replicaNode.setSkills(skills);
				}

				// tree.update(replicaNode);
				System.out.println("\nUpdated Tree [id=" + node.treeId + "]: " + replicaNode.toString());

				replicaNode.notifyAll(Operation.MODIFY);
			}
		});
	}

}
