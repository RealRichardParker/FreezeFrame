/**
 * ScoreData.java
 * Represents a playthrough of a player. Contains the score, seed, name, and time.
 *
 * @author Gahwon Lee
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
	private static final long serialVersionUID = 1328017632264793788L;
	
	private LocalDateTime time;
	private String name;
	private String seed;
	private long score;
	
	/**
	 * Constructs a new ScoreData with the computer host name and the given parameters
	 *
	 * @param random random generator to get the seed from
	 * @param score  score the player scored
	 */
	public ScoreData(RandomGenerator random, float score)
	{
		this(getHostName(), random, score);
	}
	
	/**
	 * Constructs a new ScoreData with the given parameters with the time set as now
	 *
	 * @param name  name of the player
	 * @param seed  seed of the random generator
	 * @param score score the player scored
	 */
	public ScoreData(String name, RandomGenerator seed, float score)
	{
		this(LocalDateTime.now(), name, seed.getSeed(), (long) score);
	}
	
	/**
	 * Constructs a new ScoreData with the given parameters
	 *
	 * @param time  time the score was taken
	 * @param name  name of the player
	 * @param seed  seed of the random generator
	 * @param score score the player scored
	 */
	public ScoreData(LocalDateTime time, String name, String seed, long score)
	{
		this.time = time;
		this.name = name;
		this.seed = seed;
		this.score = score;
	}
	
	/**
	 * Attempts to get the name of the player from the IP address
	 *
	 * @return IP address name of the player or unknown
	 */
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
	 * Compares this score with the other score, returning the best score
	 * @param o the score data to be compared.
	 * @return a negative integer, zero, or a positive integer as this score
	 * is less than, equal to, or greater than the specified score.
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
	
	/**
	 * Returns whether two scores are equal
	 * @param o the score to compare with
	 * @return true if the two scores are equal, false otherwise
	 */
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
	
	/**
	 * Returns the hashcode of this score
	 * @return hashcode
	 */
	@Override
	public int hashCode()
	{
		int result = time.hashCode();
		result = PRIME * result + name.hashCode();
		result = PRIME * result + seed.hashCode();
		result = PRIME * result + (int) (score ^ (score >>> (PRIME + 1)));
		return result;
	}
	
	/**
	 * Returns the string version of this score
	 * @return formatted string
	 */
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
	
	/**
	 * Gets the name of the player of this score
	 * @return name of player
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets the seed
	 * @return seed
	 */
	public String getSeed()
	{
		return seed;
	}
	
	/**
	 * Gets the score
	 * @return score
	 */
	public long getScore()
	{
		return score;
	}
	
	/**
	 * Gets the global score google form submit url
	 * @return url
	 */
	public String getUrl()
	{
		return String.format("https://docs.google.com/forms/d/e/" +
				"1FAIpQLScc0046kXez3XgQFkYFE-wCYqIqtbdRm5IYxJ2Hd4qzRH3NYA/viewform?usp" +
				"=pp_url&entry.1757642521=%s&entry.614906427=%s&entry.1280865127=%d",
			name, seed, score);
	}
	
	/**
	 * Gets the time
	 * @return time
	 */
	public String getTime()
	{
		return time.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
	}
}
