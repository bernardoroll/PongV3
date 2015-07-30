package com.book.pong_v3;

import android.graphics.PointF;

import com.book.simplegameengine_v3.SGEntity;
import com.book.simplegameengine_v3.SGWorld;

public class EntPaddle extends SGEntity 
{
	public static final int NUM_OF_SECTORS = 10;
	public static final int STATE_HIT = 0x01;
	public static final int STATE_LOOKING_UP = 0x02;
	public static final int STATE_HAPPY = 0x04;
	public static final int STATE_CONCERNED = 0x08;
	public static final int STATE_ANGRY = 0x10;
	
	private float mSectorSize;

	public EntPaddle(SGWorld world, int id, PointF position, PointF dimensions) 
	{
		super(world, id, "paddle", position, dimensions);
		
		addFlags(STATE_HAPPY);
		
		mSectorSize = dimensions.y / (NUM_OF_SECTORS - 1);
	}
	
	public float getSectorSize() { return mSectorSize; }
}

