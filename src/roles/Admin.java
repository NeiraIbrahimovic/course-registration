package roles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import courses.Courses;
import files.FileInfoReader;

/**
 * The Admin class represents an administrator user in the student management system.
 * Admins have the ability to view, add, and delete students, professors, and courses.
 * This class extends the User class and inherits its basic attributes.
 * 
 * Responsibilities:
 * - View all students, professors, and courses.
 * - Add new students, professors, and courses.
 * - Delete existing students, professors, and courses.
 * 
 */

public class Admin extends User {

	
    /**
     * Constructor for creating an empty new Admin object.
     * Initializes an admin to all null 
     *
    */
	public Admin() {
		super(null, null, null, null);
	}
	
	/**
	 * Constructor for creating an Admin object.
	 * Initializes the Admin object with the provided basic information.
	 * 
	 * @param id       The unique identifier for the admin.
	 * @param name     The name of the admin.
	 * @param username The username for the admin's login credentials.
	 * @param password The password for the admin's login credentials.
	 */

	public Admin(String id, String name, String username, String password) {
		super(id, name, username, password);
	}

    @Override
	public String toString() {
	    return "Admin{" +
	           "id='" + this.getId() + '\'' +
	           ", name='" + this.getName() + '\'' +
	           ", username='" + this.getUsername() + '\'' +
	           ", password='" + this.getPassword() + '\'' +
               '}';
	}

    /**
	 * Returns the key if the user logs in successfully, else it will return null
	 * Checks if the username and password is in the system
	 * 
     * @param adminHash HashMap containing admin IDs as keys and Admin objects as values
     * @param username of admin
     * @param password of admin
     * @return Admin ID if user logs in successfully, null if not
	 */
	public String loginSuccessful(HashMap<String, Admin> adminHash, String username, String password){

		//Goes through each stored student in the hashmap and check their username and password to see if it matches
		for (Map.Entry<String, Admin> entry : adminHash.entrySet()) {
	        String key = entry.getKey();
	        Admin value = entry.getValue();

	        // If there is a match, it will return the key (admin ID)
	        if (value.getUsername().equals(username) && value.getPassword().equals(password)) {
	            return key;
	        }
	    }
	    return null;
	}


	/*
	 * Adds a new student to the system if the student ID does not already exist.
	 * 
	 * @param students  A HashMap containing student IDs as keys and Student objects as values.
	 * @param id        The unique identifier for the new student.
	 * @param name      The name of the new student.
	 * @param username  The username for the new student's login credentials.
	 * @param password  The password for the new student's login credentials.
     * @param coursework A TreeMap containing course IDs as keys and Courses objects as values.
	 */

	public void addStudent(HashMap<String, Student> students, String id, String name, String username, String password, ArrayList<String> coursework) {
	    // Check if the student ID already exists in the HashMap
	    if (students.containsKey(id)) {
	        System.out.println("Error: Student ID '" + id + "' already exists.");
            //If student ID already exists, return without adding the student
	        return;
	    }
        //Initialize a boolean variable to false to indicate we have not checked if the username exists yet
        boolean check = false;
        // Check if the username already exists in the HashMap values
	    for (Student s : students.values()) {
	        if (s.getUsername().equals(username)) {
	            System.out.println("Error: Username '" + username + "' already exists.");
	            check = true;
	        }
	    }

        //If username already exists, return without adding the student
        if (check == true) {
            return;
        }

	    // Create a new student
	    Student newStudent = new Student(id, name, username, password, coursework);

	    // Add the new student to the HashMap
	    students.put(id, newStudent);

	    // Print a success message
	    System.out.println("Successfully added the new student: " + id + " " + name);
	}

	/*
	 * Deletes an existing student from the system if the student ID exists.
	 * 
	 * @param students A HashMap containing student IDs as keys and Student objects as values.
	 * @param studentId The unique identifier of the student to be deleted.
	 */


	public void deleteStudent(HashMap<String, Student> students, String studentId) {
		
		//Check if the student id exists in students HashMap
		if(students.containsKey(studentId)) {
			
			//If so, remove the student
			students.remove(studentId);
			
			//Print friendly message to indicate the student has been removed
			System.out.println("Successfully removed student " + studentId);
			
		//If student does not exist, print message indicating so
		} else {
			System.out.println("The student is not in the roster");
		}
		
	}

    /*
	 * Validates that the new course does not conflict with the lecturer's existing schedule.
	 * 
	 * @param lecturerId      The lecturer assigned to teach the course.
     * @param courseToAddId   ID of course to be added
	 * @param days            The days of the week the course is held.
	 * @param startTime       The start time of the course (formatted as HH:mm).
	 * @param endTime         The end time of the course (formatted as HH:mm).
     * @param courses         A TreeMap containing course IDs as keys and Courses objects as values.
	 * @return                True if the schedule is valid, false otherwise.
	 */ 

