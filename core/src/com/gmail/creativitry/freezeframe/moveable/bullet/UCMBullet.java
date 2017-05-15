/**
 * UCMBullet.java
 * Description
 *
 * @author creativitRy
 * Period: 3
 * Date: 5/14/2017
 */
package com.gmail.creativitry.freezeframe.moveable.bullet;

public class UCMBullet extends AbstractPolarBullet
{
	private float tanVel;
	
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
		return new UCMBullet();
	}
	
	@Override
	public void updatePolar(float delta)
	{
		setAngle(getAngle() + tanVel * delta);
	}
}
