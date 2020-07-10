package studentskills.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import studentskills.exceptions.StudentSkillsException;
import studentskills.exceptions.ErrorCode;

/**
 * @author preetipriyam
 *
 */
public class Results implements FileDisplayInterface, StdoutDisplayInterface {

	public Results() {
	}

	private static final StringBuilder builder = new StringBuilder();

	/**
	 * @param state
	 * @param operation
	 * @param target
	 */
	/*public static void add(String state, String operation, String target) {
		builder.append(state + "__" + operation + "::" + target + " \n");
	}

	@Override
	public void print() {
		System.out.print(builder.toString());
	}

	@Override
	public void print(String fileName) throws ChannelPopularityException {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.append(builder.toString());
			writer.close();
		} catch (IOException e) {
			throw new ChannelPopularityException(ErrorCode.INVALID_IO, e.getMessage());
		}
	}*/
}
