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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gmail.creativitry.freezeframe.FreezeFrame;
import com.gmail.creativitry.freezeframe.HealthBar;
import com.gmail.creativitry.freezeframe.Player;
import com.gmail.creativitry.freezeframe.database.ScoreData;
import com.gmail.creativitry.freezeframe.moveable.MoveableManager;
import com.gmail.creativitry.freezeframe.moveable.bullet.BulletSprayer;
import com.gmail.creativitry.freezeframe.moveable.item.ItemSpawner;
import com.gmail.creativitry.freezeframe.random.RandomGenerator;

public class GameScreen extends AbstractScreen
{
	public static final float GAME_WIDTH = SCREEN_WIDTH / 2f;
	public static final float GAME_HEIGHT = SCREEN_HEIGHT / 2f;
	public static final int VERTICAL_PAD = 70;
	public static final int SCORE_INCREASE_TIME = 6;
	public static final int SCORE_GRAZE = 5;
	public static final float DIFFICULTY_MODIFIER = 0.002f;
	public static final float STARTING_TIMER_RATE = 0.5f;
	public static final int DELTA_THRESHOLD = 2;
	public static final int TILED = 3;
	public static final int BACKGROUND_SPEED = 30;
	
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
	private ItemSpawner itemSpawner;
	private MoveableManager moveableManager;
	
	private boolean alive;
	
	private TextureRegion background;
	private float backgroundX;
	private float backgroundY;
	
	
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
		
		alive = true;
		
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
		
		final String timerPath = "timer.png";
		manager.load(timerPath, Texture.class);
		manager.finishLoadingAsset(timerPath);
		timer = new Image(manager.get(timerPath, Texture.class));
		
		final String backgroundPath = "space.png";
		manager.load(backgroundPath, Texture.class);
		manager.finishLoadingAsset(backgroundPath);
		Texture backgroundTexture = manager.get(backgroundPath, Texture.class);
		backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		background = new TextureRegion(backgroundTexture, backgroundTexture.getWidth() * TILED, backgroundTexture.getHeight() * TILED);
		backgroundX = -backgroundTexture.getWidth() * 2;
		backgroundY = -backgroundTexture.getHeight() * 2;
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
		manager.unload("space.png");
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
		if (alive)
		{
			batch.begin();
			
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
			
			backgroundX -= BACKGROUND_SPEED * scalar;
			while (backgroundX < -background.getTexture().getWidth() * 2)
				backgroundX += background.getTexture().getWidth();
			backgroundY -= BACKGROUND_SPEED * scalar;
			while (backgroundY < -background.getTexture().getHeight() * 2)
				backgroundY += background.getTexture().getHeight();
			
			batch.draw(background, backgroundX, backgroundY);
			
			player.render(batch, delta);
			
			timerRate += scalar * DIFFICULTY_MODIFIER;
			
			moveableManager.render(batch, scalar);
			bulletSprayer.render(batch, scalar);
			addScore(scalar * SCORE_INCREASE_TIME * scoreMultiplier);
			scoreMultiplier = 1;
			
			batch.end();
		}
		else
		{
			getFreezeFrame().setScreen(new ScoreScreen(getFreezeFrame(), new ScoreData(random, score)));
		}
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
		super.render(Math.min(delta, DELTA_THRESHOLD));
		
		timer.setPosition(player.getPosition().x * SCREEN_WIDTH / GAME_WIDTH - timer.getImageWidth() / 2,
			player.getPosition().y * SCREEN_HEIGHT / GAME_HEIGHT - timer.getImageHeight() / 2);
		timerStage.act();
		
		timerShader.begin();
		timerShader.setUniformf("u_timerVal", timerVal);
		timerStage.getBatch().setShader(timerShader);
		timerStage.draw();
		timerShader.end();
	}
	
	public void gameOver()
	{
		alive = false;
	}
}
