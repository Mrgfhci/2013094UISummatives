package com.space.run.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.space.run.game.Assets;
import com.space.run.game.Input;
import com.space.run.game.Level;
import com.space.run.game.SideScroller;
import com.space.run.game.StatusBar;

public class GameScreen implements Screen {

	/**
	 * The maximum time the camera can shake;
	 */
	private static final float MAX_SHAKE_TIME = 1f;

	/**
	 * The instance of game. Used to switch screens.
	 */
	private SideScroller game;

	/**
	 * Orthographic Camera:
	 * https://github.com/libgdx/libgdx/wiki/Orthographic-camera
	 * 
	 * The camera that allows everything on the screen to be viewed.
	 */
	private OrthographicCamera camera;

	/**
	 * The instance of level that contains all of the game objects.
	 */
	private Level level;

	/**
	 * The game's status bar that shows the player's health, score, and coins.
	 */
	private StatusBar statusBar;

	/**
	 * https://code.google.com/p/libgdx/wiki/InputEvent
	 * 
	 * An input multiplexer handles more than one input processor. There is one
	 * input processor for touch and keyboard controls. There is another input
	 * processor for the pause button.
	 */
	InputMultiplexer inputMultiplexer = new InputMultiplexer();

	/**
	 * The value that keeps track of how long the camera has been shaking.
	 */
	private float fShakeTime = MAX_SHAKE_TIME;

	public GameScreen(SideScroller game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, SideScroller.WIDTH, SideScroller.HEIGHT);

		level = new Level();

		Assets.musicGameLoop.stop();
	}

	@Override
	public void render(float delta) {
		// Clear the screen.
		Gdx.gl.glClearColor(0.51f, 0.52f, 0.53f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();
		level.update(delta);
		statusBar.update(delta);

		// If the level should shake, shake it.
		if (level.shouldShake() && fShakeTime > 0) {
			fShakeTime -= delta;
			camera.position.x += MathUtils.random(0f, 1f) * 10 - 5;
			camera.position.y += MathUtils.random(0f, 1f) * 10 - 5;
		}

		// If the shake time is done, stop shaking.
		if (fShakeTime <= 0) {
			level.setShouldShake(false);
			fShakeTime = MAX_SHAKE_TIME;
			camera.setToOrtho(false, SideScroller.WIDTH, SideScroller.HEIGHT);
		}

		// Check if the game is over.
		if (level.getPlayer().needsRemoval()) {
			Input.resetKeys();
			game.setScreen(game.getGameOverScreen());
		}

		/*
		 * Set the game's spritebatch's projection matrix to the camera's so
		 * that everything is drawn to the correct location on the screen.
		 */
		game.getBatch().setProjectionMatrix(camera.combined);

		/*
		 * SpriteBatch and Textures:
		 * https://github.com/libgdx/libgdx/wiki/Spritebatch
		 * %2C-textureregions%2C-and-sprite
		 */
		game.getBatch().begin();

		level.render(game.getBatch());
		statusBar.render(game.getBatch());

		game.getBatch().end();

		statusBar.getStage().draw();

		if (Input.isKeyPressed(Keys.P)) {
			pause();
		}

		Input.update();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		if (statusBar == null) {
			statusBar = new StatusBar(game);
		}

		/*
		 * Add the status bar's stage as an input processor (for the pause
		 * button). Add the Input class as an input processor and gesture
		 * detector.
		 */
		inputMultiplexer.addProcessor(statusBar.getStage());
		inputMultiplexer.addProcessor(new Input());
		inputMultiplexer.addProcessor(new GestureDetector(new Input()));

		/*
		 * Set the current input processor to the input multiplexer to use all
		 * of the input processors.
		 */
		Gdx.input.setInputProcessor(inputMultiplexer);

		Input.resetKeys();
		Assets.musicGameLoop.play();
	}

	@Override
	public void hide() {
		Assets.musicGameLoop.pause();
	}

	@Override
	public void pause() {
		Input.resetKeys();
		game.setScreen(game.getPauseScreen());
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {

	}

	/**
	 * Gets the game's level.
	 * 
	 * @return The level.
	 */
	public Level getLevel() {
		return level;
	}
}
