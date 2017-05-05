/**
 * Loadable.java
 * An object that can be loaded
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/4/2017
 */
package com.gmail.creativitry.freezeframe.behaviors;

import com.badlogic.gdx.assets.AssetManager;

public interface Loadable
{
	/**
	 * Loads all resources necessary for this object.
	 *
	 * @param manager AssetManager to load assets from
	 */
	void load(AssetManager manager);
	
	void dispose(AssetManager manager);
}
