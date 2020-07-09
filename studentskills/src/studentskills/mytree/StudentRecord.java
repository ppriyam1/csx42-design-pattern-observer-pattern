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
	public static List<StudentRecord> observers = Arrays.asList( null, null);
	private TreeHelper treeHelper = new TreeHelper();
	private StudentRecord orignalRoot;
	StudentRecord right;
	StudentRecord left;

	public StudentRecord() {
		
		this.height = 1;
	}

	public StudentRecord(StudentRecord studentRecord) {
		this.bNumber = studentRecord.bNumber;
		this.firstName = studentRecord.firstName;
		this.lastName = studentRecord.lastName;
		this.major = studentRecord.major;
		this.gpa = studentRecord.gpa;
		this.skills = studentRecord.skills;
		this.height = studentRecord.height;
	}

	@Override
	public Object clone() {
		
		return observers;
	}

	public boolean insertStudentRecord(String input) {
		// to create new student record
		StudentRecord studentRecord = new StudentRecord();
		studentRecord.formatter(input);
		if(!treeHelper.modifyIfDuplicate(studentRecord, orignalRoot)) {
		orignalRoot = treeHelper.insertStudentRecord(studentRecord, orignalRoot);
		updateObservers(studentRecord);
		}
		else {
			//notifyAll(studentRecord);
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
	
	public void printTree() {
		treeHelper.treePrint(orignalRoot);
		for(int i = 0 ; i< observers.size() ; i++) {
			System.out.println("\n Clone " + (i+1) + " -->");
			treeHelper.treePrint(observers.get(i));
		}
	}
	

	public String toString() {
		String details = this.bNumber + ":" + this.firstName + "," + this.lastName + "," + this.major + ","
				+ this.skills.toString();
		return details;
	}

	public TreeHelper getTree() {
		return treeHelper;
	}
	
	public void updateObservers(StudentRecord data) {
		for(int i = 0 ; i< observers.size() ; i++) {
			observers.set(i, treeHelper.insertStudentRecord(new StudentRecord(data), observers.get(i)));
			
		}
	}
	public void notifyll(StudentRecord record) {
		//call modify again with the data
		
	}
}