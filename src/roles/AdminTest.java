package roles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Courses;
import files.FileInfoReader;

class AdminTest {

	//declare HashMap and TreeMap for testing
	HashMap<String, Admin> adminHash;
	TreeMap<String, Courses> courses;
    //Create Admin object
	Admin admin;
	
	@BeforeEach
	void setUp() throws Exception {
		//Set up the hash and tree map so it runs before every test
		adminHash = FileInfoReader.parseAdmin("adminInfo.txt");
		courses = FileInfoReader.parseCourses("courseInfo.txt");
		admin = new Admin();
	}

	@Test
	void testToString() {
	    // Create a new professor
	    Admin admin = new Admin("TestID", "Test Admin", "TestUsername", "TestPass");
	
	    // Call the toString method to print the professor as a String and store the result as a String variable
	    String actual = admin.toString();
	
	    // Store the expected format in a String variable
	    String expected = "Admin{id='TestID', name='Test Admin', username='TestUsername', password='TestPass'}";
	    
	    //Check that the actual format matches the expected format
	    assertEquals(expected, actual);
	}
	
	@Test
	void testLoginSuccessful() {
		//Test that the method will return the key of the HashMap (Professor ID) if the user logs in successfully
		assertEquals("001",admin.loginSuccessful(adminHash, "admin01", "password590"));
		assertEquals("002",admin.loginSuccessful(adminHash, "admin02", "password590"));
		assertEquals("003",admin.loginSuccessful(adminHash, "admin03", "password590"));
		
		//Test that the method will return null if the user provides an inaccurate username
		assertNull(admin.loginSuccessful(adminHash, "notAUserName", "password590"));
		
		//Test that the method will return null if the user provides an inaccurate password
		assertNull(admin.loginSuccessful(adminHash, "Davidson", "notAPassword"));
	}
	
	
	@Test
	void testAddStudent() {
	    // Set up an Admin object, students HashMap, coursework TreeMap, and add a course
	    Admin admin = new Admin("admin001", "Admin Name", "adminUser", "password");
	    HashMap<String, Student> students = new HashMap<>();
	    ArrayList<String> coursework = new ArrayList<>();
	    coursework.add("CIS191: A");

	    //Add a student 
	    admin.addStudent(students, "student001", "Student Name", "studentUser", "studentPassword", coursework);

	    //Check that the students HashMap was updated to reflect the added student
	    assertEquals(1, students.size());
	    assertTrue(students.containsKey("student001"));

	    //Additional scenario: Check that adding a student with a duplicate ID will not affect the students HashMap
	    admin.addStudent(students, "student001", "Duplicate Student", "duplicateUser", "duplicatePassword", coursework);
	    assertEquals(1, students.size()); // Should remain unchanged
	}

	
	@Test
	void testAddProfessor() {
	    // Set up a new Admin object and professors HashMap
	    Admin admin = new Admin("admin001", "Admin Name", "adminUser", "password");
	    HashMap<String, Professor> professors = new HashMap<>();

	    //Add a professor
	    admin.addProfessor(professors, "prof001", "Professor Name", "profUser", "profPassword");

	    // Check that the professor was added to the professors HashMap
	    assertEquals(1, professors.size());
	    assertTrue(professors.containsKey("prof001"));

	    //Additional scenario: Check that adding a professor with a duplicate ID will not affect the professors HashMap
	    admin.addProfessor(professors, "prof001", "Duplicate Professor", "duplicateUser", "duplicatePassword");
	    assertEquals(1, professors.size()); // Should remain unchanged
	}

	@Test
	void testAddCourse() {
	    //Set up a new Admin object and courses TreeMap
	    Admin admin = new Admin("admin001", "Admin Name", "adminUser", "password");
	    TreeMap<String, Courses> courses = new TreeMap<>();

	    //Add a course
	    admin.addCourse(courses, "course001", "Course Name", "Lecturer Name", "MWF", "09:00", "10:00", 30);

	    //Check that the course got added to the courses TreeMap
	    assertEquals(1, courses.size());
	    assertTrue(courses.containsKey("course001"));

	    //Additional scenario: Check that adding a course with a duplicate ID will not affect the courses TreeMap
	    admin.addCourse(courses, "course001", "Duplicate Course", "Duplicate Lecturer", "MWF", "10:00", "11:00", 30);
	    assertEquals(1, courses.size()); // Should remain unchanged
	}

	@Test
	void testDeleteStudent() {
	    //Set up the Admin object and students HashMap
	    Admin admin = new Admin("admin001", "Admin Name", "adminUser", "password");
	    HashMap<String, Student> students = new HashMap<>();
        //Put a student in the students HashMap
	    students.put("student001", new Student("student001", "Student Name", "studentUser", "studentPassword", new ArrayList<>()));

	    //Delete the student we put
	    admin.deleteStudent(students, "student001");

	    //Check that the students HashMap no longer contains the student
	    assertFalse(students.containsKey("student001"));

	    //Additional scenario: Test deleting a non-existent student
	    admin.deleteStudent(students, "nonExistentID");
	    assertEquals(0, students.size()); // No changes should occur
	}

	@Test
	void testDeleteProfessor() {
	    //Set up an Admin object and professors HashMap
	    Admin admin = new Admin("admin001", "Admin Name", "adminUser", "password");
	    HashMap<String, Professor> professors = new HashMap<>();
        //Put a professor in the professors HashMap
	    professors.put("prof001", new Professor("prof001", "Professor Name", "profUser", "profPassword"));

	    //Delete the professor
	    admin.deleteProfessor(professors, "prof001");

	    //Check that the professor was deleted from the professors HashMap
	    assertFalse(professors.containsKey("prof001"));

	    //Additional scenario: Test deleting a non-existent professor
	    admin.deleteProfessor(professors, "nonExistentID");
	    assertEquals(0, professors.size()); // No changes should occur
	}

	@Test
	void testDeleteCourse() {
	    //Set up an Admin object and courses TreeMap
	    Admin admin = new Admin("admin001", "Admin Name", "adminUser", "password");
	    TreeMap<String, Courses> courses = new TreeMap<>();
        //Add a course to the courses TreeMap
	    courses.put("course001", new Courses("course001", "Course Name", "Lecturer Name", "MWF", "09:00", "10:00", 30));

	    //Delete a course
	    admin.deleteCourse(courses, "course001");

	    //Check that the course got deleted from the courses HashMap
	    assertFalse(courses.containsKey("course001"));

	    //Additional scenario: Test deleting a non-existent course
	    admin.deleteCourse(courses, "nonExistentID");
	    assertEquals(0, courses.size()); // No changes should occur
	}

	@Test
	void testValidateLecturerSchedule() {
	    // Set up an Admin object and courses TreeMap
	    Admin admin = new Admin("admin001", "Admin Name", "adminUser", "password");
	    TreeMap<String, Courses> courses = new TreeMap<>();
        //Add a course to the courses TreeMap
	    courses.put("course001", new Courses("course001", "Course1", "lecturer001", "MWF", "09:00", "10:00", 30));

	    //Store the result of the method in a boolean variable
	    boolean validSchedule = admin.validateLecturerSchedule("lecturer001", "course002", "MWF", "10:00", "11:00", courses);

	    //Ensure that the method returns true when a lecturer has no conflicts with their schedule
	    assertTrue(validSchedule);

	    //Additional scenario: Test that the method returns false when the lecturer has a schedule conflict
	    boolean conflict = admin.validateLecturerSchedule("lecturer001", "course003", "MWF", "09:30", "10:30", courses);
	    assertFalse(conflict);
	}

}