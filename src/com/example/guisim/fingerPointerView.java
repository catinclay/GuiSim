package com.example.guisim;

import android.R.bool;
import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class fingerPointerView extends FrameLayout {
	
	
	private ImageView pointerView;
	private Resources resources;
	private TextView fingerNumberTextView;
	public Boolean isClicked = false;
	private float locationX;
	private float locationY;
	
    public fingerPointerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    	resources = context.getResources();

        initView();
    }

   

    public fingerPointerView(Context context) {
        super(context);
    	resources = context.getResources();

        initView();
    }
	
	public fingerPointerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    	resources = context.getResources();

        initView();
	}
	
	
    private void initView() {
    	
        View view = inflate(getContext(), R.layout.finger_pointer, null);
        pointerView = (ImageView) view.findViewById(R.id.pointerImage);
        fingerNumberTextView = (TextView) view.findViewById(R.id.fingerNumber);
        
        addView(view);
        
    }
    
    public void setFingerNumberTextView(String number){
    	fingerNumberTextView.setText(number);
    }
    
    public void setLocationForCheck(float x, float y){
    	locationX = x;
    	locationY = y;
    	
    }
    
    public void initialIsClicked(){
    	isClicked = false;
    	pointerView.setImageDrawable(resources.getDrawable(R.drawable.yellow_point));

    }
    
    public boolean checkIfTouched(float x, float y){
    	
    	if(x-locationX<230 && x-locationX>-30 &&
    			y-locationY<230 && y-locationY>-30){
    		
	    	pointerView.setImageDrawable(resources.getDrawable(R.drawable.red_point));

    		
    		
    		isClicked = true;
    		return true;
    	}
    	return false;
    }
    
    
//	// TODO Auto-generated constructor stub
//	@Override
//	  public boolean onTouchEvent(MotionEvent event) {
//	    float eventX = event.getX();
//	    float eventY = event.getY();
//
//	    switch (event.getAction()) {
//	      case MotionEvent.ACTION_DOWN:
//	    	  
//	    	  pointerView.setImageDrawable(resources.getDrawable(R.drawable.red_point));
//	    	  
//	    	  isClicked = true;
//	        return true;
//	      case MotionEvent.ACTION_MOVE:
//	    	  pointerView.setImageDrawable(resources.getDrawable(R.drawable.red_point));
//	    	  isClicked = true;
//	        break;
//	      case MotionEvent.ACTION_UP:
//	        // kill this so we don't double draw
//	    	  pointerView.setImageDrawable(resources.getDrawable(R.drawable.yellow_point));
//
//	    	  isClicked = false;
//	        break;
//	      default:
//	        return false;
//	    }
//
//	    // Schedules a repaint.
//	    return true;
//	  }
}