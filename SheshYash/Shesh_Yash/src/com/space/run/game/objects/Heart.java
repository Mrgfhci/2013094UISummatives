package com.space.run.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.space.run.game.Assets;

public class Heart extends GameObject {

	/**
	 * The width of a heart.
	 */
	public static final float HEART_WIDTH = 22;

	/**
	 * The height of a heart.
	 */
	public static final float HEART_HEIGHT = 22;

	/**
	 * The texture of the heart.
	 */
	private TextureRegion texRegHeart;

	public Heart(Vector2 position) {
		this.position = position;
		this.width = HEART_WIDTH;
		this.height = HEART_HEIGHT;

		recBounds.set(position.x, position.y, width, height);

		texRegHeart = Assets.texRegHeart;
	}

	@Override
	public void update(float speed) {
		position.x -= speed;
		recBounds.set(position.x, position.y, width, height);
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texRegHeart, position.x, position.y, width, height);
	}
}
