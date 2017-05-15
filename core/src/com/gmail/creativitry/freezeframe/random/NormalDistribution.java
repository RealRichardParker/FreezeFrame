/**
 * NormalDistribution.java
 * Description
 *
 * @author creativitRy
 * Period: 3
 * Date: 5/14/2017
 */
package com.gmail.creativitry.freezeframe.random;

import com.badlogic.gdx.math.RandomXS128;

public class NormalDistribution extends AbstractDistribution
{
	private float std;
	private float mean;
	
	public NormalDistribution(float mean, float std)
	{
		this.mean = mean;
		this.std = std;
	}
	
	@Override
	protected float nextFloat(RandomXS128 random)
	{
		double norm = random.nextGaussian();
		
		return (float) ((norm * std) + mean);
	}
	
	public float getStd()
	{
		return std;
	}
	
	public float getMean()
	{
		return mean;
	}
}
