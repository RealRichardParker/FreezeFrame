/**
 * ScoreScreen.java
 * Displays a screen holding high scores
 *
 * @author creativitRy
 * Period: 3
 * Date: 5/13/2017
 */
package com.gmail.creativitry.freezeframe.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
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
	
	private ScoreData scoreData;
	private Scores scores;
	private boolean goBack;
	
	/**
	 * Constructs a new screen with the given game instance and size
	 *
	 * @param freezeFrame game instance that shows this screen
	 */
	public ScoreScreen(FreezeFrame freezeFrame, ScoreData scoreData)
	{
		super(freezeFrame, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		System.out.println(scoreData);
		this.scoreData = scoreData;
		
		goBack = false;
		
		getUiStage().addListener(new InputListener()
		{
			/**
			 * Called when a key goes up. When true is returned, the event is {@link Event#handle() handled}.
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
		table.pad(100);
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
		titleTable.pad(100);
		stack.add(titleTable);
		titleTable.center().top();
		
		Label names = new Label("Game Over", getSkin(), "title");
		titleTable.add(names).pad(10);
		
		Table scoreTable = new Table(getSkin());
		scoreTable.setFillParent(true);
		scoreTable.pad(100 * 2);
		stack.add(scoreTable);
		scoreTable.center();
		
		scoreTable.add(new Label("Final Score: ", getSkin(), "sub-title")).pad(50);
		scoreTable.add(new Label(scoreData.getName(), getSkin())).pad(50);
		scoreTable.add(new Label(scoreData.getSeed(), getSkin())).pad(50);
		scoreTable.add(new Label("" + scoreData.getScore(), getSkin())).pad(50);
		
		scoreTable.row();
		scoreTable.row();
		
		if (scores != null && !scores.getData().isEmpty())
		{
			int i = 1;
			for (ScoreData data : scores.getData())
			{
				scoreTable.add(new Label(i + ": ", getSkin(), "sub-title")).pad(50);
				scoreTable.add(new Label(scoreData.getName(), getSkin())).pad(50);
				scoreTable.add(new Label(scoreData.getSeed(), getSkin())).pad(50);
				scoreTable.add(new Label("" + scoreData.getScore(), getSkin())).pad(50);
				
				scoreTable.row();
				i++;
			}
		}
		
	}
	
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
	}
}
