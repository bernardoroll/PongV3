package com.book.pong_v3;

import android.view.MotionEvent;

import com.book.simplegameengine_v3.SGInputSubscriber;

public class GameController implements SGInputSubscriber 
{	
	private GameModel mModel;
	
	public GameController(GameModel model) 
	{
		mModel = model;
	}
	
	@Override
	public void onDown(MotionEvent event) 
	{
	}

	@Override
	public void onScroll(MotionEvent downEvent, MotionEvent moveEvent,
			float distanceX, float distanceY) 
	{
		if(!mModel.getRestartTimer().hasStarted())
		{
			mModel.movePlayer(-distanceX, -distanceY);
		}
	}

	@Override
	public void onUp(MotionEvent event) 
	{
	}

	public GameModel getModel() { return mModel; }
}
