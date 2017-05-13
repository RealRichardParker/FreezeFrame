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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gmail.creativitry.freezeframe.FreezeFrame;
import com.gmail.creativitry.freezeframe.HealthBar;
import com.gmail.creativitry.freezeframe.Player;
import com.gmail.creativitry.freezeframe.RandomGenerator;
import com.gmail.creativitry.freezeframe.moveable.MoveableManager;
import com.gmail.creativitry.freezeframe.moveable.bullet.BulletSprayer;

public class GameScreen extends AbstractScreen
{
	public static final float GAME_WIDTH = SCREEN_WIDTH / 2f;
	public static final float GAME_HEIGHT = SCREEN_HEIGHT / 2f;
	public static final int VERTICAL_PAD = 70;
	public static final int SCORE_INCREASE_TIME = 6;
	public static final int SCORE_GRAZE = 5;
	public static final float DIFFICULTY_MODIFIER = 0.002f;
	public static final float STARTING_TIMER_RATE = 0.5f;
	
	private RandomGenerator random;
	
	private HealthBar healthBar;
	
	private float score;
	private int scoreMultiplier;
	private Label scoreLabel;
	
	private float timerVal;
	private Image timer;
	private ShaderProgram timerShader;
	private Stage timerStage;
	private float timerRate;
	
	private Player player;
	private BulletSprayer bulletSprayer;
	private MoveableManager moveableManager;
	
	/**
	 * Constructs a new game screen with the given game instance and the random number generator
	 *
	 * @param freezeFrame game instance that shows this screen
	 * @param random      random number generator
	 */
	public GameScreen(FreezeFrame freezeFrame, RandomGenerator random)
	{
		super(freezeFrame, GAME_WIDTH, GAME_HEIGHT);
		
		this.random = random;
		player = new Player(this, GAME_WIDTH / 2, VERTICAL_PAD);
		addInputProcessor(player);
		moveableManager = new MoveableManager(this, player);
		bulletSprayer = new BulletSprayer(moveableManager, random, GAME_WIDTH / 2, GAME_HEIGHT - VERTICAL_PAD);
		healthBar = new HealthBar(Player.STARTING_HEALTH);
		scoreMultiplier = 1;
		
		timerStage = new Stage(getUiStage().getViewport());
		timerShader = new ShaderProgram(Gdx.files.internal("shaders/Timer.vert"), Gdx.files.internal("shaders/Timer.frag"));
		
		timerVal = 1;
		timerRate = STARTING_TIMER_RATE;
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
		
		final String path = "timer.png";
		manager.load(path, Texture.class);
		manager.finishLoadingAsset(path);
		timer = new Image(manager.get(path, Texture.class));
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
		
		manager.unload("timer.png");
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
		
		timerStage.addActor(timer);
	}
	
	public void addScore(float score)
	{
		this.score += score;
		
		scoreLabel.setText(String.format("%.0f", this.score));
	}
	
	public void setGrazing()
	{
		scoreMultiplier = SCORE_GRAZE;
	}
	
	public void updateHealth(int newHealth)
	{
		healthBar.setHealth(newHealth);
		System.out.println("health: " + newHealth);
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
		
		float multiplier = 1;
		if (player.getVelocityDir().x == 0 && player.getVelocityDir().y == 0 && !player.isTimeMove())
		{
			multiplier = 0;
			if (timerVal <= 0)
			{
				timerVal = 0;
				multiplier = 1;
			}
			else
			{
				timerVal -= delta * timerRate;
			}
		}
		else
		{
			if (timerVal >= 1)
			{
				timerVal = 1;
			}
			else
			{
				timerVal += delta * STARTING_TIMER_RATE;
			}
		}
		
		float scalar = delta * multiplier;
		
		timerRate += scalar * DIFFICULTY_MODIFIER;
		
		moveableManager.render(batch, scalar);
		bulletSprayer.render(batch, scalar);
		addScore(scalar * SCORE_INCREASE_TIME * scoreMultiplier);
		scoreMultiplier = 1;
		
		batch.end();
	}
	
	/**
	 * Called when the screen should render itself.
	 * Calls the main render method first before drawing the ui.
	 * Used for rendering the timer
	 *
	 * @param delta The time in seconds since the last render.
	 */
	@Override
	public void render(float delta)
	{
		super.render(delta);
		
		timer.setPosition(player.getPosition().x * SCREEN_WIDTH / GAME_WIDTH - timer.getImageWidth() / 2,
			player.getPosition().y * SCREEN_HEIGHT / GAME_HEIGHT - timer.getImageHeight() / 2);
		timerStage.act();
		
		timerShader.begin();
		timerShader.setUniformf("u_timerVal", timerVal);
		timerStage.getBatch().setShader(timerShader);
		timerStage.draw();
		timerShader.end();
	}
}
