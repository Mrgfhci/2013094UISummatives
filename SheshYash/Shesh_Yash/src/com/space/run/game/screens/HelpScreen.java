package com.space.run.game.screens;

import com.badlogic.gdx.Application.ApplicationType;
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

public class HelpScreen implements Screen {

	private SideScroller game;

	private Stage stage;
	private TextButton btnMainMenu;
	private Label label;

	private ButtonListener btnListener;

	public HelpScreen(SideScroller game) {
		this.game = game;

		btnListener = new ButtonListener(game);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);

		game.getBatch().begin();
		if (Gdx.app.getType().equals(ApplicationType.Android)) {
			game.getBatch().draw(Assets.texRegAndroidControls, 250, 40, 400, 300);
		} else {
			game.getBatch().draw(Assets.texRegDesktopControls, 200, 40, 400, 300);
		}
		game.getBatch().end();

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

		btnMainMenu = new TextButton("Back", Assets.skinUI);
		btnMainMenu.setName("Back");
		btnMainMenu.setSize(150, 50);
		btnMainMenu.setPosition(SideScroller.WIDTH / 2 - btnMainMenu.getWidth() * 2, (SideScroller.HEIGHT / 2 + btnMainMenu.getHeight() * 3) + 20);
		btnMainMenu.addListener(btnListener);

		label = new Label("Controls", Assets.skinUI);
		label.setPosition(0, SideScroller.HEIGHT / 2 + 100);
		label.setWidth(SideScroller.WIDTH);
		label.setAlignment(Align.center);

		stage.addActor(btnMainMenu);
		stage.addActor(label);
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
