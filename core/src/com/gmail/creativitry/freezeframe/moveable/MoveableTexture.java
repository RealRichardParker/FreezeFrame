/**
 * BulletTexture.java
 * Description
 *
 * @author Tiger
 * Date: 5/9/2017
 */
package com.gmail.creativitry.freezeframe.moveable;

import com.badlogic.gdx.graphics.Texture;

public class MoveableTexture
{
	private transient Texture texture;
	private float size;
	private float radius;
	private float grazeRadius;
	
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
		return size / 2f;
	}
}
