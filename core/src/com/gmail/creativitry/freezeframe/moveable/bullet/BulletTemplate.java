/**
 * BulletTemplate.java
 * Description
 *
 * @author Tiger
 * Date: 5/9/2017
 */
package com.gmail.creativitry.freezeframe.moveable.bullet;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.RandomXS128;
import com.gmail.creativitry.freezeframe.behaviors.Loadable;
import com.gmail.creativitry.freezeframe.moveable.TextureLoader;

public class BulletTemplate implements Loadable
{
	public static final int NUM_BULLET_TEXTURES = 1;
	private StringBuilder path;
	private AbstractBullet bullet;
	private float vel;
	
	public BulletTemplate(RandomXS128 random)
	{
		bullet = new Bullet();
		bullet.setLife(10);
		vel = 100;
		path = new StringBuilder("bullet/B");
		path.append(random.nextInt(NUM_BULLET_TEXTURES));
	}
	
	
	public AbstractBullet spawnBullet()
	{
		return bullet.newInstance();
	}
	
	/**
	 * Loads all resources necessary for this object.
	 *
	 * @param manager AssetManager to load assets from
	 */
	@Override
	public void load(AssetManager manager)
	{
		bullet.setTexture(TextureLoader.load(path.toString(), manager));
	}
	
	/**
	 * Disposes all resources loaded by this object.
	 *
	 * @param manager AssetManager to dispose assets from
	 */
	@Override
	public void dispose(AssetManager manager)
	{
		
	}
	
	public Class<? extends AbstractBullet> getBulletClass()
	{
		//todo
		return bullet.getClass();
	}
	
	public float getVel()
	{
		return vel;
	}
	
	public AbstractBullet getBullet()
	{
		return bullet;
	}
}
