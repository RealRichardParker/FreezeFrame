/**
 * AbstractScreen.java
 * Render self on the screen
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/4/2017
 */
package com.gmail.creativitry.freezeframe.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gmail.creativitry.freezeframe.FreezeFrame;
import com.gmail.creativitry.freezeframe.behaviors.Loadable;
import com.gmail.creativitry.freezeframe.behaviors.Renderable;

public abstract class AbstractScreen implements Screen, Loadable, Renderable
{
	public static final float SCREEN_WIDTH = 1600;
	public static final float SCREEN_HEIGHT = 900;
	
	private final FreezeFrame freezeFrame;
	private final Stage uiStage;
	private final SpriteBatch batch;
	private final OrthographicCamera camera;
	private final Viewport viewport;
	private Skin skin;
	private InputMultiplexer inputs;
	
	/**
	 * Constructs a new screen with the given game instance and size
	 *
	 * @param freezeFrame  game instance that shows this screen
	 * @param screenWidth  width of the screen
	 * @param screenHeight height of the screen
	 */
	public AbstractScreen(FreezeFrame freezeFrame, float screenWidth, float screenHeight)
	{
		this.freezeFrame = freezeFrame;
		camera = new OrthographicCamera();
		viewport = new FitViewport(screenWidth, screenHeight, camera);
		uiStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
		batch = new SpriteBatch();
		
		// set inputs
		inputs = new InputMultiplexer(uiStage);
		Gdx.input.setInputProcessor(inputs);
		addInputProcessor(uiStage);
	}
	
	protected FreezeFrame getFreezeFrame()
	{
		return freezeFrame;
	}
	
	protected Stage getUiStage()
	{
		return uiStage;
	}
	
	protected Skin getSkin()
	{
		return skin;
	}
	
	protected SpriteBatch getBatch()
	{
		return batch;
	}
	
	protected OrthographicCamera getCamera()
	{
		return camera;
	}
	
	protected Viewport getViewport()
	{
		return viewport;
	}
	
	/**
	 * Enables a new source of input
	 *
	 * @param inputProcessor source of input
	 */
	protected void addInputProcessor(InputProcessor inputProcessor)
	{
		inputs.addProcessor(inputProcessor);
	}
	
	protected InputMultiplexer getInputs()
	{
		return inputs;
	}
	
	/**
	 * Called when this screen becomes the current screen for a {@link Game}.
	 * Loads the skin for the ui
	 */
	@Override
	public void show()
	{
		skin = getFreezeFrame().getAssetManager().get("ui/neon-ui.json");
	}
	
	/**
	 * Called when the screen should render itself.
	 * Calls the main render method first before drawing the ui.
	 *
	 * @param delta The time in seconds since the last render.
	 */
	@Override
	public final void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		viewport.apply();
		batch.setProjectionMatrix(camera.combined);
		
		render(batch, delta);
		
		uiStage.act();
		uiStage.draw();
	}
	
	/**
	 * Called when the {@link com.badlogic.gdx.Application} is resized. This can happen at any point during a
	 * non-paused state but will never happen before a call to create().
	 *
	 * @param width  the new width in pixels
	 * @param height the new height in pixels
	 */
	@Override
	public void resize(int width, int height)
	{
		uiStage.getViewport().update(width, height, false);
		viewport.update(width, height, true);
	}
	
	/**
	 * Called when the {@link Application} is paused, usually when it's not active or visible on screen. An Application is also
	 * paused before it is destroyed.
	 */
	@Override
	public void pause()
	{
	}
	
	/**
	 * Called when the {@link Application} is resumed from a paused state, usually when it regains focus.
	 */
	@Override
	public void resume()
	{
	}
	
	/**
	 * Called when this screen is no longer the current screen for a Game.
	 * dispose method is called unless overwritten
	 */
	@Override
	public void hide()
	{
		dispose();
	}
	
	/**
	 * Called when this screen should release all resources.
	 */
	@Override
	public void dispose()
	{
		Gdx.app.log(this.getClass().getSimpleName(), "disposing");
		uiStage.dispose();
		batch.dispose();
	}
	
	/**
	 * Called when the screen should render itself.
	 *
	 * @param batch Batch to render on (begin and end NOT automated).
	 * @param delta The time in seconds since the last render.
	 */
	public abstract void render(SpriteBatch batch, float delta);
}
