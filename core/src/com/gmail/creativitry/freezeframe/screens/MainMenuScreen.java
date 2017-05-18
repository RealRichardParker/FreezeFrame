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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gmail.creativitry.freezeframe.FreezeFrame;
import com.gmail.creativitry.freezeframe.InputManager;
import com.gmail.creativitry.freezeframe.random.RandomGenerator;

public class MainMenuScreen extends AbstractScreen
{
	public static final Color TOP_COLOR = Color.BLACK;
	public static final Color BOTTOM_COLOR = Color.MAROON;
	public static final String INSTRUCTIONS =
		"Instructions:\n" +
		"WASD or arrow keys = Move\n" +
		"Hold SHIFT = Move slower\n" +
		"Don’t move = Freeze time\n" +
		"Hold SPACEBAR = Unfreeze time\n" +
		"Collect ITEMS for various effects";
	public static final int PAD = 10;
	public static final int LARGE_PAD = 100;
	public static final int SEED_INPUT_WIDTH = 250;
	
	private ShapeRenderer renderer;
	private Texture logoTexture;
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
			 *  Called when a key goes up. Starts the game
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
		final String fileName = "logo.png";
		manager.load(fileName, Texture.class);
		manager.finishLoadingAsset(fileName);
		logoTexture = manager.get(fileName);
	}
	
	/**
	 * Disposes all resources loaded by this object.
	 *
	 * @param manager AssetManager to dispose assets from
	 */
	@Override
	public void dispose(AssetManager manager)
	{
		manager.unload("logo.png");
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
		
		Image title = new Image(logoTexture);
		titleTable.add(title).pad(PAD);
		titleTable.row();
		
		Label names = new Label("Bryan Lee\tGahwon Lee\tTiger Zhang", getSkin());
		titleTable.add(names).pad(PAD);
		titleTable.row();
		
		titleTable.add(new Label(INSTRUCTIONS, getSkin())).pad(PAD);
		stack.add(titleTable);
		
		Table table = new Table(getSkin());
		table.setFillParent(true);
		table.pad(LARGE_PAD);
		stack.add(table);
		
		table.bottom().left();
		
		startButton = new TextButton("Start", getSkin());
		startButton.getLabel().setStyle(getSkin().get("title", Label.LabelStyle.class));
		startButton.addListener(new ChangeListener()
		{
			/**
			 * Called when the button is pressed. Starts the game
			 *
			 * @param event unused
			 * @param actor unused
			 */
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				startGame();
			}
		});
		table.add(startButton);
		
		seedText = new TextField("", getSkin());
		seedText.setMessageText("Random Pattern");
		table.add(seedText).width(SEED_INPUT_WIDTH);
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
		Gdx.app.log(this.getClass().getSimpleName(), random.toString());
	}
}
