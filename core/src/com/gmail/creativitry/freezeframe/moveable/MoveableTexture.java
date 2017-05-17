/**
 * MoveableTexture.java
 * Contains information for a Moveable's texture
 *
 * @author Tiger
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
	
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
	
	public float getRadius()
	{
		return radius;
	}
	
	public float getGrazeRadius()
	{
		return grazeRadius;
	}
	
	public float getHalfSize()
	{
		return texture.getWidth() / 2f;
	}
	
	public void render(SpriteBatch batch, float x, float y, float angle)
	{
		if (rotate)
		{
			batch.draw(texture, x - texture.getWidth() / 2, y - texture.getHeight() / 2, texture.getWidth() / 2, texture.getHeight() / 2,
				texture.getWidth(), texture.getHeight(), 1, 1, angle, 0, 0,
				texture.getWidth(), texture.getHeight(), false, false);
		}
		else
		{
			batch.draw(texture, x - getHalfSize(), y - getHalfSize());
		}
	}
}
