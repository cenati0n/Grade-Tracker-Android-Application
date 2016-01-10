package com.example.gradetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CourseListItem extends ArrayAdapter<String[]>{
	String values[][];
	Context context;
	public CourseListItem(Context context, String[][] values) {
		super(context, R.layout.courselistrow,values);
		this.context=context;
		this.values=values;
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflator=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView=inflator.inflate(R.layout.courselistrow, parent,false);
		TextView course=(TextView) rowView.findViewById(R.id.tvcourse);
		course.setText(values[position][0]);
		TextView credit=(TextView) rowView.findViewById(R.id.tvcredit);
		credit.setText(values[position][1]);
		return rowView;
	}
	

}
