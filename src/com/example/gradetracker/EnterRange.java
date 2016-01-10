package com.example.gradetracker;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.content.Intent;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.ActionBar;
import android.app.Activity;
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
import android.widget.Toast;

public class EnterRange extends Activity{
	String values[]={"  A   ","  AB  ","  B   ","  BC  ","  C  ","  CD  ","  D   ","  F   "};
	String range[][];
	ListView lv;
	Intent in;
	Button btnenterrange;
	EnterRangeRow enterrange;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static final String UPLOADRANGE_URL = "http://192.168.137.1/mgt/grading.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	SessionManager session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enterrange);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(06,138, 202)));
		bar.setTitle("Enter Range");
		lv=(ListView)findViewById(R.id.lventerrange);
		enterrange=new EnterRangeRow(this,values);
		lv.setAdapter(enterrange);
		lv.setItemsCanFocus(true);
		session=new SessionManager(getApplicationContext());	
		final String selCourse=session.getSessionCourse();
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(android.widget.AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				switch(arg2){
				case 1:
					Toast.makeText(EnterRange.this,values[arg2], Toast.LENGTH_SHORT).show();
				}
			};
		}) ;
		range=new String[8][2];
		btnenterrange=(Button) findViewById(R.id.btenterrange);
		btnenterrange.setOnClickListener(new OnClickListener() {
			boolean flag=false;
			@Override
			public void onClick(View v) {
				for(int i=0;i<8;i++)
				{
					range[i][0]=enterrange.getLower(i);
					range[i][1]=enterrange.getUpper(i);
					if(range[i][0].equals("")||range[i][1].equals(""))
					{
						flag=true;
						break;
					}
				}
				if(flag)
					confirmDialog();
				else
					new UploadRange(selCourse).execute();
			}
		});
	}
	
	class UploadRange extends AsyncTask<String, String, String>
	{
		int success=0;
		String cName;
		public UploadRange(String cName) {
			this.cName=cName;
			// TODO Auto-generated constructor stub
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog=new ProgressDialog(EnterRange.this);
			pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
		}
		@Override
		protected String doInBackground(String... args) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for(int i=0;i<8;i++)
				{
					params.add(new BasicNameValuePair("l"+i, range[i][0]));
					params.add(new BasicNameValuePair("u"+i, range[i][1]));
				}
				params.add(new BasicNameValuePair("course", cName));
				params.add(new BasicNameValuePair("type","Absolute"));
	        	Log.d("request!", "starting");
	        	JSONObject json = jsonParser.makeHttpRequest(
	                     UPLOADRANGE_URL, "POST", params);
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
						//Toast.makeText(getApplicationContext(),l, Toast.LENGTH_LONG).show()
						//Toast.makeText(getApplicationContext(),range[1][0]+" "+range[1][1], Toast.LENGTH_LONG).show();
						in=new Intent(EnterRange.this, EnterMarks.class);
						startActivity(in);
						
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
