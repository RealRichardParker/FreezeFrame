/**
 * GameScreen.java
 * Let the player play the game
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/4/2017
 */
package com.gmail.creativitry.freezeframe.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gmail.creativitry.freezeframe.FreezeFrame;
import com.gmail.creativitry.freezeframe.HealthBar;
import com.gmail.creativitry.freezeframe.Player;
import com.gmail.creativitry.freezeframe.moveable.MoveableManager;
import com.gmail.creativitry.freezeframe.moveable.bullet.BulletSprayer;

public class GameScreen extends AbstractScreen
{
	public static final float GAME_WIDTH = SCREEN_WIDTH / 2;
	public static final float GAME_HEIGHT = SCREEN_HEIGHT / 2;
	public static final int VERTICAL_PAD = 70;
	public static final int SCORE_INCREASE_TIME = 6;
	public static final int SCORE_GRAZE = 5;
	private RandomXS128 random;
	
	private HealthBar healthBar;
	
	private float score;
	private int scoreMultiplyer;
	private Label scoreLabel;
	
	private Player player;
	private BulletSprayer bulletSprayer;
	private MoveableManager moveableManager;
	
	/**
	 * Constructs a new game screen with the given game instance and the random number generator
	 *
	 * @param freezeFrame game instance that shows this screen
	 * @param random      random number generator
	 */
	public GameScreen(FreezeFrame freezeFrame, RandomXS128 random)
	{
		super(freezeFrame, GAME_WIDTH, GAME_HEIGHT);
		
		this.random = random;
		player = new Player(this, GAME_WIDTH / 2, VERTICAL_PAD);
		addInputProcessor(player);
		moveableManager = new MoveableManager(this, player);
		bulletSprayer = new BulletSprayer(moveableManager, random, GAME_WIDTH / 2, GAME_HEIGHT - VERTICAL_PAD);
		healthBar = new HealthBar(Player.STARTING_HEALTH);
		scoreMultiplyer = 1;
	}
	
	/**
	 * Loads all resources necessary for this object.
	 *
	 * @param manager AssetManager to load assets from
	 */
	@Override
	public void load(AssetManager manager)
	{
		player.load(manager);
		bulletSprayer.load(manager);
		healthBar.load(manager);
	}
	
	/**
	 * Disposes all resources loaded by this object.
	 *
	 * @param manager AssetManager to dispose assets from
	 */
	@Override
	public void dispose(AssetManager manager)
	{
		player.dispose(manager);
		bulletSprayer.dispose(manager);
		healthBar.dispose(manager);
	}
	
	/**
	 * Called when this screen becomes the current screen for a {@link Game}.
	 * Loads the skin for the ui
	 */
	@Override
	public void show()
	{
		super.show();
		
		scoreLabel = new Label("blah", getSkin());
		
		Table table = new Table(getSkin());
		table.setFillParent(true);
		table.top().left();
		table.pad(50);
		
		table.add(healthBar);
		table.row();
		table.add(scoreLabel);
		
		getUiStage().addActor(table);
	}
	
	public void addScore(float score)
	{
		this.score += score;
		
		scoreLabel.setText(String.format("%.0f", this.score));
	}
	
	public void isGrazing(boolean isGrazing)
	{
		if (isGrazing)
		{
			scoreMultiplyer = SCORE_GRAZE;
		}
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
		batch.begin();
		player.render(batch, delta);
		
		float scalar = delta;
		if (player.getVelocityDir().x == 0 && player.getVelocityDir().y == 0 && !player.isTimeMove())
			scalar = 0;
		
		moveableManager.render(batch, scalar);
		bulletSprayer.render(batch, scalar);
		addScore(scalar * SCORE_INCREASE_TIME * scoreMultiplyer);
		scoreMultiplyer = 1;
		
		batch.end();
	}
	
	public void updateHealth(int newHealth)
	{
		healthBar.setHealth(newHealth);
		System.out.println("health: " + newHealth);
	}
}
