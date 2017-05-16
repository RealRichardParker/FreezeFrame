/**
 * Bullet.java
 * A bullet that moves with constant velocity
 *
 * @author creativitRy
 * Period: 3
 * Date: 5/11/2017
 */
package com.gmail.creativitry.freezeframe.moveable.bullet;

import com.badlogic.gdx.math.MathUtils;

public class Bullet extends AbstractBullet
{
	@Override
	public void init(BulletTemplate template, float x, float y, float angle)
	{
		super.init(template, x, y, angle);
		
		setVelX(MathUtils.cosDeg(angle) * template.getVel());
		setVelY(MathUtils.sinDeg(angle) * template.getVel());
	}
	
	@Override
	public AbstractBullet newInstance()
	{
		return new Bullet();
	}
}
