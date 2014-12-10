package com.lclass.wordwrangle;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.RelativeLayout;

public class GameScreenActivity extends Activity{
	
	RelativeLayout relativeLayout;
	GameScreenDrawView dv;
	private Handler mHandler;
	
	DisplayMetrics dm;
    String targetWord;
    int level;
	
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		//getActionBar().hide();
	     setContentView(R.layout.layout_gamescreen);
	     
	   //get length height of screen
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		
		 //pull routine number from bundle
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	targetWord = extras.getString("WORD");
        	level = extras.getInt("LEVEL");
        }
		
        System.out.println("The word is " + targetWord);
		dv = new GameScreenDrawView(this, dm, targetWord, level);
		relativeLayout = (RelativeLayout) findViewById(R.id.GameScreen_view_layout);
		relativeLayout.addView(dv);
		
		
		SharedPreferences gameSettings = getSharedPreferences("com.lclass.wordwrangle", MODE_PRIVATE);

		
		//begin counter thread
		mHandler = new Handler();
		this.updateRunnable.run();
	 }
	
protected Runnable updateRunnable = new Runnable(){
		
		public void run(){
		   
		    mHandler.postDelayed(updateRunnable, 50);

		}
		
	};
}
