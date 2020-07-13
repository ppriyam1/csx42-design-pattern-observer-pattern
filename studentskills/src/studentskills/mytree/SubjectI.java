package studentskills.mytree;

import java.util.List;

public interface SubjectI {

	/**
	 * Method to register observer.
	 * @param observers
	 */
	public void registerObservers(List<StudentRecord> observers);

	/**
	 * Method to notify the changes to the observer nodes.
	 *
	 * @param operation
	 */
	public void notifyAll(Operation operation);
}
