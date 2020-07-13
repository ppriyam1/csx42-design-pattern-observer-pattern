package studentskills.util;

import studentskills.exception.ResultException;

public interface FileDisplayInterface {

	/**
	 * Method to print output to a file.
	 *
	 * @param fileName
	 * @throws ResultException
	 */
	public void printToFile(String fileName) throws ResultException;
}
