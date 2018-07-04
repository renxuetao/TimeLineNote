package com.wl.android.animation;

import android.animation.ObjectAnimator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

public class MyAnimation {
	public ScaleAnimation ButtonClickOpen()
	{
	    	ScaleAnimation animation;
	    	animation = new ScaleAnimation(1f, 10f, 1f, 10f
	    			,ScaleAnimation.RELATIVE_TO_SELF, 0.5f
	    			,ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
			animation.setDuration(1*1000);
			animation.setFillAfter(true);
			animation.setFillBefore(false);
			return animation;
	}
	public ScaleAnimation ButtonClickClose()
    {	
    	ScaleAnimation animation;
    	animation = new ScaleAnimation(10f, 1f, 10f, 1f
    			,ScaleAnimation.RELATIVE_TO_SELF, 0.5f
    			,ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(1000);
		animation.setFillAfter(true);
		animation.setFillBefore(false);
		return animation;
    }
	public AnimationSet setAnimationSet()
    {	
    	AnimationSet animationSet = new AnimationSet(true);
    	ScaleAnimation animation;
    	animation = new ScaleAnimation(1f, 2.3f, 1f, 2.3f
    			,ScaleAnimation.RELATIVE_TO_SELF, 0.5f
    			,ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(4000);
		animation.setRepeatCount(Animation.INFINITE);
		animation.setRepeatMode(Animation.RESTART);
		
		AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.0001f);
		alphaAnimation.setDuration(4000);
		alphaAnimation.setRepeatCount(Animation.INFINITE);
		alphaAnimation.setRepeatMode(Animation.RESTART);
		animationSet.addAnimation(animation);
		animationSet.addAnimation(alphaAnimation);
		
		return animationSet;
    }
}
