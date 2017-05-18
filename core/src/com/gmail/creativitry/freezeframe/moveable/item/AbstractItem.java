/**
 * AbstractItem.java
 * Describes the movement of an item
 *
 * @author Gahwon Lee, Tiger Zhang
 * Period: 3
 * Date: 5/10/2017
 */

package com.gmail.creativitry.freezeframe.moveable.item;

import com.gmail.creativitry.freezeframe.moveable.AbstractMoveable;
import com.gmail.creativitry.freezeframe.moveable.MoveableTexture;

public abstract class AbstractItem extends AbstractMoveable
{
	public static final int VELOCITY = 80;
	
	/**
	 * Constructs a new item with the given parameters and the default movement
	 *
	 * @param x       starting x position
	 * @param y       starting y position
	 * @param texture texture of the item
	 */
	public AbstractItem(float x, float y, MoveableTexture texture)
	{
		setX(x);
		setY(y);
		setVelX(0);
		setVelY(-VELOCITY);
		setTexture(texture);
		setLife(10);
	}
}
