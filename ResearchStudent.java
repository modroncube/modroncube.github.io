/**
 * CPT121 Programming 1
 * RMIT University
 * Study Period 2, 2019
 * Assignment 2
 * ResearchStudent.java
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
public class ResearchStudent extends Student
{
	private String workLimit;
	
	/**
	 * 
	 * @param studentID
	 * @param name
	 * @param program
	 */
	public ResearchStudent(String studentID, String name, String program)
	{
		super(studentID, name, program);
		
		this.workLimit = "Unlimited";
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getWorkLimit()
	{
		return this.workLimit;
	}
	
	
	/**
	 * 
	 */
	@Override
	public boolean addJob(double hours)
	{
		if ((hours + super.getCurrentWorkHours()) < (24 * 7))
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
	 */
	@Override
	public String printStudentDetails()
	{
		return super.printStudentDetails() + "-" + this.workLimit;
	}
}
