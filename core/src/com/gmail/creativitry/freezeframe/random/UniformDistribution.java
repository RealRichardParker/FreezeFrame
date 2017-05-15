/**
 * UniformDistribution.java
 * Description
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
	
	public UniformDistribution(float lower, float higher)
	{
		this.lower = lower;
		this.higher = higher;
	}
	
	@Override
	protected float nextFloat(RandomXS128 random)
	{
		return random.nextFloat() * (higher - lower) + lower;
	}
}
