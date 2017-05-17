/**
 * AbstractMoveable.java
 * Describes an object that moves and can collide with the Player
 * @author Tiger Zhang, Gahwon Lee
 * Period: 3
 * Date: 5/2/2017.
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
	
	private float lastAngle;
	
	public void render(SpriteBatch batch, float xVel, float yVel)
	{
		/*float sprayerX = GameScreen.GAME_WIDTH / 2;
		float sprayerY = GameScreen.GAME_HEIGHT - GameScreen.VERTICAL_PAD;
		float angle = MathUtils.atan2(y - sprayerY, x - sprayerX) * MathUtils.radiansToDegrees;*/
		float angle;
		if (xVel == 0 && yVel == 0)
			angle = lastAngle;
		else
		{
			angle = MathUtils.atan2(yVel, xVel) * MathUtils.radiansToDegrees;
			lastAngle = angle;
		}
		
		texture.render(batch, x, y, angle);
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
	 * @param player player to ceck collision with
	 * @return true if this object is colliding with the Player; false otherwise
	 */
	public boolean isColliding(Player player)
	{
		Vector2 position = player.getPosition();
		return !(Math.hypot(position.x - x, position.y - y) > player.getRadius() + texture.getRadius());
	}
	
	/**
	 * Method called that will modify the Player after a collision
	 * @param player the Player
	 */
	public abstract void onCollision(Player player);
}
