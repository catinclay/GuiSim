package com.example.guisim;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {

	private SparseArray<PointF> mActivePointers;
	private ArrayList<fingerPointerView> fingerPointerArrayList = new ArrayList<fingerPointerView>();
	private Queue<String> hBQueue = new LinkedList<String>();
	private int width;
	private int height;
	private RelativeLayout motherLayout;
	
	

	@SuppressLint("NewApi")
	@Override


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		motherLayout = (RelativeLayout) findViewById(R.id.mother_layout);
		mActivePointers = new SparseArray<PointF>();


		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;

		


		hBQueue.add("C");
		hBQueue.add("C");
		hBQueue.add("G");
		hBQueue.add("G");
		hBQueue.add("C");
		hBQueue.add("C");
		hBQueue.add("F");
		hBQueue.add("G");
		hBQueue.add("C");
		hBQueue.add("C");




		

		createPointer();






	}

	private void createPointer(){
		if(!hBQueue.isEmpty()){
			String sound = hBQueue.element();
			if(sound.equals("C")){
				addPointer(36*width/80, 46*height/80, "3");
				addPointer(21*width/80, 36*height/80, "2");
				addPointer(6*width/80, 16*height/80, "1");
			}else if(sound.equals("G")){
				addPointer(28*width/80, 56*height/80, "3");
				addPointer(13*width/80, 46*height/80, "2");
				addPointer(36*width/80, 6*height/80, "4");
				
			}else if(sound.equals("F")){
				addPointer(13*width/80, 26*height/80, "2");
				addPointer(36*width/80, 36*height/80, "4");
				addPointer(28*width/80, 46*height/80, "3");
				addPointer(6*width/80, 56*height/80, "1");
			}
		}
	}


	private void addPointer(int layX, int layY, String fingerNumber){
		fingerPointerView testFingerPointerView = new fingerPointerView(getApplicationContext(), null);
		RelativeLayout.LayoutParams testLayoutParams = new RelativeLayout.LayoutParams(200, 200);
		testLayoutParams.leftMargin = layX;
		testLayoutParams.topMargin = layY;
		testFingerPointerView.setLayoutParams(testLayoutParams);
		testFingerPointerView.setLocationForCheck(layX, layY);
		testFingerPointerView.setFingerNumberTextView(fingerNumber);
		motherLayout.addView(testFingerPointerView);
		fingerPointerArrayList.add(testFingerPointerView);
	}


	public boolean checkTouched(MotionEvent event){
		Boolean allTouched = true;

		for (fingerPointerView currentPointerView : fingerPointerArrayList) {
			currentPointerView.initialIsClicked();
		}


		for (int size = event.getPointerCount(), i = 0; i < size; i++) {
			PointF point = mActivePointers.get(event.getPointerId(i));
			if (point != null) {
				point.x = event.getX(i);
				point.y = event.getY(i);

				for (fingerPointerView currentPointerView : fingerPointerArrayList) {
					if(currentPointerView.checkIfTouched(point.x, point.y)){

					}
				}

			}
		}

		for (fingerPointerView currentPointerView : fingerPointerArrayList) {
			if(currentPointerView.isClicked == false){
				allTouched = false;
			}
		}


		if(allTouched == true){
			for (fingerPointerView currentPointerView : fingerPointerArrayList) {
				currentPointerView.setVisibility(View.GONE);
			}
			
			if(!hBQueue.isEmpty()){
				String sound = hBQueue.poll();
				if(sound.equals("C")){
					MediaPlayer mediaPlayerC = MediaPlayer.create(this, R.raw.c_chord);
					mediaPlayerC.start(); // no need to call prepare(); create() does that for you
					Log.e("DAVID: sound", "C");
				}else if(sound.equals("G")){
					MediaPlayer mediaPlayerG = MediaPlayer.create(this, R.raw.g_chord);
					mediaPlayerG.start(); // no need to call prepare(); create() does that for you
					Log.e("DAVID: sound", "G");

				}else if(sound.equals("F")){
					MediaPlayer mediaPlayerF = MediaPlayer.create(this, R.raw.f_chord);
					mediaPlayerF.start(); // no need to call prepare(); create() does that for you
					Log.e("DAVID: sound", "F");

				}
				allTouched = false;

			}
			mActivePointers.clear();

			fingerPointerArrayList.clear();
			createPointer();

		}


		return allTouched;
	}

	// TODO Auto-generated constructor stub
	@Override
	public boolean onTouchEvent(MotionEvent event) {


		int pointerIndex = event.getActionIndex();

		// get pointer ID
		int pointerId = event.getPointerId(pointerIndex);

		// get masked (not specific to a pointer) action
		int maskedAction = event.getActionMasked();

		switch (maskedAction) {

		case MotionEvent.ACTION_DOWN:
		{
			// We have a new pointer. Lets add it to the list of pointers

			PointF f = new PointF();
			f.x = event.getX(pointerIndex);
			f.y = event.getY(pointerIndex);
			mActivePointers.put(pointerId, f);

//			Log.e("David DOWN:", String.valueOf(f.x));


			checkTouched(event);
			break;
		}





		case MotionEvent.ACTION_POINTER_DOWN: {
			// We have a new pointer. Lets add it to the list of pointers

			PointF f = new PointF();
			f.x = event.getX(pointerIndex);
			f.y = event.getY(pointerIndex);
			mActivePointers.put(pointerId, f);

//			Log.e("David DOWN:", String.valueOf(f.x));


			checkTouched(event);
			break;
		}





		case MotionEvent.ACTION_MOVE: { // a pointer was moved
			for (int size = event.getPointerCount(), i = 0; i < size; i++) {
				PointF point = mActivePointers.get(event.getPointerId(i));
				if (point != null) {
					point.x = event.getX(i);
					point.y = event.getY(i);
					//			    	Log.e("David MOVE:", String.valueOf(point.x));

				}
			}
			checkTouched(event);
			break;
		}
		case MotionEvent.ACTION_UP:
			checkTouched(event);
			break;



		case MotionEvent.ACTION_POINTER_UP:



		case MotionEvent.ACTION_CANCEL: {
			mActivePointers.remove(pointerId);
			break;
		}


		default:
			return false;
		}

		// Schedules a repaint.
		return true;
	}


}
