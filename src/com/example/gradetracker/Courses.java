package com.example.gradetracker;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Courses extends Activity {
	SessionManager session;
	String values[][];
	ListView lv;
	TotalMarksDialog custDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courselist);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(06,138, 202)));
		bar.setTitle("Courses");
		session=new SessionManager(getApplicationContext());	
		final HashMap<String, String> userinfo=session.getUserDetails();
		if(userinfo.get("name").equals("faculty"))
		{
			Faculty fac=session.getFaculty();
			values=fac.getCourse();
		}
		else
		{
			Student stu=session.getStudent();
			values=stu.getCourse();
		}
		lv=(ListView)findViewById(R.id.lvcourse);
		lv.setAdapter(new CourseListItem(this,values));
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(android.widget.AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				/*switch(arg2){
				case 0:
					Toast.makeText(Courses.this,values[arg2][0], Toast.LENGTH_SHORT).show();
					custDialog=new TotalMarksDialog(Courses.this);
					custDialog.show();
					break;
				case 1:
					Toast.makeText(Courses.this,values[arg2][0], Toast.LENGTH_SHORT).show();
					custDialog=new TotalMarksDialog(Courses.this);
					custDialog.show();
					break;
				}*/
				String s =(String) ((TextView) arg1.findViewById(R.id.tvcourse)).getText().toString();
				session.putSessionCourse(s);
				//Toast.makeText(Courses.this,s, Toast.LENGTH_SHORT).show();
				if(userinfo.get("name").endsWith("faculty"))
				{
					custDialog=new TotalMarksDialog(Courses.this);
					custDialog.show();
				}
				else
				{
					Intent i=new Intent(Courses.this,StudentshowGrades.class);
					startActivity(i);
				}
				
			};
		}) ;
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) { 
        case R.id.logout:
        	logout();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
		
	}
	
	private void logout() {
		confirmDialog();
	}
	protected void confirmDialog()
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Logout!");
        alertDialog.setMessage("Are you sure you want to logout?");
        alertDialog.setIcon(R.drawable.logout);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	session.logoutUser();
            	finish();
            	Toast.makeText(getApplicationContext(), "Successfully loggedout!!", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
        alertDialog.show();
	
	}
}
