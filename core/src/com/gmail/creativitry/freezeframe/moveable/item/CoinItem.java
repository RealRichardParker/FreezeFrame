package com.gmail.creativitry.freezeframe.moveable.item;

import com.badlogic.gdx.math.Vector2;
import com.gmail.creativitry.freezeframe.Player;
import com.gmail.creativitry.freezeframe.moveable.MoveableTexture;

/**
 * Description
 *
 * @author Gahwon Lee
 *         Date: 5/15/2017.
 */
public class CoinItem extends AbstractItem
{
	
	private static final float SCORE = 50f;
	
	public CoinItem(float x, float y, MoveableTexture texture)
	{
		super(x, y, texture);
	}
	
	public boolean isNear(Player player)
	{
		Vector2 position = player.getPosition();
		return !(Math.hypot(position.x - getX(), position.y - getY()) > player.getRadius() + getTexture().getGrazeRadius());
	}
	
	/**
	 * Method called that will modify the Player after a collision
	 *
	 * @param player the Player
	 */
	@Override
	public void onCollision(Player player)
	{
		player.addScore(SCORE);
	}
}
