/**
 * BulletSprayer.java
 * Spawns bullets in a random pattern
 *
 * @author Gahwon Lee, Tiger Zhang
 * Period: 3
 * Date: 5/4/2017
 */

package com.gmail.creativitry.freezeframe.moveable.bullet;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gmail.creativitry.freezeframe.Player;
import com.gmail.creativitry.freezeframe.behaviors.Loadable;
import com.gmail.creativitry.freezeframe.behaviors.Renderable;
import com.gmail.creativitry.freezeframe.moveable.MoveableManager;
import com.gmail.creativitry.freezeframe.random.LimitedNormalDistribution;
import com.gmail.creativitry.freezeframe.random.RandomGenerator;
import com.gmail.creativitry.freezeframe.random.UniformDistribution;

public class BulletSprayer implements Loadable, Renderable
{
	private static final float DIFFICULTY_CURVE = 0.001f;
	private static final float MIN_HOMING_FIRE_RATE = 0.1f;
	private static final UniformDistribution RADIUS_OFFSET =
		new UniformDistribution(-40f, 40f);
	private static final int MIN_VARIATION = 2;
	private static final int MAX_VARIATION = 6;
	private static final UniformDistribution FIRE_RATE =
		new UniformDistribution(.05f, .3f);
	private static final UniformDistribution FIRE_TIME =
		new UniformDistribution(2f, 7f);
	private static final int MIN_BULLETS_PER_SUBSPRAYER = 1;
	private static final int MAX_BULLETS_PER_SUBSPRAYER = 4;
	private static final int MIN_SUBSPRAYERS = 3;
	private static final int MAX_SUBSPRAYERS = 5;
	private static final float ANGLES_IN_CIRCLE = 360f;
	private static final UniformDistribution ANGLE_BETWEEN_BULLETS =
		new UniformDistribution(60, ANGLES_IN_CIRCLE);
	private static final UniformDistribution STARTING_ANGLE =
		new UniformDistribution(0f, ANGLES_IN_CIRCLE);
	private static final UniformDistribution ANGLE_VELOCITY =
		new UniformDistribution(-400f, 400f);
	private static final UniformDistribution ANGLE_TIME =
		new UniformDistribution(2f, 7f);
	private static final LimitedNormalDistribution FIRE_RATE_HOMING =
		new LimitedNormalDistribution(0.35f, 0.05f, 3);
	
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
	
	private Player player;
	private BulletTemplate homingTemplate;
	//fire rate of homing bullet
	private float fireRateHoming;
	
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
	
	//the current fire time of homing bullet
	private float currFireRateHomingTime;
	
	/**
	 * Creates a random bullet sprayer with the given random generator
	 *
	 * @param moveableManager moveable manager to spawn bullets in
	 * @param random          random number generator
	 * @param x               x position
	 * @param y               y position
	 * @param player          player to target
	 */
	public BulletSprayer(MoveableManager moveableManager, RandomGenerator random,
						 float x, float y, Player player)
	{
		this.moveableManager = moveableManager;
		this.x = x;
		this.y = y;
		this.radiusOffset = random.nextFloat(RADIUS_OFFSET);
		this.bulletTemplate = new BulletTemplate(random);
		int randSize = random.nextInt(MAX_VARIATION - MIN_VARIATION + 1) + MIN_VARIATION;
		this.fireRates = random.getRandFloatArr(randSize, FIRE_RATE);
		this.fireTimes = random.getRandFloatArr(randSize, FIRE_TIME);
		this.bulletsPerSubsprayer = random.nextInt(MAX_BULLETS_PER_SUBSPRAYER
			- MIN_BULLETS_PER_SUBSPRAYER + 1) + MIN_BULLETS_PER_SUBSPRAYER;
		this.numSubsprayers = random.nextInt(MAX_SUBSPRAYERS - MIN_SUBSPRAYERS + 1)
			+ MIN_SUBSPRAYERS;
		this.angleBetweenBullets = random.nextFloat(ANGLE_BETWEEN_BULLETS) / numSubsprayers;
		this.startingAngle = random.nextFloat(STARTING_ANGLE);
		randSize = random.nextInt(MAX_VARIATION) + MIN_VARIATION;
		this.angVels = random.getRandFloatArr(randSize, ANGLE_VELOCITY);
		this.angTime = random.getRandFloatArr(randSize, ANGLE_TIME);
		
		this.player = player;
		homingTemplate = new BulletTemplate(random, true);
		fireRateHoming = random.nextFloat(FIRE_RATE_HOMING);
		
		currRotation = startingAngle;
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
		homingTemplate.load(manager);
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
		homingTemplate.dispose(manager);
	}
	
	/**
	 * Updates this object every frame
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
					float angle = (currRotation + (ANGLES_IN_CIRCLE / numSubsprayers) *
						subsprayer + angleBetweenBullets * bullet + ANGLES_IN_CIRCLE) %
						ANGLES_IN_CIRCLE;
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
			if (currFireRateIndex >= fireTimes.length)
				currFireRateIndex = 0;
		}
		
		if (angVels.length != 0)
		{
			currRotation += delta * angVels[currRotTimeIndex];
			while (currRotation > ANGLES_IN_CIRCLE)
				currRotation -= ANGLES_IN_CIRCLE;
			currRotTime += delta;
			while (currRotTime > angTime[currRotTimeIndex])
			{
				currRotTime -= angTime[currRotTimeIndex];
				currRotTimeIndex++;
				if (currRotTimeIndex >= angTime.length)
					currRotTimeIndex = 0;
				
			}
		}
		
		currFireRateHomingTime += delta;
		while (currFireRateHomingTime >= fireRateHoming)
		{
			currFireRateHomingTime -= fireRateHoming;
			Vector2 position = player.getPosition();
			float angle = MathUtils.atan2(position.y - y, position.x - x) *
				MathUtils.radiansToDegrees;
			
			moveableManager.addBullet(homingTemplate, x, y, angle);
		}
		
		if (fireRateHoming > MIN_HOMING_FIRE_RATE)
			fireRateHoming -= DIFFICULTY_CURVE * delta;
	}
}
