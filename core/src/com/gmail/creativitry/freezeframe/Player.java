/**
 * Player.java
 * Move around the screen, control time, and dodge bullets
 *
 * @author Gahwon Lee, Tiger Zhang
 * Period: 3
 * Date: 5/2/2017.
 */

package com.gmail.creativitry.freezeframe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.gmail.creativitry.freezeframe.behaviors.Loadable;
import com.gmail.creativitry.freezeframe.behaviors.Renderable;
import com.gmail.creativitry.freezeframe.screens.GameScreen;

public class Player implements InputProcessor, Loadable, Renderable
{
	private static final float SPEED = 200;
	private static final float FOCUS_SPEED_MODIFIER = 0.5f;
	public static final int STARTING_HEALTH = 3;
	public static final int RADIUS = 4;
	public static final int HEALTH_MAX = 10;
	private static final float ITEM_TIME = 10;
	public static final float DAMAGE_COOLDOWN = 0.5f;
	private static final int HIDE_EXPLOSION = -1000;
	
	private GameScreen gameScreen;
	
	private Texture texture;
	private Texture focusTexture;
	private ShaderProgram damagedShader;
	
	private Texture magnetTexture;
	private Texture shieldTexture;
	
	private Vector2 position;
	private Vector2 velocityDir;
	
	private int health;
	private ParticleEffect explosion;
	
	private float radius;
	private boolean focus;
	private boolean timeMove;
	private float magnetTime;
	private float shieldTime;
	private float damageCooldown;
	
	/**
	 * Constructs a new player with the given parameters
	 *
	 * @param screen screen to add the player to
	 * @param x      starting x position
	 * @param y      starting y position
	 */
	public Player(GameScreen screen, float x, float y)
	{
		gameScreen = screen;
		
		position = new Vector2(x, y);
		velocityDir = new Vector2(0, 0);
		
		health = STARTING_HEALTH;
		radius = RADIUS;
		
		damagedShader = new ShaderProgram(Gdx.files.internal("shaders/VertexShader.vert"),
			Gdx.files.internal("shaders/Damaged.frag"));
	}
	
	/**
	 * Checks whether the time move button is pressed or not
	 *
	 * @return true if the button is pressed, false otherwise
	 */
	public boolean isTimeMove()
	{
		return timeMove;
	}
	
	/**
	 * Gets the position
	 *
	 * @return position
	 */
	public Vector2 getPosition()
	{
		return position;
	}
	
	/**
	 * Gets the collision radius
	 *
	 * @return collision radius
	 */
	public float getRadius()
	{
		return radius;
	}
	
	/**
	 * Gets the direction the player is headed to
	 *
	 * @return direction
	 */
	public Vector2 getVelocityDir()
	{
		return velocityDir;
	}
	
	/**
	 * Increases the health of the player
	 */
	public void incrementHealth()
	{
		health++;
		if (health > HEALTH_MAX)
			health = HEALTH_MAX;
		gameScreen.updateHealth(health);
	}
	
	/**
	 * Decreases the health of the Player
	 */
	public void damage()
	{
		if (damageCooldown > 0)
			return;
		if (isShield())
		{
			shieldTime = 0;
			return;
		}
		
		health--;
		damageCooldown = DAMAGE_COOLDOWN;
		gameScreen.updateHealth(health);
		explosion.reset();
		
		if (health == 0)
		{
			gameScreen.gameOver();
		}
	}
	
	/**
	 * Checks whether the player is able to be damaged
	 *
	 * @return true if the player is not able to be damaged, false otherwise
	 */
	public boolean isDamaged()
	{
		return damageCooldown > 0;
	}
	
	/**
	 * Adds the given score to the current score
	 *
	 * @param score score to add
	 */
	public void addScore(float score)
	{
		gameScreen.addScore(score);
	}
	
	/**
	 * Checks whether the magnet is active
	 *
	 * @return true if the magnet is active, false otherwise
	 */
	public boolean isMagnet()
	{
		return magnetTime > 0;
	}
	
	/**
	 * Activates the magnet
	 */
	public void setMagnet()
	{
		magnetTime = ITEM_TIME;
	}
	
	/**
	 * Checks whether the shield is active
	 *
	 * @return true if the shield is active, false otherwise
	 */
	public boolean isShield()
	{
		return shieldTime > 0;
	}
	
