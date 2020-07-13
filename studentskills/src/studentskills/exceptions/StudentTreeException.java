package studentskills.exception;

public class StudentTreeException extends StudentSkillException {

	/**
	 *
	 */
	private static final long serialVersionUID = 7230484081773798509L;

	private ErrorCode code;

	/**
	 * @param message
	 */
	public StudentTreeException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param errorCode
	 */
	public StudentTreeException(ErrorCode code, String message) {
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
	public static void processException(StudentTreeException exception) throws Exception {
		throw new StudentSkillException(exception.getErrorCode() + ": " + exception.getMessage());
	}
}
