package com.book.pong_v3;

import java.util.ArrayList;

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
	private boolean mIsDebug = false;
	private GameModel mModel;
	private Rect mTempSrcRect = new Rect();
	
//	private SGImage mBallImage;
//	private SGImage mOpponentImage;
//	private SGImage mPlayerImage;
	
	private SGTileset mTsetBall;
	private SGTileset mTsetOpponent;
	private SGTileset mTsetPlayer;
	
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
		SGImage ballImage = imageFactory.createImage("ball.png");
		mTsetBall = new SGTileset(ballImage,  new Point(4,2), null);
		
		// Paddle do oponente
		SGImage opponentImage = imageFactory.createImage("opponent.png");
		mTsetOpponent = new SGTileset(opponentImage, new Point (8,2), new Rect(0, 0,
				GameModel.PADDLE_WIDTH, GameModel.PADDLE_HEIGHT));
		
		// Paddle jogador
		SGImage playerImage = imageFactory.createImage("player.png");
		mTsetPlayer = new SGTileset(playerImage, new Point(8, 2), new Rect(0, 0, 
				GameModel.PADDLE_WIDTH, GameModel.PADDLE_HEIGHT));
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
		else {
			for(SGEntity currentEntity : entities) {
				if(currentEntity.getCategory() != "trigger") {
					SGTileset tileset;
					if(currentEntity.getId() == GameModel.PLAYER_ID) {
						tileset = mTsetPlayer;
					}
					else if(currentEntity.getId() == GameModel.OPPONENT_ID) {
						tileset = mTsetOpponent;
					}
					else { // Ball
						tileset = mTsetBall;
					}
					PointF position = currentEntity.getPosition();
					PointF dimensions = currentEntity.getDimensions();
					Rect drawingArea = tileset.getTile(0);
					renderer.drawImage(tileset.getImage(), drawingArea,  position,  dimensions);;
				}
			}
		}		
		renderer.endDrawing();
	}
}




