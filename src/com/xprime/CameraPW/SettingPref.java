package com.xprime.CameraPW;

import com.xprime.OptionalService.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingPref extends PreferenceActivity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	
        addPreferencesFromResource(R.xml.preferences);
	}

}
