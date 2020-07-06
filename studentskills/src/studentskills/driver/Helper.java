package studentskills.driver;

import studentskills.util.FileProcessor;
import java.io.IOException;

public class Helper {


	/**
	 * @param input
	 */
	public static void process(String input) {

		FileProcessor fp = null;

		try {
			final String PATH = "./" + input;

			fp = new FileProcessor(PATH);

			String instruction = null;

			instruction = fp.poll(); // read next line

			// check file empty

			if (instruction == null || instruction.isEmpty()) {
				// throw new ChannelPopularityException(ErrorCode.INVALID_INPUT_EMPTY, "Input
				// file is empty");
			}

			while (instruction != null) {

				if (instruction.isEmpty()) {

					// throw new ChannelPopularityException(ErrorCode.INVALID_INPUT_EMPTY,

					// "Line in the input file does not follow the specified formats");

				}

				System.out.println(instruction);
				instruction = fp.poll();

			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (fp != null)
					fp.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}
