package studentskills.mytree;

import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;

public class StudentRecord implements Cloneable, SubjectI, ObserverI {

	Integer bNumber;
	private String firstName;
	private String lastName;
	private String major;
	private Double gpa;
	private Set<String> skills;
	private ArrayList<StudentRecord> observers;
	StudentRecord right;
	StudentRecord left;
	int height;
	private TreeHelper treeHelper = new TreeHelper();

	public StudentRecord() {
		this.firstName = null;
		this.lastName = null;
		this.major = null;
		this.skills = new TreeSet<String>();
		this.observers = new ArrayList<StudentRecord>();
		this.height = 1;
	}
	
	public void cloneStudentRecord(StudentRecord root){
		try {
			StudentRecord replica_Node_1 = (StudentRecord)root.clone();
			StudentRecord replica_Node_2 = (StudentRecord)root.clone();
			observers.add(replica_Node_1);
			observers.add(replica_Node_2);
		} catch(CloneNotSupportedException e) {
			System.out.println(e);
		}
	}
	public boolean insertStudentRecord(String input) {
		//to create new student record
		StudentRecord studentRecord = new StudentRecord();
		studentRecord.formatter(input);
		if (!treeHelper.modifyIfDuplicate(studentRecord)){
			treeHelper.insertStudentRecord(studentRecord);
		}
		return true;
	}

	@Override
	public void registerObserver(StudentRecord observerI) {
		this.observers.add(observerI);
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
	
	public ArrayList<StudentRecord> getObservers() {
		return observers;
	}

	public void setObservers(ArrayList<StudentRecord> observers) {
		this.observers = observers;
	}

	public String toString() {
		String details = this.bNumber + ":" + this.firstName + "," + this.lastName + "," + this.major + ","
				+ this.skills.toString();
		return details;
	}
	public TreeHelper getTree() {
		return treeHelper;
	}
}