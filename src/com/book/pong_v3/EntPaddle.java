package com.book.pong_v3;

import android.graphics.PointF;

import com.book.simplegameengine_v3.SGEntity;
import com.book.simplegameengine_v3.SGWorld;

public class EntPaddle extends SGEntity 
{
	public static final int NUM_OF_SECTORS = 10;
	
	private float mSectorSize;

	public EntPaddle(SGWorld world, int id, PointF position, PointF dimensions) 
	{
		super(world, id, "paddle", position, dimensions);
		
		mSectorSize = dimensions.y / (NUM_OF_SECTORS - 1);
	}
	
	public float getSectorSize() { return mSectorSize; }
}

