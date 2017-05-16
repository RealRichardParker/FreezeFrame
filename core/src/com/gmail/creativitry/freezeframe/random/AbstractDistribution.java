/**
 * AbstractDistribution.java
 * Describes a distribution of numbers
 *
 * @author creativitRy
 * Period: 3
 * Date: 5/14/2017
 */
package com.gmail.creativitry.freezeframe.random;

import com.badlogic.gdx.math.RandomXS128;

public abstract class AbstractDistribution
{
	protected abstract float nextFloat(RandomXS128 random);
}
