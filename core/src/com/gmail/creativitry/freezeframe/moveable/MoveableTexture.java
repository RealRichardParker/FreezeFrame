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
	private float radius;
	private float grazeRadius;
	
	protected void setTexture(Texture texture)
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
	
}
