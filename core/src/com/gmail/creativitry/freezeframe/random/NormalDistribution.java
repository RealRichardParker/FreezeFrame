/**
 * NormalDistribution.java
 * A normally distributed probability
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/14/2017
 */

package com.gmail.creativitry.freezeframe.random;

import com.badlogic.gdx.math.RandomXS128;

public class NormalDistribution extends AbstractDistribution
{
	private float std;
	private float mean;
	
	/**
	 * Constructs a new NormalDistribution with the given parameters
	 *
	 * @param mean mean of the normal distribution
	 * @param std  standard deviation of the normal distribution
	 */
	public NormalDistribution(float mean, float std)
	{
		this.mean = mean;
		this.std = std;
	}
	
	/**
	 * Generates a random number
	 * @param random random number generator
	 * @return generated number
	 */
	@Override
	protected float nextFloat(RandomXS128 random)
	{
		double norm = random.nextGaussian();
		
		return (float) ((norm * std) + mean);
	}
	
	/**
	 * Gets the standard deviation
	 * @return standard deviation
	 */
	public float getStd()
	{
		return std;
	}
	
	/**
	 * Gets the mean
	 * @return mean
	 */
	public float getMean()
	{
		return mean;
	}
}
