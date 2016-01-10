package com.example.gradetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class StudentShowGradesItem extends ArrayAdapter<String[]>{

	String[][] values;
	Context context;
	public StudentShowGradesItem(Context context,String[][] values) {
		super(context, R.layout.studentshowgradesitem,values);
		this.context=context;
		this.values=values;
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflator=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView=inflator.inflate(R.layout.studentshowgradesitem, parent,false);
		TextView roll=(TextView) rowView.findViewById(R.id.tvrollnos);
		roll.setText(values[position][0]);
		TextView marks=(TextView) rowView.findViewById(R.id.tvmarkss);
		marks.setText(values[position][1]);
		TextView grade=(TextView) rowView.findViewById(R.id.tvgradess);
		grade.setText(values[position][2]);
		return rowView;
	}
}
