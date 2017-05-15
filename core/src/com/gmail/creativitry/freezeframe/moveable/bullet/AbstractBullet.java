/**
 * AbstractBullet.java
 * Describes a bullet's behavior
 *
 * @author Tiger Zhang
 * Date: 5/4/2017
 * Period 3
 */
package com.gmail.creativitry.freezeframe.moveable.bullet;

import com.badlogic.gdx.math.Vector2;
import com.gmail.creativitry.freezeframe.Player;
import com.gmail.creativitry.freezeframe.moveable.AbstractMoveable;

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
	
	public void init(BulletTemplate template, float x, float y, float angle)
	{
		setX(x);
		setY(y);
		setTexture(template.getBullet().getTexture());
		setLife(template.getBullet().getLife());
	}
	
	public abstract AbstractBullet newInstance();
	
	public boolean isGrazing(Vector2 position, float radius)
	{
		return !(Math.hypot(position.x - getX(), position.y - getY()) > radius + getTexture().getGrazeRadius());
	}
}
