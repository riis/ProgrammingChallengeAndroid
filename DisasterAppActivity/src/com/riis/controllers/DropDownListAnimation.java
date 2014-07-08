package com.riis.controllers;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout.LayoutParams;

public class DropDownListAnimation extends Animation
{
	private int marginStart;
	private int marginEnd;
	private View view;
	
	private boolean isVisibleAfter = false;
	private boolean wasEndedAlready = false;
	
	private LayoutParams layoutParams;
	
	public DropDownListAnimation(View view, int duration)
	{
		setDuration(duration);
		this.view = view;
		layoutParams = (LayoutParams) view.getLayoutParams();
		
		isVisibleAfter = (view.getVisibility() == View.VISIBLE);
		marginStart = layoutParams.bottomMargin;
		Log.i("bottom margin", marginStart +"");
		marginEnd = (marginStart == 0 ? (0 - view.getHeight()) : 0);
		Log.i("marginEnd", marginEnd+"");
		
		view.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t)
	{
		super.applyTransformation(interpolatedTime, t);

		if (interpolatedTime < 1.0f)
		{
            layoutParams.bottomMargin = marginStart + (int) ((marginEnd - marginStart) * interpolatedTime);
            view.requestLayout();
        } 
		else if (!wasEndedAlready)
		{
            layoutParams.bottomMargin = marginEnd;
            view.requestLayout();
            
            if (isVisibleAfter)
            {
                view.setVisibility(View.INVISIBLE);
            }
            
            wasEndedAlready = true;
        }
	}
}