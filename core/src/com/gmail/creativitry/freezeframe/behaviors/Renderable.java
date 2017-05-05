/**
 * Renderable.java
 * An object that can be updated or rendered
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/4/2017
 */
package com.gmail.creativitry.freezeframe.behaviors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Renderable
{
	/**
	 * Renders this object
	 *
	 * @param batch Batch to render to
	 * @param delta time in seconds since last tick
	 */
	void render(SpriteBatch batch, float delta);
}
