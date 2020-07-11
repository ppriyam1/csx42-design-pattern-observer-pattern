package studentskills.driver;

import studentskills.util.FileProcessor;
import java.util.List;
import studentskills.mytree.StudentRecord;
import studentskills.mytree.TreeHelper;
import java.io.IOException;

public class Helper {
	StudentRecord myTree; // replica nide 0
	List<StudentRecord> replicaTrees;
	StudentRecord replicaTree1;
	StudentRecord replicaTree2;
	FileProcessor fp = null;
	StudentRecord studentRecord;
	TreeHelper treeHelper;

	/**
	 * @param input
	 */
	public void process(String input) {
		studentRecord = new StudentRecord();
		try {
			final String PATH = "./" + input;
			fp = new FileProcessor(PATH);
			String instruction = null;
			instruction = fp.poll(); // read next line
			// check file empty

			if (instruction == null || instruction.isEmpty()) {
				// throw new ChannelPopularityException(ErrorCode.INVALID_INPUT_EMPTY, "Input
				// file is empty");
			}

			while (instruction != null) {

				if (instruction.isEmpty()) {

					// throw new ChannelPopularityException(ErrorCode.INVALID_INPUT_EMPTY,

					// "Line in the input file does not follow the specified formats");

				}

				studentRecord.insertStudentRecord(instruction);
				instruction = fp.poll();

			}

			myTree = studentRecord.getTree(); // origanlRoot
			replicaTrees = studentRecord.getReplicas();
			replicaTree1 = replicaTrees.get(0);
			replicaTree2 = replicaTrees.get(1);
			System.out.println("\norignalTrees:->");
			myTree.printTree();
			System.out.println("\nclone1:->");
			replicaTree1.printTree();
			System.out.println("\nclone2:->");
			replicaTree2.printTree();


		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (fp != null)
					fp.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		System.out.println("END");
	}

	public void modify(String modifyFile) {
		int bNumber;
		int modificationTree;
		String oldValue;
		String newValue;
		String[] preText;
		treeHelper = new TreeHelper();
		try {
			final String PATH = "./" + modifyFile;
			fp = new FileProcessor(PATH);
			String instruction = null;
			instruction = fp.poll();
			while (instruction != null) {
				String[] breakInstruction = instruction.split(":");
				if (breakInstruction.length == 2) {
					newValue = breakInstruction[1];
					preText = breakInstruction[0].split(",");
					modificationTree = Integer.parseInt(preText[0]);
					bNumber = Integer.parseInt(preText[1]);
					oldValue = preText[2];
					if (myTree != null && replicaTree1 != null && replicaTree2 != null) {
						switch (modificationTree) {
						case 0:
							treeHelper.modify(myTree, bNumber, oldValue, newValue);
							break;
						case 1:
							treeHelper.modify(replicaTree1, bNumber, oldValue, newValue);
							break;
						case 2:
							treeHelper.modify(replicaTree2, bNumber, oldValue, newValue);
							break;
						}

					} else {
						//throw exception
						System.out.println("Build tree first cannot Modify tree Empty");
					}

				} else {
					//throw exception
					System.out.println("ERROR in \""+instruction+"\": Modification Text Empty");

				}

				instruction = fp.poll();

			}
			System.out.println("\nAfter Modification");
			myTree.printTree();
			System.out.println("\nclone1:->");
			replicaTree1.printTree();
			System.out.println("\nclone2:->");
			replicaTree2.printTree();

		} catch (Exception e) {

		}
	}

}
