import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

import courses.Courses;
import files.FileInfoReader;
import roles.Admin;
import roles.Professor;
import roles.Student;

public class Controller {
	public static void main(String[]arg) {
		// Create a Hashmap for the student. The hashmap will look like the following
		// {Key,Value} Key = student id, Value = String id, String name, String username, String password, ArrayList<String> coursework
		// ArrayList<String> coursework will contain each class and the grade for that class in each index
        HashMap<String, Student> students = FileInfoReader.parseStudents("studentInfo.txt");
        
        // Create a Hashmap for the professor. The hashmap will look like the following
     	// {Key,Value} Key = professor id, Value = String name, String id, String username, String password
        HashMap<String, Professor> professors = FileInfoReader.parseProf("profInfo.txt");
        
        // Create a Hashmap for the admin. The hashmap will look like the following
     	// {Key,Value} Key = admin id, Value = String id, String name, String username, String password
        HashMap<String, Admin> admins = FileInfoReader.parseAdmin("adminInfo.txt");
        
        // Create a TreeMap for the course so it will be in order. The TreeMap will look like the following
        // {Key,Value} Key = Course id (CIS105), Value = String courseID, String courseName, String courseLecturer, 
        // String courseDays, String courseStartTime, String courseEndTime, int courseCapacity
        TreeMap<String, Courses> courses = FileInfoReader.parseCourses("courseInfo.txt");
        
        //Create an empty student class
        Student student = new Student();
        
        //Create an empty Professor class
        Professor professor = new Professor();
        
        //Create an empty admin class
        Admin admin = new Admin();
        
        // In loop until user input 4 to break it
        while(true) {
        	System.out.println("---------------------------");
            System.out.println("Student Management System");
            System.out.println("---------------------------");
            System.out.println("1 -- Login as a student");
            System.out.println("2 -- Login as a professor");
            System.out.println("3 -- Login as an admin");
            System.out.println("4 -- Quit the system");
            
            Scanner scanner = new Scanner(System.in);  // Create a Scanner object
            System.out.println("What do you want to do today?");
            String loginAs = scanner.nextLine();
            // Check if the user input 1,2,3,4
            
         // If user inputs 3, logs in as student
            if (loginAs.equals("1")) { 
            	// Construct studentLoginID and initialize it to null
            	String studentLoginId = null;
            	while (true) { // Infinite loop until the user either logs in successfully or quit
            		// Get the input for user name and password
                	System.out.println("Please enter your username or type 'q' to quit?");
                    String userNameInput = scanner.nextLine();
                    if(userNameInput.equals("q")) {
                    	break;
                    }
                    System.out.println("Please enter your password or type 'q' to quit?");
                    String passwordInput = scanner.nextLine();
                    if(passwordInput.equals("q")) {
                    	break;
                    }
                    // Call loginSuccessfull, it will return the student id then break the loop
                    studentLoginId  = student.loginSuccessful(students,userNameInput, passwordInput);
                    if (studentLoginId != null) {
                    	break;
                    }
                    // If it returns null, that means user name and password is not correct
                    System.out.println("Incorrect username and password, try again");
            	}
                        //Goes back to the main screen if studentLoginID is null, not log in
            			if (studentLoginId == null) {
            				continue;
            			}
            			// use the studentLoginId to get This student into selectedStudent
                		Student selectedStudent =students.get(studentLoginId);
                		while(true) {
                			System.out.println("---------------------------");
                    		System.out.println("Welcome " + selectedStudent.getName());
                    		System.out.println("---------------------------");
                    		System.out.println("1 -- View all courses");
                            System.out.println("2 -- Add courses to your list");
                            System.out.println("3 -- View enrolled courses");
                            System.out.println("4 -- Drop courses in your list");
                            System.out.println("5 -- View grades");
                            System.out.println("6 -- Return to previous");
                            System.out.println("Please enter your option, eg. '1'.");
                            String studentOption = scanner.nextLine();
                            
                            // Check which input the user selects
                            if(studentOption.equals("1")) { // If the user selects 1, call printAllClasses
                            	System.out.println("Below are all the courses");
                            	Courses.printAllClasses(courses);
                            }else if(studentOption.equals("2")) { // If the user selects 2
                            	System.out.println("Please select the course ID you want to add to your list, eg. 'CIT590'.\n"
                            			+ "Or enter 'q' to return to the previous menu.");
                            	String courseToAdd = scanner.nextLine();
                            	// Get the course id the user wants to add, make it upper case, strip it, and pass it in courseToAdd
                            	selectedStudent.addCourses(courseToAdd.toUpperCase().strip(),courses);
                            }else if(studentOption.equals("3")) { 
                            	// If the user selects 3, call printCourseEnrolled
                            	selectedStudent.printCourseEnrolled(courses);
                            }else if (studentOption.equals("4")) { 
                            	// If the user selects 4, ask which class the user wants to drop and pass it in courseToDrop
                            	System.out.println("Please select the course ID you want to drop to your list, eg. 'CIT590'.\n"
                            			+ "Or enter 'q' to return to the previous menu.");
                            	String courseToDrop = scanner.nextLine();
                            	selectedStudent.dropCourseInList(courseToDrop.toUpperCase().strip());
                            }else if (studentOption.equals("5")) { 
                            	// If the user selects 5, call printGrades and pass in the students hashmap and the courses treemap
                            	selectedStudent.printGrades(students, courses);
                            }else if (studentOption.equals("6")) { 
                            	// If the user selects 6, break the loop
                            	break;
                            }	
                		}
                		
            
            // If user inputs 2, logs in as professor
            }else if (loginAs.equals("2")) {
            
                	// Construct professorLoginID and initialize it to null
                	String professorLoginId = null;
                	while (true) { // Infinite loop until the user either logs in successfully or quit
                		// Get the input for user name and password
                    	System.out.println("Please enter your username or type 'q' to quit?");
                        String userNameInput = scanner.nextLine();
                        if(userNameInput.equals("q")) {
                        	break;
                        }
                        System.out.println("Please enter your password or type 'q' to quit?");
                        String passwordInput = scanner.nextLine();
                        if(passwordInput.equals("q")) {
                        	break;
                        }
                        // Call loginSuccessful, it will return the professor id then break the loop
                        professorLoginId  = professor.loginSuccessful(professors, userNameInput, passwordInput);
                        if (professorLoginId != null) {
                        	break;
                        }
                        // If it returns null, that means user name and password is not correct
                        System.out.println("Incorrect username and password, try again");
                	}
                            //Goes back to the main screen if professorLoginId is null, not log in
            			    if (professorLoginId == null) {
            				    continue;
            			    }
                			// use the professorLoginId to get This professor into variable selectedProfessor
                    		Professor selectedProfessor = professors.get(professorLoginId);
                    		System.out.println(selectedProfessor);
                    		while(true) {
                    			System.out.println("---------------------------");
                        		System.out.println("Welcome " + selectedProfessor.getName());
                        		System.out.println("---------------------------");
                        		System.out.println("1 -- View given courses");
                                System.out.println("2 -- View student list of the given course");
                                System.out.println("3 -- Return to the previous menu");
                                System.out.println("Please enter your option, eg. '1'.");
                                String professorOption = scanner.nextLine();
                                
                                // Check which input the user selects
                                if(professorOption.equals("1")) { // If the user selects 1, call viewGivenCourses
                                	System.out.println("-------------------The Course List-------------------");
                                	String courseList = selectedProfessor.viewGivenCourses(courses, selectedProfessor.getName());
                                	System.out.println(courseList);
                                }else if(professorOption.equals("2")) { // If the user selects 2
                                	//Print a message directing the user to give input
                                	System.out.println("Enter the course Id to view the student list:");
                                	String courseId = scanner.nextLine();
                                	//Call the method in Courses class to view the student list for the course
                                	String studentList = Courses.viewStudentList(courseId, students, courses); 
                                	System.out.println(studentList);
                                }else if (professorOption.equals("3")) { 
                                	// If the user selects 3, break the loop
                                	break;
                                }	
                    		}
  
            // If user inputs 3, logs in as admin
            }else if (loginAs.equals("3")) {
            
            	// Construct adminLoginID and initialize it to null
            	String adminLoginId = null;
            	while (true) { // Infinite loop until the user either logs in successfully or quit
            		// Get the input for user name and password
                	System.out.println("Please enter your username or type 'q' to quit?");
                    String userNameInput = scanner.nextLine();
                    if(userNameInput.equals("q")) {
                    	break;
                    }
                    System.out.println("Please enter your password or type 'q' to quit?");
                    String passwordInput = scanner.nextLine();
                    if(passwordInput.equals("q")) {
                    	break;
                    }
                    // Call loginSuccessful, it will return the admin id then break the loop
                    adminLoginId  = admin.loginSuccessful(admins, userNameInput, passwordInput);
                    if (adminLoginId != null) {
                    	break;
                    }
                    // If it returns null, that means user name and password is not correct
                    System.out.println("Incorrect username and password, try again");
            	}
                        //Goes back to the main screen if adminLoginId is null, not log in
            			if (adminLoginId == null) {
            				continue;
            			}
            			// use the adminLoginId to get This admin into variable selectedAdmin
                		Admin selectedAdmin = admins.get(adminLoginId);
                		while(true) {
                			System.out.println("---------------------------");
                    		System.out.println("Welcome " + selectedAdmin.getName());
                    		System.out.println("---------------------------");
                    		System.out.println("1 -- View all courses");
                            System.out.println("2 -- Add new courses");
                            System.out.println("3 -- Delete courses");
                            System.out.println("4 -- Add new professor");
                            System.out.println("5 -- Delete professor");
                            System.out.println("6 -- Add new student");
                            System.out.println("7 -- Delete student");
                            System.out.println("8 -- Return to previous menu");
                            System.out.println("Please enter your option, eg. '1'.");
                            String adminOption = scanner.nextLine();
                            
                            // Check which input the user selects
                            if(adminOption.equals("1")) { // If the user selects 1, call printAllClasses

                                //prints all courses 
                            	System.out.println("Below are all the courses");
                            	Courses.printAllClasses(courses);

                            }else if(adminOption.equals("2")) { // If the user selects 2

                            	System.out.println("Adding a new course. Please follow the prompts.");
                                
                            	//Get course id input
                                System.out.println("Enter the course ID:");
                                String courseId = scanner.nextLine();

                                //If course input is already a course, skip adding again
                                if (courses.containsKey(courseId)) {
                                    System.out.println("Error: Course ID already exists. Please try again.");
                                    continue;
                                }

                                //Otherwise, continue with inputs and get course name
                                System.out.println("Enter the course name:");
                                String courseName = scanner.nextLine();

                                //Get course start time
                                System.out.println("Enter the course start time (e.g., '09:00'):");
                                String startTime = scanner.nextLine();

                                //Get course end time
                                System.out.println("Enter the course end time (e.g., '10:30'):");
                                String endTime = scanner.nextLine();

                                //Get course days
                                System.out.println("Enter the course days (e.g., 'MWF'):");
                                String days = scanner.nextLine();

                                //Get course capacity
                                System.out.println("Enter the course capacity:");
                                int capacity = Integer.parseInt(scanner.nextLine());

                                //Get lecturer id
                                System.out.println("Enter the lecturer ID:");
                                String lecturerId = scanner.nextLine();
                                
                                //Initialize lecture name variable for later
                                String lecturerName;

                                //Check if lecturer id already exists
                        		if (!professors.containsKey(lecturerId)) {
	                        		
                        			//Print a message indicating the lecturer does not exist yet and needs to be added first.
                        			System.out.println("Error: Lecturer ID '" + lecturerId + "' not found. Add the lecturer first.");
	                        		
	                        		//Enter new lecturer's Id
	                        		System.out.println("Enter the new lecturer's ID:");
	                                lecturerId = scanner.nextLine();
	                                
	                                //Enter the new lecturer's name and assign the lecturer name variable
	                                System.out.println("Enter the new lecturer's name:");
	                                lecturerName = scanner.nextLine();
	                                
	                                //Enter the new lecturer's username
	                                System.out.println("Enter the new lecturer's username:");
	                                String lecturerUsername = scanner.nextLine();
	                                
	                                //Enter the new lecturer's password
	                                System.out.println("Enter the new lecturer's password:");
	                                String lecPassword = scanner.nextLine();
	                                
	                                //Add the new professor
	                                selectedAdmin.addProfessor(professors, lecturerId, lecturerName, lecturerUsername, lecPassword);
                        		
                        		} else {
                        			
                        			//Otherwise, assign lecturer name to the existing professor
                        			Professor selectedProf = professors.get(lecturerId);
                        			System.out.println(selectedProf);
                        			lecturerName = selectedProf.getName();
                        			System.out.println(lecturerName);
                        		}
                        		
                        		
                                //Check if there is a time conflict with the lecturer's schedule
                                boolean check = selectedAdmin.validateLecturerSchedule(lecturerName, courseId, days, startTime, endTime, courses);
                                
                                if (check == false) {
                                	
                                	//If there is a time conflict, break
                                	break;
                                	
                                } else {
                                
	                                // If there is no time conflict, add the course
	                                selectedAdmin.addCourse(courses, courseId, courseName, lecturerName, days, startTime, endTime, capacity);

                                }
                            }else if(adminOption.equals("3")) { 
                                // If the user selects 3, print a message that they chose to delete courses
                            	System.out.println("Delete courses");
                            	
                            	//Get course id
                            	System.out.println("Enter the course ID of the course you want to delete:");
                                String courseId = scanner.nextLine();
                                
                                //Delete course
                                selectedAdmin.deleteCourse(courses, courseId);
                            	
                            }else if(adminOption.equals("4")) { // If the user selects 4
                                System.out.println("Add new professor");
                            	
                                // Collect Professor details
                                System.out.println("Please enter the Professors ID, or type 'q' to quit:");
                                String profId = scanner.nextLine();
                                if (profId.equals("q")) continue;

                                //Get the Professor name
                                System.out.println("Please enter the Professors name, or type 'q' to quit:");
                                String profName = scanner.nextLine();
                                if (profName.equals("q")) continue;

                                //Get the Professor user name
                                System.out.println("Please enter the Professors username, or type 'q' to quit:");
                                String username = scanner.nextLine();
                                if (username.equals("q")) continue;

                                // Check if the username already exists in the HashMap values
                                for (Professor prof : professors.values()) {
                                    if (prof.getUsername().equals(username)) {
                                        System.out.println("Error: Username '" + username + "' already exists.");
                                        continue;
                                    }
                                }

                                //Get the Professor password
                                System.out.println("Please enter the Professors password, or type 'q' to quit:");
                                String password = scanner.nextLine();
                                if (password.equals("q")) continue;
                                
                                // Add the new Professor
                                selectedAdmin.addProfessor(professors, profId, profName, username, password);
                            
                            }else if(adminOption.equals("5")) { // If the user selects 5
                                //Print a message stating that they chose to delete a professor
                                System.out.println("Delete Professor");
                            	
                                //Print out current professors
                                System.out.println("Professors in the System:");
                        	    System.out.println("-----------------------");

                        	    for (String p : professors.keySet()) {
                        	    	Professor prof_info = professors.get(p);
                        	        System.out.println(prof_info.toString());
                        		}
                        	    System.out.println("No more professors in the system past this point.");
                    	        System.out.println("-----------------------");
                                
                        	    //Get professor id
                        	    System.out.println("Enter the professor ID of the professor you want to delete:");
                                String Id = scanner.nextLine();
                        	        
                                //delete professor
                        	    selectedAdmin.deleteProfessor(professors, Id); 

                            }else if(adminOption.equals("6")) { // If the user selects 6
                            //Print a message stating that they chose to add a new student
                            System.out.println("Adding a new student");

                                // Collect student details
                                System.out.println("Please enter the student's ID, or type 'q' to quit:");
                                String studentId = scanner.nextLine();
                                if (studentId.equals("q")) continue;

                                //Get the student's name
                                System.out.println("Please enter the student's name, or type 'q' to quit:");
                                String studentName = scanner.nextLine();
                                if (studentName.equals("q")) continue;

                                //Get the student's user name
                                System.out.println("Please enter the student's username, or type 'q' to quit:");
                                String username = scanner.nextLine();
                                if (username.equals("q")) continue;

                                //Get the student's password
                                System.out.println("Please enter the student's password, or type 'q' to quit:");
                                String password = scanner.nextLine();
                                if (password.equals("q")) continue;

                                // Initialize coursework list
                                ArrayList<String> coursework = new ArrayList<>();
                                
                                //Create a true false check to end the while loop later
                                boolean check = true;

                                // Collect coursework details
                                while (check) {
                                    System.out.println("Please enter ID of a course which this student already took, one at a time \nType 'q' to quit, type 'n' to stop adding:");
                                    String studentCourse = scanner.nextLine();
                                    //Make the courseID uppercase
                                    studentCourse = studentCourse.toUpperCase();
                                    //Quit
                                    if (studentCourse.equals("Q")) { 
                                        System.out.println("Quitting the student addition process.");
                                        break; // Exit completely if 'q' is entered
                                        
                                    //Stop adding the courses
                                    } else if (studentCourse.equals("N")) {
                                        check = false; // Stop adding courses but continue adding the student
                                        
                                    //Add a course to the students Array List of Strings 'coursework'
                                    } else if (courses.containsKey(studentCourse)) { 

                                        // Check if the course is already in the coursework list
                                        boolean courseAlreadyAdded = false;
                                        for (String courseWithGrade : coursework) {
                                            String courseID = courseWithGrade.split(":")[0].trim(); // Extract course ID
                                            System.out.println("courseID:" + courseID);
                                            if (courseID.equals(studentCourse)) {
                                                courseAlreadyAdded = true;
                                                System.out.println("Error: Course ID '" + studentCourse + "' already exists. Please try again.");
                                                break;
                                            }
                                        }

                                        //If the courseAlreadyAdded boolean is false, proceed to add course to coursework
                                        if (!courseAlreadyAdded) {
                                            System.out.println("Please enter the grade, e.g., 'A':");
                                            String grade = scanner.nextLine();
                                            //Make the grades uppercase
                                            grade = grade.toUpperCase();
                                            // Add course with grade to the coursework list
                                            String courseWithGrade = " " + studentCourse + ": " + grade;
                                            coursework.add(courseWithGrade);
                                            System.out.println("Added: " + courseWithGrade);
	                                        
                                        }

                                    } else { 
                                        System.out.println("Error: Course ID '" + studentCourse + "' does not exist. Please try again.");
                                    }

                                    //Add the new student
                                    // Only add the student if the coursework list is not empty
                                    if (!coursework.isEmpty()) {
                                        selectedAdmin.addStudent(students, studentId, studentName, username, password, coursework);
                                    } else {
                                        System.out.println("Student not added due to incomplete information.");

                                }

                                
    }
                            
                            }else if(adminOption.equals("7")) { // If the user selects 7
                            	//Print a message stating the user chose to delete a student
                                System.out.println("Delete student");
                            	
                                //Print out current students
                                System.out.println("Students in the System:");
                        	    System.out.println("-----------------------");

                        	    for (String s : students.keySet()) {
                        	    	Student student_info = students.get(s);
                        	        System.out.println(student_info.toString());
                        	        System.out.println("No more students in the system past this point.");
                        	        System.out.println("-----------------------");

 
                        		}
                                
                        	    //Get student id
                        	    System.out.println("Enter the student ID:");
                                String Id = scanner.nextLine();
                        	        
                                //delete student
                        	    selectedAdmin.deleteStudent(students, Id);
                                       
                            }else if (adminOption.equals("8")) { 
                            	// If the user selects 3, break the loop
                            	break;
                            }	
                		}
            	
            // If user inputs 4, quit the system
            }else if (loginAs.equals("4")) {
            	// Break out of the loop
            	System.out.println("Thank you");
            	break;
            	
            // If user inputs an invalid input, print out a message
            }else { 
            	System.out.println("Please input a valid input.");
            }
            
        	
        }
        
        
	}
	
}
