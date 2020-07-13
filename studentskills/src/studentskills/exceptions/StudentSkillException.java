package studentskills.exception;

import studentskills.util.MyLogger;
import studentskills.util.MyLogger.DebugLevel;

public class StudentSkillException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -6327485196115886670L;

	private ErrorCode code;

	/**
	 * @param message
	 */
	public StudentSkillException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param errorCode
	 */
	public StudentSkillException(ErrorCode code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * @return String: errorCode
	 */
	public String getErrorCode() {
		return this.code.toString();
	}

	/**
	 * @param exception
	 * @throws StudentSkillsException
	 */
	public static void processException(StudentSkillException exception) {

		String exceptionMessage = exception.getErrorCode() + ": " + exception.getMessage();

		MyLogger.writeMessage(exceptionMessage, DebugLevel.EXCEPTION);
	}
}
