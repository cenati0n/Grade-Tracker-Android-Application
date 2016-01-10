package com.example.gradetracker;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity{
	EditText txtuname;
	EditText txtpass;
	Button btnlogin;
	SessionManager session;
	String uid;
	private ProgressDialog pDialog;
	Faculty fac;
	Student stu;
	JSONParser jsonParser = new JSONParser();
	private static final String LOGIN_URL = "http://192.168.137.1/mgt/login.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(06,138, 202)));
		session = new SessionManager(getApplicationContext());   
		txtuname=(EditText)findViewById(R.id.username);
		txtpass=(EditText)findViewById(R.id.password);
		btnlogin=(Button)findViewById(R.id.btnLogin);
		if(haveNetworkConnection())
			Toast.makeText(getApplicationContext(), "Connected to Internet!!", Toast.LENGTH_SHORT).show();
		else
		{
			Toast.makeText(getApplicationContext(), "Not Connected to Internet!!", Toast.LENGTH_SHORT).show();
			dialogNoConnection();
		}
		
		btnlogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String uname=txtuname.getText().toString();
				String pass=txtpass.getText().toString();
				session.putuid(uname);
				fac=new Faculty();
				stu=new Student();
				//fac.setDept("ABC");
				if(uname.trim().length()>0 && pass.trim().length()>0)
				{
						//session.createLoginSession("Chirag", "chiraggarg31@gmail.com",fac);
						//session.createLoginSession("Chirag", "chiraggarg31@gmail.com");
                        /*Intent i = new Intent(getApplicationContext(), Home.class);
                        startActivity(i);
                        finish();*/
						new AttemptLogin(uname, pass).execute();
				}
				else
					Toast.makeText(getApplicationContext(),"Please enter username and password", Toast.LENGTH_LONG).show();

			}
		});
	}
	class AttemptLogin extends AsyncTask<String, String, String>
	{
		String username;
		String password;
		int success=0;
		JSONArray cou = null;
		public AttemptLogin(String username,String password) {
			this.username=username;
			this.password=password;
			// TODO Auto-generated constructor stub
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog=new ProgressDialog(Login.this);
			pDialog.setMessage("Attempting for Login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
		}
		@Override
		protected String doInBackground(String... args) {
			try {
			// TODO Auto-generated method stub
				List<NameValuePair> params = new ArrayList<NameValuePair>(); 
	        	params.add(new BasicNameValuePair("uid", username)); 
	        	params.add(new BasicNameValuePair("pswd", password));
	        	Log.d("request!", "starting");
	        	JSONObject json = jsonParser.makeHttpRequest(
	                     LOGIN_URL, "POST", params);
				success=json.getInt(TAG_SUCCESS);
				if(success==1)
				{
					int type=json.getInt("type");
					if(type==2)
						{
							fac.setDept(json.getString("dept"));
							fac.setUid(json.getString("uid"));
							fac.setUname(json.getString("name"));
							cou=json.getJSONArray("course");
							for(int i=0;i< cou.length();i++)
							{
								JSONObject c=cou.getJSONObject(i);
								String cname=c.getString("course");
								int credit = c.getInt("credit");
								Course courseobj=new Course();
								courseobj.setCname(cname);
								courseobj.setCredit(credit);
								fac.addCourse(courseobj);
							}
							session.createLoginSession("faculty", "chiraggarg31@gmail.com",fac);
						}
					else
					{
						stu.setUid(json.getString("uid"));
						stu.setUname(json.getString("name"));
						cou=json.getJSONArray("course");
						for(int i=0;i< cou.length();i++)
						{
							
							JSONObject c=cou.getJSONObject(i);
							String cname=c.getString("course");
							int credit = c.getInt("credit");
							Course courseobj=new Course();
							courseobj.setCname(cname);
							courseobj.setCredit(credit);
							stu.addCourse(courseobj);
						}
						session.createLoginSessionStudent("student", "chiraggarg31@gmail.com",stu);
					}
				}
				return json.getString(TAG_MESSAGE);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(final String result) {
			// TODO Auto-generated method stub
			pDialog.dismiss();
			if(result!=null)
			{
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(success==1)
						{
							//session.createLoginSession("Chirag", "chiraggarg31@gmail.com",fac);
							//session.createLoginSession("Chirag", "chiraggarg31@gmail.com");
							Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
							Intent i = new Intent(getApplicationContext(), Home.class);
	                        startActivity(i);
	                        finish();
						}
						else
							Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();

						
					}
				});
			}
			
			
		}
		
	}	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
	protected void dialogNoConnection()
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("No Internet Connection!");
        alertDialog.setMessage("Please connect to wifi or mobile data.");
        alertDialog.setIcon(R.drawable.nocon);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	finish();
            	Toast.makeText(getApplicationContext(), "Successfully loggedout!!", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
	            if(!haveNetworkConnection())
	            {
	            	dialogNoConnection();
	            }
            }
        });
        alertDialog.show();
	}
	private boolean haveNetworkConnection() {
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option1, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) { 
        case R.id.help:
        	help();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
		
	}
	private void help() {
		Intent i=new Intent(Login.this,HelpLogin.class);
		startActivity(i);
	}

}
