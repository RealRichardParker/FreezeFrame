/**
 * AccelBullet.java
 * Description
 *
 * @author creativitRy
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
		return new AccelBullet();
	}
	
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
