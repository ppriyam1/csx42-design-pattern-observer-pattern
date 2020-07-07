package studentskills.mytree;

import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;

public class StudentRecord implements SubjectI, ObserverI {

	Integer bNumber;
	private String firstName;
	private String lastName;
	private String major;
	private Double gpa;
	private Set<String> skills;
	private ArrayList<StudentRecord> observers;
	StudentRecord right;
	StudentRecord left;
	

	public StudentRecord() {
		this.firstName = null;
		this.lastName = null;
		this.major = null;
		this.skills = new TreeSet<String>();
		this.observers = new ArrayList<StudentRecord>();
	}
	
	public static boolean insertStudentRecord(String input) {
		StudentRecord studentRecord = new StudentRecord();
		studentRecord.formatter(input);
		TreeHelper.insertStudentRecord(studentRecord);
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
		for(int i = 4 ; i< details.length; i++) {
			this.skills.add(details[i]);
		}
	
		
	}

	public Integer getbNumber() {
		return bNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMajor() {
		return major;
	}


	public Double getGpa() {
		return gpa;
	}

	public Set<String> getSkills() {
		return skills;
	}
}