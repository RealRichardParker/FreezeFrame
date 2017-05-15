package com.gmail.creativitry.freezeframe.moveable.item;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gmail.creativitry.freezeframe.behaviors.Loadable;
import com.gmail.creativitry.freezeframe.behaviors.Renderable;
import com.gmail.creativitry.freezeframe.moveable.MoveableManager;
import com.gmail.creativitry.freezeframe.moveable.MoveableTexture;
import com.gmail.creativitry.freezeframe.moveable.TextureLoader;
import com.gmail.creativitry.freezeframe.random.RandomGenerator;
import com.gmail.creativitry.freezeframe.random.UniformDistribution;

/**
 * Description
 *
 * @author Gahwon Lee
 *         Date: 5/15/2017.
 */
public class ItemSpawner implements Loadable, Renderable
{
	private static final String[] ITEM_TEXTURES = {};
	public static final UniformDistribution TIME = new UniformDistribution(3, 5);
	
	private MoveableManager moveableManager;
	private RandomGenerator random;
	
	private float timeLeft;
	
	private MoveableTexture coinTexture;
	private MoveableTexture healthTexture;
	private MoveableTexture magnetTexture;
	private MoveableTexture shieldTexture;
	
	public ItemSpawner(MoveableManager moveableManager, RandomGenerator random)
	{
		this.moveableManager = moveableManager;
		this.random = random;
		
		timeLeft = random.nextFloat(TIME);
	}
	
	/**
	 * Loads all resources necessary for this object.
	 *
	 * @param manager AssetManager to load assets from
	 */
	@Override
	public void load(AssetManager manager)
	{
		StringBuilder sb = new StringBuilder("item/");
		
		coinTexture = TextureLoader.load(sb.append("coin").toString(), manager);
		healthTexture = TextureLoader.load(sb.append("health").toString(), manager);
		magnetTexture = TextureLoader.load(sb.append("magnet").toString(), manager);
		shieldTexture = TextureLoader.load(sb.append("shield").toString(), manager);
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
