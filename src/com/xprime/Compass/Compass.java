package com.xprime.Compass;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;


public class Compass extends Activity { 

    private static final String TAG = "Compass";
   
    float pitch = 0;
	float roll = 0;
	float heading = 0;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    //private SampleView mView;
    CompassView mView;

    private final SensorEventListener mListener = new SensorEventListener() {
    	
        public void onSensorChanged(SensorEvent event) {
            if (Config.DEBUG) Log.d(TAG,
                    "sensorChanged (" + event.values[0] + ", " + event.values[1] + ", " + event.values[2] + ")");

           // azimuth,pitch,roll
            updateOrientation(event.values[2], event.values[1], event.values[0]);
        }
        
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    
  
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
 
        mView = new CompassView(this);
        setContentView(mView);
        updateOrientation(0,0,0);
        
    }

    @Override
    protected void onResume()
    {
        if (Config.DEBUG) Log.d(TAG, "onResume");
        super.onResume();

        mSensorManager.registerListener(mListener, mSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop()
    {
        if (Config.DEBUG) Log.d(TAG, "onStop");
        mSensorManager.unregisterListener(mListener);
        super.onStop();
    }
    
    @Override
    protected void onDestroy()
    {
    	mSensorManager.unregisterListener(mListener);
    	super.onDestroy();
    }
    
    private void updateOrientation(float _roll, float _pitch, float _heading){
    	this.roll = _roll;
    	this.pitch = _pitch;
    	this.heading = _heading;
    	
    	if(mView != null){
    		mView.setBearing(heading);
    		mView.setPitch(pitch);
    		mView.setRoll(roll);
    		mView.invalidate();
    	}   
    }
 
}