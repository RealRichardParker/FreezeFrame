/**
 * AbstractMoveable.java
 * Describes an object that moves and can collide with the Player
 * @author Tiger Zhang, Gahwon Lee
 * Period: 3
 * Date: 5/2/2017.
 */
package com.gmail.creativitry.freezeframe.moveable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	
	public void render(SpriteBatch batch)
	{
		batch.draw(texture.getTexture(), x - texture.getHalfSize(), y - texture.getHalfSize());
	}
	
	public float getX()
	{
		return x;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public float getVelY()
	{
		return velY;
	}
	
	public void setVelY(float velY)
	{
		this.velY = velY;
	}
	
	public float getVelX()
	{
		return velX;
	}
	
	public void setVelX(float velX)
	{
		this.velX = velX;
	}
	
	public float getLife()
	{
		return life;
	}
	
	public void setLife(float life)
	{
		this.life = life;
	}
	
	public MoveableTexture getTexture()
	{
		return texture;
	}
	
	public void setTexture(MoveableTexture texture)
	{
		this.texture = texture;
	}
	
	/**
	 * Determines if this object is colliding with the Player
	 * @param position the position of the Player
	 * @param radius the size of the player
	 * @return true if this object is colliding with the Player; false otherwise
	 */
	public boolean isColliding(Vector2 position, float radius)
	{
		if (Math.hypot(position.x - x, position.y - y) > radius + texture.getRadius())
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
