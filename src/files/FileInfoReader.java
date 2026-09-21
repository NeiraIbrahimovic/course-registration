package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

import courses.Courses;
import roles.Admin;
import roles.Professor;
import roles.Student;


/**
 * FileInfoReader class provides utility methods for reading and parsing student and course information from files.
 * The class handles file operations and data conversion for a student management system.
 */
public class FileInfoReader {

    /**
     * Reads all lines from a specified file and returns them as an ArrayList of Strings.
     * 
     * @param fileName the path to the file to be read
     * @return ArrayList<String> containing all lines from the file
     */
    public static ArrayList<String> readFile(String fileName) {
    	// Initialize the File and Buffer Reader to null
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        // Create an ArrayList to store each line
        ArrayList<String> allLines = new ArrayList<String>();
        //Initialize a file variable
        File file = new File(fileName);
        // Try to read the file
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            
            String line;
            //Store each line into allLine ArrayList
            while ((line = bufferedReader.readLine()) != null) {
                allLines.add(line);
            }
         // If the file does not exist or there's an I/O error, catch it
        } catch (FileNotFoundException e) {
            System.out.println("File can not be found");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	// Regardless what happened, close the fileReader and bufferedReader
            try {
                if (fileReader != null) fileReader.close();
                if (bufferedReader != null) bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Return all the lines.
        return allLines;
    }

    /**
     * Parses student information from a file and creates a HashMap of Student objects.
     * Expected file format: ID; Name; Username; Password; Course1,Course2,...
     * 
     * @param fileName the path to the file containing student data
     * @return HashMap<String, Student> where key is student ID and value is Student object
     */
    public static HashMap<String, Student> parseStudents(String fileName) {
        //Create a new HashMap to store student ID and Student objects
        HashMap<String, Student> students = new HashMap<>();
        //Read the studentInfo file and store it in an ArrayList
        ArrayList<String> fileContents = FileInfoReader.readFile(fileName);
        
        for (String line : fileContents) {
            // Split each line into components
            String[] userData = line.split(";");
            String id = userData[0].strip();
            String name = userData[1].strip();
            String username = userData[2].strip();
            String password = userData[3].strip();
            
            // Parse course list
            String[] courseData = userData[4].split(",");
            ArrayList<String> coursework = new ArrayList<>();
            for (int i = 0; i < courseData.length; i++) {
                coursework.add(courseData[i]);
            }

            // Create and store student object
            Student student = new Student(id, name, username, password, coursework);
            // Add the Key and Value into the students Hashmap
            students.put(id, student);
        }
        //Return the Hashmap
        return students;
    }

    /**
     * Parses professor information from a file and creates a HashMap of Professor objects.
     * Expected file format: Prof Name; Prof ID; Prof Username; Prof Password...
     * 
     * @param fileName the path to the file containing student data
     * @return HashMap<String, Professor> where key is Professor ID and value is Professor object
     */
    public static HashMap<String, Professor> parseProf(String fileName) {
        //Create a new HashMap to store professor ID and Professor objects
        HashMap<String, Professor> professors = new HashMap<>();
        //Read the profInfo file and store it in an ArrayList
        ArrayList<String> fileContents = FileInfoReader.readFile(fileName);

        //Go through every line in the ArrayList
        for (String line : fileContents) {
            // Split each line into components
            String[] userData = line.split(";");
            String name = userData[0].strip();
            String id = userData[1].strip();
            String username = userData[2].strip();
            String password = userData[3].strip();

            // Create and store Professor object
            Professor professor = new Professor(id, name, username, password);
            // Add the Key and Value into the professor HashMap
            professors.put(id, professor);
        }
        // Return the HashMap
        return professors;
    }

    /**
     * Parses admin information from a file and creates a HashMap of Admin objects.
     * Expected file format: Admin ID; Admin Name; Admin Username; Admin Password...
     * 
     * @param fileName the path to the file containing admin data
     * @return HashMap<String, Admin> where key is admin ID and value is Admin object
     */
    public static HashMap<String, Admin> parseAdmin(String fileName) {
        //Create a new HashMap to store admin ID and Admin objects
        HashMap<String, Admin> admins = new HashMap<>();
        //Read the adminInfo file and store it in an ArrayList
        ArrayList<String> fileContents = FileInfoReader.readFile(fileName);

        for (String line : fileContents) {
            // Split each line into components
            String[] userData = line.split(";");
            String id = userData[0].strip();
            String name = userData[1].strip();
            String username = userData[2].strip();
            String password = userData[3].strip();

            // Create and store Admin object
            Admin admin = new Admin(id, name, username, password);
            // Add the Key and Value into the admins HashMap
            admins.put(id, admin);
        }
        // Return the HashMap
        return admins;
    }
    


    /**
     * Parses course information from a file and creates a TreeMap of Courses objects because we want it in order.
     * Expected file format: CourseID;CourseName;Lecturer;Days;StartTime;EndTime;Capacity
     * 
     * @param fileName the path to the file containing course data
     * @return HashMap<String, Courses> where key is course ID and value is Courses object
     */
    public static TreeMap<String, Courses> parseCourses(String fileName) {
        //Create a new TreeMap to store course ID and Courses objects
    	TreeMap<String, Courses> courses = new TreeMap<>();
        //Read the courseInfo file and store it in an ArrayList
        ArrayList<String> fileContents = FileInfoReader.readFile(fileName);
        
        for (String line : fileContents) {
            // Split and parse course information
            String[] temp = line.split(";");
            String courseID = temp[0].strip();
            String courseName = temp[1].strip();
            String courseLecturer = temp[2].strip();
            String courseDays = temp[3].strip();
            String courseStartTime = temp[4].strip();
            String courseEndTime = temp[5].strip();
            // Make the courseCap into an Int
            int courseCapacity = Integer.parseInt(temp[6].strip());
            
            // Create and store course object
            Courses course = new Courses(courseID, courseName, courseLecturer, 
                                      courseDays, courseStartTime, courseEndTime, 
                                      courseCapacity);
         // Add the Key and Value into the course TreeMap
            courses.put(courseID, course);
        }
        // Return the course TreeMap
        return courses;
    }
}
