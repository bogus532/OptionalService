package com.xprime.iCalc;

import com.xprime.OptionalService.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by IntelliJ IDEA.
 * User: izahid
 * Date: May 28, 2010
 * Time: 1:05:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class iCalc extends Activity {
    EventListener mListener = new EventListener(this);

    private static final int HVGA_WIDTH_PIXELS  = 320;
    private static final String LOG_TAG = "iCalc";
    private static final boolean LOG_ENABLED = false;
    private TextView txtAnswer;

    private String pre;
    private String post;
    private Character oper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icalc);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        txtAnswer = (TextView)findViewById(R.id.txtAnswer);
        String s = "0";
        txtAnswer.setText(s.toCharArray(), 0, s.length());
    }
    
    @Override
    protected void onDestroy()
    {
    	super.onDestroy();
    	this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);    	
    }

    public Character getOper() {
        return oper;
    }

    public void setOper(Character oper) {
        this.oper = oper;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
        setOper(null);
    }

    public void setText(String s) {
        txtAnswer.setText(s.toCharArray(), 0, s.length());
    }

    static void log(String message) {
        if (LOG_ENABLED) {
            Log.w(LOG_TAG, message);
        }
    }

    static boolean isOperator(char c) {
        //plus minus times div
        return "+\u2212\u00d7\u00f7/*".indexOf(c) != -1;
    }

    static boolean isPlus(char c) {
        return (c == '+');
    }

    static boolean isMinus(char c) {
        return (c == '-' || c == '\u2212');
    }

    static boolean isMultiply(char c) {
        return (c == '*' || c == '\u00d7');
    }
    
    static boolean isDivide(char c) {
        return (c == '/' || c == '\u00f7');
    }


    /**
     * The font sizes in the layout files are specified for a HVGA display.
     * Adjust the font sizes accordingly if we are running on a different
     * display.
     * @param view Element to adjust font size
     */
    public void adjustFontSize(TextView view) {
        float fontPixelSize = view.getTextSize();
        Display display = getWindowManager().getDefaultDisplay();
        int h = Math.min(display.getWidth(), display.getHeight());
        float ratio = (float)h/HVGA_WIDTH_PIXELS;
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontPixelSize*ratio);
    }
}
