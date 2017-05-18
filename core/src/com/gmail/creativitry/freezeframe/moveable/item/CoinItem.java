/**
 * CoinItem.java
 * A coin item which increments points when picked up
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/15/2017
 */

package com.gmail.creativitry.freezeframe.moveable.item;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gmail.creativitry.freezeframe.Player;
import com.gmail.creativitry.freezeframe.moveable.MoveableTexture;

public class CoinItem extends AbstractItem
{
	private static final float ATTRACTED_VELOCITY = 120;
	private static final float SCORE = 50f;
	
	/**
	 * Constructs a new coin with the given parameters and the default movement
	 *
	 * @param x       starting x position
	 * @param y       starting y position
	 * @param texture texture of the item
	 */
	public CoinItem(float x, float y, MoveableTexture texture)
	{
		super(x, y, texture);
	}
	
	/**
	 * Checks whether the player is near the coin's graze radius
	 *
	 * @param player player to check
	 * @return true if the player is near this, false otherwise
	 */
	public boolean isNear(Player player)
	{
		Vector2 position = player.getPosition();
		return !(Math.hypot(position.x - getX(), position.y - getY()) >
			player.getRadius() + getTexture().getGrazeRadius());
	}
	
	/**
	 * Sets the velocity to move towards the player
	 *
	 * @param player player to move to
	 */
	public void getAttracted(Player player)
	{
		Vector2 position = player.getPosition();
		float angle = MathUtils.atan2(position.y - getY(), position.x - getX());
		
		setVelX(ATTRACTED_VELOCITY * MathUtils.cos(angle));
		setVelY(ATTRACTED_VELOCITY * MathUtils.sin(angle));
	}
	
	/**
	 * Sets the velocity to default velocity
	 */
	public void stopGetAttracted()
	{
		setVelX(0);
		setVelY(-VELOCITY);
	}
	
	/**
	 * Method called that will modify the Player after a collision
	 * Increases the score
	 *
	 * @param player the Player
	 */
	@Override
	public void onCollision(Player player)
	{
		player.addScore(SCORE);
	}
}
