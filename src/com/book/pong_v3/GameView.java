package com.book.pong_v3;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import com.book.simplegameengine_v3.SGEntity;
import com.book.simplegameengine_v3.SGImage;
import com.book.simplegameengine_v3.SGImageFactory;
import com.book.simplegameengine_v3.SGRenderer;
import com.book.simplegameengine_v3.SGView;

public class GameView extends SGView {
	private boolean mIsDebug = false;
	private GameModel mModel;
	private Rect mTempSrcRect = new Rect();
	
	private SGImage mBallImage;
	private SGImage mOpponentImage;
	private SGImage mPlayerImage;
	
	private GameView(Context context) 
	{
		super(context);
	}
	
	public GameView(Context context, GameModel model) 
	{
		super(context);
		mModel = model;
	}
	
	@Override
	public void setup()
	{
		mModel.setup();
		
		SGImageFactory imageFactory = getImageFactory();
		
		mBallImage = imageFactory.createImage("ball.png");
		mPlayerImage = imageFactory.createImage("player.png");
		mOpponentImage = imageFactory.createImage("opponent.png");
	}
	
	@Override 
	public void step(Canvas canvas, float elapsedTimeInSeconds) 
	{
		mModel.step(elapsedTimeInSeconds);
		
		SGRenderer renderer = getRenderer();		
		renderer.beginDrawing(canvas, Color.BLACK);
		
		ArrayList<SGEntity> entities = mModel.getEntities();
		SGEntity currentEntity;
		
		if(mIsDebug == true)
		{
			int arraySize = entities.size();		
			for(int i = 0; i < arraySize; i++) 
			{
				currentEntity = entities.get(i);
				
				SGEntity.DebugDrawingStyle style = currentEntity.getDebugDrawingStyle();
				if(style == SGEntity.DebugDrawingStyle.FILLED) 
				{
					renderer.drawRect(currentEntity.getBoundingBox(), currentEntity.getDebugColor());
				}
				else
				{
					renderer.drawOutlineRect(currentEntity.getBoundingBox(), currentEntity.getDebugColor());
				}
			}
		}
		else
		{
			int arraySize = entities.size();		
			for(int i = 0; i < arraySize; i++) 
			{
				currentEntity = entities.get(i);
				
				if(currentEntity.getCategory() != "trigger")
				{
					if(currentEntity.getId() == GameModel.PLAYER_ID)
					{
						mTempSrcRect.set(0, 0, GameModel.PADDLE_WIDTH, GameModel.PADDLE_HEIGHT);
						renderer.drawImage(mPlayerImage, mTempSrcRect, currentEntity.getPosition(), currentEntity.getDimensions());
					}
					else if(currentEntity.getId() == GameModel.OPPONENT_ID)
					{
						mTempSrcRect.set(0, 0, GameModel.PADDLE_WIDTH, GameModel.PADDLE_HEIGHT);
						renderer.drawImage(mOpponentImage, mTempSrcRect, currentEntity.getPosition(), currentEntity.getDimensions());
					}
					else // (currentEntity.getId() == GameModel.BALL_ID)
					{
						mTempSrcRect.set(0, 0, GameModel.BALL_SIZE, GameModel.BALL_SIZE);
						renderer.drawImage(mBallImage, mTempSrcRect, currentEntity.getPosition(), currentEntity.getDimensions());
					}
				}
			}
		}
		
		renderer.endDrawing();
	}
}




