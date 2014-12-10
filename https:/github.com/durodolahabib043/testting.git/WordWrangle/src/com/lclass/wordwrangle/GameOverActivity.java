package com.lclass.wordwrangle;

import com.lclass.common.Constants;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameOverActivity extends Activity implements OnClickListener{

	TextView mTxtScore;
	ImageButton mBtnRetry;
	ImageButton mBtnContinue;
	DatabaseHandler db;
	int level;
	String targetWord;
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_gameoverscreen);
	    
	    Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	level = extras.getInt("LEVEL");
        	targetWord = extras.getString("TARGET_WORD");
        }
	    
	    mTxtScore = (TextView) findViewById(R.id.txtScore);
	    mBtnRetry = (ImageButton) findViewById(R.id.btnRetry);
	    mBtnContinue = (ImageButton) findViewById(R.id.btnContinue);
	    
	    Typeface font = Typeface.createFromAsset(this.getAssets(), Constants.RUNNING_FONT); 
	    mTxtScore.setTypeface(font);
	    
	    SharedPreferences gameSettings = getSharedPreferences("com.lclass.wordwrangle", MODE_PRIVATE);
	    boolean bSuccess = gameSettings.getBoolean("success", false);
	    int iScore = gameSettings.getInt("score", 0);
	    
	    if( bSuccess )
	    {
	    	mTxtScore.setText("Level " + level + " Passed!");
	    	db = new DatabaseHandler(this);
				
				try{
					db.createDataBase();
					db.openDataBase();
				}catch(Exception e){
			 }
			 db.updateCompleted(level + 1);
			 db.close();
	    }
	    else
	    {
	    	mTxtScore.setText("Level Failed!");
	    }

	    mBtnRetry.setOnClickListener(this);
	    mBtnContinue.setOnClickListener(this);
	 }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if( v.getId() == R.id.btnRetry )
		{
			startActivity(new Intent(this, GameScreenActivity.class));
		}
		else if( v.getId() == R.id.btnContinue )
		{
			startActivity(new Intent(this, LevelScreenActivity.class).putExtra("LEVEL", level).putExtra("WORD", targetWord));
		    finish();
		}
	}
}
