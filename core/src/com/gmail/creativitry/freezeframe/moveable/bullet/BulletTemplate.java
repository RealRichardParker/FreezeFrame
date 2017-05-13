/**
 * BulletTemplate.java
 * Description
 *
 * @author Tiger
 * Date: 5/9/2017
 */
package com.gmail.creativitry.freezeframe.moveable.bullet;

import com.badlogic.gdx.assets.AssetManager;
import com.gmail.creativitry.freezeframe.RandomGenerator;
import com.gmail.creativitry.freezeframe.behaviors.Loadable;
import com.gmail.creativitry.freezeframe.moveable.TextureLoader;

public class BulletTemplate implements Loadable
{
	private static final String[] BULLETS = {"Ball"};
	private StringBuilder path;
	private AbstractBullet bullet;
	private float vel;
	
	public BulletTemplate(RandomGenerator random)
	{
		bullet = new Bullet();
		bullet.setLife(10);
		vel = 100;
		
		path = new StringBuilder("bullet/");
		path.append(random.choose(BULLETS));
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
