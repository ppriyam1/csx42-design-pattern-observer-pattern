package studentskills.exception;

public class HelperException extends StudentSkillException {

	/**
	 *
	 */
	private static final long serialVersionUID = -5227353254204163045L;

	private ErrorCode code;

	/**
	 * @param message
	 */
	public HelperException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param errorCode
	 */
	public HelperException(ErrorCode code, String message) {
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
	public static void processException(HelperException exception) throws Exception {
		throw new StudentSkillException(exception.getErrorCode() + ": " + exception.getMessage());
	}

}
