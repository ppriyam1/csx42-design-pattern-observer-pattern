package studentskills.exceptions;

/**
 * @author preetipriyam
 *
 */
public class StudentSkillsException extends Exception {

	/**
	 * @serial
	 */
	private static final long serialVersionUID = -5967807911580996637L;

	private ErrorCode code;

	/**
	 * @param message
	 * @param errorCode
	 */
	public StudentSkillsException(ErrorCode code, String message) {
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
	public static void processException(StudentSkillsException exception) throws Exception {
		throw new Exception(exception.getErrorCode() + ": " + exception.getMessage());
	}
}
