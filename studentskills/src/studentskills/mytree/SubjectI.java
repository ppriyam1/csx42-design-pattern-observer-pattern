package studentskills.mytree;

import java.util.List;

public interface SubjectI {

	public void registerObservers(List<StudentRecord> observers);

	public void notifyAll(Operation operation);
}
