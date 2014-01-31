package com.space.run.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.space.run.game.Assets;

public class Coin extends GameObject {

	/**
	 * The width of a coin.
	 */
	public static final float COIN_WIDTH = 22;

	/**
	 * The height of a coin.
	 */
	public static final float COIN_HEIGHT = 22;

	/**
	 * The current texture of the coin.
	 */
	private TextureRegion texRegCoin;
	
	/**
	 * The value that keeps track of time for the animation.
	 */
	private float fStateTime;
	
	public Coin(Vector2 position) {
		this.position = position;
		this.width = COIN_WIDTH;
		this.height = COIN_HEIGHT;

		recBounds.set(position.x, position.y, width, height);
	}

	@Override
	public void update(float speed) {
		position.x -= speed;
		recBounds.set(position.x, position.y, width, height);

		// Add the delta to the state time for the animation.
		fStateTime += Gdx.graphics.getDeltaTime();
	}

	@Override
	public void render(SpriteBatch batch) {
		texRegCoin = Assets.animCoin.getKeyFrame(fStateTime, true);
		
		batch.draw(texRegCoin, position.x, position.y, width, height);
	}
}
