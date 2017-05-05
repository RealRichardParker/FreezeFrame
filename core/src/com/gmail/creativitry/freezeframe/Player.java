/**
 * Player.java
 * Description
 *
 * @author Tiger
 * Date: 5/2/2017.
 */
package com.gmail.creativitry.freezeframe;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.gmail.creativitry.freezeframe.behaviors.Loadable;
import com.gmail.creativitry.freezeframe.behaviors.Renderable;
import com.gmail.creativitry.freezeframe.screens.GameScreen;

public class Player implements InputProcessor, Loadable, Renderable
{
	private static final float SPEED = 500;
	private static final float FOCUS_SPEED_MODIFIER = 0.5f;
	private static final int SPRITE_HALF_SIZE = 16;
	
	private Texture texture;
	
	private Vector2 position;
	private Vector2 velocityDir;
	
	private float health;
	private float radius;
	
	public float getHealth()
	{
		return health;
	}
	
	public float getRadius()
	{
		return radius;
	}
	
	/**
	 * Called when a key was pressed
	 *
	 * @param keycode one of the constants in Input.Keys
	 * @return whether the input was processed
	 */
	@Override
	public boolean keyDown(int keycode)
	{
		if (InputManager.keyUp(keycode, InputManager.UP))
		{
			if (InputManager.isPressed(InputManager.DOWN))
				velocityDir.y = 0;
			else
				velocityDir.y = 1;
			return true;
		}
		else if (InputManager.keyUp(keycode, InputManager.DOWN))
		{
			if (InputManager.isPressed(InputManager.UP))
				velocityDir.y = 0;
			else
				velocityDir.y = -1;
			return true;
		}
		else if (InputManager.keyUp(keycode, InputManager.LEFT))
		{
			if (InputManager.isPressed(InputManager.RIGHT))
				velocityDir.x = 0;
			else
				velocityDir.x = -1;
			return true;
		}
		else if (InputManager.keyUp(keycode, InputManager.RIGHT))
		{
			if (InputManager.isPressed(InputManager.LEFT))
				velocityDir.x = 0;
			else
				velocityDir.x = 1;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Called when a key was released
	 *
	 * @param keycode one of the constants in Input.Keys
	 * @return whether the input was processed
	 */
	@Override
	public boolean keyUp(int keycode)
	{
		if (InputManager.keyUp(keycode, InputManager.UP))
		{
			if (InputManager.isPressed(InputManager.DOWN))
				velocityDir.y = -1;
			else
				velocityDir.y = 0;
			return true;
		}
		else if (InputManager.keyUp(keycode, InputManager.DOWN))
		{
			if (InputManager.isPressed(InputManager.UP))
				velocityDir.y = 1;
			else
				velocityDir.y = 0;
			return true;
		}
		else if (InputManager.keyUp(keycode, InputManager.LEFT))
		{
			if (InputManager.isPressed(InputManager.RIGHT))
				velocityDir.x = 1;
			else
				velocityDir.x = 0;
			return true;
		}
		else if (InputManager.keyUp(keycode, InputManager.RIGHT))
		{
			if (InputManager.isPressed(InputManager.LEFT))
				velocityDir.x = -1;
			else
				velocityDir.x = 0;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Called when a key was typed
	 *
	 * @param character The character
	 * @return whether the input was processed
	 */
	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}
	
	/**
	 * Called when the screen was touched or a mouse button was pressed.
	 *
	 * @param screenX The x coordinate, origin is in the upper left corner
	 * @param screenY The y coordinate, origin is in the upper left corner
	 * @param pointer the pointer for the event.
	 * @param button  the button
	 * @return whether the input was processed
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}
	
	/**
	 * Called when a finger was lifted or a mouse button was released.
	 *
	 * @param screenX
	 * @param screenY
	 * @param pointer the pointer for the event.
	 * @param button  the button   @return whether the input was processed
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}
	
	/**
	 * Called when a finger or the mouse was dragged.
	 *
	 * @param screenX
	 * @param screenY
	 * @param pointer the pointer for the event.  @return whether the input was processed
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}
	
	/**
	 * Called when the mouse was moved without any buttons being pressed.
	 *
	 * @param screenX
	 * @param screenY
	 * @return whether the input was processed
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}
	
	/**
	 * Called when the mouse wheel was scrolled. Will not be called on iOS.
	 *
	 * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
	 * @return whether the input was processed.
	 */
	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
	
	/**
	 * Renders this object
	 *
	 * @param batch Batch to render to
	 * @param delta time in seconds since last tick
	 */
	@Override
	public void render(SpriteBatch batch, float delta)
	{
		float scalar = delta * SPEED;
		
		position.x += velocityDir.x * scalar;
		if (position.x - SPRITE_HALF_SIZE < 0)
			position.x = SPRITE_HALF_SIZE;
		else if (position.x + SPRITE_HALF_SIZE > GameScreen.GAME_WIDTH)
			position.x = GameScreen.GAME_WIDTH - SPRITE_HALF_SIZE;
		
		position.y += velocityDir.y * scalar;
		if (position.y - SPRITE_HALF_SIZE < 0)
			position.y = SPRITE_HALF_SIZE;
		else if (position.y + SPRITE_HALF_SIZE > GameScreen.GAME_HEIGHT)
			position.y = GameScreen.GAME_HEIGHT - SPRITE_HALF_SIZE;
		
		batch.draw(texture, position.x - SPRITE_HALF_SIZE, position.y - SPRITE_HALF_SIZE);
	}
	
	/**
	 * Loads all resources necessary for this object.
	 *
	 * @param manager AssetManager to load assets from
	 */
	@Override
	public void load(AssetManager manager)
	{
		final String fileName = "player.png";
		
		manager.load(fileName, Texture.class);
		manager.finishLoadingAsset(fileName);
		texture = manager.get(fileName);
		
		position = new Vector2(0, 0);
		velocityDir = new Vector2(0, 0);
	}
	
	@Override
	public void dispose(AssetManager manager)
	{
		manager.unload("player.png");
	}
}