	/**
	 * Activates the shield
	 */
	public void setShield()
	{
		shieldTime = ITEM_TIME;
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
		float normalDelta = Gdx.graphics.getDeltaTime();
		
		final int halfWidth = texture.getWidth() / 2;
		final int halfHeight = texture.getHeight() / 2;
		
		float scalar = normalDelta * SPEED;
		if (focus)
			scalar *= FOCUS_SPEED_MODIFIER;
		
		position.x += velocityDir.x * scalar;
		if (position.x - halfWidth < 0)
			position.x = halfWidth;
		else if (position.x + halfWidth > GameScreen.GAME_WIDTH)
			position.x = GameScreen.GAME_WIDTH - halfWidth;
		
		position.y += velocityDir.y * scalar;
		if (position.y - halfHeight < 0)
			position.y = halfHeight;
		else if (position.y + halfHeight > GameScreen.GAME_HEIGHT)
			position.y = GameScreen.GAME_HEIGHT - halfHeight;
		
		if (damageCooldown > 0)
		{
			batch.end();
			batch.begin();
			damagedShader.begin();
			batch.setShader(damagedShader);
		}
		
		batch.draw(texture, position.x - halfWidth, position.y - halfHeight);
		
		if (damageCooldown > 0)
		{
			batch.flush();
			damagedShader.end();
			batch.setShader(null);
			
			damageCooldown -= normalDelta;
		}
		
		if (!explosion.isComplete())
		{
			explosion.setPosition(position.x, position.y);
			explosion.draw(batch, delta);
		}
		
		if (focus)
			batch.draw(focusTexture, position.x - focusTexture.getWidth() / 2,
				position.y - focusTexture.getHeight() / 2);
		
		if (shieldTime > 0)
		{
			batch.draw(shieldTexture, position.x - shieldTexture.getWidth() / 2,
				position.y - shieldTexture.getHeight() / 2);
			shieldTime -= delta;
		}
		
		if (magnetTime > 0)
		{
			batch.draw(magnetTexture, position.x - magnetTexture.getWidth() / 2,
				position.y - magnetTexture.getHeight() / 2);
			magnetTime -= delta;
		}
	}
	
	/**
	 * Loads all resources necessary for this object.
	 *
	 * @param manager AssetManager to load assets from
	 */
	@Override
	public void load(AssetManager manager)
	{
		final String textureFile = "player.png";
		manager.load(textureFile, Texture.class);
		manager.finishLoadingAsset(textureFile);
		texture = manager.get(textureFile);
		
		final String focusFile = "focus.png";
		manager.load(focusFile, Texture.class);
		manager.finishLoadingAsset(focusFile);
		focusTexture = manager.get(focusFile);
		
		final String expFile = "effects/explosion.pe";
		manager.load(expFile, ParticleEffect.class);
		manager.finishLoadingAsset(expFile);
		explosion = manager.get(expFile);
		explosion.setPosition(HIDE_EXPLOSION, HIDE_EXPLOSION);
		
		final String magnetFile = "effects/magnet.png";
		manager.load(magnetFile, Texture.class);
		manager.finishLoadingAsset(magnetFile);
		magnetTexture = manager.get(magnetFile);
		
		final String shieldFile = "effects/shield.png";
		manager.load(shieldFile, Texture.class);
		manager.finishLoadingAsset(shieldFile);
		shieldTexture = manager.get(shieldFile);
	}
	
	/**
	 * Disposes all resources loaded by this object.
	 *
	 * @param manager AssetManager to dispose assets from
	 */
	@Override
	public void dispose(AssetManager manager)
	{
		manager.unload("player.png");
		manager.unload("focus.png");
		manager.unload("effects/explosion.pe");
		manager.unload("effects/magnet.png");
		manager.unload("effects/shield.png");
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
		else if (InputManager.keyUp(keycode, InputManager.FOCUS))
		{
			focus = true;
			return true;
		}
		else if (InputManager.keyUp(keycode, InputManager.MOVE_TIME))
		{
			timeMove = true;
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
		else if (InputManager.keyUp(keycode, InputManager.FOCUS))
		{
			focus = false;
			return true;
		}
		else if (InputManager.keyUp(keycode, InputManager.MOVE_TIME))
		{
			timeMove = false;
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
}