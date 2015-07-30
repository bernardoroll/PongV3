package com.book.pong_v3;

import android.graphics.PointF;
import android.util.Log;

import com.book.simplegameengine_v3.SGEntity;
import com.book.simplegameengine_v3.SGTrigger;
import com.book.simplegameengine_v3.SGWorld;

public class TrgRightGoal extends SGTrigger 
{
	public TrgRightGoal(SGWorld world, PointF position, PointF dimensions) 
	{
		super(world, GameModel.TRG_RIGHT_GOAL_ID, position, dimensions);
	}
	
	@Override
	public void onHit(SGEntity entity, float elapsedTimeInSeconds) 
	{
		GameModel model = (GameModel)getWorld();
		model.increasePlayerScore();
		
		EntOpponent opponent = model.getOpponent();
		opponent.calculateSpeed(model.getPlayerScore());
		opponent.decreaseReaction();
		
		Log.d("PongV2", "Jogador marca um ponto!");
		model.logScore();
		
		model.resetWorld();
		model.getRestartTimer().start();
	}
}
