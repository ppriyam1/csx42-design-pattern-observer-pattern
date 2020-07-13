package studentskills.mytree;

import studentskills.exception.StudentRecordException;

public interface ObserverI {

	/**
	 * Method used by observer node to update themselves.
	 *
	 * @param studentRecord
	 * @throws StudentRecordException
	 */
	public void update(StudentRecord studentRecord) throws StudentRecordException;
}
