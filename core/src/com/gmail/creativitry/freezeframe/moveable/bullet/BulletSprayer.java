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
import com.gmail.creativitry.freezeframe.RandomGenerator;
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
	private BulletTemplate bulletTemplate;
	
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
	
	public BulletSprayer(MoveableManager moveableManager, RandomGenerator random, float x, float y)
	{
		this.moveableManager = moveableManager;
		this.x = x;
		this.y = y;
		this.radiusOffset = random.nextFloat(-40f, 40f);
		this.bulletTemplate = new BulletTemplate(random);
		int randSize = random.nextInt(5) + 2;
		this.fireRates = random.getRandFloatArr(randSize, .05f, .3f);
		this.fireTimes = random.getRandFloatArr(randSize, 2f, 7f);
		this.bulletsPerSubsprayer = random.nextInt(3) + 1;
		this.numSubsprayers = random.nextInt(2) + 3;
		this.angleBetweenBullets = random.nextFloat(15f, 360f / numSubsprayers);
		this.startingAngle = random.nextFloat(0f, 360f);
		randSize = random.nextInt(5) + 2;
		this.angVels = random.getRandFloatArr(randSize, -400f, 400f);
		this.angTime = random.getRandFloatArr(randSize, 2f, 7f);
	}
	
	private static float rand(RandomXS128 random, float lower, float higher)
	{
		return random.nextFloat() * (higher - lower) + lower;
	}
	
	private static float[] getRandFloatArr(RandomXS128 random, int size, float lower, float higher)
	{
		float[] arr = new float[size];
		
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = rand(random, lower, higher);
		}
		
		return arr;
	}
	
	/**
	 * Loads all resources necessary for this object.
	 *
	 * @param manager AssetManager to load assets from
	 */
	@Override
	public void load(AssetManager manager)
	{
		bulletTemplate.load(manager);
	}
	
	/**
	 * Disposes all resources loaded by this object.
	 *
	 * @param manager AssetManager to dispose assets from
	 */
	@Override
	public void dispose(AssetManager manager)
	{
		bulletTemplate.dispose(manager);
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
					
					moveableManager.addBullet(bulletTemplate, x + angleX
						* radiusOffset, y + angleY * radiusOffset, angle);
					
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
