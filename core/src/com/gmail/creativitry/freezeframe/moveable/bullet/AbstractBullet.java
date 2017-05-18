/**
 * AbstractBullet.java
 * Moves and damages the player on collision.
 * Increases the score faster if the player is near a certain graze radius.
 *
 * @author Tiger Zhang
 * Period: 3
 * Date: 5/4/2017
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
		player.damage();
	}
	
	/**
	 * Initializes the newly created bullet with the given parameters
	 *
	 * @param template BulletTemplate to get parameters from
	 * @param x        starting x position
	 * @param y        starting y position
	 * @param angle    angle to fire at
	 */
	public void init(BulletTemplate template, float x, float y, float angle)
	{
		setX(x);
		setY(y);
		setTexture(template.getBullet().getTexture());
		setLife(template.getBullet().getLife());
	}
	
	/**
	 * Creates a new bullet
	 *
	 * @return new bullet
	 */
	public abstract AbstractBullet newInstance();
	
	/**
	 * Checks whether the player is near this
	 *
	 * @param player player to check
	 * @return true if the player is within the graze radius of the bullet
	 */
	public boolean isGrazing(Player player)
	{
		Vector2 position = player.getPosition();
		return !(Math.hypot(position.x - getX(), position.y - getY()) >
			player.getRadius() + getTexture().getGrazeRadius());
	}
}
