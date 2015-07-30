package com.book.pong_v3;

import android.graphics.PointF;

import com.book.simplegameengine_v3.SGWorld;

public class EntOpponent extends EntPaddle
{
	public static final float MAX_SPEED = 300;
	public static final float MIN_REACTION = 30;
	
	private float mReaction;
	private float mSpeed;
	
	public EntOpponent(SGWorld world, PointF position, PointF dimensions) 
	{
		super(world, GameModel.OPPONENT_ID, position, dimensions);
		
		calculateSpeed(0);
		mReaction = dimensions.y / 2;
	}
	
	public void calculateSpeed(int playerScore) 
	{
		float playerScoreSqr = (playerScore + 5) * (playerScore + 5);
		mSpeed = (playerScoreSqr / (150 + playerScoreSqr)) * MAX_SPEED;
	}
	
	public void decreaseReaction() 
	{
		if(--mReaction < MIN_REACTION) 
		{
			mReaction = MIN_REACTION;
		}
	}
	
	@Override
	public void step(float elapsedTimeInSeconds) 
	{
		PointF position = getPosition();
		PointF dimensions = getDimensions();
		GameModel model = (GameModel)getWorld();
		
		float paddleCenterY = position.y + (dimensions.y / 2);
		
		float reactionTop = paddleCenterY - mReaction;
		float reactionBottom = paddleCenterY + mReaction;
		
		EntBall ball = model.getBall();
		float ballCenterY = ball.getPosition().y + (ball.getDimensions().y / 2);
		
		if(reactionTop > ballCenterY) 
		{
			move(0, -(mSpeed * elapsedTimeInSeconds));
		}
		else if(reactionBottom < ballCenterY)
		{
			move(0, mSpeed * elapsedTimeInSeconds);
		}
		
		float opponentHeight = getBoundingBox().bottom - getBoundingBox().top;
		float opponentCenterY = (getPosition().y + (opponentHeight / 2));
		if(opponentCenterY > ballCenterY) {
			addFlags(EntPaddle.STATE_LOOKING_UP);
		}
		else {
			removeFlags(EntPaddle.STATE_LOOKING_UP);
		}
		
		
	}
	
	public float getReaction() { return mReaction; }
	public float getSpeed() { return mSpeed; }
	
	public void setReaction(float reaction) { mReaction = reaction; }
	public void setSpeed(float speed) { mSpeed = speed; }
} 

