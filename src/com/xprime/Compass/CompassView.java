package com.xprime.Compass;

import com.xprime.OptionalService.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.Path.Direction;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View{

	private float bearing;
	private Paint markerPaint;
	private Paint textPaint;
	private Paint circlePaint;
	private Paint fillPaint;
	private Paint rollTextPaint;
	private int textHeight;
	private int rollTextHeight;
	//private final int compassSize = 5;
	
	private float pitch = 0;
	private float roll = 0;
	
	private int[] borderGradientColors;
	private float[] borderGradientPositions;
	
	private int[] glassGradientColors;
	private float[] glassGradientPositions;
	
	private int skyHorizonColorFrom;
	private int skyHorizonColorTo;
	private int groundHorizonColorFrom;
	private int groundHorizonColorTo;
	
	private int textColor;
	private int pointColor;
	
	
	
	public CompassView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initCompassView();
	}
	
	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initCompassView();
	}
	
	public CompassView(Context context, AttributeSet attrs, int defaultStyle) {
		super(context, attrs, defaultStyle);
		// TODO Auto-generated constructor stub
		initCompassView();
	}
	
	private enum CompassDirection{
		N, NNE, NE, ENE, E, ESE, SE, SSE, S, SSW, SW, WSW, W, WNW, NW, NNW
	}

	protected void initCompassView(){
		setFocusable(true);
		
		Resources r = this.getResources();		
		circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePaint.setColor(r.getColor(R.color.background_color));
		circlePaint.setStrokeWidth(1);
		circlePaint.setStyle(Paint.Style.STROKE);
		
		textColor = r.getColor(R.color.text_color);
		pointColor = r.getColor(R.color.point_text_color);
		
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(textColor);
		textPaint.setTextSize(20);
		textPaint.setTextSize(textPaint.getTextSize()-3);
		textPaint.setFakeBoldText(true);
		textPaint.setSubpixelText(true);
		textPaint.setTextAlign(Align.LEFT);
		
		textHeight = (int)textPaint.measureText("yY");
		
		rollTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		rollTextPaint.setColor(Color.BLACK);
		rollTextPaint.setTextSize(20);
		rollTextPaint.setTextSize(rollTextPaint.getTextSize()-3);
		rollTextPaint.setFakeBoldText(true);
		rollTextPaint.setSubpixelText(true);
		rollTextPaint.setTextAlign(Align.LEFT);
		
		rollTextHeight = (int)rollTextPaint.measureText("yY");
		
		markerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		markerPaint.setColor(r.getColor(R.color.marker_color));	
		markerPaint.setAlpha(200);
		markerPaint.setStrokeWidth(1);
		markerPaint.setStyle(Paint.Style.STROKE);
		markerPaint.setShadowLayer(2, 1, 1, r.getColor(R.color.shadow_color));
		
		fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		fillPaint.setColor(Color.argb(100, 0, 255, 0));
		
		borderGradientColors = new int[4];
		borderGradientPositions = new float[4];
		
		borderGradientColors[3] = r.getColor(R.color.outer_border);
		borderGradientColors[2] = r.getColor(R.color.inner_border_one);
		borderGradientColors[1] = r.getColor(R.color.inner_border_two);
		borderGradientColors[0] = r.getColor(R.color.inner_border);
		borderGradientPositions[3] = 0.0f;
		borderGradientPositions[2] = 1-0.03f;
		borderGradientPositions[1] = 1-0.06f;
		borderGradientPositions[0] = 1.0f;
		
		glassGradientColors = new int[5];
		glassGradientPositions = new float[5];
		
		int glassColor = 245;
		glassGradientColors[4] = Color.argb(65, glassColor, glassColor, glassColor);
		glassGradientColors[3] = Color.argb(100, glassColor, glassColor, glassColor);
		glassGradientColors[2] = Color.argb(50, glassColor, glassColor, glassColor);
		glassGradientColors[1] = Color.argb(0, glassColor, glassColor, glassColor);
		glassGradientColors[0] = Color.argb(0, glassColor, glassColor, glassColor);
		glassGradientPositions[4] = 1-0.0f;
		glassGradientPositions[3] = 1-0.06f;
		glassGradientPositions[2] = 1-0.10f;
		glassGradientPositions[1] = 1-0.20f;
		glassGradientPositions[0] = 1-1.0f;
		
		//skyHorizonColorFrom = r.getColor(R.color.horizen_sky_from2);
		skyHorizonColorFrom = r.getColor(R.color.horizen_sky_to2);
		
		//groundHorizonColorFrom = r.getColor(R.color.horizen_ground_from2);
		groundHorizonColorFrom = r.getColor(R.color.horizen_ground_to2);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		float ringWidth = textHeight+4;

		int height = getMeasuredHeight();
		int width = getMeasuredWidth();
		
		int py = height/2;
		
		//int radius = Math.min(py,py)-2;
		int px = width/2;
		int radius = Math.min(px,py)-2;
		
		Point center = new Point(px, py);
				
		RectF boundingBox = new RectF(center.x-radius,center.y-radius,center.x+radius,center.y+radius);
		RectF innerBoundingBox = new RectF(center.x-radius+ringWidth,center.y-radius+ringWidth
				,center.x+radius-ringWidth,center.y+radius-ringWidth);
		float innerRadius = innerBoundingBox.height()/2;
		
		RadialGradient bordergradient = new RadialGradient(px, py, radius, borderGradientColors, borderGradientPositions
				,TileMode.CLAMP);
		
		Paint pgb = new Paint();
		pgb.setShader(bordergradient);
		pgb.setAntiAlias(true);
		
		Path outerRingPath = new Path();
		outerRingPath.addOval(boundingBox, Direction.CW);
		
		canvas.drawPath(outerRingPath, pgb);
		
		LinearGradient skyShader = new LinearGradient(center.x, innerBoundingBox.top, center.x, innerBoundingBox.bottom
				, skyHorizonColorFrom, skyHorizonColorTo, TileMode.CLAMP);
		
		Paint skyPaint = new Paint();
		skyPaint.setShader(skyShader);
		
		LinearGradient groundShader = new LinearGradient(center.x, innerBoundingBox.top, center.x, innerBoundingBox.bottom
				, groundHorizonColorFrom, groundHorizonColorTo, TileMode.CLAMP);
		
		Paint groundPaint = new Paint();
		groundPaint.setShader(groundShader);
		
		float tiltDegree = pitch;
		while(tiltDegree>90 || tiltDegree<-90){
			if(tiltDegree>90)tiltDegree = -90 + (tiltDegree-90);
				if(tiltDegree<-90)tiltDegree = 90-(tiltDegree+90);
		}
		float rollDegree = roll;
 		while(rollDegree>180 || rollDegree<-180){
			if(rollDegree>180)rollDegree = -180 + (rollDegree-180);
				if(rollDegree<-180)rollDegree = 180 - (rollDegree + 180);
		}
		
		Path skyPath = new Path();
		skyPath.addArc(innerBoundingBox, -tiltDegree, (180+(2*tiltDegree)));
		
		canvas.rotate(-rollDegree, px, py);
		canvas.drawOval(innerBoundingBox, groundPaint);
		canvas.drawPath(skyPath, skyPaint);
		canvas.drawPath(skyPath, markerPaint);
		
		int markWidth = radius/3;
		int startX = center.x-markWidth;
		int endX = center.x + markWidth;
		
		double h = innerRadius* Math.cos(Math.toRadians(90-tiltDegree));
		double justTiltY = center.y - h;
		
		float pxPerDegree = (innerBoundingBox.height()/2)/45f;
		
		for(int i=90; i>= -90; i-=10){
			double ypos = justTiltY + i*pxPerDegree;
			
			if((ypos<(innerBoundingBox.top+textHeight))||(ypos>(innerBoundingBox.bottom-textHeight)))
				continue;
			canvas.drawLine(startX, (float)ypos, endX, (float)ypos, markerPaint);
			int displayPos = (int)(tiltDegree-i);
			String displayString = String.valueOf(displayPos);
			float stringSizeWidth = textPaint.measureText(displayString);
			canvas.drawText(displayString,(int)(center.x-stringSizeWidth/2), (int)(ypos)+1, textPaint);
		}
		
		markerPaint.setStrokeWidth(2);
		canvas.drawLine(center.x-radius/2, (float)justTiltY, center.x+radius/2, (float)justTiltY, markerPaint);
		markerPaint.setStrokeWidth(1);
		
		Path rollArrow = new Path();
		rollArrow.moveTo(center.x-3, (int)innerBoundingBox.top+14);
		rollArrow.lineTo(center.x, (int)innerBoundingBox.top+10);
		rollArrow.moveTo(center.x+3, innerBoundingBox.top+14);
		rollArrow.lineTo(center.x, innerBoundingBox.top+10);
		canvas.drawPath(rollArrow, markerPaint);
		
		String rollText = String.valueOf(rollDegree);
		//double rollTextWidth = textPaint.measureText(rollText);
		//canvas.drawText(rollText,(float)(center.x-rollTextWidth/2), innerBoundingBox.top + textHeight+2-10, textPaint);
		double rollTextWidth = rollTextPaint.measureText(rollText);
		canvas.drawText(rollText,(float)(center.x-rollTextWidth/2), innerBoundingBox.top + rollTextHeight+2, rollTextPaint);
		canvas.restore();
		
		canvas.save();
		canvas.rotate(180, center.x, center.y);
		for(int i= -180; i<180; i += 10){
			//if(i%30==0){
			if(i == 360){ // dont display
				String rollString = String.valueOf(i*-1);
				float rollStringWidth  = textPaint.measureText(rollString);
				PointF rollStringCenter = new PointF(center.x-rollStringWidth/2, innerBoundingBox.top+1+textHeight);
				canvas.drawText(rollString,rollStringCenter.x, rollStringCenter.y, textPaint);
			}else{
				canvas.drawLine(center.x, (int)innerBoundingBox.top, center.x, (int)innerBoundingBox.top+5, markerPaint);
			}
			canvas.rotate(10, center.x, center.y);
		}
		canvas.restore();
		
		canvas.save();
		canvas.rotate(-1*(bearing), px, py);
		
		//display Candinal
		double increment = 22.5;
		for(double i=0; i<360; i += increment){
			CompassDirection cd = CompassDirection.values()[(int)(i/22.5)];
			String headString = cd.toString();
			
			float headStringWidth = textPaint.measureText(headString);
			PointF headStringCenter = new PointF(center.x-headStringWidth/2, boundingBox.top+1+textHeight);
			if(i%90==0)
			{
				textPaint.setColor(pointColor);
				//float textSize = textPaint.getTextSize();
				//textPaint.setTextSize(14.0f);
			}
			else 
			{
				textPaint.setColor(textColor);
			}
			if(i%increment==0)
				canvas.drawText(headString,headStringCenter.x, headStringCenter.y, textPaint);
			else canvas.drawLine(center.x, (int)boundingBox.top, center.x, (int)boundingBox.top+3, markerPaint);
			canvas.rotate((int)increment, center.x, center.y);
		}
		
		canvas.restore();
		
		RadialGradient glassShader = new RadialGradient(px, py, (int)innerRadius, glassGradientColors, glassGradientPositions
				,TileMode.CLAMP);
		Paint glassPaint = new Paint();
		glassPaint.setShader(glassShader);
		glassPaint.setAntiAlias(true);
		
		canvas.drawOval(innerBoundingBox, glassPaint);
		canvas.drawOval(boundingBox, glassPaint);
		
		//pitch,roll(both horizonal)
		if((pitch == 0 || Math.abs(pitch) == 180 )&& roll ==0)
		{
			canvas.drawOval(innerBoundingBox, fillPaint);
		}
		else
		{
			circlePaint.setStrokeWidth(2);
			canvas.drawOval(innerBoundingBox, circlePaint);
		}
		
		
		canvas.restore();
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

	public void setBearing(float _bearing){
		bearing = _bearing;
	}
	
	public float getBearing(){
		return bearing;
	}
	
	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

}
