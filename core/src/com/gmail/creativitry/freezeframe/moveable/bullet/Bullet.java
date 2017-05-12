/**
 * Bullet.java
 * Description
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
		setX(x);
		setY(y);
		setVelX(MathUtils.cosDeg(angle) * template.getVel());
		setVelY(MathUtils.sinDeg(angle) * template.getVel());
		setTexture(template.getBullet().getTexture());
		setLife(template.getBullet().getLife());
	}
	
	@Override
	public AbstractBullet newInstance()
	{
		return new Bullet();
	}
}
