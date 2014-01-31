package com.space.run.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.space.run.game.Assets;
import com.space.run.game.SideScroller;

public class MainMenuScreen implements Screen {

	private SideScroller game;

	private Stage stage;
	private TextButton btnPlay;
	private TextButton btnHelp;
	private TextButton btnStats;
	private Label lblTitle;

	private ButtonListener btnListener;

	public MainMenuScreen(SideScroller game) {
		this.game = game;

		btnListener = new ButtonListener(game);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		/*
		 * Menus(Buttons, Labels, etc):
		 * https://github.com/libgdx/libgdx/wiki/Scene2d
		 * https://github.com/libgdx/libgdx/wiki/Scene2d.ui
		 * https://github.com/libgdx
		 * /libgdx/blob/master/tests/gdx-tests/src/com/badlogic
		 * /gdx/tests/UITest.java
		 */
		stage = new Stage(SideScroller.WIDTH, SideScroller.HEIGHT, false, game.getBatch());
		stage.clear();

		Gdx.input.setInputProcessor(stage);

		btnPlay = new TextButton("Play!", Assets.skinUI);
		btnPlay.setName("Restart");
		btnPlay.setSize(400, 100);
		btnPlay.setPosition(SideScroller.WIDTH / 2 - btnPlay.getWidth() / 2, SideScroller.HEIGHT / 2 - btnPlay.getHeight() / 2 + 100);
		btnPlay.addListener(btnListener);

		btnHelp = new TextButton("Help!", Assets.skinUI);
		btnHelp.setName("Help");
		btnHelp.setSize(400, 100);
		btnHelp.setPosition(SideScroller.WIDTH / 2 - btnHelp.getWidth() / 2, (SideScroller.HEIGHT / 2 - btnHelp.getHeight() / 2) - 20);
		btnHelp.addListener(btnListener);

		btnStats = new TextButton("High Scores", Assets.skinUI);
		btnStats.setName("Scores");
		btnStats.setSize(400, 100);
		btnStats.setPosition(SideScroller.WIDTH / 2 - btnHelp.getWidth() / 2, (SideScroller.HEIGHT / 2 - btnHelp.getHeight() / 2) - 140);
		btnStats.addListener(btnListener);

		lblTitle = new Label("Space Run", Assets.skinUI);
		lblTitle.setPosition(0, SideScroller.HEIGHT / 2 + 180);
		lblTitle.setWidth(SideScroller.WIDTH);
		lblTitle.setAlignment(Align.center);

		stage.addActor(btnPlay);
		stage.addActor(btnHelp);
		stage.addActor(btnStats);
		stage.addActor(lblTitle);
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	static public class ButtonListener extends ClickListener {

		private SideScroller game;

		public ButtonListener(SideScroller game) {
			this.game = game;
		}

		@Override
		public void clicked(InputEvent event, float x, float y) {
			String sBtnName = event.getListenerActor().getName();

			if (sBtnName == "Restart") {
				game.setGameScreen(new GameScreen(game));
				game.setScreen(game.getGameScreen());
			} else if (sBtnName == "Help") {
				game.setScreen(game.getHelpScreen());
			} else if (sBtnName == "Resume") {
				game.setScreen(game.getGameScreen());
			} else if (sBtnName == "Scores") {
				game.setScreen(game.getHighScoresScreen());
			} else if (sBtnName == "Pause") {
				game.setScreen(game.getPauseScreen());
			} else {
				game.setScreen(game.getMainMenuScreen());
			}
		}
	}
}