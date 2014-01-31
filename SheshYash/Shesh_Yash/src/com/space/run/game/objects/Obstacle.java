package com.space.run.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.space.run.game.Assets;

public class Obstacle extends GameObject {

	/**
	 * The single box obstacle type.
	 */
	public static final int TYPE_SINGLE_BOX = 0;

	/**
	 * The width of the single box obstacle.
	 */
	public static final float SINGLE_BOX_WIDTH = 46;

	/**
	 * The height of the single box obstacle.
	 */
	public static final float SINGLE_BOX_HEIGHT = 42;

	/**
	 * The three box obstacle type.
	 */
	public static final int TYPE_THREE_BOX = 1;

	/**
	 * The width of the three box obstacle.
	 */
	public static final float THREE_BOX_WIDTH = 34;

	/**
	 * The height of the three box obstacle.
	 */
	public static final float THREE_BOX_HEIGHT = 68;

	/**
	 * The type of the obstacle.
	 */
	private int nType;

	/**
	 * The texture of the obstacle.
	 */
	private TextureRegion texRegObstacle;

	public Obstacle(Vector2 position) {
		this(Obstacle.TYPE_SINGLE_BOX, position);
	}

	public Obstacle(int nType, Vector2 position) {
		this.position = position;
		this.nType = nType;

		width = nType == Obstacle.TYPE_SINGLE_BOX ? SINGLE_BOX_WIDTH : THREE_BOX_WIDTH;
		height = nType == Obstacle.TYPE_SINGLE_BOX ? SINGLE_BOX_HEIGHT : THREE_BOX_HEIGHT;

		recBounds.set(position.x, position.y, width, height);

		texRegObstacle = nType == Obstacle.TYPE_SINGLE_BOX ? Assets.texRegObstacle1 : Assets.texRegObstacle2;
	}

	@Override
	public void update(float speed) {
		position.x -= speed;
		recBounds.set(position.x, position.y, width, height);
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texRegObstacle, position.x, position.y, width, height);
	}

	/**
	 * Gets the type of this obstacle.
	 * 
	 * @return The value that represents the type of obstacle.
	 */
	public int getType() {
		return nType;
	}
}
