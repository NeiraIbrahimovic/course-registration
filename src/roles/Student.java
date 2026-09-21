package roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import courses.Courses;
import files.FileInfoReader;

public class Student extends User {

	
	/**
	 * List of courses the student is qualified or eligible to take
	 * This represents the student's academic requirements or course plan
	 */
	private ArrayList<String> coursework;

	/**
	 * List of courses the student is currently enrolled in
	 * Initialized as empty list since students start with no enrolled courses
	 */
	private ArrayList<String> courseEnrolled = new ArrayList<String>();

	/**
	 * Retrieves the list of courses the student is currently enrolled in.
	 * This method provides access to the student's active course load.
	 *
	 * @return ArrayList<String> containing the course IDs of enrolled courses
	 */
	public ArrayList<String> getCourseEnrolled() {
	    return this.courseEnrolled;
	}

	/**
	 * Retrieves the list of courses the student is qualified to take.
	 * This represents the student's potential course selections or requirements.
	 *
	 * @return ArrayList<String> containing the course IDs of available coursework
	 */
	public ArrayList<String> getCoursework() {
	    return coursework;
	}

	/**
	 * Sets or updates the list of courses the student is qualified to take.
	 * This method is used to modify the student's available course options.
	 *
	 * @param coursework ArrayList<String> containing new list of available course IDs
	 */
	public void setCoursework(ArrayList<String> coursework) {
	    this.coursework = coursework;
	}

    /**
     * Constructor for creating an empty new Student object.
     * Initializes a student to all null 
     *
    */
	public Student() {
		super(null, null, null, null);
	}

	/**
	 * Constructor for creating a new Student object.
	 * Initializes a student with basic information and their available coursework.
	 * Extends the parent class (likely Person or User) with additional student-specific attributes.
	 *
	 * @param id Unique identifier for the student
	 * @param name Student's full name
	 * @param username Student's login username
	 * @param password Student's login password
	 * @param coursework List of courses the student is qualified to take
	 */
	public Student(String id, String name, String username, String password, ArrayList<String> coursework) {
	    super(id, name, username, password);
	    this.coursework = coursework;
	}

	/**
	 * Returns a string representation of the Student object.
	 * Includes all relevant student information in a formatted manner.
	 * Overrides the toString method from the parent class.
	 *
	 * @return String containing all student information in a formatted structure
	 */
	@Override
	public String toString() {
	    return "Student{" +
	           "id='" + this.getId() + '\'' +
	           ", name='" + this.getName() + '\'' +
	           ", username='" + this.getUsername() + '\'' +
	           ", password='" + this.getPassword() + '\'' +
	           ", coursework=" + this.getCoursework() +
	           '}';
	}
	
	
	/**
	 * Returns the key if the user logs in successfully, else it will return null
	 * Checks if the username and password is in the system
     *
     *@param studentHash HashMap containing the student IDs as keys and Student objects as values
	 *@param username of student
     *@param password of student
     *@return student ID if user logs in successfully, null if not
	 */
	public String loginSuccessful( HashMap<String, Student> studentHash, String username, String password){
		//Goes through each stored student in the HashMap and check their username and password to see if it matches
		for (Map.Entry<String, Student> entry : studentHash.entrySet()) {
	        String key = entry.getKey();
	        Student value = entry.getValue();
	        // If there is a match, it will return the key (student ID)
	        if (value.getUsername().equals(username) && value.getPassword().equals(password)) {
	            return key;
	        }
	    }
	    return null;
	}
	
