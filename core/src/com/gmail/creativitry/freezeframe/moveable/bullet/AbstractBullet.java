/**
 * AbstractBullet.java
 * Describes a bullet's behavior
 *
 * @author Tiger Zhang
 * Date: 5/4/2017
 * Period 3
 */
package com.gmail.creativitry.freezeframe.moveable.bullet;

import com.gmail.creativitry.freezeframe.moveable.AbstractMoveable;
import com.gmail.creativitry.freezeframe.Player;

public abstract class AbstractBullet extends AbstractMoveable
{
	
	/**
	 * Method called that will reduce the health of the Player after a collision
	 *
	 * @param player the Player
	 */
	@Override
	public void onCollision(Player player)
	{
		player.decrementLife();
	}
	
	public abstract void init(BulletTemplate template);
}
