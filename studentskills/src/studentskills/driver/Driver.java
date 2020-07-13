package studentskills.driver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Preeti Priyam
 *
 */
public class Driver {
	private static final int REQUIRED_NUMBER_OF_CMDLINE_ARGS = 7;

	public static void main(String[] args) throws Exception {

		/*
		 * As the build.xml specifies the arguments as input,modify, out1, out2, out3,
		 * error, debug or metrics, in case the argument value is not given java takes
		 * the default value specified in build.xml. To avoid that, below condition is
		 * used
		 */
		if ((args.length != 7) || (args[0].equals("${input}")) || (args[1].equals("${modify}"))
				|| (args[2].equals("${out1}")) || (args[3].equals("${out2}")) || (args[4].equals("${out3}"))
				|| (args[5].equals("${error}")) || (args[6].contains("${debugLevel}"))) {
			System.err.printf("Error: Incorrect number of arguments. Program accepts %d arguments.",
					REQUIRED_NUMBER_OF_CMDLINE_ARGS);
			System.exit(0);
		}

		System.out.println("Starting application...\n");

		String inputFileName = args[0];
		String modifyFileName = args[1];
		String errorFileName = args[5];
		String debugLevel = args[6];

		List<String> outputFileList = new ArrayList<>();

		for (int i = 2; i < args.length - 2; i++) {
			outputFileList.add(args[i]);
		}

		Helper.run(inputFileName, modifyFileName, outputFileList, errorFileName, debugLevel);
	}
}
