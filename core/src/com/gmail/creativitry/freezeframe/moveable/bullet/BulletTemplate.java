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

public class BulletTemplate implements Loadable
{
	
	
	public BulletTemplate(RandomXS128 random)
	{
		
	}
	
	
	public AbstractBullet spawnBullet()
	{
		//TODO: rest of the class
		return null;
	}
	
	/**
	 * Loads all resources necessary for this object.
	 *
	 * @param manager AssetManager to load assets from
	 */
	@Override
	public void load(AssetManager manager)
	{
		
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
		return null;
	}
}
