package com.space.run.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.space.run.game.screens.GameOverScreen;
import com.space.run.game.screens.GameScreen;
import com.space.run.game.screens.HelpScreen;
import com.space.run.game.screens.HighScoresScreen;
import com.space.run.game.screens.MainMenuScreen;
import com.space.run.game.screens.PauseScreen;
import com.space.run.game.screens.SplashScreen;

public class SideScroller extends Game {

	/**
	 * The width of the game. Allows support for different resolutions as it
	 * gets stretched to fit the screen.
	 */
	public static final float WIDTH = 800;

	/**
	 * The height of the game. Allows support for different resolutions as it
	 * gets stretched to fit the screen.
	 */
	public static final float HEIGHT = 480;

	/**
	 * The main SpriteBatch of the game.
	 */
	private SpriteBatch batch;

	/**
	 * The splash screen of the game.
	 */
	private SplashScreen splashScreen;

	/**
	 * The main menu of the game.
	 */
	private MainMenuScreen mainMenuScreen;

	/**
	 * The game.
	 */
	private GameScreen gameScreen;

	/**
	 * The game over screen.
	 */
	private GameOverScreen gameOverScreen;

	/**
	 * The help screen of the game. Provides instructions on how to play the
	 * game.
	 */
	private HelpScreen helpScreen;

	/**
	 * The high score screen of the game. Shows the high scores.
	 */
	private HighScoresScreen highScoresScreen;

	/**
	 * The pause screen of the game.
	 */
	private PauseScreen pauseScreen;

	@Override
	public void create() {
		Assets.load();

		batch = new SpriteBatch();

		// Create all of the screens.
		splashScreen = new SplashScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		helpScreen = new HelpScreen(this);
		highScoresScreen = new HighScoresScreen(this);
		pauseScreen = new PauseScreen(this);
		gameOverScreen = new GameOverScreen(this);

		setScreen(splashScreen);
	}

	/**
	 * Gets the SpriteBatch of the game. It is more efficient to use one
	 * SpriteBatch for the whole game to draw everything.
	 * 
	 * @return The SpriteBatch of the game.
	 */
	public SpriteBatch getBatch() {
		return batch;
	}

	public GameOverScreen getGameOverScreen() {
		return gameOverScreen;
	}

	public SplashScreen getSplashScreen() {
		return splashScreen;
	}

	public MainMenuScreen getMainMenuScreen() {
		return mainMenuScreen;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	public HelpScreen getHelpScreen() {
		return helpScreen;
	}

	public HighScoresScreen getHighScoresScreen() {
		return highScoresScreen;
	}

	public PauseScreen getPauseScreen() {
		return pauseScreen;
	}
}
