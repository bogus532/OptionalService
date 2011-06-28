package com.xprime.FlashLight;

import com.xprime.OptionalService.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class FlashLight_Setting extends Activity implements OnCheckedChangeListener {
	private final String REDTINT = "REDTINT";
	CheckBox cb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flashlight_settingview);
		
		cb = (CheckBox) findViewById(R.id.cbRedTint);
		cb.setOnCheckedChangeListener(this);
		setCheckboxValue();
	}

	private void setCheckboxValue() {
		cb = (CheckBox) findViewById(R.id.cbRedTint);
		SharedPreferences settings = this.getSharedPreferences("flashlight", 0);
		cb.setChecked(settings.getBoolean(REDTINT, false));
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, Menu.FIRST, 0, "Save");
		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST:
			setTintSetting();
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void setTintSetting() {
		CheckBox cb = (CheckBox) findViewById(R.id.cbRedTint);
		setSetting(REDTINT, cb.isChecked());
	}

	private void setSetting(String name, boolean value) {
		SharedPreferences settings = getSharedPreferences("flashlight", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(name, value);
		editor.commit();
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
		setTintSetting();
		//finish();
	}

}
