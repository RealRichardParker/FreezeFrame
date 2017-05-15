/**
 * SHMBullet.java
 * Description
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
	
	private float time;
	
	@Override
	public void init(BulletTemplate template, float x, float y, float angle)
	{
		setX(x);
		setY(y);
		setAngle(angle);
		setRadVel(template.getVel());
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
		
		setAngle(getAngle() + amplitude * MathUtils.cosDeg(MathUtils.PI2 / period));
	}
}
