package com.xprime.unitconvert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import com.xprime.OptionalService.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class unitconvert extends Activity {
	// Instance variables

	// ARRAY of UNITS
	/*
	 * Key: 0 1 2 3 4 5 Value: | Name | Abbreviation | Unit Type | Base Unit |
	 * Conversion Factor | Special |
	 */
	String[][] units = {
			{ "kilometer", "km", "length", "meter", "0.001", "null" },
			{ "meter", "m", "length", "meter", "1", "null" },
			{ "decimeter", "dm", "length", "meter", "10", "null" },
			{ "centimeter", "cm", "length", "meter", "100", "null" },
			{ "millimeter", "mm", "length", "meter", "1000", "null" },
			{ "micrometer", "micron", "length", "meter", "1000000", "null" },
			{ "nanometer", "nm", "length", "meter", "1000000000", "null" },
			{ "mile", "mi", "length", "meter", "0.000621371", "null" },
			{ "yard", "yd", "length", "meter", "1.0936133", "null" },
			{ "foot", "ft", "length", "meter", "3.2808399", "null" },
			{ "inch", "in", "length", "meter", "39.3700787", "null" },
			{ "kiloton", "kt", "mass", "kilogram", "0.000001", "null" },
			{ "ton", "t", "mass", "kilogram", "0.001", "null" },
			{ "kilogram", "kg", "mass", "kilogram", "1", "null" },
			{ "gram", "g", "mass", "kilogram", "1000", "null" },
			{ "centigram", "cg", "mass", "kilogram", "100000", "null" },
			{ "milligram", "mg", "mass", "kilogram", "1000000", "null" },
			{ "microgram", "mcg", "mass", "kilogram", "1000000000", "null" },
			{ "stone", "st", "mass", "kilogram", "0.157473044", "null" },
			{ "pound", "lb", "mass", "kilogram", "2.20462262", "null" },
			{ "ounce", "oz", "mass", "kilogram", "35.2739619", "null" },
			{ "dram", "dr", "mass", "kilogram", "564.383391", "null" },
			{ "cubic meter", "m^3", "volume", "liter", "0.001", "null" },
			{ "cubic decimeter", "dm^3", "volume", "liter", "1", "null" },
			{ "cubic centimeter", "cc", "volume", "liter", "1000", "null" },
			{ "cubic millimeter", "mm^3", "volume", "liter", "1000000", "null" },
			{ "hectoliter", "hl", "volume", "liter", "0.01", "null" },
			{ "decaliter", "dkl", "volume", "liter", "0.1", "null" },
			{ "liter", "l", "volume", "liter", "1", "null" },
			{ "decileter", "dl", "volume", "liter", "10", "null" },
			{ "centileter", "cl", "volume", "liter", "100", "null" },
			{ "millileter", "ml", "volume", "liter", "1000", "null" },
			{ "microleter", "�l", "volume", "liter", "1000000", "null" },
			{ "gallon", "gal", "volume", "liter", "0.264172052", "null" },
			{ "quart", "qt", "volume", "liter", "1.05668821", "null" },
			{ "pint", "pt", "volume", "liter", "2.11337642", "null" },
			{ "fluid ounce", "oz", "volume", "liter", "33.8140227", "null" },
			{ "year", "yr", "time", "hour", "0.0001144688", "null" },
			{ "week", "wk", "time", "hour", "0.00595238", "null" },
			{ "day", "d", "time", "hour", "0.04166666", "null" },
			{ "hour", "hr", "time", "hour", "1", "null" },
			{ "minute", "min", "time", "hour", "60", "null" },
			{ "second", "sec", "time", "hour", "3600", "null" },
			/*
			 * {"exabit","Ebit","data","gigabyte","7.45058E-09","null"},
			 * {"petabit","Pbit","data","gigabyte","7.6294E-06","null"},
			 * {"terabit","Tbit","data","gigabyte","0.0078125","null"},
			 * {"gigabit","Gbit","data","gigabyte","8","null"},
			 * {"megabit","Mbit","data","gigabyte","8192","null"},
			 * {"kilobit","Kbit","data","gigabyte","8.39E+06","null"},
			 * {"bit","bit","data","gigabyte","8589935000","null"},
			 * {"exbibyte","Eb","data","gigabyte","9.31323E-10","null"},
			 * {"petabyte","Pb","data","gigabyte","0.000000954","null"},
			 * {"terabyte","Tb","data","gigabyte","0.000976563","null"},
			 * {"gigabyte","Gb","data","gigabyte","1","null"},
			 * {"megabyte","Mb","data","gigabyte","1024","null"},
			 * {"kilobyte","Kb","data","gigabyte","1048576","null"},
			 * {"byte","b","data","gigabyte","1073742000","null"},
			 */
			// {"degrees","deg","angles","radians","57.3","null"},
			// {"radians","rad","angles","radians","1","null"},
			{ "hectare", "ha", "area", "square meter", "0.0001", "null" },
			{ "decare", "da", "area", "square meter", "0.001", "null" },
			{ "acre", "a", "area", "square meter", "0.000247105381", "null" },
			{ "square kilometer", "km^2", "area", "square meter", "0.000001",
					"null" },
			{ "square meter", "m^2", "area", "square meter", "1", "null" },
			{ "square centimeter", "cm^2", "area", "square meter", "10000",
					"null" },
			{ "square millimeter", "mm^2", "area", "square meter", "1000000",
					"null" },
			{ "acre", "ac", "area", "square meter", "0.000247105381", "null" },
			{ "square yard", "yd^2", "area", "square meter", "1.19599005",
					"null" },
			{ "square foot", "ft^2", "area", "square meter", "10.7639104",
					"null" },
			{ "square inch", "in^2", "area", "square meter", "1550.0031",
					"null" },
			{ "kilogram", "kg", "kitchen", "cup", "0.24", "null" },
			{ "gram", "g", "kitchen", "cup", "240", "null" },
			{ "liter", "l", "kitchen", "cup", "0.24", "null" },
			{ "millileter", "ml", "kitchen", "cup", "240", "null" },
			{ "cup", "c", "kitchen", "cup", "1", "null" },
			{ "tablespoon", "tbsp", "kitchen", "cup", "16", "null" },
			{ "teaspoon", "tsp", "kitchen", "cup", "48", "null" },
			{ "pound", "lb", "kitchen", "cup", "0.5216", "null" },
			{ "ounce", "oz", "kitchen", "cup", "8.345", "null" },
			{ "pint", "pt", "kitchen", "cup", "0.5", "null" },
			{ "fluid ounce", "fl oz", "kitchen", "cup", "8", "null" },
			{ "cup", "c", "kitchen", "cup", "1", "null" },
			{ "tablespoon", "tbsp", "kitchen", "cup", "16", "null" },
			{ "teaspoon", "tsp", "kitchen", "cup", "48", "null" },
			{ "gallon", "g", "kitchen", "cup", "0.0625", "null" },
			/*
			 * {"Joule", "J", "Energy", "joule", "1", "null"}, {"Calorie",
			 * "cal", "Energy", "joule", "0.000239005736", "null"},
			 * {"Kilojoule", "Kj", "Energy", "joule", ".001", "null"},
			 */
			{ "Farenheit", "F", "Temperature", "Kelvin", "null", "temp" },
			{ "Celcius", "C", "Temperature", "Kelvin", "null", "temp" },
			{ "Kelvin", "K", "Temperature", "Kelvin", "null", "temp" },
			{ "Atmosphere", "atm", "Pressure", "Pascal", "0.00000986923266716",
					"null" },
			{ "Bar", "b", "Pressure", "Pascal", "0.00001", "null" },
			{ "Hectopascal", "hPa", "Pressure", "Pascal", "0.01", "null" },
			{ "Kilogram per Square Centimeter", "kg / sq. cm", "Pressure",
					"Pascal", "0.00001019716212978", "null" },
			{ "Kilogram per Square Meter", "kg / sq. m", "Pressure", "Pascal",
					"0.1019716212978", "null" },
			{ "Kilopascal", "kPa", "Pressure", "Pascal", "0.001", "null" },
			{ "Millibar", "mbar", "Pressure", "Pascal", "0.01", "null" },
			{ "Millimeter Mercury", "mmHg", "Pressure", "Pascal",
					"0.007500616827042", "null" },
			{ "Pascal", "Pascal", "Pressure", "Pascal", "1", "null" },
			{ "Pounds per Square Foot", "psf", "Pressure", "Pascal",
					"0.02088545632547", "null" },
			{ "Pounds per Square Inch", "psi", "Pressure", "Pascal",
					"0.0001450378911491", "null" },
			{ "Torr", "torr", "Pressure", "Pascal", "0.007500616827042", "null" } };

	// Screen element instance variables
	Spinner convert_from;
	Spinner convert_to;
	Spinner Unit_choose;
	EditText from_quantity;
	EditText to_quantity;
	TextView from_text;
	TextView to_text;
	TextView Status;
	Button convert;

	// Program-wide Variables
	ArrayList<String> Unit_types = new ArrayList<String>();
	ArrayList<String> From_Units = new ArrayList<String>();
	ArrayList<String> To_Units = new ArrayList<String>();

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Preserve text fields
		from_quantity.setText(from_quantity.getText().toString());
		to_quantity.setText(to_quantity.getText().toString());

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.unitconvert);

		// Set up Screen elements
		convert_from = (Spinner) this.findViewById(R.id.From_units);
		convert_to = (Spinner) this.findViewById(R.id.To_Units);
		from_quantity = (EditText) this.findViewById(R.id.From_quantity);
		to_quantity = (EditText) this.findViewById(R.id.To_quantity);
		from_text = (TextView) this.findViewById(R.id.From_Unit_text);
		to_text = (TextView) this.findViewById(R.id.To_unit_text);
		Status = (TextView) this.findViewById(R.id.Status);
		Unit_choose = (Spinner) this.findViewById(R.id.Unit_Type);

		// Set initial Screen Text
		from_quantity.setText("");
		to_quantity.setText("");
		from_text.setText("Convert From");
		to_text.setText("Convert To");
		// Set Spinners
		set_Units_Choose_Spinner();
		from_quantity.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				/*
				try {
					if (!(Double.isNaN(Double.valueOf(from_quantity.getText()
							.toString())))) {
						convert(from_quantity.getText().toString(), "to");
					}
				} catch (NumberFormatException e) {
					Log.d("error", e.toString());
				}
				*/
				convert2(from_quantity.getText().toString(), "to");
				return false;
			}
		});

		from_quantity.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				// Clear Quantities
				from_quantity.setText("");
				to_quantity.setText("");
				return false;
			}

		});
		to_quantity.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				/*
				try {
					if (!(Double.isNaN(Double.valueOf(to_quantity.getText()
							.toString())))) {
						convert(to_quantity.getText().toString(), "from");
					}
				} catch (NumberFormatException e) {
					Log.d("error", e.toString());
				}
				*/
				convert2(to_quantity.getText().toString(), "from");
				return false;
			}
		});

		to_quantity.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				// Clear Quantities
				from_quantity.setText("");
				to_quantity.setText("");
				return false;
			}

		});

		// check if there are saved values

	}
	
	private void convert2(String from,String direction)
	{
		try 
		{
			if (!(Double.isNaN(Double.valueOf(from)))) 
			{
				convert(from, direction);
			}
		} 
		catch (NumberFormatException e) 
		{
			Log.d("error", e.toString());
		}
	}

	private void set_Convert_From_Spinner(final String discriminator) {
		// Log.d("Seed (CONVERT_FROM", discriminator);
		From_Units = new ArrayList<String>();
		for (int i = 0; i < units.length; i++) {
			if (discriminator.equalsIgnoreCase("All Units")) {
				// Log.d("Adding Unit (Convert From)", units[i][2]);
				From_Units.add(units[i][0]);
			} else {
				if (units[i][2].equalsIgnoreCase(discriminator)) {
					// Log.d("Adding Unit (Convert From)", units[i][2]);
					From_Units.add(units[i][0]);
				}
			}
		}
		// ArrayAdapter<String> Convert_From_Adapter = new
		// ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
		// From_Units);
		ArrayAdapter<String> Convert_From_Adapter = new ArrayAdapter<String>(
				this, R.layout.spinner_style, From_Units);
		Convert_From_Adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		convert_from.setAdapter(Convert_From_Adapter);

		// Add listener
		convert_from.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// setup the convert from spinner
				Log.d("test",
						Integer.toString(lookup(From_Units.get(position))));
				set_Convert_To_Spinner(units[lookup(From_Units.get(position))][2]);
				// set text proper
				if (!from_text.getText().equals("Convert From")) {
					from_text.setText(From_Units.get(position));
				}
				from_text.setText(From_Units.get(position));
				convert2(from_quantity.getText().toString(), "to");
				//clear();
				// from_quantity.setText("");
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// Do Nothing

			}

		});
	}

	private void set_Convert_To_Spinner(String discriminator) {
		// Log.d("Seed(CONVERT TO", discriminator);
		// Generate ArrayList
		To_Units = new ArrayList<String>();
		for (int i = 0; i < units.length; i++)
			if (units[i][2].equalsIgnoreCase(discriminator)) {

				// Log.d("Adding Unit (Convert TO)", units[i][0]);
				To_Units.add(units[i][0]);
			}
		// Populate Spinner based on ArrayList
		// ArrayAdapter<String> Convert_To_Adapter = new
		// ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
		// To_Units);
		ArrayAdapter<String> Convert_To_Adapter = new ArrayAdapter<String>(
				this, R.layout.spinner_style, To_Units);
		Convert_To_Adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		convert_to.setAdapter(Convert_To_Adapter);
		convert_to.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// Make conversion
				// Log.d("SEED....", from_quantity.getText().toString());
				// Set Text
				to_text.setText(To_Units.get(arg2));
				//clear();
				convert2(to_quantity.getText().toString(), "from");
				// to_quantity.setText("");
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// DO NOTHING

			}

		});
	}

	private void set_Units_Choose_Spinner() {
		Unit_types.clear();
		Unit_types.add("All Units");
		for (int j = 0; j < units.length; j++) {
			if (!Unit_types.contains(units[j][2])) {
				// Log.d("Adding Unit (UNITS CHOOSE)", units[j][2]);
				Unit_types.add(units[j][2]);
			}
		}

		Unit_choose.setPrompt("Select");
		// ArrayAdapter<String> Unit_Types_Adapter = new
		// ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
		// Unit_types);
		ArrayAdapter<String> Unit_Types_Adapter = new ArrayAdapter<String>(
				this, R.layout.spinner_style, Unit_types);
		Unit_Types_Adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Unit_choose.setAdapter(Unit_Types_Adapter);

		Unit_choose.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// Setup to the convert from spinner based on selection
				set_Convert_From_Spinner(Unit_types.get(position));
				clear();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// DO NOTHING
			}
		});

	}

	public void convert(String from, String direction) {
		if (units[lookup(convert_from.getSelectedItem().toString())][5]
				.equals("null")) {
			try {
				BigDecimal value = new BigDecimal(from);
				// Find what we have to divide by to get to base unit
				BigDecimal conversion_to_base_rate;
				if (direction.equalsIgnoreCase("to")) {
					conversion_to_base_rate = new BigDecimal(
							units[lookup(convert_from.getSelectedItem()
									.toString())][4]);
				} else {
					conversion_to_base_rate = new BigDecimal(
							units[lookup(convert_to.getSelectedItem()
									.toString())][4]);
				}

				// Divide by conversion rate
				value = value.divide(conversion_to_base_rate, 20,
						RoundingMode.HALF_UP);
				// Get covert to rate
				if (direction.equalsIgnoreCase("to")) {
					conversion_to_base_rate = new BigDecimal(
							units[lookup(convert_to.getSelectedItem()
									.toString())][4]);
				} else {
					conversion_to_base_rate = new BigDecimal(
							units[lookup(convert_from.getSelectedItem()
									.toString())][4]);
				}

				// Multiply by reate
				value = value.multiply(conversion_to_base_rate)
						.stripTrailingZeros();
				if (convert_from.getSelectedItem().equals(
						convert_to.getSelectedItem())) {
					value = new BigDecimal(from);
				}
				if (direction.equalsIgnoreCase("to")) {
					to_quantity.setText(value.toString());
				} else {
					from_quantity.setText(value.toString());
				}

			} catch (ArithmeticException e) {
				Log.d("ERROR", e.toString());
			} catch (NumberFormatException e) {
				Log.d("error", e.toString());
			}
		}
		if (units[lookup(convert_from.getSelectedItem().toString())][5]
				.equals("temp")) {
			String Kelvin = null;
			if (convert_from.getSelectedItem().equals(
					convert_to.getSelectedItem())) {
				if (direction.equalsIgnoreCase("to")) {
					to_quantity.setText(from_quantity.getText().toString());
				} else {
					from_quantity.setText(to_quantity.getText().toString());
				}
			} else {
				// Convert to Kelvin
				if (direction.equals("to")) {
					if (convert_from.getSelectedItem().toString()
							.equals("Farenheit")) {
						Kelvin = FtoK(Double.valueOf(from_quantity.getText()
								.toString()));
					}
					if (convert_from.getSelectedItem().toString()
							.equals("Celcius")) {
						Kelvin = CtoK(Double.valueOf(from_quantity.getText()
								.toString()));
					}
					if (convert_from.getSelectedItem().toString()
							.equals("Kelvin")) {
						Kelvin = from_quantity.getText().toString();
					}
				} else {
					if (convert_to.getSelectedItem().toString()
							.equals("Farenheit")) {
						Kelvin = FtoK(Double.valueOf(to_quantity.getText()
								.toString()));
					}
					if (convert_to.getSelectedItem().toString()
							.equals("Celcius")) {
						Kelvin = CtoK(Double.valueOf(to_quantity.getText()
								.toString()));
					}
					if (convert_to.getSelectedItem().toString()
							.equals("Kelvin")) {
						Kelvin = to_quantity.getText().toString();
					}
				}
				Log.d("Kelvin", Kelvin);
				// Go from kelvin to unit wanted
				if (direction.equals("to")) {
					if (convert_to.getSelectedItem().toString()
							.equals("Farenheit")) {
						to_quantity.setText(KtoF(Double.valueOf(Kelvin)));
					}
					if (convert_to.getSelectedItem().toString()
							.equals("Celcius")) {
						to_quantity.setText(KtoC(Double.valueOf(Kelvin)));
					}
					if (convert_to.getSelectedItem().toString()
							.equals("Kelvin")) {
						to_quantity.setText(Kelvin);
					}
				} else {
					if (convert_from.getSelectedItem().toString()
							.equals("Farenheit")) {
						from_quantity.setText(KtoF(Double.valueOf(Kelvin)));
					}
					if (convert_from.getSelectedItem().toString()
							.equals("Celcius")) {
						from_quantity.setText(KtoC(Double.valueOf(Kelvin)));
					}
					if (convert_from.getSelectedItem().toString()
							.equals("Kelvin")) {
						from_quantity.setText(Kelvin);
					}
				}
			}
		}
	}

	public int lookup(String term) {
		// Get Type of unit
		String Type = (String) Unit_choose.getItemAtPosition(Unit_choose
				.getSelectedItemPosition());
		if (Type.equalsIgnoreCase("All Units")) {
			for (int i = 0; i < units.length; i++) {
				if (units[i][0].equalsIgnoreCase(term)) {
					return i;
				}
			}
		} else {
			for (int i = 0; i < units.length; i++) {
				if (units[i][0].equalsIgnoreCase(term)
						&& units[i][2].equalsIgnoreCase(Type)) {
					return i;
				}
			}
		}
		return -1;
	}

	private void clear() {
		from_quantity.setText("");
		to_quantity.setText("");
	}

	// Temperature Conversion Functions
	private String FtoC(Double F_temp) {
		return Double.toString((((F_temp - 32) / (9)) * 5));
	}

	private String FtoK(Double F_temp) {
		return Double.toString((Double.parseDouble((FtoC(F_temp)))) + 273.15);
	}

	private String CtoF(Double C_temp) {
		return Double.toString(((((Double) (C_temp) * 9) / 5) + 32));
	}

	private String CtoK(Double C_temp) {
		return Double.toString((Double) C_temp + 273.15);
	}

	private String KtoF(Double K_value) {
		return CtoF(Double.parseDouble(KtoC(K_value)));
	}

	private String KtoC(Double K_value) {
		return Double.toString(K_value - 273.15);
	}
}
