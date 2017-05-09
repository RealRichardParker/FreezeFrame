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
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.gmail.creativitry.freezeframe.behaviors.Loadable;
import com.gmail.creativitry.freezeframe.behaviors.Renderable;
import com.gmail.creativitry.freezeframe.moveable.MoveableManager;

import java.util.Random;

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
	
	//the BulletSprayer's time, will increment from 0 to currRotTIme and then reset to 0
	private float currRotTime;
	private int currRotTimeINdex;
	
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
		
	}
}
