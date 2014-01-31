package com.space.run.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.space.run.game.Assets;
import com.space.run.game.SideScroller;
import com.space.run.game.screens.MainMenuScreen.ButtonListener;

public class HighScoresScreen implements Screen {

	/**
	 * The number of high scores to show on the screen.
	 */
	private static final int NUM_SCORES_TO_SHOW = 10;

	private SideScroller game;

	private Stage stage;
	private TextButton btnBack;
	private Label lblTitle;

	/**
	 * The scores.txt file. Holds the scores of a player.
	 */
	private FileHandle fileScores;

	/**
	 * The names.txt file. Holds the names of the player who got the score.
	 */
	private FileHandle fileScoreNames;

	/**
	 * The indices of the scores and names that are part of the high scores.
	 */
	private int[] arnIndices;

	/**
	 * The array of strings that holds the scores of the player who played the
	 * game.
	 */
	private String[] arsScores;

	/**
	 * The array of strings that holds the names of the player who played the
	 * game.
	 */
	private String[] arsNames;

	public HighScoresScreen(SideScroller game) {
		this.game = game;

		show();
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

		btnBack = new TextButton("Back", Assets.skinUI);
		btnBack.setName("Back");
		btnBack.setSize(150, 50);
		btnBack.setPosition(SideScroller.WIDTH / 2 - btnBack.getWidth() * 2, (SideScroller.HEIGHT / 2 + btnBack.getHeight() * 3) + 20);
		btnBack.addListener(new ButtonListener(game));

		lblTitle = new Label("High Scores", Assets.skinUI);
		lblTitle.setPosition(0, SideScroller.HEIGHT / 2 + 120);
		lblTitle.setWidth(SideScroller.WIDTH);
		lblTitle.setAlignment(Align.center);

		// Display the high scores.
		if (fileScores.exists() && fileScoreNames.exists()) {
			loop: for (int i = 0; i < NUM_SCORES_TO_SHOW; i++) {
				for (int j = 0; j < i; j++) {
					// Don't show duplicate indices.
					if (arnIndices[j] == arnIndices[i]) {
						continue loop;
					}
				}

				Label lblName = new Label(arsNames[arnIndices[i]], Assets.skinUI);
				lblName.setPosition(SideScroller.WIDTH / 2 - lblName.getWidth() - 20, SideScroller.HEIGHT / 2 + 75 - i * 30);

				Label lblScore = new Label(arsScores[arnIndices[i]], Assets.skinUI);
				lblScore.setPosition(SideScroller.WIDTH / 2 + 75, SideScroller.HEIGHT / 2 + 75 - i * 30);

				stage.addActor(lblName);
				stage.addActor(lblScore);
			}
		}

		stage.addActor(btnBack);
		stage.addActor(lblTitle);
	}

	@Override
	public void show() {
		/*
		 * File IO: https://github.com/libgdx/libgdx/wiki/File-handling
		 */
		fileScores = Gdx.files.local("data/statistics/scores.txt");
		arnIndices = new int[NUM_SCORES_TO_SHOW];

		if (fileScores.exists()) {
			arsScores = fileScores.readString().split(System.getProperty("line.separator"));
			String[] arsTemp = arsScores.clone();

			/*
			 * Find the NUM_SCORES_TO_SHOW largest numbers in the array of
			 * scores.
			 */
			for (int i = 0; i < NUM_SCORES_TO_SHOW; i++) {
				long lLargest = 0;
				int nTempIndex = -1;
				for (int j = 0; j < arsTemp.length; j++) {
					if (Long.valueOf(arsTemp[j]) > lLargest) {
						lLargest = Long.valueOf(arsTemp[j]);
						arsTemp[j] = "-1";
						arnIndices[i] = j;

						if (nTempIndex != -1) {
							arsTemp[nTempIndex] = arsScores[nTempIndex];
						}

						nTempIndex = j;
					}
				}
			}
		}

		fileScoreNames = Gdx.files.local("data/statistics/names.txt");

		if (fileScoreNames.exists()) {
			arsNames = fileScoreNames.readString().split(System.getProperty("line.separator"));
		}
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