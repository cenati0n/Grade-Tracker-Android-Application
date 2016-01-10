package com.example.gradetracker;

import android.content.Context;
import android.view.View.OnFocusChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EnterRangeRow extends ArrayAdapter<String>{
	String[] values;
	Context context;
	String[] upper;
	String[] lower;
	EditText edtUpper,edtLower;
	public EnterRangeRow(Context context, String[] values) {
		super(context,R.layout.enterrangerow, values);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.values=values;
		upper=new String[8];
		lower=new String[8];
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView=inflator.inflate(R.layout.enterrangerow, parent,false);
		TextView course=(TextView) rowView.findViewById(R.id.tvgrade);
		course.setText(values[position]);
		edtUpper=(EditText) rowView.findViewById(R.id.etup);
		edtLower=(EditText) rowView.findViewById(R.id.etlow);
		edtUpper.setTag(position);
		edtLower.setTag(position);
		edtUpper.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus){
                    final int posit = (Integer) v.getTag();
                    final EditText Caption = (EditText) v;
                   // Toast.makeText(getApplicationContext(),"upper+"+posit, Toast.LENGTH_SHORT).show();
	                upper[posit] = Caption.getText().toString();
				}
			}
		});
		edtLower.setOnFocusChangeListener(new OnFocusChangeListener() {
					
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						if (!hasFocus){
		                    final int posit = (Integer) v.getTag();
		                    final EditText Caption = (EditText) v;
		                //    Toast.makeText(getApplicationContext(),"upper+"+posit, Toast.LENGTH_SHORT).show();
			               lower[posit] = Caption.getText().toString();
			                 
						}
					}
				});
		return rowView;
	}
	public String getUpper(int pos)
	{
		return upper[pos];
	}
	public String getLower(int pos)
	{
		return lower[pos];
	}
}
