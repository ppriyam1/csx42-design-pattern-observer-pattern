package studentskills.driver;

import java.io.IOException;

import studentskills.mytree.TreeHelper;
import studentskills.util.FileProcessor;

public class Helper {

	static TreeHelper treeHelper;

	/**
	 * @param input
	 */
	public static void process(String fileName) {
		final Integer countOfReplicas = 3;

		if (!(TreeHelper.TreeMap.size() > 0)) { // Initialize Tree
			treeHelper = new TreeHelper();
			treeHelper.init(countOfReplicas);
		}

		FileProcessor fp = null;

		try {
			final String PATH = "./" + fileName;
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
				if (fileName.contains("input")) {
					treeHelper.build(instruction);
				} else if (fileName.contains("modify")) {
					treeHelper.modify(instruction);
				} else {

				}

				instruction = fp.poll();
			}

			treeHelper.display();

			// treeHelper.print();
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

}
