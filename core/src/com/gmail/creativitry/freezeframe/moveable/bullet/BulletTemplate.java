/**
 * BulletTemplate.java
 * Creates a pattern to spawn bullets in
 *
 * @author Tiger
 * Date: 5/9/2017
 */
package com.gmail.creativitry.freezeframe.moveable.bullet;

import com.badlogic.gdx.assets.AssetManager;
import com.gmail.creativitry.freezeframe.behaviors.Loadable;
import com.gmail.creativitry.freezeframe.moveable.TextureLoader;
import com.gmail.creativitry.freezeframe.random.*;

public class BulletTemplate implements Loadable
{
	private static final String[] BULLET_TEXTURES = {"Fireball"};
	
	private static final AbstractBullet[] BULLET_TYPES = {new Bullet(), new AccelBullet(), new UCMBullet(), new SHMBullet()};
	private static final float[] TYPE_CHANCES = {5, 3, 3, 1};
	public static final AbstractDistribution LIFE = new LimitedNormalDistribution(30, 1, 3);
	public static final UniformDistribution VELOCITY = new UniformDistribution(100, 200);
	
	public static final UniformDistribution ACCEL = new UniformDistribution(-10, -20);
	public static final LimitedNormalDistribution LIMIT = new LimitedNormalDistribution(400, 50, 3);
	
	public static final ReflectedUniformDistribution TAN_VEL = new ReflectedUniformDistribution(10, 30);
	
	public static final UniformDistribution AMPLITUDE = new UniformDistribution(50, 100);
	public static final LimitedNormalDistribution PERIOD = new LimitedNormalDistribution(0.001f, 0.00025f, 3);
	public static final LimitedNormalDistribution HOMING_VELOCITY = new LimitedNormalDistribution(250, 20, 3);
	
	private StringBuilder path;
	private AbstractBullet bullet;
	private float vel;
	
	public BulletTemplate(RandomGenerator random)
	{
		this(random, random.choose(BULLET_TYPES, TYPE_CHANCES));
	}
	
	public BulletTemplate(RandomGenerator random, boolean isHoming)
	{
		this(random, new Bullet());
		if (isHoming)
			vel = random.nextFloat(HOMING_VELOCITY);
	}
	
	public BulletTemplate(RandomGenerator random, AbstractBullet bulletType)
	{
		bullet = bulletType;
		System.out.println(bullet);
		if (bullet instanceof AccelBullet)
		{
			AccelBullet b = (AccelBullet) bullet;
			
			b.setAccel(random.nextFloat(ACCEL));
			float limit = random.nextFloat(LIMIT);
			b.setVelMax(limit);
			b.setVelMin(-limit);
		}
		else if (bullet instanceof UCMBullet)
		{
			((UCMBullet) bullet).setTanVel(random.nextFloat(TAN_VEL));
		}
		else if (bullet instanceof SHMBullet)
		{
			SHMBullet b = (SHMBullet) bullet;
			
			float amplitude = random.nextFloat(AMPLITUDE);
			b.setAmplitude(amplitude);
			b.setPeriod(amplitude * random.nextFloat(PERIOD));
		}
		
		bullet.setLife(random.nextFloat(LIFE));
		vel = random.nextFloat(VELOCITY);
		
		path = new StringBuilder("bullet/");
		path.append(random.choose(BULLET_TEXTURES));
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
		//todo
		//manager.unload(path.toString());
	}
	
	public Class<? extends AbstractBullet> getBulletClass()
	{
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
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractBullet> T getBullet(T type)
	{
		if (type.getClass() != bullet.getClass())
			throw new IllegalArgumentException("Type is not " + bullet.getClass());
		return (T) bullet;
	}
}
