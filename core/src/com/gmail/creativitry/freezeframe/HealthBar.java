/**
 * HealthBar.java
 * //TODO
 *
 * @author creativitRy
 * Period: 3
 * Date: 5/13/2017
 */
package com.gmail.creativitry.freezeframe;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gmail.creativitry.freezeframe.behaviors.Loadable;

public class HealthBar extends Actor implements Loadable
{
	private NinePatch ninePatch;
	private int size;
	
	private int health;
	
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
	
	public void setHealth(int health)
	{
		this.health = health;
		setWidth(size * health);
	}
	
	/**
	 * Draws the actor. The batch is configured to draw in the parent's coordinate system.
	 * {@link Batch#draw(TextureRegion, float, float, float, float, float, float, float, float, float)
	 * This draw method} is convenient to draw a rotated and scaled TextureRegion. {@link Batch#begin()} has already been called on
	 * the batch. If {@link Batch#end()} is called to draw without the batch then {@link Batch#begin()} must be called before the
	 * method returns.
	 * <p>
	 * The default implementation does nothing.
	 *
	 * @param batch
	 * @param parentAlpha Should be multiplied with the actor's alpha, allowing a parent's alpha to affect all children.
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
