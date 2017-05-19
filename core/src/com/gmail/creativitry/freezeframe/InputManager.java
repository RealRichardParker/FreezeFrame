/**
 * InputManager.java
 * Checks whether an input is pressed
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/5/2017
 */

package com.gmail.creativitry.freezeframe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gmail.creativitry.freezeframe.screens.AbstractScreen;

public class InputManager
{
	public static int[] UP = {Keys.UP, Keys.W, Keys.NUMPAD_8};
	public static int[] DOWN = {Keys.DOWN, Keys.S, Keys.NUMPAD_5};
	public static int[] LEFT = {Keys.LEFT, Keys.A, Keys.NUMPAD_4};
	public static int[] RIGHT = {Keys.RIGHT, Keys.D, Keys.NUMPAD_6};
	public static int[] FOCUS = {Keys.SHIFT_LEFT, Keys.SHIFT_RIGHT};
	public static int[] CONFIRM = {Keys.ENTER};
	public static int[] MOVE_TIME = {Keys.SPACE};
	
	/**
	 * Checks whether the given key is in the given array of keys
	 *
	 * @param key  key to find
	 * @param keys array of keys to find the key from
	 * @return true if the key was found, false otherwise
	 */
	public static boolean keyUp(int key, int[] keys)
	{
		for (int i : keys)
		{
			if (i == key)
				return true;
		}
		return false;
	}
	
	/**
	 * Checks whether any of the keys is pressed
	 * @param keys array of keys to check
	 * @return true if a key is pressed, false otherwise
	 */
	public static boolean isPressed(int[] keys)
	{
		for (int key : keys)
		{
			if (Gdx.input.isKeyPressed(key))
				return true;
		}
		return false;
	}
}
