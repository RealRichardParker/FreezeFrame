/**
 * RandomArray.java
 * Chooses a random values from a given array and the corresponding chances array
 *
 * @author Tiger
 * Date: 5/12/2017
 */
package com.gmail.creativitry.freezeframe;

import com.badlogic.gdx.math.RandomXS128;


public class RandomArray
{
	private RandomArray()
	{
	}
	
	public static float chooseRand(float[] values, float[] chances, RandomXS128 rand)
	{
		float totalChance = 0f;
		for (int i = 0; i < chances.length; i++)
		{
			totalChance += values[i];
		}
		float choose = rand.nextFloat() * totalChance;
		int index = 0;
		while (chances[index] < choose)
		{
			choose -= chances[index];
			index++;
		}
		return values[index];
	}
	
	public static int chooseRand(int[] values, float[] chances, RandomXS128 rand)
	{
		float totalChance = 0f;
		for (int i = 0; i < chances.length; i++)
		{
			totalChance += values[i];
		}
		float choose = rand.nextFloat() * totalChance;
		int index = 0;
		while (chances[index] < choose)
		{
			choose -= chances[index];
			index++;
		}
		return values[index];
	}
}
