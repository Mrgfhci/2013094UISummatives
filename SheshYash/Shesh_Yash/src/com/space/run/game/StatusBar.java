package com.space.run.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.space.run.game.objects.Heart;
import com.space.run.game.objects.Player;
import com.space.run.game.objects.PowerUp;
import com.space.run.game.screens.MainMenuScreen.ButtonListener;

public class StatusBar {

	/**
	 * The stage where the pause button is placed.
	 */
	private Stage stage;

	/**
	 * The pause button for the game. Pauses the game when pressed.
	 */
	private ImageTextButton btnPause;

	/**
	 * The level's player.
	 */
	private Player player;

	/**
	 * The value that keeps track of time for animation.
	 */
	private float fStateTime;

	public StatusBar(SideScroller game) {
		this.player = game.getGameScreen().getLevel().getPlayer();

		/*
		 * Menus(Buttons, Labels, etc):
		 * https://github.com/libgdx/libgdx/wiki/Scene2d
		 * https://github.com/libgdx/libgdx/wiki/Scene2d.ui
		 */
		stage = new Stage(SideScroller.WIDTH, SideScroller.HEIGHT, false, game.getBatch());

		btnPause = new ImageTextButton("", Assets.skinUI);
		btnPause.setName("Pause");
		btnPause.setSize(40, 40);
		btnPause.setPosition(SideScroller.WIDTH / 2 - btnPause.getWidth() / 2, (SideScroller.HEIGHT - 40));

		btnPause.addListener(new ButtonListener(game));
		stage.addActor(btnPause);
	}

	/**
	 * Updates the state time of the status bar.
	 * 
	 * @param delta
	 *            The time in seconds since the last frame.
	 */
	public void update(float delta) {
		fStateTime += delta;
		stage.act(delta);
	}

	/**
	 * Draws everything on the status bar to the screen.
	 * 
	 * @param batch
	 *            The SpriteBatch instance.
	 */
	public void render(SpriteBatch batch) {
		// Draws score and number of coins on top left part of screen.
		Assets.fontWhite.draw(batch, "Score " + player.getScore(), 10, SideScroller.HEIGHT - 10);
		Assets.fontWhite.draw(batch, "Coins " + player.getCoins(), 10, SideScroller.HEIGHT - 30);

		/*
		 * If the player has the bullet power up, draw the PowerUpOn texture,
		 * otherwise draw the PowerUpOff texture.
		 * 
		 * While the bullet power up is active, create a flashing effect on the
		 * PowerUpOn texture.
		 */
		if (player.getPowerUpManager().hasPowerUp(PowerUp.TYPE_BULLET_POWER_UP) || (player.getPowerUpManager().isActive(PowerUp.TYPE_BULLET_POWER_UP) && fStateTime % 0.25 < 0.125)) {
			batch.draw(Assets.texRegBulletPowerUpOn, SideScroller.WIDTH / 2 + 200, SideScroller.HEIGHT - 35, 34, 35);
		} else {
			batch.draw(Assets.texRegBulletPowerUpOff, SideScroller.WIDTH / 2 + 200, SideScroller.HEIGHT - 35, 34, 35);
		}

		/*
		 * Draws the number of lives the player has on the top right part of
		 * screen.
		 */
		for (int i = 0; i < player.getLives(); i++) {
			batch.draw(Assets.texRegHeart, SideScroller.WIDTH - ((30 * i) + 30), SideScroller.HEIGHT - 30, Heart.HEART_WIDTH, Heart.HEART_HEIGHT);
		}
	}

	/**
	 * Gets the status bar's stage where the pause button is drawn.
	 * 
	 * @return The status bar's stage.
	 */
	public Stage getStage() {
		return stage;
	}
}
