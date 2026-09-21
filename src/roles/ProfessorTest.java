package roles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Courses;
import files.FileInfoReader;

class ProfessorTest {

		//Declare HashMap and TreeMap for testing
		HashMap<String, Professor> professorHash;
		TreeMap<String, Courses> courses;
		Professor professor;
		
		@BeforeEach
		void setUp() throws Exception {
			//Set up the hash and tree map so it runs before every test
			professorHash = FileInfoReader.parseProf("profInfo.txt");
			courses = FileInfoReader.parseCourses("courseInfo.txt");
			professor = new Professor();
		}
	
	@Test
	void testToString() {
        // Create a new professor
        Professor professor = new Professor("TestID", "Test Professor", "TestUsername", "TestPass");

        // Call the toString method to print the professor as a String and store the result as a String variable
        String actual = professor.toString();

        // Store the expected format in a String variable
        String expected = "Professor{name='Test Professor', id='TestID', username='TestUsername', password='TestPass'}";
        
        //Check that the actual format matches the expected format
        assertEquals(expected, actual);
	}
	
	@Test
	void testLoginSuccessful() {
		//Test that the method will return the key of the HashMap (Professor ID) if the user logs in successfully
		assertEquals("001",professor.loginSuccessful(professorHash, "Greenberg", "password590"));
		assertEquals("017",professor.loginSuccessful(professorHash, "Agarwal", "password590"));
		assertEquals("029",professor.loginSuccessful(professorHash, "Krakowsky", "password590"));
		
		//Test that the method will return null if the user provides an inaccurate username
		assertNull(professor.loginSuccessful(professorHash, "notAUserName", "password590"));
		
		//Test that the method will return null if the user provides an inaccurate password
		assertNull(professor.loginSuccessful(professorHash, "Davidson", "notAPassword"));
	}
	
	@Test
	void viewGivenCourses() {
		//Test that the method will return the given courses for the given professor name
		String expected = courses.get("CIT590").toString() + "\n";
		String actual = professor.viewGivenCourses(courses, "Brandon L Krakowsky");
		assertEquals(expected, actual);
		
		String expected2 = courses.get("CIS557").toString() + "\n" + courses.get("CIT594").toString() + "\n";
		String actual2 = professor.viewGivenCourses(courses, "Eric Noel Fouh Mbindi");
		assertEquals(expected2, actual2);
		
		//Test that the method will return no courses for a given professor name that does not exist
		String expected3 = "";
		String actual3 = professor.viewGivenCourses(courses, "NonExistentProf");
		assertEquals(expected3, actual3);
		
	}

}
