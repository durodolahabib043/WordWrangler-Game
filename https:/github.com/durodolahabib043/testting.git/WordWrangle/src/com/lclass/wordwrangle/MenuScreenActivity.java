package com.lclass.wordwrangle;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.vending.billing.IInAppBillingService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MenuScreenActivity extends Activity{

	private MediaPlayer sound;
	public LinearLayout adHolder;
	
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_menuscreen);
	    Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
	    serviceIntent.setPackage("com.android.vending");
	    bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
	    
	    sound = MediaPlayer.create(MenuScreenActivity.this, R.raw.letter_clicked); 
	    
	    adHolder = (LinearLayout) findViewById (R.id.adholder);
	    ImageButton jump = (ImageButton) findViewById(R.id.btn_start);
	    ImageButton start = (ImageButton) findViewById(R.id.imgbtn_sound);
	    ImageButton mute = (ImageButton) findViewById(R.id.imgbtn_mute);
	    ImageButton info = (ImageButton) findViewById(R.id.infobutton);
	    
	  
	    
	    start.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
					sound.start();
				
			}
		});
	    
	   mute.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if (sound.isPlaying()) {

                sound.pause();
            }
		}
	});
	    
	    jump.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<String> skuList = new ArrayList<String> ();
				skuList.add("extratime");
				Bundle querySkus = new Bundle();
				querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
				
				try {
					Bundle skuDetails = mService.getSkuDetails(3, getPackageName(), "inapp", querySkus);
					int response = skuDetails.getInt("RESPONSE_CODE");
					if (response == 0) {
					   ArrayList<String> responseList
					      = skuDetails.getStringArrayList("DETAILS_LIST");
					   
					   for (String thisResponse : responseList) {
					      JSONObject object = new JSONObject(thisResponse);
					      String sku = object.getString("productId");
					      String price = object.getString("price");
					      if (sku.equals("extratime")) {System.out.println("SKU is: " + sku);}
					      else if (sku.equals("extratime")) {System.out.println("PRICE is: " + price);}
					   }
					}
				
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	    

	 info.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),InfoClass.class);
				startActivity(i);
				
			}
		});
	    
	 }
	
	
	IInAppBillingService mService;

	ServiceConnection mServiceConn = new ServiceConnection() {
	   @Override
	   public void onServiceDisconnected(ComponentName name) {
	       mService = null;
	   }

	   @Override
	   public void onServiceConnected(ComponentName name, 
	      IBinder service) {
	       mService = IInAppBillingService.Stub.asInterface(service);
	   }
	};
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    if (mService != null) {
	        unbindService(mServiceConn);
	    }   
	}
}
