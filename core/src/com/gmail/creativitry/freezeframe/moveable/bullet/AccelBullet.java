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
	
	public float getAccel()
	{
		return accel;
	}
	
	public void setAccel(float accel)
	{
		this.accel = accel;
	}
	
	public float getVelMin()
	{
		return velMin;
	}
	
	public void setVelMin(float velMin)
	{
		this.velMin = velMin;
	}
	
	public float getVelMax()
	{
		return velMax;
	}
	
	public void setVelMax(float velMax)
	{
		this.velMax = velMax;
	}
	
	@Override
	public void init(BulletTemplate template, float x, float y, float angle)
	{
		super.init(template, x, y, angle);
		
		vel = template.getVel();
		accel = template.getBullet(this).getAccel();
		velMin = template.getBullet(this).getVelMin();
		velMax = template.getBullet(this).getVelMax();
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
