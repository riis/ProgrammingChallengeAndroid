package com.riis.controllers;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout.LayoutParams;

import com.riis.R;

public class MessageIndicatorAnimation extends Animation
{
	private int marginStart;
	private int marginEnd;
	private View view;
	
	private boolean isVisibleAfter = false;
	private boolean wasEndedAlready = false;
	
	private LayoutParams layoutParams;
	
	public MessageIndicatorAnimation(View view, int duration)
	{
		setDuration(duration);
		this.view = view;
		layoutParams = (LayoutParams) view.getLayoutParams();
		
		isVisibleAfter = (view.getVisibility() == View.VISIBLE);
		
		marginStart = layoutParams.bottomMargin;
		marginEnd = (marginStart == 0 ? (0 - view.getHeight()) : 0);
		
		view.setVisibility(View.VISIBLE);
		view.findViewById(R.id.indicatorListEmail).setVisibility(View.VISIBLE);
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t)
	{
		super.applyTransformation(interpolatedTime, t);
		
		if (interpolatedTime < 1.0f)
		{
            layoutParams.bottomMargin = marginStart + (int) ((marginEnd - marginStart) * interpolatedTime);
            view.findViewById(R.id.indicatorListEmail).setVisibility(View.VISIBLE);
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