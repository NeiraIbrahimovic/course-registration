package files;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Courses;
import roles.Student;
import roles.Professor;
import roles.Admin;

class FileInfoReaderTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testReadFile() {
		//Read in the file studentInfo.txt line by line and check if all the lines get read
		ArrayList<String> fileContents = FileInfoReader.readFile("studentInfo.txt");
		assertEquals("001; StudentName1; testStudent01; password590; CIS191: A, CIS320: A", fileContents.get(0));
		assertEquals("002; StudentName2; testStudent02; password590; CIT592: A, CIT593: A-", fileContents.get(1));
		//Read in the file courseInfo.txt line by line and check if the lines get read 
		ArrayList<String> fileContents2 = FileInfoReader.readFile("courseInfo.txt");
		assertEquals("CIT590; Programming Languages and Techniques; Brandon L Krakowsky; MW; 16:30; 18:00; 110",
				fileContents2.get(0));
		//After inputting the courseInfo, it should have 50 lines, which means the arrayList should be size 50
		assertEquals(50,fileContents2.size());

	}

	@Test
	void testParseStudents() {
		//Read in from studentInfo.txt
		HashMap<String, Student> students = FileInfoReader.parseStudents("studentInfo.txt");
		//Check to see if student 001 and 002 got read in
		assertTrue(students.containsKey("001"));
		assertTrue(students.containsKey("002"));
		//Student 003 does not exist so should return false
		assertFalse(students.containsKey("003"));
		// Get the name, username and password of 001 to ensure it parse correctly
		assertEquals("StudentName1",students.get("001").getName());
		assertEquals("testStudent01",students.get("001").getUsername());
		assertEquals("password590",students.get("001").getPassword());
	}
	

	@Test
	void testParseProf() {
		//Read in from profInfo.txt
		HashMap<String, Professor> professors = FileInfoReader.parseProf("profInfo.txt");
		//Check to see if professor 001, 012, and 032 got read in
		assertTrue(professors.containsKey("001"));
		assertTrue(professors.containsKey("012"));
		assertTrue(professors.containsKey("032"));
		//Professor 033 does not exist so should return false
		assertFalse(professors.containsKey("033"));
		// Get the name, username and password of 001 to ensure it parse correctly
		assertEquals("Clayton Greenberg",professors.get("001").getName());
		assertEquals("Greenberg",professors.get("001").getUsername());
		assertEquals("password590",professors.get("001").getPassword());
	}
	

	@Test 
	void testParseAdmin() {
		
		//Read in from admininfo.txt
		HashMap<String, Admin> admins = FileInfoReader.parseAdmin("adminInfo.txt");
		//Check to see if admins 1 2 and 3 got read in
		assertTrue(admins.containsKey("001"));
		assertTrue(admins.containsKey("002"));
		assertTrue(admins.containsKey("003"));
		//Student 004 does not exist so should return false
		assertFalse(admins.containsKey("004"));
		// Get the name, username and password of 001 to ensure it parse correctly
		assertEquals("admin",admins.get("001").getName());
		assertEquals("admin01",admins.get("001").getUsername());
		assertEquals("password590",admins.get("001").getPassword());	
		
	}


	@Test
	void testParseCourses() {
		//Read in from courseInfo.txt
		TreeMap<String, Courses> courses = FileInfoReader.parseCourses("courseInfo.txt");
		//Check if courses like CIT593,CIS198,CIS419 are in there
		assertTrue(courses.containsKey("CIT593"));
		assertTrue(courses.containsKey("CIS198"));
		assertTrue(courses.containsKey("CIS419"));
		//CIS619 is not a course so should return false
		assertFalse(courses.containsKey("CIS619"));
		//Get the course lecturer of CIT593
		assertEquals("Thomas Joseph Farmer",courses.get("CIT593").getCourseLecturer());
		//Get the capacity of CIT593
		assertEquals(72,courses.get("CIT593").getCourseCapacity());
		// Get the course days of CIT590
		assertEquals("MW",courses.get("CIT590").getCourseDays());
		
	}
}
