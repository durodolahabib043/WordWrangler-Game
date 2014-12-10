package com.lclass.wordwrangle;

import com.lclass.common.Constants;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class InfoClass extends Activity {
	
	private Typeface font;
	TextView tx;
	
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.infolayout);
	     tx = (TextView) findViewById(R.id.scopetextview);
	 	 font = Typeface.createFromAsset(this.getAssets(), Constants.RUNNING_FONT);
	 	 tx.setTypeface(font);
	}

}
