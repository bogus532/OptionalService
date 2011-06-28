package com.xprime.CameraPW;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawOnSurface extends View {
	
	float pitch = 0;
	float roll = 0;
	float heading = 0;
	float distance = 0;
	float MeasureHeight = 0;
	int MeasureIndex=0;

	public DrawOnSurface(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		paint.setColor(Color.argb(70, 255, 0, 0));
		paint.setStrokeWidth(10); 

		int height = getMeasuredHeight();
		int width = getMeasuredWidth();

		int py = height / 2;
		int px = width / 2;
		int radius = Math.min(px, py) - 2;

		//Point center = new Point(px, py);
		if(pitch == 0 || Math.abs(pitch) == 180)
		{
			paint.setColor(Color.argb(70, 0, 255, 0));
			canvas.drawCircle(px, py, radius, paint);
		}
		else
		{
			canvas.drawCircle(px, py, radius, paint);
		}
		
		//out of range
		if((pitch >= 90 && pitch <= 180)&& MeasureIndex ==1)
		{
			paint.setColor(Color.argb(255, 0, 0, 0));
			canvas.drawCircle(px, py, radius, paint);
			paint.setColor(Color.argb(255, 255, 255, 255));
			paint.setTextSize(20);
			canvas.drawText("Out of Range", px-55, py+40, paint);
		}
		else if((pitch < 90 && pitch > 0)&& MeasureIndex == 2)
		{
			paint.setColor(Color.argb(255, 0, 0, 0));
			canvas.drawCircle(px, py, radius, paint);
			paint.setColor(Color.argb(255, 255, 255, 255));
			paint.setTextSize(20);
			canvas.drawText("Out of Range", px-55, py+40, paint);
		}

		//Draw Center Line
		paint.setStrokeWidth(1);
		//paint.setAntiAlias(true);
		paint.setColor(Color.argb(70, 255, 255, 255));
		canvas.drawLine(px-20,py,px+20,py,paint);
		canvas.drawLine(px,py-20,px,py+20,paint);
		
		
		paint.setColor(Color.argb(70, 0, 0, 255));
		
		canvas.drawCircle(px, py, 20, paint);

		Paint p = new Paint();
		p.setAntiAlias(true);
		//p.setColor(android.graphics.Color.MAGENTA);
		p.setColor(android.graphics.Color.WHITE);
		
		canvas.drawText("Bearing: " + heading + "\u00b0", 22, 22, p);
		canvas.drawText("Pitch: " + pitch + "\u00b0", 22, 35, p);
		
		if(pitch >= 90 && pitch <= 180)
		{
			roll = 90 - roll;
		}
		canvas.drawText("Roll: " + roll + "\u00b0", 22, 48, p);
	
		p.setTextSize(20);
		//if(!(pitch >= 90 && pitch <= 180))
		{
			if(distance<1 && distance > -1)
			{
				canvas.drawText("Distance: " + distance*100 + "Cm", 22, height-20, p);
			}
			else
			{
				canvas.drawText("Distance: " +  distance  + "m", 22, height-20, p);
			}
		}

		canvas.drawText("Height: "+ MeasureHeight + "m", 22, height-40, p);
			
		super.onDraw(canvas);
	}
	
	public void setHeading(float _heading){
		heading = _heading;
	}
	
	public float getHeading(){
		return heading;
	}
	
	public float getPitch() {
		return pitch;
	}

	public void setPitch(float _pitch) {
		this.pitch = _pitch;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float _roll) {
		this.roll = _roll;
	}
	
	public float getDistance() {
		return distance;
	}

	public void setDistance(float _distance) {
		this.distance = _distance;
	}
	
	public float getMeasureHeight() {
		return MeasureHeight;
	}

	public void setMeasureHeight(float _MeasureHeight) {
		this.MeasureHeight = _MeasureHeight;
	}
	
	public float getMeasureIndex() {
		return MeasureIndex;
	}

	public void setMeasureIndex(int _MeasureIndex) {
		this.MeasureIndex = _MeasureIndex;
	}

}
