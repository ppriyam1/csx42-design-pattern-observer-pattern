package studentskills.exception;

public class StudentRecordException extends StudentSkillException {

	/**
	 *
	 */
	private static final long serialVersionUID = 3696267212004680923L;

	private ErrorCode code;

	/**
	 * @param message
	 */
	public StudentRecordException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param errorCode
	 */
	public StudentRecordException(ErrorCode code, String message) {
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
	public static void processException(StudentRecordException exception) throws Exception {
		throw new StudentSkillException(exception.getErrorCode() + ": " + exception.getMessage());
	}

}
