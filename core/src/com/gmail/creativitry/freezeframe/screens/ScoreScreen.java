/**
 * ScoreScreen.java
 * Displays a screen holding high scores
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/13/2017
 */

package com.gmail.creativitry.freezeframe.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gmail.creativitry.freezeframe.FreezeFrame;
import com.gmail.creativitry.freezeframe.InputManager;
import com.gmail.creativitry.freezeframe.database.ScoreData;
import com.gmail.creativitry.freezeframe.database.Scores;

public class ScoreScreen extends AbstractScreen
{
	
	public static final int HIGHSCORES_AMOUNT = 10;
	public static final int PAD = 50;
	public static final int LARGE_PAD = 100;
	
	private ScoreData scoreData;
	private Scores scores;
	
	private TextureRegion screenshot;
	private ShaderProgram screenShader;
	private int width;
	private int height;
	
	private boolean goBack;
	
	/**
	 * Constructs a new screen with the given game instance and size
	 *
	 * @param freezeFrame game instance that shows this screen
	 */
	public ScoreScreen(FreezeFrame freezeFrame, ScoreData scoreData, TextureRegion screenshot)
	{
		super(freezeFrame, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		this.scoreData = scoreData;
		scores = new Scores(scoreData);
		
		screenShader = new ShaderProgram(Gdx.files.internal("shaders/VertexShader.vert"),
			Gdx.files.internal("shaders/Screenshot.frag"));
		this.screenshot = screenshot;
		
		goBack = false;
		
		getUiStage().addListener(new InputListener()
		{
			/**
			 * Called when a key goes up. Marks to move to the main menu
			 */
			@Override
			public boolean keyUp(InputEvent event, int keycode)
			{
				if (InputManager.keyUp(keycode, InputManager.CONFIRM))
				{
					// avoids destruction of listener before true is returned
					goBack = true;
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
		screenshot.getTexture().dispose();
	}
	
	/**
	 * Called when this screen becomes the current screen for a {@link Game}.
	 * Loads the skin for the ui
	 */
	@Override
	public void show()
	{
		super.show();
		
		Stack stack = new Stack();
		stack.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		getUiStage().addActor(stack);
		
		Table table = new Table(getSkin());
		table.setFillParent(true);
		table.pad(LARGE_PAD);
		stack.add(table);
		table.bottom().left();
		
		TextButton button = new TextButton("Back", getSkin());
		button.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				goBack();
			}
		});
		table.add(button);
		
		Table titleTable = new Table(getSkin());
		titleTable.setFillParent(true);
		titleTable.pad(LARGE_PAD);
		stack.add(titleTable);
		titleTable.center().top();
		
		Label names = new Label("Game Over", getSkin(), "title");
		titleTable.add(names).pad(10);
		
		Table scoreTable = new Table(getSkin());
		scoreTable.setFillParent(true);
		scoreTable.pad(LARGE_PAD * 2);
		stack.add(scoreTable);
		scoreTable.center();
		
		addData(scoreData, scoreTable, "Final Score");
		scoreTable.row();
		
		TextButton submitButton = new TextButton("Add to the global leaderboard",
			getSkin());
		submitButton.addListener(new ChangeListener()
		{
			/**
			 * Called when the button is pressed. Opens the link.
			 *
			 * @param event unused
			 * @param actor unused
			 */
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				Gdx.net.openURI(scoreData.getUrl());
			}
		});
		scoreTable.add(submitButton).padBottom(PAD);
		scoreTable.row();
		
		
		if (scores != null && !scores.getData().isEmpty())
		{
			int i = 1;
			for (ScoreData data : scores.getData())
			{
				addData(data, scoreTable, i);
				i++;
				
				if (i > HIGHSCORES_AMOUNT)
					break;
			}
		}
		
	}
	
	/**
	 * Adds the score data to the table
	 * @param data data to add
	 * @param scoreTable table to add to
	 * @param description description to display at the left of the data
	 */
	private void addData(ScoreData data, Table scoreTable, Object description)
	{
		scoreTable.add(new Label(description + ": ", getSkin(), "sub-title"))
			.padLeft(PAD).padRight(PAD * 2);
		
		scoreTable.add(new Label(data.getTime(), getSkin())).padRight(PAD);
		scoreTable.add(new Label(data.getName(), getSkin())).padRight(PAD);
		scoreTable.add(new Label(data.getSeed(), getSkin())).padRight(PAD);
		scoreTable.add(new Label("" + data.getScore(), getSkin())).padRight(PAD);
		
		scoreTable.row();
	}
	
	/**
	 * Moves back to the main menu screen
	 */
	private void goBack()
	{
		getFreezeFrame().setScreen(new MainMenuScreen(getFreezeFrame()));
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
		if (goBack)
			goBack();
		else
		{
			batch.begin();
			screenShader.begin();
			screenShader.setUniformf("u_resolution", width, height);
			batch.setShader(screenShader);
			batch.draw(screenshot, 0, 0);
			batch.flush();
			screenShader.end();
			batch.end();
		}
	}
	
	/**
	 * Called when the {@link Application} is resized. This can happen at any point during a
	 * non-paused state but will never happen before a call to create().
	 *
	 * @param width  the new width in pixels
	 * @param height the new height in pixels
	 */
	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
		
		this.width = width;
		this.height = height;
	}
}