	public boolean validateLecturerSchedule(String lecturerId, String courseToAddId, String days, String startTime, String endTime, TreeMap<String, Courses> courses) {
	    boolean isValid = true; // Assume valid until proven otherwise

	    // Get the start time and end time of the new course
	    int courseToAddStartTime = Integer.parseInt(startTime.replace(":", ""));
	    int courseToAddEndTime = Integer.parseInt(endTime.replace(":", ""));

	    // Check each course in the map
	    for (Courses course : courses.values()) {
	        // If the lecturer is the same
	        if (course.getCourseLecturer().equals(lecturerId)) {
	            // Get the enrolled course times
	            int enrolledCourseStartTime = Integer.parseInt(course.getCourseStartTime().replace(":", ""));
	            int enrolledCourseEndTime = Integer.parseInt(course.getCourseEndTime().replace(":", ""));

	            // Check for time conflicts
	            if (!(enrolledCourseEndTime <= courseToAddStartTime || enrolledCourseStartTime >= courseToAddEndTime)) {
	                // Conflict exists
	                System.out.println("This course has a conflict with " + course.getCourseID() + ". Not adding course.");
	                isValid = false; // Mark as invalid
	            }
	        }
	    }

	    return isValid; // Return whether the schedule is valid
	}

	/*
	 * Deletes an existing course from the system if the course ID exists.
	 * 
	 * @param courses A TreeMap containing course IDs as keys and Course objects as values.
	 * @param courseId      The unique identifier of the course to be deleted.
	 */
	public void deleteCourse(TreeMap<String, Courses> courses, String courseId) {
		
		//If course id is in courses, delete it
		if (courses.containsKey(courseId)) {
			courses.remove(courseId);
			System.out.println("Successfully deleted " + courseId);

		} else {
			//Otherwise, print message
			System.out.println("This course is not in the course list");
		}
		
	}

    /*
	 * Adds a new course to the system 
	 * 
	 * @param courses    A HashMap containing course IDs as keys and Course objects as values.
	 * @param courseId   The unique identifier for the new course.
	 * @param courseName The name of the new course.
	 * @param lecturer   The lecturer assigned to teach the course.
	 * @param days       The days of the week the course is held.
	 * @param startTime  The start time of the course (formatted as HH:mm).
	 * @param endTime    The end time of the course (formatted as HH:mm).
	 * @param capacity   The maximum number of students allowed in the course.
	 */
	public void addCourse(TreeMap<String, Courses> courses, String courseId, String courseName,
			String lecturer, String days, String startTime, String endTime, int capacity) {
	
		// Add the new course
		Courses newCourse = new Courses(courseId, courseName, lecturer, days, startTime, endTime, capacity);
		courses.put(courseId, newCourse);
		System.out.println("Success: Course '" + courseName + "' has been added.");
	}


    /*
	 * Adds a new professor to the system if the professor ID does not already exist.
	 * 
	 * @param professors A HashMap containing professor IDs as keys and Professor objects as values.
	 * @param lecturerId The unique identifier for the new professor.
	 * @param name       The name of the new professor.
	 * @param username   The username for the new professor's login credentials.
	 * @param password   The password for the new professor's login credentials.
	 */
	public void addProfessor(HashMap<String, Professor> professors, String lecturerId, String name, String username, String password) {
	    // Check if the student ID already exists in the HashMap
	    if (professors.containsKey(lecturerId)) {
	        System.out.println("Error: Professor ID '" + lecturerId + "' already exists.");
	        return;
	    }

        // Check if the username already exists in the HashMap values
	    for (Professor prof : professors.values()) {
	        if (prof.getUsername().equals(username)) {
	            System.out.println("Error: Username '" + username + "' already exists.");
	            return;
	        }
	    }

	    // Create a new professor
	    Professor newProf = new Professor(lecturerId, name, username, password);

	    // Add the new professor to the professors HashMap
	    professors.put(lecturerId, newProf);

	    // Print a success message
	    System.out.println("Successfully added the new professor: " + lecturerId + " " + name);
	}

    /*
	 * Deletes an existing professor from the system if the professor ID exists.
	 * 
	 * @param professors A HashMap containing professor IDs as keys and Professor objects as values.
	 * @param id         The unique identifier of the professor to be deleted.
	 */
	public void deleteProfessor(HashMap<String, Professor> professors, String professorId) {
		
		
		//Check if the professor id exists in professors hash map
		if(professors.containsKey(professorId)) {
			
			//If so, remove the professor
			professors.remove(professorId);
			
			//Print friendly message
			System.out.println("Successfully removed professor " + professorId);
			
		//If professor does not exist, make note
		} else {
			System.out.println("The professor is not in the roster.");
		}
		
	}


}