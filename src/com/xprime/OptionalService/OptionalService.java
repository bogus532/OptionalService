package com.xprime.OptionalService;

import com.xprime.CameraPW.CameraPW;
import com.xprime.Compass.Compass;
import com.xprime.FlashLight.FlashLight;
import com.xprime.unitconvert.unitconvert;
import com.xprime.iCalc.iCalc;
import com.xprime.SMSSending.SMSSending;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class OptionalService extends Activity implements OnClickListener{
	Intent intent;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        View btnflashlight = findViewById(R.id.flashlight_button);
        View btncompass = findViewById(R.id.compass_button);
        View btnmeasure = findViewById(R.id.measure_button);
        View btnunitconvert = findViewById(R.id.unitconvert_button);
        View btnicalc = findViewById(R.id.icalc_button);
        View btnsmssending = findViewById(R.id.smssending_button);
        
        btnflashlight.setOnClickListener(this);
        btncompass.setOnClickListener(this);
        btnmeasure.setOnClickListener(this);
        btnunitconvert.setOnClickListener(this);
        btnicalc.setOnClickListener(this);
        btnsmssending.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.compass_button:
			intent = new Intent(OptionalService.this, Compass.class); 
			startActivity(intent);
			break;
		case R.id.flashlight_button:
			intent = new Intent(OptionalService.this, FlashLight.class); 
			startActivity(intent);
			break;
		case R.id.measure_button:
			intent = new Intent(OptionalService.this, CameraPW.class); 
			startActivity(intent);
			break;
		case R.id.icalc_button:
			intent = new Intent(OptionalService.this, iCalc.class); 
			startActivity(intent);
			break;
		case R.id.unitconvert_button:
			intent = new Intent(OptionalService.this, unitconvert.class); 
			startActivity(intent);
			break;
			
		case R.id.smssending_button:
			intent = new Intent(OptionalService.this, SMSSending.class); 
			startActivity(intent);
			break;
		}
		
	}
    
    
}