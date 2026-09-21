package courses;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

import roles.Student;


/**
 * Courses class provides utility methods getting all the course related information as well as printing out the course
 * into ideal format
 */
public class Courses {
// Create all the variables in the Courses Class
	private String courseID;
	private String courseName;
	private String courseLecturer;
	private String courseDays;
	private String courseStartTime;
	private String courseEndTime;
	private int courseCapacity;
	private int courseStudentCount;
	/**
	 * @return the courseID
	 */
	public String getCourseID() {
		return courseID;
	}
	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * @return the courseLecturer
	 */
	public String getCourseLecturer() {
		return courseLecturer;
	}
	/**
	 * @return the courseDays
	 */
	public String getCourseDays() {
		return courseDays;
	}
	/**
	 * @return the courseStartTime
	 */
	public String getCourseStartTime() {
		return courseStartTime;
	}
	/**
	 * @return the courseEndTime
	 */
	public String getCourseEndTime() {
		return courseEndTime;
	}
	/**
	 * @return the courseCapacity
	 */
	public int getCourseCapacity() {
		return courseCapacity;
	}
	/**
	 * @return the courseStudentCount
	 */
	public int getCourseStudentCount() {
		return courseStudentCount;
	}
	
	
	/**
	 * Constructor for creating a new Course object with specified parameters.
	 * Initializes a course with given details and sets initial student count to 0.
	 *
	 * @param courseID Unique identifier for the course
	 * @param courseName Name of the course
	 * @param courseLecturer Name of the lecturer teaching the course
	 * @param courseDays Days when the course is held
	 * @param courseStartTime Start time of the course
	 * @param courseEndTime End time of the course
	 * @param courseCapacity Maximum number of students allowed in the course
	 */
	public Courses(String courseID, String courseName, String courseLecturer, 
	               String courseDays, String courseStartTime, String courseEndTime,
	               int courseCapacity) {
	    // Initialize course details
	    this.courseID = courseID;
	    this.courseName = courseName;
	    this.courseLecturer = courseLecturer;
	    this.courseDays = courseDays;
	    this.courseStartTime = courseStartTime;
	    this.courseEndTime = courseEndTime;
	    this.courseCapacity = courseCapacity;
	    this.courseStudentCount = 0;  // Initialize student count to zero because no one has enrolled in the course yet
	}

	/**
	 * Returns a string representation of the course in a formatted manner.
	 * Format: CourseID|CourseName, StartTime-EndTime on Days, with course capacity: , 
	 * student:, lecturer: Professor Name
	 *
	 * @return String containing formatted course information
	 */
	public String toString() {
	    return this.getCourseID() + "|" + this.getCourseName() + ", " +
	           this.getCourseStartTime() + "-" + this.getCourseEndTime() +
	           " on " + this.getCourseDays() + ", with course capacity: " +
	           this.getCourseCapacity() + ", student: " + this.getCourseStudentCount() + 
	           ", lecturer: Professor " + this.getCourseLecturer();
	}

	/**
	 * Static utility method to print information about all courses in a TreeMap.
	 * Iterates through the TreeMap and prints details of each course using the same format as toString().
	 * 
	 * @param courseHash TreeMap containing course IDs mapped to Course objects
	 */
	public static void printAllClasses(TreeMap<String, Courses> courseHash) {
	    // Iterate through each entry in the HashMap
	    for (Map.Entry<String, Courses> entry : courseHash.entrySet()) {
	    	// Get the value for each key and call toString to print it.
	        Courses course = entry.getValue();
	        System.out.println(course.toString());
	    }
	}
	
    /**
     * Displays the list of students enrolled in this course by cross-referencing the students' coursework.
     *
     * @param courseId course ID for the course one is viewing the student list for
     * @param students HashMap of all students with their ID as the key and Student object as the value
     * @param courses TreeMap of all courses with their ID as they key and Course object as the value
     * @return String representing the enrolled student list or a message if Course Id does not exist or no students are enrolled
     */
    public static String viewStudentList(String courseId, HashMap<String, Student> students, TreeMap<String, Courses> courses) {
        
        // If the course ID does not exist, return a message
        if (!courses.containsKey(courseId)) {
        	return "Course ID does not exist.";
        }
        
        //Initialize a StringBuilder variable with a string stating students enrolled in the course
    	StringBuilder studentList = new StringBuilder("Students enrolled in " + courseId + ":\n");
        
        //Initialize the boolean variable to false, as no enrolled students have been found yet
        boolean hasStudents = false;

        // Iterate through all students in the students HashMap to check if they're enrolled in this course
        for (Student student : students.values()) {
            //If the student is enrolled in the course, add them to the StringBuilder
            if (student.getCourseEnrolled().contains(courseId)) {
                studentList.append("Student id='" + student.getId() + '\'' +
         	           ", Student Name='" + student.getName()).append("'\n");
                //Set the boolean variable to true to indicate the course has students enrolled
                hasStudents = true;
            }
        }

        // If no students are found in the course, return a message
        if (!hasStudents) {
            return "No students are enrolled in this course.";
        }

        //Return the StringBuilder containing the list of students in the course
        return studentList.toString();
    }
	
}
