package com.lclass.wordwrangle;

import java.util.ArrayList;

import com.lclass.actor.Level;
import com.lclass.common.Constants;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class LevelScreenActivity extends Activity implements OnClickListener{
	DatabaseHandler db;
	private ImageButton go;
	private ImageButton ib1;
	private ImageButton ib2;
	private ImageButton ib3;
	private ImageButton ib4;
	private ImageButton ib5;
	
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	private TextView tv5;
	
	private Typeface font;
	String	word ;
	TextView textView1;
	ArrayList<Level> levels;
	int selectedLevel;
	 
		@Override
		 protected void onCreate(Bundle savedInstanceState) {
			 super.onCreate(savedInstanceState);
		     setContentView(R.layout.android_levelscreen);
		    
		     levels = new ArrayList<Level>();
		     db = new DatabaseHandler(this);
				
				try{
					db.createDataBase();
					db.openDataBase();
				}catch(Exception e){
				
				}
				
			 levels = db.getLevels();
			 db.close();
			 word = "";
			 
			 font = Typeface.createFromAsset(this.getAssets(), Constants.RUNNING_FONT); 
				
			 textView1 = (TextView) findViewById(R.id.textView1);
			 go = (ImageButton) findViewById(R.id.button1);
			 ib1 = (ImageButton) findViewById(R.id.imageButton1);
			 ib2 = (ImageButton) findViewById(R.id.imageButton2);
			 ib3 = (ImageButton) findViewById(R.id.imageButton3);
			 ib4 = (ImageButton) findViewById(R.id.imageButton4);
			 ib5 = (ImageButton) findViewById(R.id.imageButton5);
			 
			 tv1 = (TextView) findViewById(R.id.textView2);
			 tv2 = (TextView) findViewById(R.id.textView3);
			 tv3 = (TextView) findViewById(R.id.textView4);
			 tv4 = (TextView) findViewById(R.id.textView5);
			 tv5 = (TextView) findViewById(R.id.textView6);

			 //set font
			 textView1.setTypeface(font);
			 tv1.setTypeface(font);
			 tv2.setTypeface(font);
			 tv3.setTypeface(font);
			 tv4.setTypeface(font);
			 tv5.setTypeface(font);
		//	 textView1.setText(word);
			 go.setOnClickListener(this);
			 ib1.setOnClickListener(this);
			 ib2.setOnClickListener(this);
			 ib3.setOnClickListener(this);
			 ib4.setOnClickListener(this);
			 ib5.setOnClickListener(this);
			 ib2.setClickable(true);
			 ib3.setClickable(true);
			 ib4.setClickable(true);
			 ib5.setClickable(true);
			 System.out.println("textView1"+textView1);
			//	db.close();
				
		 }
		
		@Override
		public void onClick(View v) {
			
			//Intent intent = null;
			int id = v.getId();
			switch (id) {
			case R.id.button1:
				Intent intent = new Intent(LevelScreenActivity.this, GameScreenActivity.class);
				if (word.equalsIgnoreCase("")){
					textView1.setText("No Level Selected");
				}
				else{
					intent.putExtra("WORD", word);
					intent.putExtra("LEVEL", selectedLevel);
					startActivity(intent);
				}
				break;
			case R.id.imageButton1:
				     word = levels.get(0).getWord();
					 textView1.setText("Word to Spell: "+ word);
					 selectedLevel = 1;
				break;
			case R.id.imageButton2:
				 if(levels.get(1).getEnabled() == 1){
					 word = levels.get(1).getWord();
					 textView1.setText("Word to Spell: "+ word);
					 selectedLevel = 2;
				 }
				 else{
					 textView1.setText("Level Locked");
					 word = "";
				 }
				 
				break;
			case R.id.imageButton3:
				if(levels.get(2).getEnabled() == 1){
					word = levels.get(2).getWord();
					textView1.setText("Word to Spell: "+ word);
					 selectedLevel = 3;
				}
				else
				{
					 textView1.setText("Level Locked");
					 word = "";
				}
				  break;
			case R.id.imageButton4:
				if(levels.get(3).getEnabled() == 1){
					word = levels.get(3).getWord();
					textView1.setText("Word to Spell: "+ word);
					 selectedLevel = 4;
				}
				else
				{
					 textView1.setText("Level Locked");
					 word = "";
				}
				break;
			case R.id.imageButton5:
				if(levels.get(4).getEnabled() == 1){
					word = levels.get(4).getWord();
					textView1.setText("Word to Spell: "+ word);
					selectedLevel = 5;
				}
				else
				{
					 textView1.setText("Level Locked");
					 word = "";
				}
				break;
				
			} 
			
		}
		}


