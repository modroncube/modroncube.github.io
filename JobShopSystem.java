/*
Sample Menu driven program for Assignment 2: SP2-2019

*/

import java.util.Scanner;

public class JobShopSystem {
	private static Scanner sc = new Scanner(System.in);
	private static Student[] studentArray = new Student[50];
	private static int currentStudents = 0;

	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String selection;

		do {

			// Display menu options
			System.out.println("   ***** Job Shop System Menu *****");
			System.out.println("A. Add New Student");
			System.out.println("B. View student (job seeker) Information");
			System.out.println("C. Apply for a casual Job");
			System.out.println("D. Add New Research Student");
			System.out.println("E. Display Students Report");
			System.out.println("X. Exit the program");
			System.out.println();

			// prompt user to enter selection
			System.out.print("Enter selection: ");
			selection = sc.nextLine();

			System.out.println();

			// validate selection input length
			if (selection.length() != 1) {
				System.out.println("Error - invalid selection!");
			} else {

				// process user's selection
				switch (selection.toUpperCase()) {
				case "A":
					addNewStudent();
					break;

				case "B":
					viewstudentInformation();
					break;

				case "C":
					applyCasualJob();
					break;

				case "D":
					addNewresearchStudent();
					break;
					
				case "E":
					displaystudentsReport();
					break;

				case "X":
					System.out.println("Exiting the program...");
					break;

				default:
					System.out.println("Error - invalid selection!");
				}
			}
			System.out.println();

		} while (!selection.equalsIgnoreCase("X"));
	}

	
	/**
	 * 
	 * @param ID
	 * @return
	 */
	private static Student findAstudent(String ID) {

		/*
		 * Optional - you can implement code to search for a student and return
		 * it here (after which the relevant methods below can call this method
		 * when they need to locate a specific student).
		 * 
		 * NOTE: It is also acceptable to implement (repeat) your search code
		 * inside each of the methods below which need to locate a specific
		 * student.
		 * 
		 */		
		for (int i = 0; i < studentArray.length; i++)
		{
			if (studentArray[i].getStudentID().equals(ID))
			{
				return studentArray[i];
			}
		}
		return null;
	}

	
	/**
	 * 
	 */
	private static void addNewStudent() {
		/*
		 * Implement application level code to facilitate adding a new Student
		 * to the system here.
		 * 
		 * Note that the array in which the collection of Student objects is
		 * being stored and a Scanner called 'sc' have been declared at the top
		 * of the class and you can refer to them here inside this inside method
		 * as required.
		 */
		
		System.out.print("Enter the student's ID: ");
		String id = sc.nextLine();
		System.out.print("Enter the student's full name: ");
		String name = sc.nextLine();
		System.out.print("Enter the student's study program title: ");
		String program = sc.nextLine();
		
		Student newStudent = new Student(id, name, program);
		studentArray[currentStudents] = newStudent;
		currentStudents++;
	}

	
	/**
	 * 
	 */
	private static void viewstudentInformation() {
		/*
		 * Implement application level code to facilitate displaying a student
		 * information here.
		 * 
		 * Note that the array in which the collection of Student objects is
		 * being stored and a Scanner called 'sc' have been declared at the top
		 * of the class and you can refer to them here inside this inside method
		 * as required.
		 */
		
		System.out.print("Enter the student's ID: ");
		String id = sc.nextLine();
		
		try
		{
			if (!findAstudent(id).equals(null))
			{
				System.out.println("ID: " + findAstudent(id).getStudentID() + "\n" +
				 			  	   "Name: " + findAstudent(id).getName() + "\n" +
				 		           "Program: " + findAstudent(id).getProgram());
			}
		}
		catch (Exception e)
		{
			System.out.println("ERROR: Student ID not found");
		}
	}

	
	/**
	 * 
	 */
	private static void applyCasualJob() {
		/*
		 * Implement application level code to facilitate application of a
		 * casual Job here. Job information is supplied by the user from the
		 * table presented in the Assignment
		 * 
		 */
		
		System.out.println("Enter the student's ID:");
		String id = sc.nextLine();
		
		try
		{
			if (!findAstudent(id).equals(null))
			{
				System.out.print("Enter job title: ");
				String jobTitle = sc.nextLine();
				System.out.print("Enter employer: ");
				String employer = sc.nextLine();
				System.out.print("Enter work hours per week: ");
				double hours = sc.nextDouble();
				sc.nextLine();
				
				if (findAstudent(id).addJob(hours))
				{
					findAstudent(id).setJobTitle(jobTitle);
					findAstudent(id).setEmployer(employer);
					findAstudent(id).setCurrentWorkHours(hours);
					
					System.out.println("Successfully added " + jobTitle);
				}
				else
				{
					System.out.println("Unable to add job (exceeds maximum hours per week)");
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("ERROR: Student ID not found");
		}
	}

	
	/**
	 * 
	 */
	private static void addNewresearchStudent() {
		/*
		 * Implement application level code to facilitate adding a new research
		 * Student to the system here.
		 * 
		 * Note that the array in which the collection of Student objects is
		 * being stored and a Scanner called 'sc' have been declared at the top
		 * of the class and you can refer to them here inside this inside method
		 * as required.
		 */
		
		System.out.print("Enter the student's ID: ");
		String id = sc.nextLine();
		System.out.print("Enter the student's full name: ");
		String name = sc.nextLine();
		System.out.print("Enter the student's study program title: ");
		String program = sc.nextLine();
		
		Student newStudent = new ResearchStudent(id, name, program);
		studentArray[currentStudents] = newStudent;
		currentStudents++;
	}

	
	/**
	 * 
	 */
	private static void displaystudentsReport() {
		/*
		 * Implement application level code to display Students report currently
		 * stored in the system here. Note that the array in which the
		 * collection of student objects is being stored and a Scanner called
		 * 'sc' have been declared at the top of the class and you can refer to
		 * them here inside this inside method as required.
		 */
		
		String[] headers = { "Student ID:",
							 "Name:",
							 "Program:",
							 "Job:",
							 "Current working hours:",
							 "Employer:"
							};
		
		for (int i = 0; i < studentArray.length; i++)
		{
			try
			{
				if (!studentArray[i].equals(null))
				{
					String[] studentTemp = studentArray[i].printStudentDetails().split("-");
					
					for (int j = 0; j < studentTemp.length; j++)
					{						
						System.out.printf("%-30s %s\n", headers[j], studentTemp[j]);
						if (j == (studentTemp.length - 2) && (studentArray[i] instanceof ResearchStudent))
						{
							System.out.printf("%-30s %s\n", "Work Limit:", (((ResearchStudent) studentArray[i]).getWorkLimit()));
							System.out.println("");
						}
					}
					System.out.println("");
				}
			}
			catch (Exception e)
			{
				//
			}
		}
	}	
}
