/**
 * MoveableTexture.java
 * Contains information for a Moveable's texture, radius, graze radius, and whether the
 * texture should rotate or not
 *
 * @author Gahwon Lee, Tiger Zhang
 * Period: 3
 * Date: 5/9/2017
 */

package com.gmail.creativitry.freezeframe.moveable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MoveableTexture
{
	private transient Texture texture;
	private float radius;
	private float grazeRadius;
	private boolean rotate;
	
	/**
	 * Gets the texture
	 *
	 * @return texture
	 */
	public Texture getTexture()
	{
		return texture;
	}
	
	/**
	 * Sets the texture
	 *
	 * @param texture new texture
	 */
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
	
	/**
	 * Gets the radius
	 * @return radius
	 */
	public float getRadius()
	{
		return radius;
	}
	
	/**
	 * Gets the graze radius for bullets or magnet radius for coins
	 * @return graze radius
	 */
	public float getGrazeRadius()
	{
		return grazeRadius;
	}
	
	/**
	 * Renders this texture on the screen
	 * @param batch batch to render to
	 * @param x x position
	 * @param y y position
	 * @param angle angle from the sprayer
	 */
	public void render(SpriteBatch batch, float x, float y, float angle)
	{
		if (rotate)
		{
			batch.draw(texture, x - texture.getWidth() / 2, y - texture.getHeight() / 2,
				texture.getWidth() / 2, texture.getHeight() / 2,
				texture.getWidth(), texture.getHeight(), 1, 1, angle, 0, 0,
				texture.getWidth(), texture.getHeight(), false, false);
		}
		else
		{
			batch.draw(texture, x - texture.getWidth() / 2, y - texture.getHeight() / 2);
		}
	}
}
