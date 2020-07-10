package studentskills.mytree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public abstract class Records {
	Integer bNumber;
	int height;
	private String firstName;
	private String lastName;
	private String major;
	private Double gpa;
	private Set<String> skills = new TreeSet<String>();
	public List<StudentRecord> observers = new ArrayList<StudentRecord>();
	StudentRecord right;
	StudentRecord left;
	final int numOfObservers = 2;
	
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
	public void setbNumber(int b) {
		this.bNumber = b;
	}

	public void setObservers(List<StudentRecord> observersI) {
		observers = observersI;
	}

	public void registerObserver() {
		StudentRecord replicaNode = null;
		try{
		replicaNode = (StudentRecord) this.clone();
		this.observers.add(replicaNode);
		}catch(Exception e){
		}
	}

	public void unregisterObserver(StudentRecord observerI) {
		// To-Do
	}
}
