package com.space.run.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.space.run.game.Assets;

public class Platform extends GameObject {

	/**
	 * The height of the platform.
	 */
	public static final float PLATFORM_HEIGHT = 30;

	/**
	 * The texture of the platform.
	 */
	private TextureRegion texRegPlatform;

	public Platform(Vector2 position, float width) {
		this.position = position;
		this.width = width;
		this.height = PLATFORM_HEIGHT;

		recBounds.set(position.x, position.y, width, height);

		texRegPlatform = Assets.texRegPlatform;
	}

	@Override
	public void update(float speed) {
		position.x -= speed;
		recBounds.set(position.x, position.y, width, height);
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texRegPlatform, position.x, position.y, width, height);
	}
}
