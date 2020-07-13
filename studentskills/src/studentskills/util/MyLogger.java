package studentskills.util;

public class MyLogger {

	// FIXME: Add more enum values as needed for the assignment
	public static enum DebugLevel {
		STUDENT_RECORD, STUDENT_TREE, TREE_HELPER, RESULTS, EXCEPTION, NONE
	};

	private static DebugLevel debugLevel;

	// FIXME: Add switch cases for all the levels
	public static void setDebugValue(int levelIn) {
		switch (levelIn) {
		case 4:
			debugLevel = DebugLevel.STUDENT_RECORD;
			break;
		case 3:
			debugLevel = DebugLevel.STUDENT_TREE;
			break;
		case 2:
			debugLevel = DebugLevel.TREE_HELPER;
			break;
		case 1:
			debugLevel = DebugLevel.RESULTS;
			break;
		case 0:
			debugLevel = DebugLevel.EXCEPTION;
			break;
		default:
			debugLevel = DebugLevel.NONE;
			break;
		}
	}

	public static void setDebugValue(DebugLevel levelIn) {
		debugLevel = levelIn;
	}

	public static void writeMessage(String message, DebugLevel levelIn) {
		if (levelIn == debugLevel)
			System.out.println(message);
	}

	public String toString() {
		return "The debug level has been set to the following " + debugLevel;
	}
}
