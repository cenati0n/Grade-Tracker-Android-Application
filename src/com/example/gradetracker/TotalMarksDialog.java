package com.example.gradetracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class TotalMarksDialog extends Dialog implements android.view.View.OnClickListener{

	public Activity c;
	public Dialog d;
	public Button btnSubmit;

	public TotalMarksDialog(Activity a) {
	super(a);
	this.c = a;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.ttlmrksdialog);
	btnSubmit = (Button) findViewById(R.id.btnttlmrks);
	btnSubmit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
	    case R.id.btnttlmrks:
	    	dismiss();
			c.startActivity(new Intent(c, EnterMarks.class));
			break;
	    }
		
		
	}	
}