/**
 * MagnetItem.java
 * Item that increases player pickup range
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/15/2017
 */

package com.gmail.creativitry.freezeframe.moveable.item;

import com.gmail.creativitry.freezeframe.Player;
import com.gmail.creativitry.freezeframe.moveable.MoveableTexture;

public class MagnetItem extends AbstractItem
{
	/**
	 * Constructs a new magnet with the given parameters and the default movement
	 *
	 * @param x       starting x position
	 * @param y       starting y position
	 * @param texture texture of the item
	 */
	public MagnetItem(float x, float y, MoveableTexture texture)
	{
		super(x, y, texture);
	}
	
	/**
	 * Method called that will modify the Player after a collision.
	 * Allows coins to be attracted to the player when within their graze radius
	 *
	 * @param player the Player
	 */
	@Override
	public void onCollision(Player player)
	{
		player.setMagnet();
	}
}
