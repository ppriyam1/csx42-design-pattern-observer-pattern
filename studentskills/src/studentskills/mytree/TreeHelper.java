package studentskills.mytree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import studentskills.exception.ErrorCode;
import studentskills.exception.ResultException;
import studentskills.exception.StudentRecordException;
import studentskills.exception.TreeHelperException;
import studentskills.util.MyLogger;
import studentskills.util.MyLogger.DebugLevel;

public class TreeHelper {

	public static final Map<Integer, StudentTree> TreeMap = new HashMap<>();

	/**
	 * @param count
	 */
	public TreeHelper() {
	}

	/**
	 * Method to initialize the tree helper which will create the tree(s) for
	 * student records.
	 *
	 * @param outputFileNameList
	 * @throws TreeHelperException
	 */
	public final void init(List<String> outputFileNameList) throws TreeHelperException {
		Integer counter = 0;

		while (counter < outputFileNameList.size()) {
			TreeMap.put(counter, new StudentTree());

			if (!outputFileNameList.isEmpty() && outputFileNameList.get(counter) != null) {
				TreeMap.get(counter).outputFileName = outputFileNameList.get(counter);
			} else {
				String message = ErrorCode.INVALID_INPUT_EMPTY + ": " + "Output file names are required";
				MyLogger.writeMessage(message, DebugLevel.EXCEPTION);

				throw new TreeHelperException(message);
			}

			counter++;
		}

		MyLogger.writeMessage(TreeMap.size() + " tree has been intialized", MyLogger.DebugLevel.TREE_HELPER);
	}

	public void display() {
		TreeMap.forEach((k, v) -> {
			v.show();
		});
	}

	/**
	 * method to build tree recursively.
	 *
	 * @param node
	 * @param counter
	 * @param list
	 * @throws TreeHelperException
	 */
	public void buildTree(StudentRecord node, Integer counter, List<StudentRecord> list) throws TreeHelperException {

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
					MyLogger.writeMessage(e.getMessage(), DebugLevel.EXCEPTION);
					e.printStackTrace();
					throw new TreeHelperException(e.getMessage());
				}
			}

			nextNode = replicaNode;

			buildTree(nextNode, ++counter, list);

			try {
				tree.insert(node); // 0 1 2 -> 2 1 0
			} catch (ResultException e) {
				MyLogger.writeMessage(e.getMessage(), DebugLevel.EXCEPTION);
				e.printStackTrace();
				throw new TreeHelperException(e.getMessage());
			}

		}
	}

	/**
	 * Method to register observers to a subject.
	 *
	 * @param observers
	 */
	public void registerListeners(List<StudentRecord> observers) {

		MyLogger.writeMessage("Registering the observers", MyLogger.DebugLevel.TREE_HELPER);
		observers.forEach(k -> {
			List<StudentRecord> temp = new ArrayList<>();

			temp = observers.stream().filter(j -> j.tree.getId() != k.tree.getId()).collect(Collectors.toList());
			k.registerObservers(temp);
		});
	}

	/**
	 * Method to insert a node(s) based on the input from file.
	 *
	 * @param input
	 * @param errorFileName
	 * @throws TreeHelperException
	 */
	public void insert(String input, String errorFileName) throws TreeHelperException {

		StudentRecord node = null;

		try {
			node = new StudentRecord().formatter(input, errorFileName);

		} catch (StudentRecordException e) {
			e.printStackTrace();
			throw new TreeHelperException(e.getMessage());
		}

		if (node != null) {
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

				MyLogger.writeMessage("\nUpdated Tree [id=" + replicaNode.tree.getId() + "]: " + replicaNode.toString(),
						MyLogger.DebugLevel.TREE_HELPER);

				replicaNode.notifyAll(Operation.INSERT);
			}
		}

	}

	/**
	 * Method to modify node(s) based on the input from file.
	 *
	 * @param input
	 * @throws TreeHelperException
	 */
	public void modify(String input) throws TreeHelperException {
		StudentRecord node;

		try {
			node = new StudentRecord().modifyFormatter(input);
		} catch (StudentRecordException e) {
			e.printStackTrace();
			throw new TreeHelperException(e.getMessage());
		}

		if (node != null) {
			TreeMap.forEach((k, v) -> {

				if (node.treeId == v.getId()) {
					StudentRecord ifExistRecord = v.search(node);

					StudentRecord replicaNode = ifExistRecord;

					if (replicaNode != null) {

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
						MyLogger.writeMessage("\nUpdated Tree [id=" + node.treeId + "]: " + replicaNode.toString(),
								MyLogger.DebugLevel.TREE_HELPER);

						replicaNode.notifyAll(Operation.MODIFY);

					}
				}
			});
		}
	}

}
