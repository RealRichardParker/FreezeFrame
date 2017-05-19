/**
 * ReflectedUniformDistribution.java
 * Uniformly distributed probability that is reflected across the y axis
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/14/2017
 */

package com.gmail.creativitry.freezeframe.random;

import com.badlogic.gdx.math.RandomXS128;

public class ReflectedUniformDistribution extends UniformDistribution
{
	/**
	 * Constructs a new ReflectedUniformDistribution with the given parameters
	 *
	 * @param lower  lowest possible number, inclusive
	 * @param higher highest possible number, exclusive
	 */
	public ReflectedUniformDistribution(float lower, float higher)
	{
		super(lower, higher);
	}
	
	/**
	 * Generates a random number
	 * @param random random number generator
	 * @return generated number
	 */
	@Override
	protected float nextFloat(RandomXS128 random)
	{
		if (random.nextBoolean())
			return super.nextFloat(random);
		return -super.nextFloat(random);
	}
}
