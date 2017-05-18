/**
 * AbstractMoveable.java
 * Describes an object that moves and can collide with the Player
 *
 * @author Gahwon Lee, Tiger Zhang
 * Period: 3
 * Date: 5/2/2017
 */

package com.gmail.creativitry.freezeframe.moveable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gmail.creativitry.freezeframe.Player;

public abstract class AbstractMoveable
{
	private float x;
	private float y;
	private float velY;
	private float velX;
	private MoveableTexture texture;
	private float life;
	
	private float lastAngle;
	
	/**
	 * Decrements life
	 * @param delta the change in health
	 * @return true if life is less than zero; false otherwise
	 */
	public boolean decrementLife(float delta)
	{
		life -= delta;
		return life <= 0;
	}
	
	/**
	 * Updates position and velocity every frame
	 * @param delta
	 */
	public void update(float delta)
	{
		x += velX * delta;
		y += velY * delta;
	}
	
	/**
	 * Renders the texture of this moveable
	 *
	 * @param batch batch to render to
	 */
	public void render(SpriteBatch batch)
	{
		float angle;
		if (velX == 0 && velY == 0)
			angle = lastAngle;
		else
		{
			angle = MathUtils.atan2(velY, velX) * MathUtils.radiansToDegrees;
			lastAngle = angle;
		}
		
		texture.render(batch, x, y, angle);
	}
	
	/**
	 * Gets the x position
	 * @return x position
	 */
	public float getX()
	{
		return x;
	}
	
	/**
	 * Sets the x position
	 * @param x new x position
	 */
	public void setX(float x)
	{
		this.x = x;
	}
	
	/**
	 * Gets the x position
	 * @return x position
	 */
	public float getY()
	{
		return y;
	}
	
	/**
	 * Sets the y position
	 * @param y new y position
	 */
	public void setY(float y)
	{
		this.y = y;
	}
	
	/**
	 * Gets the x velocity
	 * @return x velocity
	 */
	public float getVelX()
	{
		return velX;
	}
	
	/**
	 * Sets the x velocity
	 * @param velX new x velocity
	 */
	public void setVelX(float velX)
	{
		this.velX = velX;
	}
	
	/**
	 * Gets the x velocity
	 *
	 * @return x velocity
	 */
	public float getVelY()
	{
		return velY;
	}
	
	/**
	 * Sets the y velocity
	 *
	 * @param velY new y velocity
	 */
	public void setVelY(float velY)
	{
		this.velY = velY;
	}
	
	/**
	 * Gets the remaining time of life of the moveable
	 * @return life
	 */
	public float getLife()
	{
		return life;
	}
	
	/**
	 * Sets the remaining time of life of the moveable
	 * @param life new life
	 */
	public void setLife(float life)
	{
		this.life = life;
	}
	
	/**
	 * Gets the texture, radius, and graze radius
	 * @return texture
	 */
	public MoveableTexture getTexture()
	{
		return texture;
	}
	
	/**
	 * Sets the texture, radius, and graze radius
	 * @param texture new texture
	 */
	public void setTexture(MoveableTexture texture)
	{
		this.texture = texture;
	}
	
	/**
	 * Determines if this object is colliding with the Player
	 * @param player player to ceck collision with
	 * @return true if this object is colliding with the Player; false otherwise
	 */
	public boolean isColliding(Player player)
	{
		Vector2 position = player.getPosition();
		return !(Math.hypot(position.x - x, position.y - y) >
			player.getRadius() + texture.getRadius());
	}
	
	/**
	 * Method called that will modify the Player after a collision
	 * @param player the Player
	 */
	public abstract void onCollision(Player player);
}
