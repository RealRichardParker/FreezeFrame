/**
 * LimitedNormalDistribution.java
 * Description
 *
 * @author creativitRy
 * Period: 3
 * Date: 5/14/2017
 */
package com.gmail.creativitry.freezeframe.random;

import com.badlogic.gdx.math.RandomXS128;

public class LimitedNormalDistribution extends NormalDistribution
{
	private static final int MAX_TRIES = 20;
	private float stdLimit;
	
	public LimitedNormalDistribution(float mean, float std, float stdLimit)
	{
		super(mean, std);
		this.stdLimit = stdLimit;
	}
	
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
