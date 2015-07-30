package com.book.pong_v3;

import java.util.ArrayList;

import com.book.simplegameengine_v3.SGAnimation;
import com.book.simplegameengine_v3.SGEntity;
import com.book.simplegameengine_v3.SGImage;
import com.book.simplegameengine_v3.SGImageFactory;
import com.book.simplegameengine_v3.SGRenderer;
import com.book.simplegameengine_v3.SGTileset;
import com.book.simplegameengine_v3.SGView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;


public class GameView extends SGView {
	private boolean 	mIsDebug = false;
	private GameModel 	mModel;
	
	private SGTileset	mTsetBall;
	private SGTileset	mTsetOpponent;
	private SGTileset	mTsetPlayer;
	
	private SGAnimation mAnimBallCCW;
	private SGAnimation mAnimBallCW;
	private SGAnimation mAnimOpponentDownAngry;
	private SGAnimation mAnimOpponentDownConcerned;
	private SGAnimation mAnimOpponentDownHappy;
	private SGAnimation mAnimOpponentUpAngry;
	private SGAnimation mAnimOpponentUpConcerned;
	private SGAnimation mAnimOpponentUpHappy;
	private SGAnimation mAnimPlayerDownAngry;
	private SGAnimation mAnimPlayerDownConcerned;
	private SGAnimation mAnimPlayerDownHappy;
	private SGAnimation mAnimPlayerUpAngry;
	private SGAnimation mAnimPlayerUpConcerned;
	private SGAnimation mAnimPlayerUpHappy;
	
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
		
		// Ball
		SGImage ballImage = getImageFactory().createImage("tilesets/ball.png");
		mTsetBall = new SGTileset(ballImage, new Point(4, 2), null);
		int[] ballTilesCCW = {0, 1, 2, 3};
		mAnimBallCCW = new SGAnimation(ballTilesCCW, 0.1f);
		int[] ballTilesCW = {4, 5, 6, 7};
		mAnimBallCW = new SGAnimation(ballTilesCW, 0.1f);
		
		// Paddle do oponente
		SGImage opponentImage = getImageFactory().createImage("tilesets/opponent.png");
		mTsetOpponent = new SGTileset(opponentImage, new Point(8, 2), new Rect(0, 0,
				GameModel.PADDLE_WIDTH, 
				GameModel.PADDLE_HEIGHT));

		// Paddle do jogador
		SGImage playerImage = getImageFactory().createImage("tilesets/player.png");
		mTsetPlayer = new SGTileset(playerImage, new Point(8, 2), new Rect(0, 0,
				GameModel.PADDLE_WIDTH,
				GameModel.PADDLE_HEIGHT));

		int[] tilesDownHappy = { 0, 1 };
		mAnimOpponentDownHappy = new SGAnimation(tilesDownHappy, 0.1f);
		mAnimPlayerDownHappy = new SGAnimation(tilesDownHappy, 0.1f);

		int[] tilesUpHappy = { 2, 3 };
		mAnimOpponentUpHappy = new SGAnimation(tilesUpHappy, 0.1f);
		mAnimPlayerUpHappy = new SGAnimation(tilesUpHappy, 0.1f);

		int[] tilesDownConcerned = { 4, 5 };
		mAnimOpponentDownConcerned = new SGAnimation(tilesDownConcerned, 0.1f);
		mAnimPlayerDownConcerned = new SGAnimation(tilesDownConcerned, 0.1f);

		int[] tilesUpConcerned = { 6, 7 };
		mAnimOpponentUpConcerned = new SGAnimation(tilesUpConcerned, 0.1f);
		mAnimPlayerUpConcerned = new SGAnimation(tilesUpConcerned, 0.1f);

		int[] tilesDownAngry = { 8, 9 };
		mAnimOpponentDownAngry = new SGAnimation(tilesDownAngry, 0.1f);
		mAnimPlayerDownAngry = new SGAnimation(tilesDownAngry, 0.1f);

