package studentskills.mytree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class StudentRecord implements Cloneable, SubjectI, ObserverI {

	protected Integer bNumber;
	protected StudentRecord left;
	protected StudentRecord right;
	protected StudentTree tree;
	protected Integer treeId;
	protected String newValue;
	protected String originalValue;

	private String firstName;
	private String lastName;
	private String major;
	private Double gradePointAverage;
	private Set<String> skills = new TreeSet<String>();

	private List<StudentRecord> observers = new ArrayList<>();

	protected Operation operation;

	public StudentRecord() {
		right = left = null;
	}

	public StudentRecord(Integer bNumber) {
		this();
		this.bNumber = bNumber;
	}

	public StudentRecord(StudentRecord studentRecord) {
		this(studentRecord.bNumber);

		this.firstName = studentRecord.getFirstName();
		this.lastName = studentRecord.getLastName();
		this.major = studentRecord.getMajor();
		this.gradePointAverage = studentRecord.getGradePointAverage();
		this.skills = studentRecord.getSkills();

	}

	protected StudentRecord formatter(String input) {
		String[] studentDetails = input.split(":");
		String[] details = studentDetails[1].split(",");

		this.bNumber = Integer.parseInt(studentDetails[0]);
		this.firstName = details[0];
		this.lastName = details[1];
		this.gradePointAverage = Double.parseDouble(details[2]);
		this.major = details[3];

		if ((details.length - 4) > 10) {
			// TODO: throw exception that number of skills > 10
		} else {
			for (int i = 4; i < details.length; i++)
				this.skills.add(details[i]);
		}

		return this;
	}

	protected StudentRecord modifyFormatter(String input) {

		// <REPLICA_ID>,<B_NUMBER>,<ORIG_VALUE>:<NEW_VALUE>
		String[] studentDetails = input.split(":");
		if (studentDetails.length == 2) {
			String[] studentArray = studentDetails[0].split(",");
			this.treeId = Integer.parseInt(studentArray[0]);
			this.bNumber = Integer.parseInt(studentArray[1]);
			this.originalValue = studentArray[2];

			this.newValue = studentDetails[1];
		}

		return this;
	}

	public final StudentRecord getCopy() {
		StudentRecord tempStudentRecordCopy = new StudentRecord();

		tempStudentRecordCopy.bNumber = this.bNumber;
		tempStudentRecordCopy.left = this.left;
		tempStudentRecordCopy.right = this.right;

		tempStudentRecordCopy.setFirstName(this.firstName);
		tempStudentRecordCopy.setLastName(this.lastName);
		tempStudentRecordCopy.setMajor(this.major);
		tempStudentRecordCopy.setGradePointAverage(this.gradePointAverage);
		tempStudentRecordCopy.setSkills(this.skills);

		return new StudentRecord(tempStudentRecordCopy);
	}

	/**
	 * @implNote Using Prototype pattern to enable cloning of current instance
	 * @return new StudentRecord
	 */
	public StudentRecord cloneStudentRecord() throws CloneNotSupportedException {
		// cloning
		return getCopy();
	}

	@Override
	public void registerObservers(List<StudentRecord> observers) {
		this.observers = observers;
	}

	@Override
	public void update(StudentRecord record) { // treeReplicaA

		this.setFirstName(record.getFirstName());
		this.setLastName(record.getLastName());
		this.setMajor(record.getMajor());
		this.setGradePointAverage(record.getGradePointAverage());
		this.setSkills(record.getSkills());

		if (record.operation.equals(Operation.INSERT))
			this.tree.update(this);
		else
			System.out.println("Updated Tree [id:" + this.tree.getId() + "]: " + this.toString());
	}

	public void notify(StudentRecord studentRecord) { // insert this to the observers
		System.out.println("observing...");
		this.observers.forEach(o -> {
			o.update(studentRecord);// treeReplicaA
		});
	}

	@Override
	public void notifyAll(Operation operation) {
		this.operation = operation;
		notify(this); // tree
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public Double getGradePointAverage() {
		return gradePointAverage;
	}

	public void setGradePointAverage(Double gradePointAverage) {
		this.gradePointAverage = gradePointAverage;
	}

	public Set<String> getSkills() {
		return skills;
	}

	public void setSkills(Set<String> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "StudentRecord [bNumber=" + bNumber + ", firstName=" + firstName + ", lastName=" + lastName + ", major="
				+ major + ", gpa=" + gradePointAverage + ", skills=" + skills + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bNumber == null) ? 0 : bNumber.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gradePointAverage == null) ? 0 : gradePointAverage.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((major == null) ? 0 : major.hashCode());
		result = prime * result + ((skills == null) ? 0 : skills.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (bNumber == null) {
			if (other.bNumber != null)
				return false;
		} else if (!bNumber.equals(other.bNumber))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gradePointAverage == null) {
			if (other.gradePointAverage != null)
				return false;
		} else if (!gradePointAverage.equals(other.gradePointAverage))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (major == null) {
			if (other.major != null)
				return false;
		} else if (!major.equals(other.major))
			return false;
		if (skills == null) {
			if (other.skills != null)
				return false;
		} else if (!skills.equals(other.skills))
			return false;
		return true;
	}
}
