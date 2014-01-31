package com.space.run.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.space.run.game.Assets;

public class GroundBlock extends GameObject {
	/**
	 * The width of a ground block.
	 */
	public static final float GROUND_BLOCK_WIDTH = 77;

	/**
	 * The height of a ground block.
	 */
	public static final float GROUND_BLOCK_HEIGHT = 60;

	/**
	 * The texture of the ground block.
	 */
	private TextureRegion texRegGround;

	/**
	 * Whether the ground block is solid. If it is solid, the player can collide
	 * with it. If it is not, the ground block is not rendered and the player
	 * will fall through it.
	 */
	private boolean isSolid = true;

	public GroundBlock(Vector2 position) {
		this(position, true);
	}

	public GroundBlock(Vector2 position, boolean isSolid) {
		this.position = position;
		this.width = GROUND_BLOCK_WIDTH;
		this.height = GROUND_BLOCK_HEIGHT;
		this.isSolid = isSolid;

		recBounds.set(position.x, position.y, width, height);

		texRegGround = Assets.texRegGround;
	}

	@Override
	public void update(float speed) {
		position.x -= speed;
		recBounds.set(position.x, position.y, width, height);
	}

	@Override
	public void render(SpriteBatch batch) {
		if (isSolid) {
			batch.draw(texRegGround, position.x, position.y, width, height);
		}
	}

	/**
	 * Checks if this ground block is solid(collidable).
	 * 
	 * @return Whether this ground block is solid.
	 */
	public boolean isSolid() {
		return isSolid;
	}
}
