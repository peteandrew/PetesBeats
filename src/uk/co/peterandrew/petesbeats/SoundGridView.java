package uk.co.peterandrew.petesbeats;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

public class SoundGridView extends View {
	private float mControlWidth;
	private float mControlHeight;
	private Boolean mStates[][] = new Boolean[5][16];
	private GridClickListener gridClickListener;
	private int currStep;
	
	
	interface GridClickListener {
		public void onGridClick(int xPos, int yPos);
	}

	
    public SoundGridView(Context context) {
        super(context);
        
        for (int j = 0; j < 5; j++) {
        	for (int i = 0; i < 16; i++) {
        		mStates[j][i] = false;
        	}
        }
    }

    public void setGridClickListener(GridClickListener gridClickListener) {
    	this.gridClickListener = gridClickListener;
    }

    public void step(int currStep) {
    	this.currStep = currStep;
    	invalidate();
    }
    
    protected void onDraw(Canvas canvas) {    	
        mControlWidth = this.getWidth() / 16;
        mControlHeight = this.getHeight() / 5;
    	
    	RectF rect = new RectF(0, 0, mControlWidth - 2, mControlHeight - 2);
    	Paint paintOff = new Paint();
    	paintOff.setColor(Color.RED);
    	Paint paintOn = new Paint();
    	paintOn.setColor(Color.BLUE);
    	Paint paintBeat = new Paint();
    	paintBeat.setColor(Color.GRAY);
    	
    	for (int i = 0; i < 5; i++) {
    		canvas.translate(1, 1);
    		for (int j = 0; j < 16; j++) {
    			Paint currPaint;
    			if (j == currStep) {
    				currPaint = paintBeat;
    			} else {
    				if (!mStates[i][j]) {
    					currPaint = paintOff;
    				} else {
    					currPaint = paintOn;
    				}
    			}
    			canvas.drawRoundRect(rect, 0.1f, 0.1f, currPaint);
    			canvas.translate(mControlWidth, 0);
    		}
    		canvas.translate(-(canvas.getWidth() + 1), mControlHeight);
    	}
    }
    
    public boolean onTouchEvent(MotionEvent event) {
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
    		int clickBoxX = (int)(event.getX() / mControlWidth);
    		int clickBoxY = (int)(event.getY() / mControlHeight);
    		
    		mStates[clickBoxY][clickBoxX] = !mStates[clickBoxY][clickBoxX];
    		
    		gridClickListener.onGridClick(clickBoxX, clickBoxY);
    		
    		invalidate();
    	}
    	return true;
    }
}
