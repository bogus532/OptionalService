package com.xprime.FlashLight;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class FlashLight extends Activity {
	FlashLightView mView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	
		mView = new FlashLightView(this);
		setContentView(mView);
     }
    
    @Override
	public void onResume() {
		super.onResume();
		int colorindex;
		Color color = null;
		setBrightness(1f);
		
		if (useRedBackground())
			colorindex = color.rgb(255, 0, 0);
		else
			colorindex = color.rgb(255, 255, 255);;
		
		if(mView != null)
		{
			mView.setColor(colorindex);
		}
		
	}

	private boolean useRedBackground() {
		SharedPreferences settings = this.getSharedPreferences("flashlight", 0);
		return settings.getBoolean("REDTINT", false);
	}

	private void setBrightness(float brightness) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.screenBrightness = brightness;
		getWindow().setAttributes(lp);
	}

	@Override
	public void onPause() {
		super.onPause();
		setBrightness(-1f);
	}
	
	@Override
    protected void onDestroy()
    {
    	super.onDestroy();
    	this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);    	
    }

	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, Menu.FIRST, 0, "Settings");
		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST:
			Intent startSettings = new Intent(this, FlashLight_Setting.class);
			startActivity(startSettings);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Intent startSettings = new Intent(this, FlashLight_Setting.class);
			startActivity(startSettings);
			return true;
		}
		return false;
	}
}