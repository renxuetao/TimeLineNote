package com.wl.android.myeditView;


import com.example.timelinenote.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

	public class MyEditView extends EditText {
		  
//		private static final String TAG = "com.lms.todo.views.MyEditView";
	  private Rect mRect;
	  private Paint mPaint;
	  private int mColor = R.color.grayBlown_press; 
	  private final int padding = 10;
	  
	  private int lineHeight;
	  private int viewHeight,viewWidth;

	  public MyEditView(Context context) {
	    this(context, null);
	  }

	  public MyEditView(Context context, AttributeSet attrs) {
	    this(context, attrs, 0);
	  }

	  public MyEditView(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	    init(context, attrs);
	  }

	  private void init(Context context, AttributeSet attrs) {
	    mRect = new Rect();
	    mPaint = new Paint();
	    mPaint.setStyle(Paint.Style.STROKE);
	    mPaint.setColor(mColor);
	    mPaint.setStrokeWidth(2);
	    mPaint.setAntiAlias(true);
	    
	    setFocusable(true);
	    setFocusableInTouchMode(true);		
	  }

	  @Override
	  protected void onDraw(Canvas canvas) {
	    int count = getLineCount();
	    Rect r = mRect;		 
	    Paint paint = mPaint;
	    int lineHeight = 10;
	    int i = 0;		
	    while (i < count) {
	      lineHeight = getLineBounds(i, r);
	      canvas.drawLine(r.left, lineHeight + padding, r.right, lineHeight + padding,
	          paint);
	      i++;
	    }
	    int maxLines = 15;
	    int avgHeight = lineHeight / count;
	    int currentLineHeight = lineHeight;
	    i = 0;
	    while(i < maxLines){
	      currentLineHeight = currentLineHeight + avgHeight + padding;
	      canvas.drawLine(r.left, currentLineHeight, r.right, currentLineHeight, paint);
	      i++;
	    }
	    super.onDraw(canvas);	  
	  }
	}

