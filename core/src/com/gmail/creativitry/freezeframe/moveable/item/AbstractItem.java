/**
 * AbstractItem.java
 * Description
 *
 * @author Tiger
 * Date: 5/10/2017
 */
package com.gmail.creativitry.freezeframe.moveable.item;

import com.gmail.creativitry.freezeframe.moveable.AbstractMoveable;
import com.gmail.creativitry.freezeframe.moveable.MoveableTexture;

public abstract class AbstractItem extends AbstractMoveable
{
	public AbstractItem(float x, float y, MoveableTexture texture)
	{
		setX(x);
		setY(y);
		setVelX(0);
		setVelY(-50);
		setTexture(texture);
		setLife(10);
	}
}
