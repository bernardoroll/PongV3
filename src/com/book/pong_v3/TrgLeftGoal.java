package com.book.pong_v3;

import android.graphics.PointF;
import android.util.Log;

import com.book.simplegameengine_v3.SGEntity;
import com.book.simplegameengine_v3.SGTrigger;
import com.book.simplegameengine_v3.SGWorld;

public class TrgLeftGoal extends SGTrigger 
{
	public TrgLeftGoal(SGWorld world, PointF position, PointF dimensions) 
	{
		super(world, GameModel.TRG_LEFT_GOAL_ID, position, dimensions);
	}
	
	@Override
	public void onHit(SGEntity entity, float elapsedTimeInSeconds) 
	{
		if(isActive()) {
			GameModel model = (GameModel)getWorld();
			
			model.increaseOpponentScore();
			
			Log.d("PongV2", "Oponente marca um ponto!");
			model.logScore();
			
			if(model.getOpponentScore() == 2) {
				model.getPlayer().addFlags(EntPaddle.STATE_CONCERNED);
				model.getPlayer().removeFlags(EntPaddle.STATE_HAPPY);
			}
			else if(model.getOpponentScore() == 4) {
				model.getPlayer().addFlags(EntPaddle.STATE_ANGRY);
				model.getPlayer().removeFlags(EntPaddle.STATE_CONCERNED);
			}			
			
			model.resetWorld();
			
			if(model.getOpponentScore() == 5) {
				model.setGameOver(true);
				Log.d("PongV2", "Fim de jogo!");
			}
			else {
				model.getRestartTimer().start();
			}
		}		
	}
}
