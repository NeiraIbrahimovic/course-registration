package roles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Courses;
import files.FileInfoReader;

class StudentTest {
	//declare HashMap and TreeMap for testing
	HashMap<String, Student> studentHash;
	TreeMap<String, Courses> courses;
	Student student;
	
	@BeforeEach
	void setUp() throws Exception {
		//Set up the hash and tree map so it runs before every test
		studentHash = FileInfoReader.parseStudents("studentInfo.txt");
		courses = FileInfoReader.parseCourses("courseInfo.txt");
		student = new Student();
		//this.student = new Student();
	}
	
	@Test
	void testSetUp() { 
		//Check if the courses map is size 50
		assertEquals(50, this.courses.size());
		//check if the student map is size 2
		assertEquals(2, this.studentHash.size());
	}
	@Test
	void testStudent() {
        //Create a coursework ArrayList
		ArrayList<String> coursework = new ArrayList<String>();
        //Add in coursework,id,name,username and password
		coursework.add("CIS198: F");
		coursework.add("CIS320: C");
		String id = "12345";
		String name = "Bob Smith";
		String username = "Bobsmith1234";
		String password = "Bobsmith1234pw";
		//Create A new Student
		Student student5 = new Student(id, name, username, password, coursework);
        //Get the ID and Name of the student
		assertEquals("12345",student5.getId());
		assertEquals("Bob Smith",student5.getName());
		
	}

	@Test
	void testLoginSuccessful() {
        //Log in with correct credentials 
		assertEquals("001",student.loginSuccessful(studentHash, "testStudent01", "password590"));
		assertEquals("002",student.loginSuccessful(studentHash, "testStudent02", "password590"));
        //Log in with wrong user name but existing password
        assertNull(student.loginSuccessful(studentHash, "notAUserName", "password590"));
        //Log in with correct username but wrong password
        assertNull(student.loginSuccessful(studentHash, "testStudent02", "notAPassword"));
	}

	@Test
	void testAddCourses() {
		Student student = studentHash.get("001");
		//Should have no class enrolled
		assertEquals(0,student.getCourseEnrolled().size());
		student.addCourses("CIS191", courses);
		// Adding a course will have now 1 class enrolled
		assertEquals(1,student.getCourseEnrolled().size());
		student.addCourses("CIS191", courses);
		//Adding the name class does not increase the size
		assertEquals(1,student.getCourseEnrolled().size());
		student.addCourses("CIT590", courses);
		//Adding another course, should now have 2 classes
		assertEquals(2,student.getCourseEnrolled().size());
		student.addCourses("CIS553", courses);
		//Adding a course with conflict should not be allowed, remains 2 classes
		assertEquals(2,student.getCourseEnrolled().size());
		//Contains CIS191, CIT590 but not CIS553
		assertTrue(student.getCourseEnrolled().contains("CIS191"));
		assertTrue(student.getCourseEnrolled().contains("CIT590"));
		assertFalse(student.getCourseEnrolled().contains("CIS553"));

	}

	@Test
	void testDropCourseInList() {
		//Set up with 2 classes
		Student student = studentHash.get("001");
		student.addCourses("CIS191", courses);
		student.addCourses("CIT590", courses);
		assertEquals(2,student.getCourseEnrolled().size());
		//Show that it contains course CIS191
		assertTrue(student.getCourseEnrolled().contains("CIS191"));
		student.dropCourseInList("CIS191");
		//Dropping a course, should now have 1 course left
		assertEquals(1,student.getCourseEnrolled().size());
		//No longer contains ICS 191
		assertFalse(student.getCourseEnrolled().contains("CIS191"));
		student.dropCourseInList("CIS195");
		//Dropping a course that is not enrolled, should not change the size
		assertEquals(1,student.getCourseEnrolled().size());
		//Still contains CIT590
		assertTrue(student.getCourseEnrolled().contains("CIT590"));
		student.dropCourseInList("CIT590");
		//No longer contains CIT590
		assertFalse(student.getCourseEnrolled().contains("CIT590"));
		
		
	}

}
