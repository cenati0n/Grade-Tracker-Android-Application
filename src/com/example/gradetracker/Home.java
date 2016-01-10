package com.example.gradetracker;

import java.util.HashMap;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Home extends Activity {
	SessionManager session;
	Button btnLogout;
	ImageButton imgvwCourse,imgvwQuery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(06,138, 202)));
		bar.setTitle("Home");
			session = new SessionManager(getApplicationContext());
			HashMap<String, String> user = session.getUserDetails();
			if(user.get("name").equals("faculty"))
			{
				Faculty fac=session.getFaculty();
			}
			else
			{
				Student stu=session.getStudent();
			}
			
			String name=user.get(SessionManager.KEY_NAME);
		   // Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn() + " " + name+" "+ fac.getDept() , Toast.LENGTH_SHORT).show();
		   	imgvwCourse=(ImageButton) findViewById(R.id.imgvwCourse);
		   	imgvwQuery=(ImageButton) findViewById(R.id.imgvwQuery);
		   	imgvwCourse.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(getApplicationContext(),Courses.class);
					startActivity(i);
				}
			});
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
        case R.id.help:
        	help();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
		
	}
	private void help() {
		Intent i=new Intent(Home.this,HomeHelp.class);
		startActivity(i);
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
