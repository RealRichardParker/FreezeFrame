/**
 * AbstractMoveable.java
 * Describes an object that moves and can collide with the Player
 * @author Tiger
 * Date: 5/2/2017.
 */
package com.gmail.creativitry.freezeframe;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractMoveable
{
	private float posX;
	private float posY;
	private float velY;
	private float velX;
	private Texture texture;
	private float radius;
	private float life;
	
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
	public abstract void update(float delta);
	
	/**
	 * @param batch
	 */
	//creativitRy writes later
	public void render(SpriteBatch batch)
	{
		
	}
	
	/**
	 * @return the Y velocity of this object
	 */
	public float getVelY()
	{
		return velY;
	}
	
	/**
	 * @return the X velocity of this object
	 */
	public float getVelX()
	{
		return velX;
	}
	
	/**
	 * @return the X position of this object
	 */
	public float getPosX()
	{
		return posX;
	}
	
	/**
	 * @return the Y position of this object
	 */
	public float getPosY()
	{
		return posY;
	}
	
	/**
	 * Determines if this object is colliding with the Player
	 * @param position the position of the Player
	 * @param radius the size of the player
	 * @return true if this object is colliding with the Player; false otherwise
	 */
	public boolean isColliding(Vector2 position, float radius)
	{
		if (Math.hypot(position.x - posX, position.y - posY) > radius + this.radius)
			return false;
		else
			return true;
	}
	
	/**
	 * Method called that will modify the Player after a collision
	 * @param player the Player
	 */
	public abstract void onCollision(Player player);
}
