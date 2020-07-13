package studentskills.driver;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.List;

import studentskills.exception.ErrorCode;
import studentskills.exception.HelperException;
import studentskills.exception.StudentSkillException;
import studentskills.exception.TreeHelperException;
import studentskills.mytree.TreeHelper;
import studentskills.util.FileProcessor;
import studentskills.util.MyLogger;
import studentskills.util.MyLogger.DebugLevel;

public class Helper {

	static TreeHelper treeHelper;

	public static void setLogger(String level) {
		MyLogger.setDebugValue(Integer.parseInt(level));
	}

	/**
	 * @param input
	 * @throws HelperException
	 * @throws StudentSkillException
	 */
	public static void process(String fileName, List<String> outputFileNameList, String errorFileName)
			throws HelperException {

		if (!(TreeHelper.TreeMap.size() > 0)) { // Initialize Tree
			treeHelper = new TreeHelper();

			try {
				treeHelper.init(outputFileNameList);
			} catch (TreeHelperException e) {
				MyLogger.writeMessage(ErrorCode.INVALID_IO + ": " + e.getMessage(), DebugLevel.EXCEPTION);
				e.printStackTrace();
				throw new HelperException(e.getMessage());
			}
		}

		FileProcessor fp = null;

		final String PATH = "./" + fileName;

		try {
			fp = new FileProcessor(PATH);
		} catch (InvalidPathException | SecurityException | IOException e) {
			MyLogger.writeMessage(ErrorCode.INVALID_IO + ": " + e.getMessage(), DebugLevel.EXCEPTION);
			e.printStackTrace();
			throw new HelperException(e.getMessage());
		}

		String instruction = null;

		try {
			instruction = fp.poll();
		} catch (IOException e) {
			MyLogger.writeMessage(ErrorCode.INVALID_IO + ": " + e.getMessage(), DebugLevel.EXCEPTION);
			e.printStackTrace();
			throw new HelperException(e.getMessage());
		}

		// read next line
		// check file empty
		if (instruction == null || instruction.isEmpty()) {
			String message = ErrorCode.INVALID_INPUT_EMPTY + ": " + "Input file is empty";
			MyLogger.writeMessage(message, DebugLevel.EXCEPTION);
			throw new HelperException(message);
		}

		while (instruction != null) {

			if (instruction.isEmpty()) {
				String message = ErrorCode.INVALID_INPUT_EMPTY + ": "
						+ "Line in the input file does not follow the specified formats";
				MyLogger.writeMessage(message, DebugLevel.EXCEPTION);
				throw new HelperException(message);

			}

			if (fileName.contains("input")) {
				try {
					treeHelper.insert(instruction, errorFileName);
				} catch (TreeHelperException e) {
					MyLogger.writeMessage(e.getMessage(), DebugLevel.EXCEPTION);
					e.printStackTrace();
					throw new HelperException(e.getMessage());
				}
			} else if (fileName.contains("modify")) {
				try {
					treeHelper.modify(instruction);
				} catch (TreeHelperException e) {
					MyLogger.writeMessage(e.getMessage(), DebugLevel.EXCEPTION);
					e.printStackTrace();
					throw new HelperException(e.getMessage());
				}
			} else {

			}

			try {
				instruction = fp.poll();
			} catch (IOException e) {
				MyLogger.writeMessage(ErrorCode.INVALID_IO + ": " + e.getMessage(), DebugLevel.EXCEPTION);
				e.printStackTrace();
				throw new HelperException(e.getMessage());
			}
		}

	}

	public static void run(String inputFileName, String modifyFileName, List<String> outputFileNameList,
			String errorFileName, String debugLevel) throws StudentSkillException {

		setLogger(debugLevel); // configure logger

		try {

			process(inputFileName, outputFileNameList, errorFileName);
			process(modifyFileName, outputFileNameList, errorFileName);
		} catch (HelperException e) {
			MyLogger.writeMessage(e.getMessage(), DebugLevel.EXCEPTION);
			e.printStackTrace();
			throw new StudentSkillException(e.getMessage());
		}
	}
}
