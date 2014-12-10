package com.lclass.wordwrangle;

import java.util.ArrayList;
import java.util.Random;

import com.lclass.common.Constants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameScreenDrawView extends View{

	Context mContext;
	public Paint mPaint;
	
	public Handler mHandler;
	
	DisplayMetrics mDisplayMetrics;
	Random mRandom;
	
	public int mScore;
	public int mDifficulty;
	
	String mTargetWord;
	int mMoves;
	private Typeface font;
	
	ArrayList<FallingLetter> mLetterList;
	String mTouchedString;
	
	int level;
	
	public GameScreenDrawView(Context context, DisplayMetrics dm, String targetWord, int level) {
		super(context);
		mContext = context;
		
		//get height and width of screen
		this.mDisplayMetrics = dm;
		this.level = level;
		mScore = 0;
		mTargetWord = targetWord;
		mMoves = mTargetWord.length()+2;
		mLetterList = new ArrayList<FallingLetter>();
		mTouchedString = "";
		
		//get random int
		mRandom = new Random();
		font = Typeface.createFromAsset(context.getAssets(), Constants.RUNNING_FONT); 
		
		Point p = new Point(dm.widthPixels/2,dm.heightPixels - 200);
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(70);
		mPaint.setTypeface(font);
		
		createLetters();
		
		//instantiate runnable
		mHandler = new Handler();
		updateRunnable.run();
		
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{		
		canvas.drawText("Moves : " +  mMoves, 0, 70, mPaint);
		canvas.drawText(String.format("Characters : %s", mTouchedString ), 0,180, mPaint);
		
		for (FallingLetter letter : mLetterList) {
			if( letter.getVisible() ) {
				canvas.drawBitmap(letter.image, letter.getLeft(), letter.getTop(), null);
			}
		}

		
		invalidate();
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		switch(action){
		case MotionEvent.ACTION_DOWN:
			FallingLetter touchedLetter = getTouchedLetter(x,y);
			if(touchedLetter != null)
			{
				
				MediaPlayer sounds;
				sounds=MediaPlayer.create(this.getContext(), R.raw.letter_clicked);
				sounds.start();
				
				mTouchedString += touchedLetter.getLetter().toLowerCase();
				Log.i("", mTouchedString + " string ");
				mMoves--;
				
				if( checkWord() )
				{
					//Game Over! Success
					SharedPreferences gameSettings = mContext.getSharedPreferences("com.lclass.wordwrangle", mContext.MODE_PRIVATE);
					Editor editor = gameSettings.edit();
					editor.putBoolean("success", true);
					editor.putInt("score", mMoves);
					editor.apply();
					
					((GameScreenActivity)mContext).finish();
					mContext.startActivity(new Intent(mContext, GameOverActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("LEVEL", level).putExtra("TARGET_WORD", mTargetWord));
					
				}
				else if( mMoves <= 0 )
				{
					//Game Over! Fail
					SharedPreferences gameSettings = mContext.getSharedPreferences("com.lclass.wordwrangle", mContext.MODE_PRIVATE);
					Editor editor = gameSettings.edit();
					editor.putBoolean("success", false);
					editor.putInt("score", mMoves);
					editor.apply();
					
					((GameScreenActivity)mContext).finish();
					mContext.startActivity(new Intent(mContext, GameOverActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				}
			}

			break;
		}
		invalidate();
		return true;
	}
	
	public FallingLetter getTouchedLetter(int x, int y)
	{
		for (FallingLetter letter : mLetterList) {
			
			if(letter.getLeft() < x && letter.getLeft() + letter.getWidth() > x && 
					letter.getTop() < y && letter.getTop() + letter.getHeight() > y &&
					letter.getVisible() ) {
				letter.setVisible(false);
				return letter;
				
			}
		}
		
		return null;
	}

	private void moveLetters() {
		for (FallingLetter letter : mLetterList) {

			letter.setTop(letter.getTop() + 10);
			
			if( letter.getTop() > mDisplayMetrics.heightPixels+letter.getHeight()+10 )
			{
				letter.resetletter();
				int iPosX = mRandom.nextInt(mDisplayMetrics.widthPixels-100);
				int iPosY = -150 - mRandom.nextInt(100) - letter.getHeight();
				
				if( null != getTouchedLetter(iPosX, iPosY) )
					continue;
				
				if( null != getTouchedLetter(iPosX+letter.getWidth(), iPosY+letter.getHeight()) )
					continue;
			
				letter.moveTo(iPosX, iPosY);
				letter.setVisible(true);
				
				
			}
			
		}
	}
	
	private void createLetters()
	{
		
		FallingLetter newLetter;
		for( int i = 0 ; i < 25 ; i++){
			newLetter = new FallingLetter(mContext, 
				new Point(mRandom.nextInt(mDisplayMetrics.widthPixels-100), (-150*i)), 
				mRandom.nextInt(26));
			
			mLetterList.add(newLetter);
		}
	}
	
	private boolean checkWord()
	{
		for (int i = 0; i < mTargetWord.length() ; i++) {
			char letter = mTargetWord.charAt(i);
			if( !mTouchedString.contains(letter + "") )
			{
				return false;				
			}
		}
		
		return true;
	}
	
	
	protected Runnable updateRunnable = new Runnable() {

		int mCount = 0;
		public void run() {

			mCount++;
	
			moveLetters();

			mHandler.postDelayed(updateRunnable, 10);
		}

	};
}
