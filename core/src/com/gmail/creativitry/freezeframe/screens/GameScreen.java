/**
 * GameScreen.java
 * Let the player play the game
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/4/2017
 */
package com.gmail.creativitry.freezeframe.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.RandomXS128;
import com.gmail.creativitry.freezeframe.FreezeFrame;
import com.gmail.creativitry.freezeframe.Player;
import com.gmail.creativitry.freezeframe.bullet.BulletSprayer;

public class GameScreen extends AbstractScreen
{
	public static final float GAME_WIDTH = SCREEN_WIDTH / 2;
	public static final float GAME_HEIGHT = SCREEN_HEIGHT / 2;
	private RandomXS128 random;
	
	private Player player;
	private BulletSprayer sprayer;
	
	/**
	 * Constructs a new game screen with the given game instance and the random number generator
	 * @param freezeFrame game instance that shows this screen
	 * @param random random number generator
	 */
	public GameScreen(FreezeFrame freezeFrame, RandomXS128 random)
	{
		super(freezeFrame, GAME_WIDTH, GAME_HEIGHT);
		
		this.random = random;
		player = new Player();
		addInputProcessor(player);
	}
	
	/**
	 * Loads all resources necessary for this object.
	 *
	 * @param manager AssetManager to load assets from
	 */
	@Override
	public void load(AssetManager manager)
	{
		player.load(manager);
	}
	
	/**
	 * Disposes all resources loaded by this object.
	 *
	 * @param manager AssetManager to dispose assets from
	 */
	@Override
	public void dispose(AssetManager manager)
	{
		player.dispose(manager);
	}
	
	/**
	 * Called when the screen should render itself.
	 *
	 * @param batch Batch to render on (begin and end NOT automated).
	 * @param delta The time in seconds since the last render.
	 */
	@Override
	public void render(SpriteBatch batch, float delta)
	{
		batch.begin();
		player.render(batch, delta);
		batch.end();
	}
}
