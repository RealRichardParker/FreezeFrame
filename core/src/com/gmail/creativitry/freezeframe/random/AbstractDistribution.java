/**
 * AbstractDistribution.java
 * Describes a distribution of numbers
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/14/2017
 */

package com.gmail.creativitry.freezeframe.random;

import com.badlogic.gdx.math.RandomXS128;

public abstract class AbstractDistribution
{
	/**
	 * Generates a random number
	 *
	 * @param random random number generator
	 * @return generated number
	 */
	protected abstract float nextFloat(RandomXS128 random);
}
