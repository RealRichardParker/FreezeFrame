/**
 * ShieldItem.java
 * Item that prevents losing of a life from a single bullet collision
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/15/2017
 */

package com.gmail.creativitry.freezeframe.moveable.item;

import com.gmail.creativitry.freezeframe.Player;
import com.gmail.creativitry.freezeframe.moveable.MoveableTexture;

public class ShieldItem extends AbstractItem
{
	/**
	 * Constructs a new shield item with the given parameters and the default movement
	 *
	 * @param x       starting x position
	 * @param y       starting y position
	 * @param texture texture of the item
	 */
	public ShieldItem(float x, float y, MoveableTexture texture)
	{
		super(x, y, texture);
	}
	
	/**
	 * Method called that will modify the Player after a collision.
	 * Activates the shield for the player
	 *
	 * @param player the Player
	 */
	@Override
	public void onCollision(Player player)
	{
		player.setShield();
	}
}
