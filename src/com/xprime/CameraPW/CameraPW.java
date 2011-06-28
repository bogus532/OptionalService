package com.xprime.CameraPW;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Config;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class CameraPW extends Activity {

	private static final String TAG = "CameraPW";
	
	Button btnMeasure;

	private PreviewCamera mPreview;
	private DrawOnSurface mDraw;

	private SensorManager mSensorManager;
	private Sensor mSensor;
	protected PowerManager.WakeLock mWakeLock;
	
	private static final int EDIT_ID = Menu.FIRST + 2;

	float pitch = 0;
	float roll = 0;
	float heading = 0;
	float distance = 0;
	float MeasureHeight = 0;
	
	double height;
	
	boolean fix_distance = false;
	boolean fix_measure_height = false;

	private final SensorEventListener mListener = new SensorEventListener() {

		public void onSensorChanged(SensorEvent event) {
			if (Config.DEBUG)
				Log.d(TAG, "sensorChanged (" + event.values[0] + ", "
						+ event.values[1] + ", " + event.values[2] + ")");

			// azimuth,pitch,roll
			updateOrientation(event.values[2], event.values[1], event.values[0]);
			
			if(!fix_distance)
			{
				updateDistance(measureDistance());
			}
			else if(!fix_measure_height && fix_distance)
			{
				updateMeasureHeight(measureHeight());
			}
			//else if()
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//------------------------------------------------------------------
		//Sleep,Sensor 
		//------------------------------------------------------------------
		final PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
		this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, TAG);
		this.mWakeLock.acquire();

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

		//------------------------------------------------------------------
		//layout
		//------------------------------------------------------------------
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		
		//RelativeLayout relativelayout = new RelativeLayout(getApplicationContext());
		AbsoluteLayout absolutelayout = new AbsoluteLayout(this);
		
		btnMeasure = new Button(this);
		btnMeasure.setText("Fix Distance");
		btnMeasure.setOnClickListener(new OnClickListener() {
		                                                    
		@Override
		public void onClick(View v) {
			btnOperation();
		}
		});
		
		mPreview = new PreviewCamera(this);
		mDraw = new DrawOnSurface(this);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		//setContentView(mPreview);
		//addContentView(mDraw, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		absolutelayout.addView(mPreview);
		absolutelayout.addView(mDraw);
		absolutelayout.addView(btnMeasure);
		
		Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		
		AbsoluteLayout.LayoutParams ap = new AbsoluteLayout.LayoutParams( btnMeasure.getLayoutParams() );
				
		ap.x = width - 100;
		ap.y = height - 80;
		ap.width = 100;
		ap.height = 80;
		
		btnMeasure.setLayoutParams(ap);
		
		setContentView(absolutelayout);		

		updateOrientation(0, 0, 0);
	}
	
	@Override
    protected void onResume()
    {
        if (Config.DEBUG) Log.d(TAG, "onResume");
        super.onResume();

        mSensorManager.registerListener(mListener, mSensor,
                SensorManager.SENSOR_DELAY_GAME);
        
        getPref();
    }

    @Override
    protected void onStop()
    {
        if (Config.DEBUG) Log.d(TAG, "onStop");
        mSensorManager.unregisterListener(mListener);
        super.onStop();
        
        getPref();
    }
    /*
    @Override
    protected void onPause()
    {
        if (Config.DEBUG) Log.d(TAG, "onPause");
        mSensorManager.unregisterListener(mListener);
        super.onStop();
        
        getPref();
    }
    */
    @Override
    protected void onDestroy()
    {
    	mSensorManager.unregisterListener(mListener);
    	super.onDestroy();
    	
    	this.mWakeLock.release();
    }

	private void updateOrientation(float _roll, float _pitch, float _heading) {
		this.roll = _roll;
		this.pitch = _pitch;
		this.heading = _heading;
		
    	if(mDraw != null){
    		mDraw.setHeading(heading);
    		mDraw.setPitch(pitch);
    		mDraw.setRoll(roll);
    		mDraw.invalidate();
    	}   
		
	}

	private void updateDistance(float _distance) {
		this.distance = _distance;
		
		if(mDraw != null){
    		mDraw.setDistance(distance);
    		mDraw.setMeasureIndex(1);
     		mDraw.invalidate();
    	}   
		
	}
	
	private float measureDistance()
	{
		if(pitch <=180 && pitch >= 90)
			return 0;
		
		float mdistance =0 ;
		roll = 90-roll;
		double radian = roll*(Math.PI/180);
		Log.d(TAG,"roll :"+roll+" ,MATH.tan("+radian+") : "+Math.tan(radian));
		double result = height/Math.tan(radian);
		String str2 = Double.toString(result);
		mdistance = Float.valueOf(str2).floatValue();
 		
		if(mdistance < 0)
		{
			mdistance = 0;			
		}
		return mdistance;
	}
	
	private void updateMeasureHeight(float _MeasureHeight) {
		this.MeasureHeight = _MeasureHeight;
		
    	if(mDraw != null){
    		mDraw.setMeasureHeight(MeasureHeight);
    		mDraw.setMeasureIndex(2);
     		mDraw.invalidate();
    	}   
		
	}
	
	private float measureHeight()
	{
		if(pitch < 90 && pitch > 0)
			return 0;

		float mHeight =0 ;
		roll = 90 - roll;
		double radian = roll*(Math.PI/180);
		Log.d(TAG,"Height,roll :"+roll+" ,MATH.tan: "+Math.tan(radian));
		double result = this.distance*Math.tan(radian);
		Log.d(TAG,"result :"+result+", this.distance :"+this.distance);
		String str2 = Double.toString(result);
		mHeight = Float.valueOf(str2).floatValue();
		Log.d(TAG,"mHeight :"+mHeight);
		return mHeight+(float)height;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, EDIT_ID, Menu.NONE, "Setting")
			.setIcon(android.R.drawable.ic_menu_preferences);

		return (super.onCreateOptionsMenu(menu));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case EDIT_ID:
			//startActivity(new Intent(CameraPW.this, SettingHeight.class));
			startActivity(new Intent(CameraPW.this, SettingPref.class));
 
			return true;
		}

		return (super.onOptionsItemSelected(item));
	}
	
	public void getPref()
	{
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		String s=sharedPref.getString("default_height", "150");
		
		height = Double.valueOf(s).doubleValue()/100;
	}

	public void btnOperation()
	{
		if(fix_distance == false && fix_measure_height==false)
		{
			fix_distance = true;
			//fix_measure_height = true;
			btnMeasure.setText("Fix Height");
		}
		else if(fix_distance == true && fix_measure_height==false)
		{
			//fix_distance = false;
			fix_measure_height = true;
			btnMeasure.setText("Done");
		}
		else if(fix_distance == true && fix_measure_height==true)
		{
			fix_distance = false;
			fix_measure_height = false;
			btnMeasure.setText("Fix Distance");
			updateMeasureHeight(0);
		}
	}
	
	public void displayOutofrange()
	{
		Toast.makeText(this,
        		"Out of Range", 
        		Toast.LENGTH_SHORT).show(); 
	}
}

