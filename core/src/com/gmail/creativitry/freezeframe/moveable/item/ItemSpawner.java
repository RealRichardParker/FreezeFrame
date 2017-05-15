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

/**
 * Description
 *
 * @author Gahwon Lee
 *         Date: 5/15/2017.
 */
public class ItemSpawner implements Loadable, Renderable
{
	private static final String[] ITEM_TEXTURES = {};
	private static final UniformDistribution TIME = new UniformDistribution(3, 5);
	private static final int THRESHOLD = 25;
	private static final UniformDistribution X_POS = new UniformDistribution(10, GameScreen.GAME_WIDTH - 10);
	private static final float Y_POS = GameScreen.GAME_HEIGHT + 10;
	public static final float[] ITEM_CHANCES = {5, 1, 1, 3};
	
	private MoveableManager moveableManager;
	private RandomGenerator random;
	private Player player;
	
	private float timeLeft;
	
	private MoveableTexture coinTexture;
	private MoveableTexture healthTexture;
	private MoveableTexture magnetTexture;
	private MoveableTexture shieldTexture;
	
	public ItemSpawner(MoveableManager moveableManager, RandomGenerator random, Player player)
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
	
	private AbstractItem spawnRandom()
	{
		for (int i = 0; i < THRESHOLD; i++)
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
					if (player.getHealth() < Player.HEALTH_MAX)
					{
						return new HealthItem(random.nextFloat(X_POS), Y_POS, healthTexture);
					}
					break;
				}
				case MagnetItem:
				{
					if (!player.isMagnet())
					{
						return new MagnetItem(random.nextFloat(X_POS), Y_POS, magnetTexture);
					}
					break;
				}
				case ShieldItem:
				{
					if (!player.isShield())
					{
						return new ShieldItem(random.nextFloat(X_POS), Y_POS, shieldTexture);
					}
					break;
				}
				default:
				{
					throw new IllegalArgumentException("Unknown item");
				}
			}
		}
		
		return null;
	}
	
	private enum ItemEnum
	{
		CoinItem,
		HealthItem,
		MagnetItem,
		ShieldItem
	}
}
