/**
 * AbstractBullet.java
 * Description
 *
 * @author Tiger
 * Date: 5/4/2017.
 */
package com.gmail.creativitry.freezeframe.bullet;

import com.gmail.creativitry.freezeframe.AbstractMoveable;
import com.gmail.creativitry.freezeframe.Player;

public abstract class AbstractBullet extends AbstractMoveable
{
	/**
	 * Method called that will modify the Player after a collision
	 *
	 * @param player the Player
	 */
	@Override
	public void onCollision(Player player)
	{
		
	}
	
	
}
