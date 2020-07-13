package studentskills.exception;

/**
 * @author preetipriyam
 *
 */
public class ResultException extends StudentSkillException {

	private static final long serialVersionUID = -1495657226233523884L;

	private ErrorCode code;

	/**
	 * @param message
	 */
	public ResultException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param errorCode
	 */
	public ResultException(ErrorCode code, String message) {
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
	public static void processException(ResultException exception) throws Exception {
		throw new StudentSkillException(exception.getErrorCode() + ": " + exception.getMessage());
	}

}
