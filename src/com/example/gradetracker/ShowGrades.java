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
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class ShowGrades extends Activity{
	SessionManager session;
	JSONArray stdlist = null;
	ListView lv;
	String values[][];
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static final String SHOWGRADES_URL = "http://192.168.137.1/mgt/showgrades.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	ArrayList<String> list=new ArrayList<String>();
	ArrayList<String> listmarks=new ArrayList<String>();
	ArrayList<String> listgrades=new ArrayList<String>();	
	ShowGrades sg=this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showgrades);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(06,138, 202)));
		bar.setTitle("Grades");
		lv=(ListView) findViewById(R.id.lvshowGrades);
		session=new SessionManager(getApplicationContext());	
		final String selCourse=session.getSessionCourse();
		new GetGrades(selCourse).execute();
	}
	class GetGrades extends AsyncTask<String, String, String>
	{
		int success=0;
		String cName;
		public GetGrades(String cName) {
			this.cName=cName;
			// TODO Auto-generated constructor stub
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog=new ProgressDialog(ShowGrades.this);
			pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
		}
		@Override
		protected String doInBackground(String... args) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("course", cName));
	        	JSONObject json = jsonParser.makeHttpRequest(
	                     SHOWGRADES_URL, "POST", params);
	        	try {
					success=json.getInt("success");
					if(success==1)
					{		
						stdlist=json.getJSONArray("list");
						for(int i=0;i< stdlist.length();i++)
						{
							JSONObject c=stdlist.getJSONObject(i);
							String stduid=c.getString("uid");
							list.add(stduid);
							String strmrks=c.getString("marks");
							listmarks.add(strmrks);
							String strgrdes=c.getString("grade");
							listgrades.add(strgrdes);
						}
						int l=list.size();
						values= new String[l][3];
						for(int i=0;i<l;i++)
						{
							values[i][0]=list.get(i);
							values[i][1]=listmarks.get(i);
							values[i][2]=listgrades.get(i);
						}
					}
	        	}
	        	catch (JSONException e) {
					e.printStackTrace();
				}
//	        	
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
						lv.setAdapter(new ShowGradesItem(sg,values));
						//Toast.makeText(getApplicationContext(),values[0][0]+" "+values[0][1]+" "+values[0][2], Toast.LENGTH_LONG).show();
;
						
					}
				});
			}
			
			
		//}
		
	}

}
