/**
 * UCMBullet.java
 * A bullet that moves in circular motion
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/14/2017
 */

package com.gmail.creativitry.freezeframe.moveable.bullet;

public class UCMBullet extends AbstractPolarBullet
{
	private float tanVel;
	
	private float time;
	
	/**
	 * Gets the tangential velocity
	 *
	 * @return tangential velocity
	 */
	public float getTanVel()
	{
		return tanVel;
	}
	
	/**
	 * Sets the tangential velocity
	 *
	 * @param tanVel new tangential velocity
	 */
	public void setTanVel(float tanVel)
	{
		this.tanVel = tanVel;
	}
	
	/**
	 * Initializes the newly created bullet with the given parameters
	 *
	 * @param template BulletTemplate to get parameters from
	 * @param x        starting x position
	 * @param y        starting y position
	 * @param angle    angle to fire at
	 */
	@Override
	public void init(BulletTemplate template, float x, float y, float angle)
	{
		super.init(template, x, y, angle);
		
		tanVel = template.getBullet(this).getTanVel();
		
		time = 0;
	}
	
	/**
	 * Creates a new bullet
	 *
	 * @return new bullet
	 */
	@Override
	public AbstractBullet newInstance()
	{
		return new UCMBullet();
	}
	
	/**
	 * Updates angle and radial velocity every frame
	 *
	 * @param delta time since this method was last called
	 */
	@Override
	public void updatePolar(float delta)
	{
		time += delta;
		
		if (delta != 0)
			setAngle(getAngle() + tanVel * delta / time * 10);
	}
}
