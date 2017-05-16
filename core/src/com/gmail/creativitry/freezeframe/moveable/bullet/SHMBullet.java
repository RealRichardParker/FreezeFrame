/**
 * SHMBullet.java
 * A bullet that oscillates in motion similar to simple harmonic motion
 *
 * @author creativitRy
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
	
	public float getPeriod()
	{
		return period;
	}
	
	public void setPeriod(float period)
	{
		this.period = period;
	}
	
	public float getAmplitude()
	{
		return amplitude;
	}
	
	public void setAmplitude(float amplitude)
	{
		this.amplitude = amplitude;
	}
	
	@Override
	public void init(BulletTemplate template, float x, float y, float angle)
	{
		super.init(template, x, y, angle);
		
		period = template.getBullet(this).getPeriod();
		amplitude = template.getBullet(this).getAmplitude();
		
		startingAngle = angle;
		time = 0;
	}
	
	@Override
	public AbstractBullet newInstance()
	{
		return new SHMBullet();
	}
	
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
