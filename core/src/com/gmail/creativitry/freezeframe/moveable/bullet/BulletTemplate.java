/**
 * BulletTemplate.java
 * Creates a template for newly spawned bullets
 *
 * @author Gahwon Lee, Tiger Zhang
 * Period: 3
 * Date: 5/9/2017
 */

package com.gmail.creativitry.freezeframe.moveable.bullet;

import com.badlogic.gdx.assets.AssetManager;
import com.gmail.creativitry.freezeframe.behaviors.Loadable;
import com.gmail.creativitry.freezeframe.moveable.TextureLoader;
import com.gmail.creativitry.freezeframe.random.*;

public class BulletTemplate implements Loadable
{
	private static final String[] BULLET_TEXTURES =
		{"Ball", "Beam", "Triangular", "Fireball"};
	
	private static final AbstractBullet[] BULLET_TYPES =
		{new Bullet(), new AccelBullet(), new UCMBullet(), new SHMBullet()};
	private static final float[] TYPE_CHANCES = {5, 3, 2, 2};
	private static final AbstractDistribution LIFE =
		new LimitedNormalDistribution(30, 1, 3);
	private static final UniformDistribution VELOCITY =
		new UniformDistribution(100, 200);
	
	private static final UniformDistribution ACCEL =
		new UniformDistribution(-10, -20);
	private static final LimitedNormalDistribution LIMIT =
		new LimitedNormalDistribution(400, 50, 3);
	
	private static final ReflectedUniformDistribution TAN_VEL =
		new ReflectedUniformDistribution(10, 30);
	
	private static final UniformDistribution AMPLITUDE =
		new UniformDistribution(50, 100);
	private static final LimitedNormalDistribution PERIOD =
		new LimitedNormalDistribution(0.001f, 0.00025f, 3);
	private static final LimitedNormalDistribution HOMING_VELOCITY =
		new LimitedNormalDistribution(250, 20, 3);
	
	private StringBuilder path;
	private AbstractBullet bullet;
	private float vel;
	
	/**
	 * Generates a new template with a random bullet type
	 *
	 * @param random random number generator
	 */
	public BulletTemplate(RandomGenerator random)
	{
		this(random, random.choose(BULLET_TYPES, TYPE_CHANCES));
	}
	
	/**
	 * Generates a new template with a regular bullet. Bullet gets a homing velocity if
	 * it is homing
	 * @param random random number generator
	 * @param isHoming true if the bullet should initially follow the player
	 */
	public BulletTemplate(RandomGenerator random, boolean isHoming)
	{
		this(random, new Bullet());
		if (isHoming)
			vel = random.nextFloat(HOMING_VELOCITY);
	}
	
	/**
	 * Generates a new template with the given bullet type
	 *
	 * @param random     random number generator
	 * @param bulletType bullet type
	 */
	private BulletTemplate(RandomGenerator random, AbstractBullet bulletType)
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
	
	/**
	 * Creates a new bullet. It doesn't have parameters set yet
	 * @return new bullet
	 */
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
		if (manager.isLoaded(path.toString()))
			manager.unload(path.toString());
	}
	
	/**
	 * Gets the type of bullet
	 * @return class of bullet being spawned
	 */
	public Class<? extends AbstractBullet> getBulletClass()
	{
		return bullet.getClass();
	}
	
	/**
	 * Gets the radial velocity of the bullet
	 * @return radial velocity
	 */
	public float getVel()
	{
		return vel;
	}
	
	/**
	 * Gets the template bullet for its parameters
	 * @return template bullet
	 */
	public AbstractBullet getBullet()
	{
		return bullet;
	}
	
	/**
	 * Gets the template bullet for its parameters
	 * @param type template bullet's type
	 * @param <T> type of template bullet
	 * @return template bullet casted to its type
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractBullet> T getBullet(T type)
	{
		if (type.getClass() != bullet.getClass())
			throw new IllegalArgumentException("Type is not " + bullet.getClass());
		return (T) bullet;
	}
}
