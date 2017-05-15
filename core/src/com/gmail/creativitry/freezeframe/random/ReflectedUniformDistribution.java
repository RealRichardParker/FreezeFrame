/**
 * ReflectedUniformDistribution.java
 * Description
 *
 * @author creativitRy
 * Period: 3
 * Date: 5/14/2017
 */
package com.gmail.creativitry.freezeframe.random;

import com.badlogic.gdx.math.RandomXS128;

public class ReflectedUniformDistribution extends UniformDistribution
{
	public ReflectedUniformDistribution(float lower, float higher)
	{
		super(lower, higher);
	}
	
	@Override
	protected float nextFloat(RandomXS128 random)
	{
		if (random.nextBoolean())
			return super.nextFloat(random);
		return -super.nextFloat(random);
	}
}
