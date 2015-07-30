package com.book.pong_v3;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import com.book.simplegameengine_v3.SGActivity;
import com.book.simplegameengine_v3.SGInputPublisher;
import com.book.simplegameengine_v3.SGPreferences;

public class GameActivity extends SGActivity {
	private GameController mController;
	private GameModel mModel;
	private GameView mView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		enableFullScreen();
		enableKeepScreenOn();
		
		SGPreferences preferences = getPreferences();
		if(preferences.getInt("first_time", -1) == -1) 
		{
			preferences.begin()
				.putInt("first_time", 1)
				.putInt("difficulty", 0)
				.putInt("high_score", 15)
				.end();
			Log.d("PongV2", "Primeira inicialização.");
		}
		
		Point worldDimensions = new Point(480, 320);
		mModel = new GameModel(worldDimensions);
		
		mView = new GameView(this, mModel);
		setContentView(mView);
		
		SGInputPublisher inputPublisher = new SGInputPublisher(this);		
		mController = new GameController(mModel);
		inputPublisher.registerSubscriber(mController);
		setInputPublisher(inputPublisher);
	}
}


