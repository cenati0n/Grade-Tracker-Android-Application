/* package whatever; // don't place package name! */
package com.example.gradetracker;
import java.util.*;
import java.lang.*;
import java.io.*;
/* Name of the class has to be "Main" only if the class is public. */
public class Faculty extends User
{
	private String dept;
	public Vector<Course> v = new Vector();

	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	//public Course 
	public void addCourse(Course c)
	{
		v.addElement(c);
	}
	public String[][] getCourse()
	{
		int rows=v.size();
		int col=2;
		String str[][];
		  str = new String[rows][col];
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<col;j++)
			{
				Course c=v.get(i);
				if(j==0)
				{
					str[i][j]=c.getCname();
				}
				else
				{
					String credit=c.getCredit()+"";
					str[i][j]=credit;
				}
			}
		}
		return str;
	}
}