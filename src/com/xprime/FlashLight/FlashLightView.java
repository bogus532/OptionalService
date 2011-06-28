package com.xprime.FlashLight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

public class FlashLightView extends View {
	
	int color;
	
	public FlashLightView(Context context) {
		super(context);

		initFlashLightView();
	}
	
	public FlashLightView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initFlashLightView();
	}
	
	public FlashLightView(Context context, AttributeSet attrs, int defaultStyle) {
		super(context, attrs, defaultStyle);
		
		initFlashLightView();
	}
	
	protected void initFlashLightView(){
		
		setFocusable(true);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		//canvas.drawColor(Color.rgb(255, 255, 255));
		canvas.drawColor(color);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measureWidth = measure(widthMeasureSpec);
		int measureHeight = measure(heightMeasureSpec);
		
		setMeasuredDimension(measureWidth, measureHeight);
	}
	
	private int measure(int measureSpec) {
		int result =0;
		
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if(specMode == MeasureSpec.UNSPECIFIED){
			result = 200;
		}else{
			result = specSize;
		}		
		return result;
	}
	
	public int getColor() {
		return color;
	}

	public void setColor(int _color) {
		this.color = _color;
	}

}
