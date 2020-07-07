package studentskills.mytree;

public interface SubjectI {

	public void registerObserver(StudentRecord observer);

	public void unregisterObserver(StudentRecord observer);
	// public void notifyAll();
}