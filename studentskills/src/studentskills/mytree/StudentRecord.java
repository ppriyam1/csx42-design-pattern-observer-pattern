package studentskills.mytree;

import java.util.List;
import java.util.Arrays;
import java.util.TreeSet;

public class StudentRecord extends Records implements Cloneable, SubjectI, ObserverI {

	public enum Operation {
		INSERT, MODIFY
	}

	public static List<StudentRecord> replicaTrees = Arrays.asList(null, null);
	private TreeHelper treeHelper = new TreeHelper();
	private static StudentRecord orignalRoot;
	public StudentRecord() {
	}

	public StudentRecord(String instruction) {
		this.formatter(instruction);
		this.height = 1;
		for (int i = 0; i < numOfObservers; i++)
			this.registerObserver();
	}

	protected Object clone() {
		StudentRecord clone = null;
		try {
			clone = (StudentRecord) super.clone();
			clone.setSkills(new TreeSet<String>());
		} catch (CloneNotSupportedException e) {

		}
		return clone;
	}

	// Method to create a new instance of student record and build tree using tree
	public boolean insertStudentRecord(String input) {
		// to create new student record
		StudentRecord studentRecord = new StudentRecord(input);
		if (!treeHelper.modifyIfDuplicate(studentRecord, orignalRoot)) {
			orignalRoot = treeHelper.insertStudentRecord(studentRecord, orignalRoot);
			update(Operation.INSERT, studentRecord);
		}
		return true;
	}

	protected void formatter(String input) {

		String[] studentDetails = input.split(":");
		String[] details = studentDetails[1].split(",");
		this.bNumber = Integer.parseInt(studentDetails[0]);
		this.setFirstName(details[0]);
		this.setLastName(details[1]);
		this.setGpa(Double.parseDouble(details[2]));
		this.setMajor(details[3]);
		for (int i = 4; i < details.length; i++) {
			this.getSkills().add(details[i]);
		}

	}

	public void printTree() {
		treeHelper.treePrint(this);
	}

	public String toString() {
		String details = this.bNumber + ":" + this.getFirstName() + "," + this.getLastName() + "," + this.getGpa() + ","
				+ this.getMajor() + "," + this.getSkills().toString();
		return details;
	}

	public StudentRecord getTree() {
		return orignalRoot;
	}

	public List<StudentRecord> getReplicas() {
		return replicaTrees;
	}

	public void notifyAll(Operation o) {
		this.equalObserver();
	}

	@Override
	public void update(Operation operation, StudentRecord data) {
		// TODO Auto-generated method stub;
		if (operation == Operation.INSERT) {
			for (int i = 0; i < replicaTrees.size(); i++) {
				replicaTrees.set(i, treeHelper.insertStudentRecord(data.observers.get(i), replicaTrees.get(i)));
				if(data.observers.get(i).observers.size() != (numOfObservers +1)) {
					data.observers.get(i).observers.add(data);
				}
			}
		}

	}
	public void update(Operation operation) {
		if (operation == Operation.MODIFY) {
			this.equalObserver();
		}
	}
	public void equalObserver() {
		for (int i = 0; i < this.observers.size(); i++) {
			observers.get(i).setbNumber(this.getbNumber());
			observers.get(i).setFirstName(this.getFirstName());
			observers.get(i).setLastName(this.getLastName());
			observers.get(i).setGpa(this.getGpa());
			observers.get(i).setMajor(this.getMajor());
			observers.get(i).setSkills(this.getSkills());
		}
	}
}
