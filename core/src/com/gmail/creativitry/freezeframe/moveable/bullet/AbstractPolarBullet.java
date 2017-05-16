/**
 * AbstractPolarBullet.java
 * Describes a bullet with circular movement
 *
 * @author Tiger Zhang
 * Date: 5/4/2017
 * Period 3
 */
package com.gmail.creativitry.freezeframe.moveable.bullet;

import com.badlogic.gdx.math.MathUtils;

public abstract class AbstractPolarBullet extends AbstractBullet
{
	private float angle;
	private float radVel;
	private final float DEGREES_IN_CIRCLE = 360;
	
	private float distFromCenter;
	
	public float getAngle()
	{
		return angle;
	}
	
	public void setAngle(float angle)
	{
		while (angle < 0)
			angle += DEGREES_IN_CIRCLE;
		while (angle >= 360)
			angle -= DEGREES_IN_CIRCLE;
		this.angle = angle;
	}
	
	public float getRadVel()
	{
		return radVel;
	}
	
	public void setRadVel(float radVel)
	{
		this.radVel = radVel;
	}
	
	@Override
	public void init(BulletTemplate template, float x, float y, float angle)
	{
		super.init(template, x, y, angle);
		setAngle(angle);
		setRadVel(template.getVel());
	}
	
	/**
	 * Updates position and velocity every frame
	 *
	 * @param delta
	 */
	@Override
	public void update(float delta)
	{
		updatePolar(delta);
		polarToXY();
		super.update(delta);
	}
	
	public abstract void updatePolar(float delta);
	
	public void polarToXY()
	{
		setVelX(radVel * MathUtils.cosDeg(angle));
		setVelY(radVel * MathUtils.sinDeg(angle));
	}
}