	/**
	 * This will add the courses to the arraylist of courses for this student
	 * This class will not return anything
     *
     * @param courseToAdd the course to be added
     * @param courses TreeMap containing courseIds as keys and Courses objects as values
	 * 
	 */
	public void addCourses(String courseToAdd, TreeMap<String, Courses> courses) {
		// Check if the course is in the courses TreeMap by comparing the input with keys
		if (courses.containsKey(courseToAdd)) {
			// If there is a match, get the course date, start time and end time of that course from the courses TreeMap
			String courseToAddDay = courses.get(courseToAdd).getCourseDays();
			// Get the start time and end time and parse it to Int for later comparison
			int courseToAddStartTime = Integer.parseInt(courses.get(courseToAdd).getCourseStartTime().replace(":", ""));
			int courseToAddEndTime = Integer.parseInt(courses.get(courseToAdd).getCourseEndTime().replace(":", ""));
			
			// Go through every course already enrolled and compare the Days, start time and end time with the new class attempting to be enrolled
			// to see if there is a conflict or not
			for (int i = 0; i < this.courseEnrolled.size(); i++) {
				// Goes through all the courses that this student wants to enroll in the system and gets the course ID
				String enrolledCourseID = this.courseEnrolled.get(i);
				// Use the course ID from above to get the course days, start Time and End Time
				String enrolledCourseDay = courses.get(enrolledCourseID).getCourseDays();
				// Get the start time and end time and parse it to Int for later comparison
				int enrolledCourseStartTime = Integer.parseInt(courses.get(enrolledCourseID).getCourseStartTime().replace(":", ""));
				int enrolledCourseEndTime = Integer.parseInt(courses.get(enrolledCourseID).getCourseEndTime().replace(":", ""));
				//If course to be added is not in the same day as course enrolled, go to next enrolled course to check
                // If they appear in the same day then check the time
				if( courseToAddDay.equals(enrolledCourseDay)) {
					if(enrolledCourseEndTime <= courseToAddStartTime || enrolledCourseStartTime >= courseToAddEndTime) {
					
					}else {
						// Print a message if there is a conflict with another class
						System.out.println("This course has a conflict with " + enrolledCourseID);
						return ;
					}
					
				}
			}
			// If there is no conflict, add the class to the enrolled class
			this.courseEnrolled.add(courseToAdd);
			System.out.println("Successfully added " + courseToAdd.toUpperCase());
		}else {
			System.out.println(courseToAdd + " does not exist");
		}
		
		
	}	
	/**
	 * This class will print out all the courses this student is currently enrolled in
	 * This class will not return anything
	 * 
     * @param courses TreeMap containing course IDs as keys and Courses objects as values
	 */
	public void printCourseEnrolled(TreeMap<String, Courses> courses) {
		System.out.println("---------------------------");
		System.out.println("Here are the classes you are enrolled in:");
		System.out.println("---------------------------");
		// Goes through each course in the courseEnrolled ArrayList, gets the ID and searches up that ID in the courses TreeMap
		for (int i = 0 ; i < this.courseEnrolled.size(); i ++) {
			String courseEnrolled = this.courseEnrolled.get(i);
			Courses courseEnrolledInfo = courses.get(courseEnrolled.toUpperCase());
			
            // Prints out the information about that course
			System.out.println(courseEnrolledInfo.toString());
		}
	}

	/**
	 * This method will drop the courses that this student has already enrolled in
	 * This method will not return anything
     *
     * @param courseToDrop course that needs to be dropped
	 */
	public void dropCourseInList(String courseToDrop) {
		// Check if the user is enrolled in this class, if not, print out a message
		if (!this.courseEnrolled.contains(courseToDrop)) {
			System.out.println("You did not enroll in " + courseToDrop);
		}else {
			// If the user is enrolled in the class, remove it from the arraylist
			this.courseEnrolled.remove(courseToDrop);
			System.out.println(courseToDrop + " has been dropped");
		}
		
	}

	/**
	 * This method will print out all the grades this student has already received
	 * This method will not return anything
     *
     * @param students HashMap containing student IDs as keys and Student objects as values
     * @param courses TreeMap containing course IDs as keys and Courses objects as values
	 */
	
	public void printGrades(HashMap<String, Student> students,TreeMap<String, Courses> courses) {
		
		System.out.println("Here are the courses you have already taken, with your grade in letter format:");
		// Get the ID and coursework of this student 
		ArrayList<String> courseArray = students.get(this.getId()).getCoursework();
		// Goes through the array list of the courses this student has received a grade from
		for (int i = 0 ; i < courseArray.size() ; i++) {
			// For each index from the array, split it by ":", then get the courseID and the grade
			String [] gradeClassSplit = courseArray.get(i).split(":");
			String courseID = gradeClassSplit[0].trim();
			String grade = gradeClassSplit[1].trim();
			// Use the courseID to get the course name from the courses TreeMap, and print out the message
			System.out.println("Grade of "+ courseID + " " + courses.get(courseID).getCourseName() + ": " + grade);
		}

		
	}
	
}
