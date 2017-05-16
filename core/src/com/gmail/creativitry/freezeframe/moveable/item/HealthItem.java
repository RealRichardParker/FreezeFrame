/**
 * HealthItem.java
 * A Health item that increments the number of lives a player has when picked up
 *
 * @author Gahwon Lee
 *         Date: 5/15/2017.
 */

package com.gmail.creativitry.freezeframe.moveable.item;

import com.gmail.creativitry.freezeframe.Player;
import com.gmail.creativitry.freezeframe.moveable.MoveableTexture;

public class HealthItem extends AbstractItem
{
	public HealthItem(float x, float y, MoveableTexture texture)
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
		player.incrementLife();
	}
}
