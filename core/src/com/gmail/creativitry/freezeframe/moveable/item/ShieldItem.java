/**
 * ShieldItem.java
 * Item that prevents losing of a life from a single bullet collision
 *
 * @author Gahwon Lee
 *         Date: 5/15/2017.
 */


package com.gmail.creativitry.freezeframe.moveable.item;

import com.gmail.creativitry.freezeframe.Player;
import com.gmail.creativitry.freezeframe.moveable.MoveableTexture;

public class ShieldItem extends AbstractItem
{
	public ShieldItem(float x, float y, MoveableTexture texture)
	{
		super(x, y, texture);
	}
	
	/**
	 * Method called that will modify the Player after a collision
	 *
	 * @param player the Player
	 */
	@Override
	public void onCollision(Player player)
	{
		player.setShield();
	}
}
