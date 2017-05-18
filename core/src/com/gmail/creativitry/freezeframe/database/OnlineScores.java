package com.gmail.creativitry.freezeframe.database;

import java.util.TreeSet;

/**
 * Description
 *
 * @author Gahwon Lee
 *         Date: 5/18/2017.
 */
public class OnlineScores
{
	private OnlineScoreData[] scores;
	
	public TreeSet<ScoreData> toScores()
	{
		TreeSet<ScoreData> set = new TreeSet<>();
		
		for (OnlineScoreData score : scores)
		{
			set.add(score.toScoreData());
		}
		
		return set;
	}
}
