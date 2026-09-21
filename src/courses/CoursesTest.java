package courses;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import files.FileInfoReader;
import roles.Professor;
import roles.Student;

class CoursesTest {

	//Declare HashMap and TreeMap for testing
	HashMap<String, Student> students;
	TreeMap<String, Courses> courses;
	
	@BeforeEach
	void setUp() throws Exception {
		//Set up the HashMap and TreeMap so it is generated before every test
		students = FileInfoReader.parseStudents("studentInfo.txt");
		courses = FileInfoReader.parseCourses("courseInfo.txt");
	}

	@Test
	void testViewStudentList() {
		// TEST 1: Test viewing the student list for a given course that has no students enrolled	
		String expected = "No students are enrolled in this course.";
		String actual = Courses.viewStudentList("CIT590", students, courses);
		assertEquals(expected, actual);
		
		String actual1 = Courses.viewStudentList("CIS105", students, courses);
		assertEquals(expected, actual1);
		
		// TEST 2: Test viewing the student list for a given course that has students enrolled
		
		//Get the first student from the HashMap and store as variable
		Student testStudent01 = students.get("001");
		
		//Enroll the student in the course, CIT590
		testStudent01.addCourses("CIT590", courses);
		
		//Get the second student from the HashMap and store as variable
		Student testStudent02 = students.get("002");
		
		//Enroll the student in the same course, CIT590
		testStudent02.addCourses("CIT590", courses);
		
		//Check that viewing the student list for CIT590 will show both students are enrolled
		String expected2 = "Students enrolled in CIT590:\n" +
			"Student id='001', Student Name='StudentName1'\n" +
			"Student id='002', Student Name='StudentName2'\n";

		String actual2 = Courses.viewStudentList("CIT590", students, courses);
		assertEquals(expected2, actual2);

		
		// TEST 3: Test viewing the student list for a course that does not exist
		String expected3 = "Course ID does not exist.";
		String actual3 = Courses.viewStudentList("Non Existent Course ID", students, courses);
		assertEquals(expected3, actual3);
	}

}
