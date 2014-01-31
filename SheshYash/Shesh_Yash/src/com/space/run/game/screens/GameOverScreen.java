package com.space.run.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.space.run.game.Assets;
import com.space.run.game.SideScroller;
import com.space.run.game.screens.MainMenuScreen.ButtonListener;

public class GameOverScreen implements Screen {

	private SideScroller game;

	private Stage stage;
	private TextButton btnRestart;
	private TextButton btnMainMenu;
	private Label lblTitle;
	private Label lblScore;
	private Label lblCoins;

	/**
	 * https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests/src/com/
	 * badlogic/gdx/tests/UITest.java
	 * 
	 * The text field where the player enters their name.
	 */
	private TextField txtInput;

	private ButtonListener btnListener;

	public GameOverScreen(SideScroller game) {
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

		btnRestart = new TextButton("Restart!", Assets.skinUI);
		btnRestart.setName("Restart");
		btnRestart.setSize(400, 100);
		btnRestart.setPosition(SideScroller.WIDTH / 2 - btnRestart.getWidth() / 2, (SideScroller.HEIGHT / 2 - btnRestart.getHeight() / 2) - 50);
		btnRestart.addListener(btnListener);

		btnMainMenu = new TextButton("MainMenu!", Assets.skinUI);
		btnMainMenu.setName("Menu");
		btnMainMenu.setSize(400, 100);
		btnMainMenu.setPosition(SideScroller.WIDTH / 2 - btnMainMenu.getWidth() / 2, (SideScroller.HEIGHT / 2 - btnMainMenu.getHeight() / 2) - 170);
		btnMainMenu.addListener(btnListener);

		lblTitle = new Label("Game Over!", Assets.skinUI);
		lblTitle.setPosition(0, SideScroller.HEIGHT / 2 + 190);
		lblTitle.setWidth(SideScroller.WIDTH);
		lblTitle.setAlignment(Align.center);

		lblScore = new Label("Score: " + (game.getGameScreen().getLevel().getPlayer().getScore() + game.getGameScreen().getLevel().getPlayer().getCoins()), Assets.skinUI);
		lblScore.setPosition(0, SideScroller.HEIGHT / 2 + 90);
		lblScore.setWidth(SideScroller.WIDTH);
		lblScore.setAlignment(Align.center);

		lblCoins = new Label("Coins: " + game.getGameScreen().getLevel().getPlayer().getCoins(), Assets.skinUI);
		lblCoins.setPosition(0, SideScroller.HEIGHT / 2 + 40);
		lblCoins.setWidth(SideScroller.WIDTH);
		lblCoins.setAlignment(Align.center);

		// Get the last player name that was entered.
		FileHandle fileHighScoreNames = Gdx.files.local("data/statistics/names.txt");
		String sLatestName = "Player";
		String[] arsLines = null;

		if (fileHighScoreNames.exists()) {
			arsLines = fileHighScoreNames.readString().split(System.getProperty("line.separator"));

			sLatestName = arsLines[arsLines.length - 1];
		}

		txtInput = new TextField(sLatestName, Assets.skinUI);
		txtInput.setWidth(135);
		txtInput.setPosition(SideScroller.WIDTH / 2 - txtInput.getWidth() / 2, SideScroller.HEIGHT / 2 + 140);
		txtInput.setTextFieldListener(new TextFieldListener() {
			public void keyTyped(TextField textField, char key) {
				// If the enter key is pressed, hide the on screen keyboard.
				if (key == 13) {
					textField.getOnscreenKeyboard().show(false);
				}
			}
		});

		stage.addActor(btnRestart);
		stage.addActor(btnMainMenu);
		stage.addActor(lblTitle);
		stage.addActor(lblScore);
		stage.addActor(lblCoins);
		stage.addActor(txtInput);
	}

	@Override
	public void show() {
		/*
		 * File IO: https://github.com/libgdx/libgdx/wiki/File-handling
		 */
		FileHandle fileScores = Gdx.files.local("data/statistics/scores.txt");
		fileScores.writeString(game.getGameScreen().getLevel().getPlayer().getScore() + game.getGameScreen().getLevel().getPlayer().getCoins() + System.getProperty("line.separator"), true);
	}

	@Override
	public void hide() {
		FileHandle fileScoreNames = Gdx.files.local("data/statistics/names.txt");

		// If the text field is empty, just put Player as the name.
		if (txtInput.getText().isEmpty()) {
			fileScoreNames.writeString("Player" + System.getProperty("line.separator"), true);
		} else {
			fileScoreNames.writeString(txtInput.getText() + System.getProperty("line.separator"), true);
		}

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
