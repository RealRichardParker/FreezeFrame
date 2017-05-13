/**
 * MainMenuScreen.java
 * Load a random or specific level for GameScreen
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/4/2017
 */
package com.gmail.creativitry.freezeframe.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gmail.creativitry.freezeframe.FreezeFrame;
import com.gmail.creativitry.freezeframe.InputManager;
import com.gmail.creativitry.freezeframe.RandomGenerator;

public class MainMenuScreen extends AbstractScreen
{
	public static final Color TOP_COLOR = Color.BLACK;
	public static final Color BOTTOM_COLOR = Color.MAROON;
	
	private ShapeRenderer renderer;
	private TextButton startButton;
	private TextField seedText;
	
	private boolean moveToGame;
	
	/**
	 * Constructs a new main menu screen with the given game instance
	 *
	 * @param freezeFrame game instance that shows this screen
	 */
	public MainMenuScreen(FreezeFrame freezeFrame)
	{
		super(freezeFrame, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		moveToGame = false;
		
		getUiStage().addListener(new InputListener()
		{
			/**
			 *  Called when a key goes up. When true is returned, the event is {@link Event#handle() handled}.
			 */
			@Override
			public boolean keyUp(InputEvent event, int keycode)
			{
				if (InputManager.keyUp(keycode, InputManager.CONFIRM))
				{
					// avoids destruction of listener before true is returned
					moveToGame = true;
					return true;
				}
				return false;
			}
		});
	}
	
	/**
	 * Loads all resources necessary for this object.
	 *
	 * @param manager AssetManager to load assets from
	 */
	@Override
	public void load(AssetManager manager)
	{
	
	}
	
	/**
	 * Disposes all resources loaded by this object.
	 *
	 * @param manager AssetManager to dispose assets from
	 */
	@Override
	public void dispose(AssetManager manager)
	{
	
	}
	
	/**
	 * Called when this screen becomes the current screen for a {@link Game}.
	 */
	@Override
	public void show()
	{
		super.show();
		
		renderer = new ShapeRenderer();
		
		Stack stack = new Stack();
		stack.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		getUiStage().addActor(stack);
		
		Table titleTable = new Table(getSkin());
		titleTable.setFillParent(true);
		titleTable.center();
		
		//todo: replace with sprite of logo
		Label title = new Label("FreezeFrame", getSkin());
		titleTable.add(title).pad(10);
		titleTable.row();
		
		Label names = new Label("Bryan Lee\tGahwon Lee\tTiger Zhang", getSkin());
		titleTable.add(names).pad(10);
		stack.add(titleTable);
		
		Table table = new Table(getSkin());
		table.setFillParent(true);
		table.pad(100);
		stack.add(table);
		
		//table.setDebug(true);
		
		table.bottom();
		table.left();
		
		startButton = new TextButton("Start", getSkin());
		startButton.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				startGame();
			}
		});
		table.add(startButton);
		
		seedText = new TextField("", getSkin());
		seedText.setMessageText("Random Pattern");
		table.add(seedText).width(250);
	}
	
	/**
	 * Called when the screen should render itself.
	 *
	 * @param batch Batch to render on (begin and end NOT automated).
	 * @param delta The time in seconds since the last render.
	 */
	@Override
	public void render(SpriteBatch batch, float delta)
	{
		renderer.setProjectionMatrix(getCamera().combined);
		renderer.setAutoShapeType(true);
		renderer.begin();
		renderer.set(ShapeRenderer.ShapeType.Filled);
		renderer.rect(
			0,
			0,
			SCREEN_WIDTH,
			SCREEN_HEIGHT,
			BOTTOM_COLOR,
			BOTTOM_COLOR,
			TOP_COLOR,
			TOP_COLOR
		);
		renderer.end();
		
		if (moveToGame)
			startGame();
	}
	
	/**
	 * Creates a new random number generator from the seed user entered.
	 * A random seed is used if the user did not enter a seed
	 *
	 * @return random number generator
	 */
	private RandomGenerator getRandom()
	{
		if (seedText.getText().isEmpty())
			return new RandomGenerator();
		
		return new RandomGenerator(seedText.getText());
	}
	
	/**
	 * Moves to the main game screen with the given seed for the random number generator
	 */
	private void startGame()
	{
		RandomGenerator random = getRandom();
		getFreezeFrame().setScreen(new GameScreen(getFreezeFrame(), random));
		Gdx.app.log(this.getClass().getSimpleName(), "" + seedText.getText().hashCode());
	}
}
