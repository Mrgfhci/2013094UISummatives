package com.space.run.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.space.run.game.Assets;
import com.space.run.game.SideScroller;
import com.space.run.game.screens.MainMenuScreen.ButtonListener;

public class PauseScreen implements Screen {

	private SideScroller game;

	private Stage stage;
	private TextButton btnResume;
	private TextButton btnRestart;
	private TextButton btnMainMenu;
	private Label lblTitle;

	private ButtonListener btnListener;

	public PauseScreen(SideScroller game) {
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

		btnResume = new TextButton("Resume!", Assets.skinUI);
		btnResume.setName("Resume");
		btnResume.setSize(400, 100);
		btnResume.setPosition(SideScroller.WIDTH / 2 - btnResume.getWidth() / 2, (SideScroller.HEIGHT / 2 - btnResume.getHeight() / 2) + 100);
		btnResume.addListener(btnListener);

		btnRestart = new TextButton("Restart!", Assets.skinUI);
		btnRestart.setName("Restart");
		btnRestart.setSize(400, 100);
		btnRestart.setPosition(SideScroller.WIDTH / 2 - btnRestart.getWidth() / 2, (SideScroller.HEIGHT / 2 - btnRestart.getHeight() / 2) - 20);
		btnRestart.addListener(btnListener);

		btnMainMenu = new TextButton("MainMenu!", Assets.skinUI);
		btnMainMenu.setName("Menu");
		btnMainMenu.setSize(400, 100);
		btnMainMenu.setPosition(SideScroller.WIDTH / 2 - btnMainMenu.getWidth() / 2, (SideScroller.HEIGHT / 2 - btnMainMenu.getHeight() / 2) - 140);

		btnMainMenu.addListener(btnListener);

		lblTitle = new Label("Game Paused!", Assets.skinUI);
		lblTitle.setPosition(0, SideScroller.HEIGHT / 2 + 180);
		lblTitle.setWidth(SideScroller.WIDTH);
		lblTitle.setAlignment(Align.center);

		stage.addActor(btnResume);
		stage.addActor(btnRestart);
		stage.addActor(btnMainMenu);
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

}
