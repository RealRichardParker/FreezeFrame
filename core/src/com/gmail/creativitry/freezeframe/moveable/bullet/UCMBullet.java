/**
 * UCMBullet.java
 * A bullet that moves in circular motion
 *
 * @author creativitRy
 * Period: 3
 * Date: 5/14/2017
 */
package com.gmail.creativitry.freezeframe.moveable.bullet;

public class UCMBullet extends AbstractPolarBullet
{
	private float tanVel;
	
	private float time;
	
	public float getTanVel()
	{
		return tanVel;
	}
	
	public void setTanVel(float tanVel)
	{
		this.tanVel = tanVel;
	}
	
	@Override
	public void init(BulletTemplate template, float x, float y, float angle)
	{
		super.init(template, x, y, angle);
		
		tanVel = template.getBullet(this).getTanVel();
		
		time = 0;
	}
	
	@Override
	public AbstractBullet newInstance()
	{
		return new UCMBullet();
	}
	
	@Override
	public void updatePolar(float delta)
	{
		time += delta;
		
		if (delta != 0)
			setAngle(getAngle() + tanVel * delta / time * 10);
	}
}
