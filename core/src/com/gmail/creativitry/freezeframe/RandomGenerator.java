/**
 * RandomGenerator.java
 * Randomly
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/13/2017
 */
package com.gmail.creativitry.freezeframe;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;

import java.util.Arrays;

public class RandomGenerator
{
	private String seed;
	private RandomXS128 random;
	
	public RandomGenerator(String seed)
	{
		this.seed = seed;
		random = new RandomXS128(seed.hashCode());
	}
	
	public RandomGenerator()
	{
		this(getRandomSeed());
	}
	
	private static String getRandomSeed()
	{
		StringBuilder str = new StringBuilder();
		
		int randSize = MathUtils.random(5, 15);
		for (int i = 0; i < randSize; i++)
		{
			str.append(getRandomChar());
		}
		
		return str.toString();
	}
	
	private static char getRandomChar()
	{
		int charType = MathUtils.random(2);
		
		switch (charType)
		{
			case 0:
				return (char) MathUtils.random('A', 'Z');
			case 1:
				return (char) MathUtils.random('a', 'z');
			case 2:
				return (char) MathUtils.random('0', '9');
		}
		
		return '0';
	}
	
	public String getSeed()
	{
		return seed;
	}
	
	public RandomXS128 getRandom()
	{
		return random;
	}
	
	@SuppressWarnings("Duplicates")
	public float choose(float[] values, float[] chances)
	{
		float totalChance = 0f;
		for (int i = 0; i < chances.length; i++)
		{
			totalChance += chances[i];
		}
		float choose = random.nextFloat() * totalChance;
		int index = 0;
		while (chances[index] < choose)
		{
			choose -= chances[index];
			index++;
		}
		return values[index];
	}
	
	@SuppressWarnings("Duplicates")
	public int choose(int[] values, float[] chances)
	{
		float totalChance = 0f;
		for (int i = 0; i < chances.length; i++)
		{
			totalChance += chances[i];
		}
		float choose = random.nextFloat() * totalChance;
		int index = 0;
		while (chances[index] < choose)
		{
			choose -= chances[index];
			index++;
		}
		return values[index];
	}
	
	@SuppressWarnings("Duplicates")
	public <T> T choose(T[] values, float[] chances)
	{
		float totalChance = 0f;
		for (int i = 0; i < chances.length; i++)
		{
			totalChance += chances[i];
		}
		float choose = random.nextFloat() * totalChance;
		int index = 0;
		while (chances[index] < choose)
		{
			choose -= chances[index];
			index++;
		}
		return values[index];
	}
	
	public <T> T choose(T[] values)
	{
		float[] chances = new float[values.length];
		Arrays.fill(chances, 1f);
		return choose(values, chances);
	}
	
	public float nextFloat(float lower, float higher)
	{
		return random.nextFloat() * (higher - lower) + lower;
	}
	
	public int nextInt(int n)
	{
		return random.nextInt(n);
	}
	
	public float[] getRandFloatArr(int size, float lower, float higher)
	{
		float[] arr = new float[size];
		
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = nextFloat(lower, higher);
		}
		
		return arr;
	}
	
	/**
	 * Generates a random number based on a normally distributed probability
	 *
	 * @param mean mean of the normal distribution
	 * @param std  standard deviation of the normal distribution
	 * @return randomly generated float
	 */
	public float nextNormal(float mean, float std)
	{
		double norm = random.nextGaussian();
		
		return (float) ((norm * std) + mean);
	}
	
	@Override
	public String toString()
	{
		return "RandomGenerator{" +
			"seed='" + seed + '\'' +
			'}';
	}
}
