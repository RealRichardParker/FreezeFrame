/**
 * RandomGenerator.java
 * Randomly generates numbers
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/13/2017
 */

package com.gmail.creativitry.freezeframe.random;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;

import java.util.Arrays;

public class RandomGenerator
{
	private static final int SHORTEST_SEED_SIZE = 5;
	private static final int LONGEST_SEED_SIZE = 15;
	
	private String seed;
	private RandomXS128 random;
	
	/**
	 * Constructs a new generator with the given seed
	 *
	 * @param seed seed to feed the random generator
	 */
	public RandomGenerator(String seed)
	{
		this.seed = seed;
		random = new RandomXS128(seed.hashCode());
	}
	
	/**
	 * Constructs a new generator with a random seed
	 */
	public RandomGenerator()
	{
		this(getRandomSeed());
	}
	
	/**
	 * Generates a random string seed that is readable by humans
	 *
	 * @return random seed
	 */
	private static String getRandomSeed()
	{
		StringBuilder str = new StringBuilder();
		
		int randSize = MathUtils.random(SHORTEST_SEED_SIZE, LONGEST_SEED_SIZE);
		for (int i = 0; i < randSize; i++)
		{
			str.append(getRandomChar());
		}
		
		return str.toString();
	}
	
	/**
	 * Generates a random character that is either an alphabet or a number
	 *
	 * @return random character
	 */
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
	
	/**
	 * Gets the seed of the random generator
	 *
	 * @return seed
	 */
	public String getSeed()
	{
		return seed;
	}
	
	/**
	 * Gets the internal random generator
	 *
	 * @return random generator
	 */
	public RandomXS128 getRandom()
	{
		return random;
	}
	
	/**
	 * From a list of values, one is randomly chosen
	 *
	 * @param values  values to choose from
	 * @param chances the probability the respective value has for being chosen
	 * @return chosen value
	 */
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
	
	/**
	 * From a list of values, one is randomly chosen
	 *
	 * @param values  values to choose from
	 * @param chances the probability the respective value has for being chosen
	 * @return chosen value
	 */
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
	
	/**
	 * From a list of values, one is randomly chosen
	 *
	 * @param values  values to choose from
	 * @param chances the probability the respective value has for being chosen
	 * @return chosen value
	 */
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
	
	/**
	 * From a list of values, one is randomly chosen.
	 * All values have the same probability of getting chosen
	 *
	 * @param values values to choose from
	 * @return chosen value
	 */
	public <T> T choose(T[] values)
	{
		float[] chances = new float[values.length];
		Arrays.fill(chances, 1f);
		return choose(values, chances);
	}
	
	/**
	 * Returns a pseudo-random, uniformly distributed int value between 0 (inclusive) and
	 * the specified value (exclusive), drawn from this random number generator's sequence.
	 *
	 * @param n the positive bound on the random number to be returned.
	 * @return the next pseudo-random int value between 0 (inclusive) and n (exclusive).
	 */
	public int nextInt(int n)
	{
		return random.nextInt(n);
	}
	
	/**
	 * Generates a random array of the given size with each index containing a random
	 * number generated following the distribution
	 * @param size size of the array
	 * @param distribution distribution of probabilities
	 * @return random generated float array
	 */
	public float[] getRandFloatArr(int size, AbstractDistribution distribution)
	{
		float[] arr = new float[size];
		
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = nextFloat(distribution);
		}
		
		return arr;
	}
	
	/**
	 * Generates a random number from the distribution
	 * @param distribution distribution of probabilities
	 * @return randomly generated number
	 */
	public float nextFloat(AbstractDistribution distribution)
	{
		return distribution.nextFloat(random);
	}
	
	/**
	 * Gets the string version of this, containing the seed
	 * @return formatted string
	 */
	@Override
	public String toString()
	{
		return "RandomGenerator{" +
			"seed='" + seed + '\'' +
			'}';
	}
}
