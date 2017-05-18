/**
 * Bullet.java
 * A bullet that moves with constant velocity
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/11/2017
 */

package com.gmail.creativitry.freezeframe.moveable.bullet;

import com.badlogic.gdx.math.MathUtils;

public class Bullet extends AbstractBullet
{
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
		
		setVelX(MathUtils.cosDeg(angle) * template.getVel());
		setVelY(MathUtils.sinDeg(angle) * template.getVel());
	}
	
	/**
	 * Creates a new bullet
	 *
	 * @return new bullet
	 */
	@Override
	public AbstractBullet newInstance()
	{
		return new Bullet();
	}
}
