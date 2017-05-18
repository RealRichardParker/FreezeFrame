/**
 * HealthBar.java
 * Displays the health of the player
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/13/2017
 */

package com.gmail.creativitry.freezeframe;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gmail.creativitry.freezeframe.behaviors.Loadable;

public class HealthBar extends Actor implements Loadable
{
	private NinePatch ninePatch;
	private int size;
	
	private int health;
	
	/**
	 * Constructs a new healthbar with the given health
	 *
	 * @param health starting health to display
	 */
	public HealthBar(int health)
	{
		this.health = health;
	}
	
	/**
	 * Loads all resources necessary for this object.
	 *
	 * @param manager AssetManager to load assets from
	 */
	@Override
	public void load(AssetManager manager)
	{
		final String fileName = "heart.png";
		
		manager.load(fileName, Texture.class);
		manager.finishLoadingAsset(fileName);
		Texture texture = manager.get(fileName, Texture.class);
		
		size = texture.getWidth();
		setHealth(health);
		setHeight(size);
		
		ninePatch = new NinePatch(texture);
	}
	
	/**
	 * Disposes all resources loaded by this object.
	 *
	 * @param manager AssetManager to dispose assets from
	 */
	@Override
	public void dispose(AssetManager manager)
	{
		manager.unload("heart.png");
	}
	
	/**
	 * Sets the new health to display
	 * @param health new health
	 */
	public void setHealth(int health)
	{
		this.health = health;
		setWidth(size * health);
	}
	
	/**
	 * Draws the healthbar to the screen
	 *
	 * @param batch batch to render to
	 * @param parentAlpha unused
	 */
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
		
		for (int i = 0; i < health; i++)
		{
			ninePatch.draw(batch, getX() + i * size, getY(), size, size);
		}
	}
}
