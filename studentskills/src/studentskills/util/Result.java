package studentskills.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import studentskills.exception.ResultException;

/**
 * @author preetipriyam
 *
 */
public class Result implements FileDisplayInterface, StdoutDisplayInterface {

	private final StringBuilder builder = new StringBuilder();

	public Result() {
	}

	public void add(String message) {
		builder.append(message + " \n");
	}

	public void addException(String exception) {
		builder.append(exception + " \n");
	}

	// Method to print to console
	@Override
	public void printToStdout() {
		MyLogger.writeMessage(builder.toString(), MyLogger.DebugLevel.RESULTS);

	}

	// Method to write to a file
	@Override
	public void printToFile(String fileName) throws ResultException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

			writer.append(builder.toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResultException(e.getMessage());
		}
	}
}
