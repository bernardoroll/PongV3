package com.book.pong_v3;

import android.graphics.PointF;

import com.book.simplegameengine_v3.SGEntity;
import com.book.simplegameengine_v3.SGTrigger;
import com.book.simplegameengine_v3.SGWorld;

public class TrgGap extends SGTrigger 
{
	public static final int GAP_SIZE = 50;
	
	public TrgGap(SGWorld world, PointF position, PointF dimensions) 
	{
		super(world, GameModel.TRG_GAP_ID, position, dimensions);
	}
	
	@Override
	public void onHit(SGEntity entity, float elapsedTimeInSeconds) 
	{
		entity.setPosition(entity.getPosition().x, GAP_SIZE);
	}
}

