/**
 * CPT121 Programming 1
 * RMIT University
 * Study Period 2, 2019
 * Assignment 2
 * Student.java
 * 
 * @author Michael Seymour - s3040138
 * 
 * michael.seymour@student.rmit.edu.au
 *
 */


/**
 * 
 * 
 *
 */
public class Student
{
	private String name;
	private String studentID;
	private String program;
	private String employer;
	private String jobTitle;
	private double currentWorkHours;
	
	/**
	 * 
	 * @param studentID
	 * @param name
	 * @param program
	 */
	public Student(String studentID, String name, String program)
	{
		this.studentID = studentID;
		this.name = name;
		this.program = program;
		
		this.employer = " ";
		this.jobTitle = " ";
		this.currentWorkHours = 0.0;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getName()
	{
		return this.name;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getStudentID()
	{
		return this.studentID;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getProgram()
	{
		return this.program;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getEmployer()
	{
		return this.employer;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public double getCurrentWorkHours()
	{
		return this.currentWorkHours;
	}
	
	
	/**
	 * 
	 * @param hours
	 */
	public void setCurrentWorkHours(double hours)
	{
		this.currentWorkHours += hours;
	}
	
	
	/**
	 * 
	 * @param hours
	 * @return
	 */
	public boolean addJob(double hours)
	{
		if ((hours + this.currentWorkHours) < 20)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	/**
	 * 
	 * @param jobTitle
	 */
	public void setJobTitle(String jobTitle)
	{
		if (!this.jobTitle.equals(" "))
		{
			this.jobTitle += ", " + jobTitle;
		}
		else
		{
			this.jobTitle = jobTitle;
		}
	}
	
	
	/**
	 * 
	 * @param employer
	 */
	public void setEmployer(String employer)
	{
		if (!this.employer.equals(" "))
		{
			this.employer += ", " + employer;
		}
		else
		{
			this.employer = employer;
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String printStudentDetails()
	{
	
		return this.studentID + "-" +
			   this.name + "-" +
			   this.program + "-" +
			   this.jobTitle + "-" +
			   String.valueOf(this.currentWorkHours) + "-" +
			   this.employer;
	}
}
