package com.book.pong_v3;

import android.graphics.PointF;

import com.book.simplegameengine_v3.SGWorld;

public class EntPlayer extends EntPaddle 
{
	public EntPlayer(SGWorld world, PointF position, PointF dimensions) 
	{
		super(world, GameModel.PLAYER_ID, position, dimensions);
	}
	
	@Override
	public void step(float elapsedTimeInSeconds) 
	{
	}
}