		int[] tilesUpAngry = { 10, 11 };
		mAnimOpponentUpAngry = new SGAnimation(tilesUpAngry, 0.1f);
		mAnimPlayerUpAngry = new SGAnimation(tilesUpAngry, 0.1f);
	}
	
	@Override 
	public void step(Canvas canvas, float elapsedTimeInSeconds) 
	{
		mModel.step(elapsedTimeInSeconds);
		
		SGRenderer renderer = getRenderer();		
		renderer.beginDrawing(canvas, Color.BLACK);
		
		ArrayList<SGEntity> entities = mModel.getEntities();
		
		if(mIsDebug == true)
		{
			for(SGEntity currentEntity : entities)
			{				
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
			for(SGEntity currentEntity : entities) 
			{
				if(currentEntity.getCategory() == "paddle") 
				{
					SGAnimation	currentAnimation;
					SGTileset 	tileset;
					
					if(currentEntity.getId() == GameModel.PLAYER_ID) 
					{
						tileset = mTsetPlayer;
						if(currentEntity.hasFlag(EntPaddle.STATE_LOOKING_UP)) 
						{
							if(currentEntity.hasFlag(EntPaddle.STATE_HAPPY)) 
							{
								currentAnimation = mAnimPlayerUpHappy;
							}
							else if(currentEntity.hasFlag(EntPaddle.STATE_CONCERNED)) 
							{
								currentAnimation = mAnimPlayerUpConcerned;
							}
							else // entity.hasFlag(EntPaddle.STATE_ANGRY)
							{ 
								currentAnimation = mAnimPlayerUpAngry;
							}
						}
						else 
						{
							if(currentEntity.hasFlag(EntPaddle.STATE_HAPPY)) 
							{
								currentAnimation = mAnimPlayerDownHappy;
							}
							else if(currentEntity.hasFlag(EntPaddle.STATE_CONCERNED)) 
							{
								currentAnimation = mAnimPlayerDownConcerned;
							}
							else // entity.hasFlag(EntPaddle.STATE_ANGRY)
							{
								currentAnimation = mAnimPlayerDownAngry;
							}
						}
					}
					else // entity.getId() == GameModel.OPPONENT_ID
					{ 
						tileset = mTsetOpponent;
						if(currentEntity.hasFlag(EntPaddle.STATE_LOOKING_UP)) 
						{
							if(currentEntity.hasFlag(EntPaddle.STATE_HAPPY)) 
							{
								currentAnimation = mAnimOpponentUpHappy;
							}
							else if(currentEntity.hasFlag(EntPaddle.STATE_CONCERNED)) 
							{
								currentAnimation = mAnimOpponentUpConcerned;
							}
							else // entity.hasFlag(EntPaddle.STATE_ANGRY)
							{ 
								currentAnimation = mAnimOpponentUpAngry;
							}
						}
						else 
						{
							if(currentEntity.hasFlag(EntPaddle.STATE_HAPPY)) 
							{
								currentAnimation = mAnimOpponentDownHappy;
							}
							else if(currentEntity.hasFlag(EntPaddle.STATE_CONCERNED)) 
							{
								currentAnimation = mAnimOpponentDownConcerned;
							}
							else // entity.hasFlag(EntPaddle.STATE_ANGRY)
							{
								currentAnimation = mAnimOpponentDownAngry;
							}
						}
					}
					
					int tileIndex = currentAnimation.step(elapsedTimeInSeconds);
					
					if(currentEntity.hasFlag(EntPaddle.STATE_HIT)) 
					{
						currentAnimation.start(2);
					}
	
					PointF position = currentEntity.getPosition();
					PointF dimensions = currentEntity.getDimensions();				
					Rect drawingArea = tileset.getTile(tileIndex);
					
					renderer.drawImage(tileset.getImage(), drawingArea, position, dimensions);
				}				
				else if(currentEntity.getCategory() == "ball") 
				{
					SGAnimation	currentAnimation;
					int 		tileIndex;
					
					if(currentEntity.hasFlag(EntBall.STATE_ROLL_CW)) 
					{
						currentAnimation = mAnimBallCW;						
					}
					else 
					{
						currentAnimation = mAnimBallCCW;
					}
					
					if(!mModel.getRestartTimer().hasStarted()) {
						currentAnimation.start(-1);
						tileIndex = currentAnimation.step(elapsedTimeInSeconds);
					}
					else {
						tileIndex = currentAnimation.getCurrentTile();
					}
					
					PointF position = currentEntity.getPosition();
					PointF dimensions = currentEntity.getDimensions();
					Rect drawingArea = mTsetBall.getTile(tileIndex);
					
					renderer.drawImage(mTsetBall.getImage(), drawingArea, position, dimensions);
				}
			}
		}
		
		renderer.endDrawing();
	
	}
}




