package studentskills.mytree;

import studentskills.mytree.StudentRecord.Operation;

public interface ObserverI{
	public void update(Operation o, StudentRecord data);
}
