package studentskills.mytree;

import java.util.List;
import java.util.Arrays;

import java.util.Set;
import java.util.TreeSet;

public class StudentRecord implements Cloneable, SubjectI, ObserverI {

	Integer bNumber;
	int height;
	private String firstName;
	private String lastName;
	private String major;
	private Double gpa;
	private Set<String> skills = new TreeSet<String>();
	public List<StudentRecord> observers = Arrays.asList(null, null);
	private TreeHelper treeHelper = new TreeHelper();
	private static StudentRecord orignalRoot;
	StudentRecord right;
	StudentRecord left;

	// final int numOfObservers = 2;
	public enum Operation {
		INSERT, MODIFY
	}

	public StudentRecord() {
		this.height = 1;
	}

	protected Object clone() {
		StudentRecord clone = null;
		try {
			clone = (StudentRecord) super.clone();
		} catch (CloneNotSupportedException e) {

		}

		return clone;
	}

	public boolean insertStudentRecord(String input) {
		// to create new student record
		StudentRecord studentRecord = new StudentRecord();
		studentRecord.formatter(input);
		if (!treeHelper.modifyIfDuplicate(studentRecord, orignalRoot)) {
			orignalRoot = treeHelper.insertStudentRecord(studentRecord, orignalRoot);
			update(Operation.INSERT, studentRecord);
		} else {
			update(Operation.MODIFY,studentRecord);
		}
		return true;
	}

	@Override
	public void registerObserver(StudentRecord observerI) {
		observers.add(observerI);
	}

	@Override
	public void unregisterObserver(StudentRecord observerI) {
		// To-Do
	}

	protected void formatter(String input) {

		String[] studentDetails = input.split(":");
		String[] details = studentDetails[1].split(",");
		this.bNumber = Integer.parseInt(studentDetails[0]);
		this.firstName = details[0];
		this.lastName = details[1];
		this.gpa = Double.parseDouble(details[2]);
		this.major = details[3];
		for (int i = 4; i < details.length; i++) {
			this.skills.add(details[i]);
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Integer getbNumber() {
		return bNumber;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Double getGpa() {
		return gpa;
	}

	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}

	public Set<String> getSkills() {
		return skills;
	}

	public void setSkills(Set<String> skills) {
		this.skills = skills;
	}

	public List<StudentRecord> getObservers() {
		return observers;
	}

	public void setObservers(List<StudentRecord> observersI) {
		observers = observersI;
	}

	public void debug() {
		treeHelper.treePrint(orignalRoot);
		for (int i = 0; i < observers.size(); i++) {
			System.out.println("\n Clone " + (i + 1) + " -->");
			treeHelper.treePrint(observers.get(i));
		}
	}
	public void printTree() {
		treeHelper.treePrint(this);
	}

	public String toString() {
		String details = this.bNumber + ":" + this.firstName + "," + this.lastName + "," + this.gpa + "," + this.major
				+ "," + this.skills.toString();
		return details;
	}

	public StudentRecord getTree() {
		orignalRoot.setObservers(observers);
		return orignalRoot;
	}
	public List<StudentRecord> getReplicas(){
		
		return observers;
	}


	public void notifyAll(int bNumber, String oldValue, String newValue) {
		
		// call modify again with the data
		for (int i = 0; i < observers.size(); i++) {
			treeHelper.modifyValues(observers.get(i), bNumber, oldValue, newValue);
		}

	}

	@Override
	public void update(Operation operation, StudentRecord data) {
		// TODO Auto-generated method stub
		if(operation == Operation.INSERT) {
			for (int i = 0; i < observers.size(); i++) {
				StudentRecord replicaNode = (StudentRecord) data.clone();
				if (replicaNode != null) { 
					observers.set(i, treeHelper.insertStudentRecord(replicaNode, observers.get(i)));
				} else {
					System.out.println("Cloning failed");
				}
			}
			
		}
		else if(operation == Operation.MODIFY) {
			for (int i = 0; i < observers.size(); i++) {
			treeHelper.modifyIfDuplicate(data, observers.get(i));
			}
		}
		observers.get(1).setObservers(Arrays.asList(orignalRoot,observers.get(0)));
		observers.get(0).setObservers(Arrays.asList(orignalRoot,observers.get(1)));
	}
}