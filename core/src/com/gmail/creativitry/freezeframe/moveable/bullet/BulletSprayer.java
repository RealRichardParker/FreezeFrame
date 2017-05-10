/**
 * BulletSprayer.java
 * Description
 *
 * @author Tiger
 * Date: 5/4/2017.
 */
package com.gmail.creativitry.freezeframe.moveable.bullet;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import com.gmail.creativitry.freezeframe.behaviors.Loadable;
import com.gmail.creativitry.freezeframe.behaviors.Renderable;
import com.gmail.creativitry.freezeframe.moveable.MoveableManager;

public class BulletSprayer implements Loadable, Renderable
{
	private MoveableManager moveableManager;
	//position
	private float x;
	private float y;
	
	//how far off center bullets spawn
	private float radiusOffset;
	
	//how bullets should function
	private BulletTemplate bulletTemplete;
	
	//how much time between bullet spawns
	private float[] fireRates;
	//how long to use a certain fireRate
	private float[] fireTimes;
	
	private int bulletsPerSubsprayer;
	//angle between bullets pf the subsprayer
	private float angleBetweenBullets;
	private int numSubsprayers;
	
	//starting angle of the BulletSprayer
	private float startingAngle;
	//how fast BulletSprayer rotates
	private float[] angVels;
	//how long for a given speed the BulletSprayer will rotate
	private float[] angTime;
	
	//the BulletSprayer's time, will increment from 0 to fireRate and then reset to 0
	private float currFireRateTime;
	private int currFireRateIndex;
	
	// the current time using the current fireRate
	private float currFireTime;
	
	//the BulletSprayer's time, will increment from 0 to currRotTIme and then reset to 0
	private float currRotTime;
	private int currRotTimeIndex;
	
	//the current rotation
	private float currRotation;
	
	
	//TODO: things
	
	public BulletSprayer(MoveableManager moveableManager, RandomXS128 random)
	{
		this.moveableManager = moveableManager;
		
		//TODO: randomize
		this.x = x;
		this.y = y;
		this.radiusOffset = radiusOffset;
		this.bulletTemplete = bulletTemplete;
		this.fireRates = fireRates;
		this.fireTimes = fireTimes;
		this.bulletsPerSubsprayer = bulletsPerSubsprayer;
		this.angleBetweenBullets = angleBetweenBullets;
		this.numSubsprayers = numSubsprayers;
		this.startingAngle = startingAngle;
		this.angVels = angVels;
		this.angTime = angTime;
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
	
	/**
	 * Renders this object
	 *
	 * @param batch Batch to render to
	 * @param delta time in seconds since last tick
	 */
	@Override
	public void render(SpriteBatch batch, float delta)
	{
		currFireRateTime += delta;
		while (currFireRateTime >= fireRates[currFireRateIndex])
		{
			currFireRateTime -= fireRates[currFireRateIndex];
			for (int subsprayer = 0; subsprayer < numSubsprayers; subsprayer++)
			{
				for (int bullet = 0; bullet < bulletsPerSubsprayer; bullet++)
				{
					float angle = (currRotation + (360f / numSubsprayers) *
						subsprayer + angleBetweenBullets * bullet + 360) % 360;
					float angleX = MathUtils.cosDeg(angle);
					float angleY = MathUtils.sinDeg(angle);
					
					moveableManager.add(bulletTemplete.spawnBullet(x + angleX
						* radiusOffset, y + angleY * radiusOffset, angle));
					
				}
			}
		}
		currFireTime += delta;
		while (currFireTime >= fireTimes[currFireRateIndex])
		{
			currFireTime -= fireTimes[currFireRateIndex];
			currFireRateIndex++;
			if(currFireRateIndex >= fireTimes.length)
				currFireRateIndex = 0;
		}
		
		if (angVels.length != 0)
		{
			currRotation += delta * angVels[currRotTimeIndex];
			while(currRotation > 360)
				currRotation -= 360;
			currRotTime += delta;
			while(currRotTime > angTime[currRotTimeIndex])
			{
				currRotTime -= angTime[currRotTimeIndex];
				currRotTimeIndex++;
				if(currRotTimeIndex >= angTime.length)
					currRotTimeIndex = 0;
				
			}
		}
		
	}
}
