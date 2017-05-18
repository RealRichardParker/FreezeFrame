/**
 * SHMBullet.java
 * A bullet that oscillates in motion similar to simple harmonic motion
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/14/2017
 */

package com.gmail.creativitry.freezeframe.moveable.bullet;

import com.badlogic.gdx.math.MathUtils;

public class SHMBullet extends AbstractPolarBullet
{
	private float period;
	private float amplitude;
	
	private float startingAngle;
	private float time;
	
	/**
	 * Gets the period (seconds per oscillation)
	 *
	 * @return period
	 */
	public float getPeriod()
	{
		return period;
	}
	
	/**
	 * Sets the period (seconds per oscillation)
	 *
	 * @param period new period
	 */
	public void setPeriod(float period)
	{
		this.period = period;
	}
	
	/**
	 * Gets the amplitude (size of oscillation)
	 *
	 * @return amplitude
	 */
	public float getAmplitude()
	{
		return amplitude;
	}
	
	/**
	 * Sets the amplitude (size of oscillation)
	 *
	 * @param amplitude new amplitude
	 */
	public void setAmplitude(float amplitude)
	{
		this.amplitude = amplitude;
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
		
		period = template.getBullet(this).getPeriod();
		amplitude = template.getBullet(this).getAmplitude();
		
		startingAngle = angle;
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
		return new SHMBullet();
	}
	
	/**
	 * Updates angle and radial velocity every frame
	 *
	 * @param delta time since this method was last called
	 */
	@Override
	public void updatePolar(float delta)
	{
		//A * cos(w * t)
		//T = 2 * pi / w = 1 / f = seconds per revolution
		//w = 2 * pi / T
		
		time += delta;
		while (time >= period)
			time -= period;
		
		setAngle(startingAngle + amplitude * MathUtils.cosDeg(MathUtils.PI2 / period * time));
	}
}
