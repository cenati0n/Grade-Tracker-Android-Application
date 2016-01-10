package com.example.gradetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	SessionManager session;
	final int TIMER_RUNTIME=20000;
	boolean mbActive;
	ProgressBar mProgressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		mProgressBar=(ProgressBar)findViewById(R.id.splash_progressBar);
		Thread timerThread= new Thread(){
			public void run() {
				mbActive=true;
				try{
					int waited=0;
					while(mbActive && (waited<TIMER_RUNTIME)){
						sleep(150);
						if(mbActive){
							waited+=1000;
							updateProgress(waited);
						}
					}
				}catch(InterruptedException e){}finally{
					onContinue();
				}
			}
		};
		timerThread.start();
	}
	public void updateProgress(final int timePassed){
		if(mProgressBar!=null){
			final int progress=mProgressBar.getMax()*timePassed/TIMER_RUNTIME;
			mProgressBar.setProgress(progress);
		}
	}
	
	public void onContinue(){
		Intent intent;
		session = new SessionManager(getApplicationContext());
		if(session.isLoggedIn())
			intent=new Intent(MainActivity.this, Home.class);
		else
			intent=new Intent(MainActivity.this,Login.class);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		startActivity(intent);
		finish();
	}

}
