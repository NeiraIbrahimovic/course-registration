package roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import files.FileInfoReader;
import courses.Courses;

/**
 * The Professor class represents a professor user in the student management system.
 * Professors have the ability to view the information for courses they teach, view the student list for a given course, 
 * or return to the previous menu.
 * This class extends the User class and inherits its basic attributes.
 * 
 * Responsibilities:
 * - View given courses
 * - View student list of the given course
 * 
 */

public class Professor extends User {
    /**
     * Constructor for creating an empty new Professor object.
     * Initializes a professor to all null 
     *
    */
	public Professor() {
		super(null, null, null, null);
	}
	
	
	/**
	 * Constructor for creating a Professor object.
	 * Initializes the Professor object with the provided basic information.
	 * 
	 * @param name      The name of the professor.  
	 * @param id     	The unique identifier for the professor.
	 * @param username  The username for the professor's login credentials.
	 * @param password  The password for the professor's login credentials.
	 */

	public Professor(String id, String name, String username, String password) {
		super(id, name, username, password);
	}

    @Override
	public String toString() {
	    return "Professor{" +
	           "name='" + this.getName() + '\'' +
	           ", id='" + this.getId() + '\'' +
	           ", username='" + this.getUsername() + '\'' +
	           ", password='" + this.getPassword() + '\'' +
               '}';
	}
      
	/**
	 * Returns the key if the user logs in successfully, else it will return null
	 * Checks if the username and password is in the system
	 * 
     * @param professorHash HashMap containing the professor Id as the key and {rofessor object as the value
     * @param username of professor
     * @param password of professor
	 */
	public String loginSuccessful( HashMap<String, Professor> professorHash, String username, String password){
		//Goes through each stored professor in the HashMap and checks their username and password to see if it matches
		for (Map.Entry<String, Professor> entry : professorHash.entrySet()) {
	        String key = entry.getKey();
	        Professor value = entry.getValue();
	        // If there is a match, return the key (professor ID)
	        if (value.getUsername().equals(username) && value.getPassword().equals(password)) {
	            return key;
	        }
	    }
		//Return null if the username and password is not in the system
	    return null;
	}

	/**
	 * Prints a list of the courses the professor teaches
	 * @param courseHash TreeMap containing the Course Ids as kays and Courses object as values 
	 * @param professorName Name of professor
	 * @return list of courses for given professor
	 */
	public String viewGivenCourses(TreeMap<String, Courses> courseHash, String professorName) {
		
        //Initialize a StringBuilder to hold the course list
		StringBuilder courseList = new StringBuilder();

		// Iterate through each course in the hashmap
		for (Map.Entry<String, Courses> entry : courseHash.entrySet()) {
            //Store the current iteration of the Courses object in a variable 
	        Courses course = entry.getValue();
	        
	        // Check if the professor is teaching the course
	        if (course.getCourseLecturer().equals(professorName)){
                // If so, append course details
	        	courseList.append(course).append("\n"); 
	        }
		
		}
		
        //Return the list of courses as a String representation
		return courseList.toString();
	
	}
}

