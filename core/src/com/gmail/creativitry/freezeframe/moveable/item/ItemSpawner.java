/**
 * ItemSpawner.java
 * Spawns an item every once in a while
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/15/2017
 */

package com.gmail.creativitry.freezeframe.moveable.item;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gmail.creativitry.freezeframe.Player;
import com.gmail.creativitry.freezeframe.behaviors.Loadable;
import com.gmail.creativitry.freezeframe.behaviors.Renderable;
import com.gmail.creativitry.freezeframe.moveable.MoveableManager;
import com.gmail.creativitry.freezeframe.moveable.MoveableTexture;
import com.gmail.creativitry.freezeframe.moveable.TextureLoader;
import com.gmail.creativitry.freezeframe.random.RandomGenerator;
import com.gmail.creativitry.freezeframe.random.UniformDistribution;
import com.gmail.creativitry.freezeframe.screens.GameScreen;

public class ItemSpawner implements Loadable, Renderable
{
	private static final UniformDistribution TIME =
		new UniformDistribution(3, 5);
	private static final UniformDistribution X_POS =
		new UniformDistribution(10, GameScreen.GAME_WIDTH - 10);
	private static final float Y_POS = GameScreen.GAME_HEIGHT + 10;
	public static final float[] ITEM_CHANCES = {8, 1, 1, 3};
	
	private MoveableManager moveableManager;
	private RandomGenerator random;
	private Player player;
	
	private float timeLeft;
	
	private MoveableTexture coinTexture;
	private MoveableTexture healthTexture;
	private MoveableTexture magnetTexture;
	private MoveableTexture shieldTexture;
	
	/**
	 * Constructs a new item spawner with the given parameters
	 *
	 * @param moveableManager MoveableManager to spawn items in
	 * @param random          random number generator
	 * @param player          player to give item effects to
	 */
	public ItemSpawner(MoveableManager moveableManager, RandomGenerator random,
					   Player player)
	{
		this.moveableManager = moveableManager;
		this.random = random;
		this.player = player;
		
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
		final String dir = "item/";
		
		coinTexture = TextureLoader.load(dir + "CoinItem", manager);
		healthTexture = TextureLoader.load(dir + "HealthItem", manager);
		magnetTexture = TextureLoader.load(dir + "MagnetItem", manager);
		shieldTexture = TextureLoader.load(dir + "ShieldItem", manager);
	}
	
	/**
	 * Disposes all resources loaded by this object.
	 *
	 * @param manager AssetManager to dispose assets from
	 */
	@Override
	public void dispose(AssetManager manager)
	{
		manager.unload("item/CoinItem.png");
		manager.unload("item/HealthItem.png");
		manager.unload("item/MagnetItem.png");
		manager.unload("item/ShieldItem.png");
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
		timeLeft -= delta;
		
		if (timeLeft < 0)
		{
			AbstractItem item = spawnRandom();
			
			if (item != null)
			{
				moveableManager.addItem(item);
			}
			
			timeLeft += random.nextFloat(TIME);
		}
	}
	
	/**
	 * Spawns a random item
	 *
	 * @return randomly spawned item
	 */
	private AbstractItem spawnRandom()
	{
		ItemEnum itemChooser = random.choose(ItemEnum.values(), ITEM_CHANCES);
		
		switch (itemChooser)
		{
			case CoinItem:
			{
				return new CoinItem(random.nextFloat(X_POS), Y_POS, coinTexture);
			}
			case HealthItem:
			{
				return new HealthItem(random.nextFloat(X_POS), Y_POS, healthTexture);
				
			}
			case MagnetItem:
			{
				return new MagnetItem(random.nextFloat(X_POS), Y_POS, magnetTexture);
				
			}
			case ShieldItem:
			{
				return new ShieldItem(random.nextFloat(X_POS), Y_POS, shieldTexture);
			}
		}
		throw new IllegalArgumentException("Unknown item");
	}
	
	/**
	 * All the spawnable items
	 *
	 * @author Gahwon Lee
	 * Period: 3
	 * Date: 5/15/2017
	 */
	private enum ItemEnum
	{
		CoinItem,
		HealthItem,
		MagnetItem,
		ShieldItem
	}
}
