package com.gmail.creativitry.freezeframe.database;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Description
 *
 * @author Gahwon Lee
 *         Date: 5/18/2017.
 */
public class OnlineScoreData
{
	private String name;
	private long time;
	private String seed;
	private long score;
	
	public ScoreData toScoreData()
	{
		return new ScoreData(
			LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.UTC), name, seed, score);
	}
}
