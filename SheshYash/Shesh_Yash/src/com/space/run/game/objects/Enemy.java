package com.space.run.game.objects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.space.run.game.Assets;
import com.space.run.game.Level;

public class Enemy extends GameObject {

	/**
	 * The width of a enemy.
	 */
	public static final float ENEMY_WIDTH = 35;

	/**
	 * The height of a enemy.
	 */
	public static final float ENEMY_HEIGHT = 35;

	/**
	 * The time the enemy has to wait in order to shoot again.
	 */
	private static final float MAX_SHOT_COOLDOWN = 1;

	/**
	 * The texture of the enemy.
	 */
	private TextureRegion texRegEnemy;

	/**
	 * The list of the enemy's bullets.
	 */
	private ArrayList<GameObject> alBullets;

	/**
	 * The delay before the enemy can shoot. If this is greater than 0, the
	 * player cannot shoot.
	 */
	private float fShotTime = 0;

	/**
	 * The amount of points the player gets for killing this enemy;
	 */
	private int nPoints;

	/**
	 * The level instance, so the enemy knows the location of the player and
	 * obstacles for collision detection.
	 */
	private Level level;

	public Enemy(Vector2 position, Level level) {
		this.position = position;
		this.width = ENEMY_WIDTH;
		this.height = ENEMY_HEIGHT;
		this.level = level;

		recBounds.set(position.x, position.y, width, height);

		texRegEnemy = Assets.texRegEnemy1;

		alBullets = new ArrayList<GameObject>();

		nPoints = 20;
	}

	@Override
	public void update(float speed) {
		// Update the shot time delay.
		fShotTime = fShotTime > 0 ? fShotTime - Gdx.graphics.getDeltaTime() : 0;

		position.x -= speed;
		recBounds.set(position.x, position.y, width, height);

		/*
		 * Only shoot a bullet if the player is near this enemy and this enemy
		 * can shoot.
		 */
		if (position.y <= level.getPlayer().getPosition().y + 5 && position.y >= level.getPlayer().getPosition().y - 5 && position.x - level.getPlayer().getPosition().x < 500
				&& position.x - level.getPlayer().getPosition().x > 0 && canShoot()) {
			fShotTime = MAX_SHOT_COOLDOWN;

			// Create a bullet at the position of the enemy.
			alBullets.add(new Bullet("Enemy", position.cpy().add(width / 2, height / 2)));
		}

		// Update the enemy's bullets.
		for (int i = 0; i < alBullets.size(); i++) {
			Bullet bullet = (Bullet) alBullets.get(i);

			bullet.update(speed);

			if (bullet.needsRemoval()) {
				alBullets.remove(i);
				i--;
			}
		}

		// Check if this enemy's bullets collided with any other objects.
		for (int i = 0; i < level.getLevelSections().size(); i++) {
			checkCollisionOfBullets(alBullets, level.getLevelSections().get(i).getObstacles());
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(texRegEnemy, position.x, position.y, width, height);

		for (GameObject bullet : alBullets) {
			bullet.render(batch);
		}
	}

	/**
	 * Handles the collision between the enemy's bullets and a list of game
	 * objects.
	 * 
	 * @param alGameObjects
	 *            A list of objects that are currently in the game.
	 */
	private void checkCollisionOfBullets(ArrayList<GameObject> alBullets, ArrayList<GameObject> alGameObjects) {
		/*
		 * Cycle through the list of objects and check if there is a collision.
		 */
		for (GameObject bullet : alBullets) {
			for (GameObject object : alGameObjects) {
				if (bullet.getBounds().overlaps(object.getBounds())) {
					// If the bullet hits an obstacle, remove the bullet.
					if (object.getClass().equals(Obstacle.class)) {
						bullet.flagForRemoval();
					}
				}
			}
		}
	}

	/**
	 * Whether the enemy can currently shoot.
	 * 
	 * @return If the enemy's shot time delay is zero.
	 */
	private boolean canShoot() {
		return fShotTime <= 0;
	}

	/**
	 * Gets the enemy's bullets.
	 * 
	 * @return The list of bullets
	 */
	public ArrayList<GameObject> getBullets() {
		return alBullets;
	}

	/**
	 * Gets the value of this enemy.
	 * 
	 * @return The number of points this enemy is worth.
	 */
	public int getPoints() {
		return nPoints;
	}
}
