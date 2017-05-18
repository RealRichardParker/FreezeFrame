/**
 * AbstractPolarBullet.java
 * A bullet that operates from polar coordinates, converting radius and angle
 * into x and y
 *
 * @author Tiger Zhang
 * Period: 3
 * Date: 5/4/2017
 */

package com.gmail.creativitry.freezeframe.moveable.bullet;

import com.badlogic.gdx.math.MathUtils;

public abstract class AbstractPolarBullet extends AbstractBullet
{
	private static final float ANGLES_IN_CIRCLE = 360;
	
	private float angle;
	private float radVel;
	
	private float distFromCenter;
	
	/**
	 * Gets the angle from the sprayer
	 *
	 * @return angle
	 */
	public float getAngle()
	{
		return angle;
	}
	
	/**
	 * Sets the angle from the sprayer
	 *
	 * @param angle new angle
	 */
	public void setAngle(float angle)
	{
		while (angle < 0)
			angle += ANGLES_IN_CIRCLE;
		while (angle >= ANGLES_IN_CIRCLE)
			angle -= ANGLES_IN_CIRCLE;
		this.angle = angle;
	}
	
	/**
	 * Gets the radial velocity
	 *
	 * @return radial velocity
	 */
	public float getRadVel()
	{
		return radVel;
	}
	
	/**
	 * Sets the radial velocity
	 *
	 * @param radVel new radial velocity
	 */
	public void setRadVel(float radVel)
	{
		this.radVel = radVel;
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
		setAngle(angle);
		setRadVel(template.getVel());
	}
	
	/**
	 * Updates position and velocity every frame based on polar coordinates
	 *
	 * @param delta time since this method was last called
	 */
	@Override
	public void update(float delta)
	{
		updatePolar(delta);
		polarToXY();
		super.update(delta);
	}
	
	/**
	 * Updates angle and radial velocity every frame
	 *
	 * @param delta time since this method was last called
	 */
	public abstract void updatePolar(float delta);
	
	/**
	 * Converts polar coordinates to rectangular coordinates
	 */
	public void polarToXY()
	{
		setVelX(radVel * MathUtils.cosDeg(angle));
		setVelY(radVel * MathUtils.sinDeg(angle));
	}
}
