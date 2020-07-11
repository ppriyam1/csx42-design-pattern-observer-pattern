package studentskills.driver;

/**
 * @author Preeti Priyam
 *
 */
public class Driver {
	private static final int REQUIRED_NUMBER_OF_CMDLINE_ARGS = 7;

	public static void main(String[] args) throws Exception {

		/*
		 * As the build.xml specifies the arguments as input,modify, out1, out2, out3, error, debug or metrics, in case the
		 * argument value is not given java takes the default value specified in
		 * build.xml. To avoid that, below condition is used
		 */
		if ((args.length != 7) || (args[0].equals("${input}")) || (args[4].equals("${modify}")) || (args[1].equals("${out1}")) || (args[2].equals("${out2}")) || (args[3].equals("${out3}")) || (args[5].equals("${error}")) || (args[6].equals("${debug}"))) {
			System.err.printf("Error: Incorrect number of arguments. Program accepts %d arguments.", REQUIRED_NUMBER_OF_CMDLINE_ARGS);
			System.exit(0);
		}
		Helper helper = new Helper();
        helper.process(args[0]);
        helper.modify(args[1]);


	}
}
