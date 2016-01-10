package com.example.gradetracker;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class EnterMarksItem extends ArrayAdapter<String[]>{
	String[][] values;
	Context context;
	EditText etmarks;
	String[] mrks;
	public EnterMarksItem(Context context, String[][] values) {
		super(context, R.layout.entermarksrow,values);
		this.context=context;
		this.values=values;
		mrks=new String[values.length+1];
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflator=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView=inflator.inflate(R.layout.entermarksrow, parent,false);
		TextView course=(TextView) rowView.findViewById(R.id.tvrollno);
		course.setText(values[position][0]);
		etmarks=(EditText) rowView.findViewById(R.id.etmarks);
		etmarks.setTag(position);
		etmarks.setText(values[position][1]);
		etmarks.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus){
                    final int posit = (Integer) v.getTag();
                    final EditText Caption = (EditText) v;
	                mrks[posit] = Caption.getText().toString();
	                 
                }
			}
		});
		
		return rowView;
	}
	public String getRollNo(int pos)
	{
		return values[pos][0];
	}
	public String getMarks(int pos)
	{
		return mrks[pos];
	}
}
