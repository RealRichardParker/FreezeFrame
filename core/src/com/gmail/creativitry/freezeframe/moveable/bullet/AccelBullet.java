/**
 * AccelBullet.java
 * Describes a bullet that moves with changing velocity
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/14/2017
 */

package com.gmail.creativitry.freezeframe.moveable.bullet;

public class AccelBullet extends AbstractPolarBullet
{
	private float vel;
	private float accel;
	private float velMin;
	private float velMax;
	
	/**
	 * Gets the radial acceleration
	 *
	 * @return acceleration
	 */
	public float getAccel()
	{
		return accel;
	}
	
	/**
	 * Sets the radial acceleration
	 *
	 * @param accel new acceleration
	 */
	public void setAccel(float accel)
	{
		this.accel = accel;
	}
	
	/**
	 * Gets the minimum velocity
	 *
	 * @return minimum velocity
	 */
	public float getVelMin()
	{
		return velMin;
	}
	
	/**
	 * Sets the minimum velocity
	 *
	 * @param velMin new minimum velocity
	 */
	public void setVelMin(float velMin)
	{
		this.velMin = velMin;
	}
	
	/**
	 * Gets the maximum velocity
	 *
	 * @return maximum velocity
	 */
	public float getVelMax()
	{
		return velMax;
	}
	
	/**
	 * Sets the maximum velocity
	 *
	 * @param velMax new maximum velocity
	 */
	public void setVelMax(float velMax)
	{
		this.velMax = velMax;
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
		
		vel = template.getVel();
		accel = template.getBullet(this).getAccel();
		velMin = template.getBullet(this).getVelMin();
		velMax = template.getBullet(this).getVelMax();
	}
	
	/**
	 * Creates a new bullet
	 *
	 * @return new bullet
	 */
	@Override
	public AbstractBullet newInstance()
	{
		return new AccelBullet();
	}
	
	/**
	 * Updates angle and radial velocity every frame
	 *
	 * @param delta time since this method was last called
	 */
	@Override
	public void updatePolar(float delta)
	{
		vel += accel * delta;
		
		if (vel < velMin)
			setRadVel(velMin);
		else if (vel > velMax)
			setRadVel(velMax);
		else
			setRadVel(vel);
	}
}
