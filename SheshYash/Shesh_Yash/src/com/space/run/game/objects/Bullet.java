package com.space.run.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.space.run.game.Assets;
import com.space.run.game.SideScroller;

public class Bullet extends GameObject {

	/**
	 * The width of a bullet.
	 */
	public static final float BULLET_WIDTH = 14.1f;

	/**
	 * The height of a bullet.
	 */
	public static final float BULLET_HEIGHT = 6;

	/**
	 * The speed of the bullet for the player.
	 */
	public static final float PLAYER_BULLET_SPEED = 350;

	/**
	 * The speed of the bullet for the enemy.
	 */
	public static final float ENEMY_BULLET_SPEED = 300;
	/**
	 * The texture of the bullet.
	 */
	private TextureRegion texRegBullet;

	/**
	 * The name of the object that shot this bullet.
	 */
	private String sOwnerName;

	public Bullet(Vector2 position) {
		this("Player", position);
	}

	public Bullet(String sOwnerName, Vector2 position) {
		this.position = position;
		this.velocity = new Vector2();
		this.width = BULLET_WIDTH;
		this.height = BULLET_HEIGHT;
		this.sOwnerName = sOwnerName;

		recBounds.set(position.x, position.y, width, height);

		texRegBullet = sOwnerName.equals("Player") ? Assets.texRegBullet1 : Assets.texRegBullet2;
	}

	@Override
	public void update(float speed) {
		/*
		 * If the player shot the bullet, the bullet travels left. If an enemy
		 * shot the bullet, the bullet travels right.
		 */
		velocity.x = sOwnerName.equals("Player") ? PLAYER_BULLET_SPEED : -ENEMY_BULLET_SPEED;

		// If the bullet goes off the screen, remove it from the game.
		if (position.x > SideScroller.WIDTH || position.x < 0) {
			flagForRemoval();
		}

		// Make sure bullet speed increases with faster player speed.
		position.add(velocity.scl(Gdx.graphics.getDeltaTime()).add(-speed, 0));
		recBounds.set(position.x, position.y, width, height);
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texRegBullet, position.x, position.y, width, height);
	}

}
