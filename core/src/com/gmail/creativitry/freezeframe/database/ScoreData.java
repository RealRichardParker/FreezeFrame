/**
 * ScoreData.java
 * //TODO Description
 *
 * @author creativitRy
 * Period: 3
 * Date: 5/13/2017
 */
package com.gmail.creativitry.freezeframe.database;

import com.gmail.creativitry.freezeframe.random.RandomGenerator;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ScoreData implements Comparable<ScoreData>, Serializable
{
	private static final transient int PRIME = 31;
	private LocalDateTime time;
	private String name;
	private String seed;
	private long score;
	
	public ScoreData(LocalDateTime time, String name, String seed, long score)
	{
		this.time = time;
		this.name = name;
		this.seed = seed;
		this.score = score;
	}
	
	public ScoreData(String name, RandomGenerator seed, float score)
	{
		this(LocalDateTime.now(), name, seed.getSeed(), (long) score);
	}
	
	public ScoreData(RandomGenerator random, float score)
	{
		this(getHostName(), random, score);
		
	}
	
	private static String getHostName()
	{
		try
		{
			InetAddress addr = InetAddress.getLocalHost();
			return addr.getHostName();
		}
		catch (UnknownHostException ex)
		{
			return "Unknown";
		}
	}
	
	/**
	 * @param o the score data to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object.
	 */
	@Override
	public int compareTo(ScoreData o)
	{
		if (score == o.score)
		{
			if (name.equals(o.name))
			{
				if (seed.equals(o.seed))
					return time.compareTo(o.time);
				return seed.compareTo(o.seed);
			}
			
			return name.compareTo(o.name);
		}
		return Float.compare(o.score, score);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		ScoreData scoreData = (ScoreData) o;
		
		if (score != scoreData.score) return false;
		if (!time.equals(scoreData.time)) return false;
		if (!name.equals(scoreData.name)) return false;
		return seed.equals(scoreData.seed);
	}
	
	@Override
	public int hashCode()
	{
		int result = time.hashCode();
		result = PRIME * result + name.hashCode();
		result = 31 * result + seed.hashCode();
		result = 31 * result + (int) (score ^ (score >>> 32));
		return result;
	}
	
	@Override
	public String toString()
	{
		return "ScoreData{" +
			"time=" + time +
			", name='" + name + '\'' +
			", seed='" + seed + '\'' +
			", score=" + score +
			'}';
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getSeed()
	{
		return seed;
	}
	
	public long getScore()
	{
		return score;
	}
	
	public String getUrl()
	{
		return String.format("https://docs.google.com/forms/d/e/" +
				"1FAIpQLScc0046kXez3XgQFkYFE-wCYqIqtbdRm5IYxJ2Hd4qzRH3NYA/viewform?usp" +
				"=pp_url&entry.1757642521=%s&entry.614906427=%s&entry.1280865127=%d",
			name, seed, score);
	}
	
	public String getTime()
	{
		return time.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
	}
}
