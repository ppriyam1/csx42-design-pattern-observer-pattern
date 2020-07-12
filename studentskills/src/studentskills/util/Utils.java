package studentskills.util;

public class Utils {

	private static Integer count = 0;

	public static final Integer getUniqueId(){
		return count++;
	}
}
