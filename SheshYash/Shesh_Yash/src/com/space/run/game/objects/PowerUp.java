package com.space.run.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.space.run.game.Assets;

public class PowerUp extends GameObject {

	/**
	 * The width of a power up.
	 */
	public static final float POWER_UP_WIDTH = 30;

	/**
	 * The height of a power up.
	 */
	public static final float POWER_UP_HEIGHT = 30;

	/**
	 * The invincible power up type.
	 */
	public static final int TYPE_INVINCIBLE_POWER_UP = 0;

	/**
	 * The bullet power up type. Makes the player's bullets penetrate through
	 * obstacles and enemies.
	 */
	public static final int TYPE_BULLET_POWER_UP = 1;

	/**
	 * The amount of time in seconds that the power ups last. Index corresponds
	 * to the type of the power up.
	 */
	public static final float[] POWER_UP_LENGTHS = new float[] { 10, 10 };

	/**
	 * The type of the power up.
	 */
	private int nType;

	/**
	 * The texture of the power up.
	 */
	private TextureRegion texRegPowerUp;

	public PowerUp(int nType, Vector2 position) {
		this.position = position;
		this.nType = nType;
		this.width = POWER_UP_WIDTH;
		this.height = POWER_UP_HEIGHT;

		recBounds.set(position.x, position.y, width, height);

		// If there were more power ups, this would make more sense.
		if (nType == PowerUp.TYPE_INVINCIBLE_POWER_UP) {
			texRegPowerUp = Assets.texRegInvinciblePowerUp;
		}
	}

	@Override
	public void update(float speed) {
		position.x -= speed;
		recBounds.set(position.x, position.y, width, height);
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texRegPowerUp, position.x, position.y, width, height);
	}

	/**
	 * Gets the type of this power up.
	 * 
	 * @return The value that represents the type of power up.
	 */
	public int getType() {
		return nType;
	}
}
