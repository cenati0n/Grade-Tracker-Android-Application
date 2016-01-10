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
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class EnterMarks extends Activity{
	SessionManager session;
	JSONArray stdlist = null;
	//String values[]={"73","74"};
	String values[][];
	String marks[][];
	ListView lv;
	RadioGroup rg;
	RadioButton rb;
	EnterMarks em=this;
	EnterMarksItem emi;
	ArrayList<String> list=new ArrayList<String>();
	ArrayList<String> listmarks=new ArrayList<String>();
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static final String LOGIN_URL = "http://192.168.137.1/mgt/enter_marks.php";
	private static final String UPLOADMARKS_URL = "http://192.168.137.1/mgt/enter_marks_to_databse.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	Button btnSubmit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entermarks);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(06,138, 202)));
		bar.setTitle("Enter Marks");
		btnSubmit=(Button) findViewById(R.id.btnclcgrade);
		lv=(ListView)findViewById(R.id.lventermarks);
		lv.setItemsCanFocus(true);
		session=new SessionManager(getApplicationContext());	
		final String selCourse=session.getSessionCourse();
		//lv.setAdapter(new EnterMarksItem(this,values));
		rg=(RadioGroup) findViewById(R.id.rg);
//		int selectedId=rg.getCheckedRadioButtonId();
//		rb=(RadioButton) findViewById(selectedId);
//		while(rb==null)
//		{
//			rb=(RadioButton) findViewById(selectedId);
//			if(rb.getText().equals("Absolute"))
//			{
//				Intent i= new Intent(EnterMarks.this,EnterRange.class);
//				startActivity(i);
//			}
//		}
		new AttemptMarks(selCourse).execute();
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				rb=(RadioButton) findViewById(checkedId);
				if(rb.getText().equals("Absolute"))
				{
					Intent i= new Intent(EnterMarks.this,EnterRange.class);
					startActivity(i);
				}				
			}
		})
		;
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				marks=new String[list.size()][2];
				boolean flag=false;
				for(int i=0;i<list.size();i++)
				{
					marks[i][0]=emi.getRollNo(i);
					marks[i][1]=emi.getMarks(i);
					marks[i][0].trim();
					marks[i][1].trim();
					if(marks[i][0].equals("")||marks[i][1].equals(""))
					{
						flag=true;
						break;
					}	
					//Toast.makeText(getApplicationContext(),emi.getMarks(i), Toast.LENGTH_SHORT).show();
				}
				if(flag)
					confirmDialog();
				else
					new UploadMarks(selCourse).execute();
				
			}
		});
	}
	class AttemptMarks extends AsyncTask<String, String, String>
	{
		int l;
		int sidsp;
		String cName;
		int success=0;
		int gl=0;
		public AttemptMarks(String cName) {
			this.cName=cName;
			// TODO Auto-generated constructor stub
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog=new ProgressDialog(EnterMarks.this);
			pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
		}
		@Override
		protected String doInBackground(String... args) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				cName=cName.trim();
	        	params.add(new BasicNameValuePair("course", cName)); //course
	        	JSONObject json = jsonParser.makeHttpRequest(
	                     LOGIN_URL, "POST", params);
				try {
					success=json.getInt("success");
				if(success==1)
				{		
					stdlist=json.getJSONArray("uid");
						gl=stdlist.length();
						for(int i=0;i< stdlist.length();i++)
						{
							JSONObject c=stdlist.getJSONObject(i);
							String stduid=c.getString("uid");
							list.add(stduid);
							String strmrks=c.getString("marks");
							listmarks.add(strmrks);
						}
						l=list.size();
						values= new String[l][2];
						for(int i=0;i<l;i++)
						{
							values[i][0]=list.get(i);
							values[i][1]=listmarks.get(i);
						}
				}
				//return json.getString("xyz");
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
			/*if(result!=null)
			{*/
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						//Toast.makeText(getApplicationContext(),l, Toast.LENGTH_LONG).show();
						if(success==1)
						{
							emi=new EnterMarksItem(em,values);
							lv.setAdapter(emi);
						}
					else
						Toast.makeText(getApplicationContext(),"NOOOO", Toast.LENGTH_LONG).show();

						
					}
				});
			}
			
			
		//}
		
	}
	class UploadMarks extends AsyncTask<String, String, String>
	{
		int success=0;
		String cName;
		public UploadMarks(String cName) {
			this.cName=cName;
			// TODO Auto-generated constructor stub
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog=new ProgressDialog(EnterMarks.this);
			pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
		}
		@Override
		protected String doInBackground(String... args) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				String ttl=list.size()+"";
				for(int i=0;i<list.size();i++)
				{
					params.add(new BasicNameValuePair("uid"+i, marks[i][0]));
					params.add(new BasicNameValuePair("marks"+i, marks[i][1]));
				}
				params.add(new BasicNameValuePair("course", cName));
				params.add(new BasicNameValuePair("total",ttl));
	        	Log.d("request!", "starting");
	        	JSONObject json = jsonParser.makeHttpRequest(
	                     UPLOADMARKS_URL, "POST", params);
	        	try {
					success=json.getInt("success");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				try {
//					success=json.getInt("success");
//				}
//				catch (JSONException e) {
//					e.printStackTrace();
//				}
			return null;
		}
		@Override
		protected void onPostExecute(final String result) {
			// TODO Auto-generated method stub
			pDialog.dismiss();
			/*if(result!=null)
			{*/
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//Toast.makeText(getApplicationContext(),l, Toast.LENGTH_LONG).show();
						
							Intent i=new Intent(EnterMarks.this, ShowGrades.class);
							startActivity(i);
						
						//Toast.makeText(getApplicationContext(),"NOOOO1", Toast.LENGTH_LONG).show();
;
						
					}
				});
			}
			
			
		//}
		
	}
	protected void confirmDialog()
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Warning!");
        alertDialog.setMessage("Please fill all the fields.");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	dialog.cancel();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
        alertDialog.show();
	}

}
