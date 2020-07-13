package studentskills.mytree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import studentskills.exception.ErrorCode;
import studentskills.exception.ResultException;
import studentskills.exception.StudentRecordException;
import studentskills.util.MyLogger;
import studentskills.util.MyLogger.DebugLevel;
import studentskills.util.Result;

/**
 * @author preetipriyam
 *
 */
public class StudentRecord implements Cloneable, SubjectI, ObserverI {

	protected static Result result = new Result();
	protected static String errorFileName;

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

	/**
	 * Method to validate the input arguments from input file.
	 *
	 * @param studentDetails
	 * @param details
	 * @return
	 * @throws ResultException
	 */
	protected boolean validate(String[] studentDetails, String[] details) throws ResultException {

		boolean temp = true;

		String message = null;

		if (studentDetails[0] == null || studentDetails[0].isEmpty()) {
			message = "bNumber doesn't exist";
			temp = false;
		} else if (details.length < 1 || details[0] == null || details[0].isEmpty()) {
			message = "First Name doesn't exist";
			temp = false;
		} else if (details.length < 2 || details[1] == null || details[1].isEmpty()) {
			message = "Last Name doesn't exist";
			temp = false;
		} else if (details.length < 3 || details[2] == null || details[2].isEmpty()) {
			message = "Grade point average doesn't exist";
			temp = false;
		} else if (details.length < 4 || details[3] == null || details[3].isEmpty()) {
			message = "Major doesn't exist";
			temp = false;
		}

		if (!temp) {
			MyLogger.writeMessage(message, MyLogger.DebugLevel.STUDENT_RECORD);
			StudentRecord.result.add(message);
			StudentRecord.result.printToFile(errorFileName);
		}

		return temp;
	}

	/**
	 * Methods to format the input.
	 *
	 * @param input
	 * @param errorFileName
	 * @return
	 * @throws StudentRecordException
	 */
	protected StudentRecord formatter(String input, String errorFileName) throws StudentRecordException {
		StudentRecord.errorFileName = errorFileName;

		String[] studentDetails = input.split(":");
		String[] details = studentDetails[1].split(",");

		boolean flag;

		try {
			flag = validate(studentDetails, details);
		} catch (ResultException e) {
			e.printStackTrace();
			throw new StudentRecordException(ErrorCode.INVALID_IO + ": " + e.getMessage());
		}

		if (flag) {
			this.bNumber = Integer.parseInt(studentDetails[0]);
			this.firstName = details[0];
			this.lastName = details[1];
			this.gradePointAverage = Double.parseDouble(details[2]);
			this.major = details[3];

			if ((details.length - 4) > 10) {
				// TODO: throw exception that number of skills > 10
				throw new StudentRecordException(ErrorCode.SKILLS_RANGE_EXCEEDED,
						"maximum number of skills cannot excced 10");
			} else {
				for (int i = 4; i < details.length; i++)
					this.skills.add(details[i]);
			}

			return this;
		}

		return null;
	}

	/**
	 * Method to validate the input format from the modify.txt file.
	 *
	 * @param studentArray
	 * @param studentDetails
	 * @return
	 * @throws ResultException
	 */
	protected boolean modifyValidate(String[] studentArray, String[] studentDetails) throws ResultException {

		boolean temp = true;

		String message = null;

		if (studentArray.length < 1 || studentArray[0] == null || studentArray[0].isEmpty()) {
			message = "treeId doesn't exist";
			temp = false;
		} else if (studentArray.length < 2 || studentArray[1] == null || studentArray[1].isEmpty()) {
			message = "bNumber doesn't exist";
			temp = false;
		} else if (studentArray.length < 3 || studentArray[2] == null || studentArray[2].isEmpty()) {
			message = "originalValue doesn't exist";
			temp = false;
		} else if (studentDetails.length < 1 || studentDetails[1] == null || studentDetails[1].isEmpty()) {
			message = "newValue doesn't exist";
			temp = false;
		}

		if (!temp) {
			MyLogger.writeMessage(message, MyLogger.DebugLevel.STUDENT_RECORD);
			StudentRecord.result.add(message);
			StudentRecord.result.printToFile(errorFileName);
		}

		return temp;
	}

	/**
	 * Method to format the input from modify.txt file.
	 *
	 * @param input
	 * @return
	 * @throws StudentRecordException
	 */
	protected StudentRecord modifyFormatter(String input) throws StudentRecordException {

		// <REPLICA_ID>,<B_NUMBER>,<ORIG_VALUE>:<NEW_VALUE>
		String[] studentDetails = input.split(":");
		if (studentDetails.length == 2) {
			String[] studentArray = studentDetails[0].split(",");

			boolean flag = false;

			try {
				flag = modifyValidate(studentArray, studentDetails);
			} catch (ResultException e) {
				e.printStackTrace();
				throw new StudentRecordException(ErrorCode.INVALID_IO + ": " + e.getMessage());
			}

			if (flag) {
				this.treeId = Integer.parseInt(studentArray[0]);
				this.bNumber = Integer.parseInt(studentArray[1]);
				this.originalValue = studentArray[2];

				this.newValue = studentDetails[1];

				return this;
			}


		}

		return null;
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
	 * Using Prototype pattern to enable cloning of current instance
	 *
	 * @return
	 * @throws CloneNotSupportedException
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
	public void update(StudentRecord record) throws StudentRecordException { // treeReplicaA

		this.setFirstName(record.getFirstName());
		this.setLastName(record.getLastName());
		this.setMajor(record.getMajor());
		this.setGradePointAverage(record.getGradePointAverage());
		this.setSkills(record.getSkills());

		if (record.operation.equals(Operation.INSERT))
			try {
				this.tree.update(this);
			} catch (ResultException e) {
				MyLogger.writeMessage(e.getMessage(), DebugLevel.EXCEPTION);
				e.printStackTrace();
				throw new StudentRecordException(e.getMessage());
			}
		else {

			String message = "Updated Tree [id:" + this.tree.getId() + "]: " + this.toString();
			MyLogger.writeMessage(message, MyLogger.DebugLevel.STUDENT_RECORD);

			this.tree.result.add(message);

			try {
				this.tree.result.printToFile(this.tree.outputFileName);
			} catch (ResultException e) {
				MyLogger.writeMessage(e.getMessage(), DebugLevel.EXCEPTION);
				e.printStackTrace();
				throw new StudentRecordException(e.getMessage());
			}
		}
	}

	/**
	 * Method to iterating over all the observers for the current subjects.
	 *
	 * @param studentRecord
	 */
	public void notify(StudentRecord studentRecord) { // insert this to the observers

		this.observers.forEach(o -> {
			try {
				o.update(studentRecord);
			} catch (StudentRecordException e) {
				MyLogger.writeMessage(e.getMessage(), DebugLevel.EXCEPTION);
				e.printStackTrace();
			}
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
