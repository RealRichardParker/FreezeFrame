/**
 * UniformDistribution.java
 * Uniformly distributed probability
 *
 * @author creativitRy
 * Period: 3
 * Date: 5/14/2017
 */

package com.gmail.creativitry.freezeframe.random;

import com.badlogic.gdx.math.RandomXS128;

public class UniformDistribution extends AbstractDistribution
{
	private float lower;
	private float higher;
	
	/**
	 * Constructs a new UniformDistribution with the given parameters
	 *
	 * @param lower  lowest possible number, inclusive
	 * @param higher highest possible number, exclusive
	 */
	public UniformDistribution(float lower, float higher)
	{
		this.lower = lower;
		this.higher = higher;
	}
	
	/**
	 * Generates a random number
	 * @param random random number generator
	 * @return generated number
	 */
	@Override
	protected float nextFloat(RandomXS128 random)
	{
		return random.nextFloat() * (higher - lower) + lower;
	}
}
