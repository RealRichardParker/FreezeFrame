/**
 * LimitedNormalDistribution.java
 * Normally distributed with absolutely no chance of getting beyond certain standard
 * deviations away from the mean
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/14/2017
 */

package com.gmail.creativitry.freezeframe.random;

import com.badlogic.gdx.math.RandomXS128;

public class LimitedNormalDistribution extends NormalDistribution
{
	private static final int MAX_TRIES = 20;
	private float stdLimit;
	
	/**
	 * Constructs a new LimitedNormalDistribution with the given parameters
	 *
	 * @param mean     mean of the normal distribution
	 * @param std      standard deviation of the normal distribution
	 * @param stdLimit limit of how many standard deviations away from the mean
	 */
	public LimitedNormalDistribution(float mean, float std, float stdLimit)
	{
		super(mean, std);
		this.stdLimit = stdLimit;
	}
	
	/**
	 * Generates a random number
	 * @param random random number generator
	 * @return generated number
	 */
	@Override
	protected float nextFloat(RandomXS128 random)
	{
		for (int i = 0; i < MAX_TRIES; i++)
		{
			float value = super.nextFloat(random);
			if (Math.abs(getMean() - value) / getStd() < stdLimit)
				return value;
		}
		return getMean();
	}
}
