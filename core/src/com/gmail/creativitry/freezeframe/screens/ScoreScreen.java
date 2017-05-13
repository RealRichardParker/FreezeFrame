/**
 * ScoreScreen.java
 * Description
 *
 * @author creativitRy
 * Period: 3
 * Date: 5/13/2017
 */
package com.gmail.creativitry.freezeframe.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gmail.creativitry.freezeframe.FreezeFrame;
import com.gmail.creativitry.freezeframe.database.ScoreData;

public class ScoreScreen extends AbstractScreen
{
	
	/**
	 * Constructs a new screen with the given game instance and size
	 *
	 * @param freezeFrame game instance that shows this screen
	 */
	public ScoreScreen(FreezeFrame freezeFrame, ScoreData scoreData)
	{
		super(freezeFrame, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		System.out.println(scoreData);
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
	 * Called when the screen should render itself.
	 *
	 * @param batch Batch to render on (begin and end NOT automated).
	 * @param delta The time in seconds since the last render.
	 */
	@Override
	public void render(SpriteBatch batch, float delta)
	{
	
	}
}
