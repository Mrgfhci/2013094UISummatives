package com.space.run.game.objects;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.space.run.game.Assets;
import com.space.run.game.Input;
import com.space.run.game.Level;
import com.space.run.game.PowerUpManager;

public class Player extends GameObject {

	/**
	 * The number of lives the player starts out with.
	 */
	private static final int STARTING_LIVES = 3;

	/**
	 * The number of points the player gets per second as they are running.
	 */
	private static final float POINTS_PER_SECOND = 60;

	/**
	 * The time the player is invincible when it hits an obstacle.
	 */
	private static final float MAX_INVINCIBLE_TIME = 2.5f;

	/**
	 * The time the player has to wait in order to shoot again.
	 */
	private static final float MAX_SHOT_COOLDOWN = 0.3f;

	/**
	 * The number of coins the player has to collect in order to get the bullet
	 * power up.
	 */
	private static final int COINS_FOR_BULLET_POWER_UP = 30;

	/**
	 * The speed at which the player can move at the start of the game.
	 */
	private static final float STARTING_SPEED = 2000;

	/**
	 * The maximum speed at which the player can move.
	 */
	private static final float MAX_SPEED = 3500;

	/**
	 * The force of gravity on the player.
	 */
	private static final float GRAVITY = -2800f;

	/**
	 * The maximum speed that the player can jump.
	 */
	private static final float MAX_JUMP_SPEED = 700f;

	/**
	 * The maximum time the player can jump, for one jump.
	 */
	private static final float LONG_JUMP_PRESS = 0.15f;

	/**
	 * The force of friction on the player.
	 */
	private static final float FRICTION = 0.885f;

	/**
	 * The current state of the player.
	 */
	private State state = State.RUNNING;

	/**
	 * The current texture of the player.
	 */
	private TextureRegion texRegPlayer;

	/**
	 * The player's current speed;
	 */
	private float fSpeed;

	/**
	 * The value that keeps track of time for the player's animation.
	 */
	private float fStateTime;

	/**
	 * Whether the player is jumping.
	 */
	private boolean isJumping;

	/**
	 * The time the player is jumping.
	 */
	private float fJumpTime = 0;

	/**
	 * Whether the player is on the ground.
	 */
	private boolean onGround = false;

	/**
	 * Whether the player should fall through the platform.
	 */
	private boolean shouldFall = false;

	/**
	 * The number of lives the player currently has.
	 */
	private int nLives;

	/**
	 * The number of points the player currently has.
	 */
	private static long lScore;

	/**
	 * The number of coins the player currently has.
	 */
	private int nCoins;

	/**
	 * The delay before the player can shoot. If this is greater than 0, the
	 * player cannot shoot.
	 */
	private float fShotTime = 0;

	/**
	 * The list of the player's bullets.
	 */
	private ArrayList<Bullet> alBullets;

	/**
	 * The player's power up manager. Handles the power ups that the player can
	 * collect.
	 */
	private PowerUpManager powerUpManager;

	/**
	 * Whether the player should be drawn as invincible.
	 */
	private boolean shouldDrawInvincible = false;

	/**
	 * The value that keeps track of the number of coins that goes towards
	 * getting the bullet power up.
	 */
	private int nBulletCoinCounter = 0;

	/**
	 * The level instance, so the player knows the location of all objects in
	 * the game and the ground for collision detection.
	 */
	private Level level;

	public enum State {
		RUNNING, JUMPING;
	}

	public Player(Vector2 position, Level level) {
		this.position = position;
		this.velocity = new Vector2();
		this.texRegPlayer = Assets.animPlayerRunning.getKeyFrame(0);
		this.width = texRegPlayer.getRegionWidth();
		this.height = texRegPlayer.getRegionWidth();
		this.fSpeed = STARTING_SPEED;
		this.level = level;

		recBounds.set(position.x, position.y, width, height);

		powerUpManager = new PowerUpManager();
		alBullets = new ArrayList<Bullet>();

		nLives = STARTING_LIVES;
		lScore = 0;
		nCoins = 0;
	}

	/**
	 * @param delta
	 *            The time in seconds since the last frame.
	 */
	@Override
	public void update(float delta) {
		// Increase the player's score.
		lScore += POINTS_PER_SECOND * delta;

		// Every 1000 score, increase the player's speed.
		if (lScore % 1000 == 0) {
			fSpeed += 200;
		}

		updateTimers(delta);

		/*
		 * Player Movement:
		 * http://obviam.net/index.php/getting-started-in-android
		 * -game-development
		 * -with-libgdx-tutorial-part-3-jumping-gravity-and-movement/
		 */

		// Check if the player jumps.
		if (Input.isKeyDown(Keys.UP) && velocity.y >= 0) {

			/*
			 * If the player is not already jumping, the player jumps and keeps
			 * track of the time that it jumped.
			 */
			if (state != State.JUMPING) {
				isJumping = true;
				fJumpTime = LONG_JUMP_PRESS;

				state = State.JUMPING;
				velocity.y = MAX_JUMP_SPEED;

				onGround = false;

				Assets.soundJump.play();
			} else {

				/*
				 * If the jump time is zero, set it so the player is not jumping
				 * anymore. Otherwise, make the player jump a little higher.
				 */
				if (isJumping && fJumpTime <= 0) {
					isJumping = false;
				} else if (isJumping) {
					velocity.y = MAX_JUMP_SPEED;
				}
			}
		}

		/*
		 * Check if the player wants to fall through the platform that it is
		 * standing on.
		 */
		if (Input.isKeyDown(Keys.DOWN) && !shouldFall) {
			if (state != State.JUMPING && onGround) {
				onGround = false;
				shouldFall = true;
			}
		}

		/*
		 * Check if the player the player shoots. Only shoot if the shot time
		 * delay is 0.
		 */
		if (Input.isKeyDown(Keys.Z) && canShoot()) {
			fShotTime = MAX_SHOT_COOLDOWN;

			// Create a bullet at the position of the player.
			alBullets.add(new Bullet(position.cpy().add(width / 2, height / 2 - 7)));
			Assets.soundShoot.play();
		}

		// Check if the player wants to use the bullet power up.
		if (Input.isKeyPressed(Keys.X)) {
			powerUpManager.activate(PowerUp.TYPE_BULLET_POWER_UP);
		}

		// If you get enough coins, collect the bullet power up.
		if (nBulletCoinCounter >= COINS_FOR_BULLET_POWER_UP) {
			powerUpManager.set(PowerUp.TYPE_BULLET_POWER_UP);
			nBulletCoinCounter = 0;
		}

		/*
		 * If the player is on the ground and jumping, set the player's state to
		 * be running.
		 */
		if (onGround && state == State.JUMPING) {
			state = State.RUNNING;
		}

		/*
		 * If the player falls off the level or loses all of its lives, the game
		 * is over.
		 */
		if (position.y < 0 || nLives <= 0) {
			flagForRemoval();
		}

		/*
		 * Add the speed multiplied by the delta to the velocity. Delta is the
		 * time in between frames and is multiplied to the speed to prevent
		 * faster computers from running the game faster than intended.
		 */
		velocity.x += fSpeed * delta;
		velocity.y += GRAVITY * delta;

		/*
		 * Check if the player or player's bullets collided with any other
		 * objects. The velocity is multiplied by the delta time again in order
		 * to figure out where the player is supposed to be this frame to be
		 * used in the collision detection. It is then undone in order to not
		 * mess with the other calculations.
		 */
		velocity.scl(delta);

		/*
		 * Collision:
		 * http://obviam.net/index.php/getting-started-in-android-game
		 * -development-with-libgdx-tutorial-part-4-collision-detection/
		 */

		// Only check collision with the solid ground blocks.
		checkCollisionXY(level.getGround().getSolidGroundBlocks());

		for (LevelSection levelSection : level.getLevelSections()) {
			checkCollisionWithPlatforms(levelSection.getPlatforms());

			checkCollisionXY(levelSection.getObstacles());
			checkCollisionXY(levelSection.getCoins());
			checkCollisionXY(levelSection.getHearts());
			checkCollisionXY(levelSection.getEnemies());
			checkCollisionXY(levelSection.getPowerUps());

			for (GameObject enemy : levelSection.getEnemies()) {
				checkCollisionX(((Enemy) enemy).getBullets());
			}

			checkCollisionOfBullets(levelSection.getEnemies());
			checkCollisionOfBullets(levelSection.getObstacles());
		}

		velocity.scl(1 / delta);

		// If the player decides to stop, friction slows it down gradually.
		velocity.x *= FRICTION;

		// Clamp the player's velocity to the maximum speed.
		velocity.x = Math.min(velocity.x, MAX_SPEED);

		/*
		 * Add the velocity to the position and set the player's collision box
		 * to the current position.
		 */
		position.add(0, velocity.y * delta);
		recBounds.set(position.x, position.y, width, height);

		// Add the delta to the state time for the animation.
		fStateTime += delta;

		// Update the player's bullets.
		for (int i = 0; i < alBullets.size(); i++) {
			Bullet bullet = alBullets.get(i);

			bullet.update(delta);

			if (bullet.needsRemoval()) {
				alBullets.remove(i);
				i--;
			}
		}

		powerUpManager.update(delta);

		if (!powerUpManager.isActive(PowerUp.TYPE_INVINCIBLE_POWER_UP)) {
			shouldDrawInvincible = false;
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		/*
		 * Animation: https://github.com/libgdx/libgdx/wiki/Sprite-animation
		 * 
		 * Set the current texture to the current frame of the running
		 * animation.
		 */
		texRegPlayer = shouldDrawInvincible ? Assets.animPlayerRunningInvincible.getKeyFrame(fStateTime, true) : Assets.animPlayerRunning.getKeyFrame(fStateTime, true);

		/*
		 * If the player is jumping, change the texture to the jumping sprite.
		 * On the way down, change it the falling sprite.
		 */
		if (state == State.JUMPING) {
			if (shouldDrawInvincible) {
				texRegPlayer = velocity.y > 0 ? Assets.texRegPlayerJumpInvincible : Assets.texRegPlayerFallInvincible;
			} else {
				texRegPlayer = velocity.y > 0 ? Assets.texRegPlayerJump : Assets.texRegPlayerFall;
			}
		}

		// Create flashing effect if player is currently invincible.
		if (!powerUpManager.isActive(PowerUp.TYPE_INVINCIBLE_POWER_UP) || fStateTime % 0.25 < 0.125) {
			width = texRegPlayer.getRegionWidth();
			height = texRegPlayer.getRegionHeight();
			batch.draw(texRegPlayer, position.x, position.y, width, height);
		}

		for (Bullet bullet : alBullets) {
			bullet.render(batch);
		}
	}

	/**
	 * Checks the collision between the player and a list of objects on both the
	 * x and y axis.
	 * 
	 * @param alGameObjects
	 *            A list of objects to check the collision on both axes.
	 */
	private void checkCollisionXY(ArrayList<GameObject> alGameObjects) {
		checkCollisionX(alGameObjects);
		checkCollisionY(alGameObjects);
	}

	/**
	 * Handles the collision between the player and a list of objects along the
	 * x-axis.
	 * 
	 * @param alGameObjects
	 *            A list of objects to check collision with along the x-axis.
	 */
	private void checkCollisionX(ArrayList<GameObject> alGameObjects) {
		// Set the next x-position of the player to the player's collision box.
		recBounds.x += velocity.x;

		/*
		 * Cycle through the list of objects and check if there is a collision.
		 */
		for (GameObject object : alGameObjects) {
			if (recBounds.overlaps(object.getBounds())) {

				/*
				 * If the player hits an obstacle, enemy, or enemy's bullet, the
				 * player's lives are decreased and the player becomes
				 * invincible for MAX_INVINCIBLE_TIME. The obstacle, enemy, or
				 * enemy's bullet is also removed.
				 * 
				 * If the player is already invincible and is inside an
				 * obstacle, enemy, or enemy's bullet, but the invincible time
				 * is less than 0.3 seconds, the invincible time is increased
				 * for a little bit.
				 * 
				 * The camera also shakes.
				 */
				if (object.getClass().equals(Obstacle.class) || object.getClass().equals(Enemy.class) || object.getClass().equals(Bullet.class)) {
					if (powerUpManager.isActive(PowerUp.TYPE_INVINCIBLE_POWER_UP)) {
						if (powerUpManager.getTimer(PowerUp.TYPE_INVINCIBLE_POWER_UP) < 0.3) {
							powerUpManager.setTimer(PowerUp.TYPE_INVINCIBLE_POWER_UP, powerUpManager.getTimer(PowerUp.TYPE_INVINCIBLE_POWER_UP) + 0.3f);
						}
						break;
					}
					
					level.setShouldShake(true);
					object.flagForRemoval();

					nLives--;
					powerUpManager.setTimer(PowerUp.TYPE_INVINCIBLE_POWER_UP, MAX_INVINCIBLE_TIME);
				}

				/*
				 * If the object is a coin, the player's coins are increased and
				 * the coin is removed.
				 * 
				 * The coin counter for the bullet power up is also increased if
				 * the player doesn't have the power up already.
				 */
				else if (object.getClass().equals(Coin.class)) {
					object.flagForRemoval();
					nCoins++;

					if (!powerUpManager.isActive(PowerUp.TYPE_BULLET_POWER_UP) || !powerUpManager.hasPowerUp(PowerUp.TYPE_BULLET_POWER_UP)) {
						nBulletCoinCounter++;
					}
				}

				/*
				 * If the object is a heart, the player's lives are increased
				 * and the heart is removed.
				 */
				else if (object.getClass().equals(Heart.class)) {
					object.flagForRemoval();
					nLives++;
				}

				/*
				 * If the object is a power up, the power up is activated and
				 * the power up object is removed. The player will also be drawn
				 * as invincible.
				 */
				else if (object.getClass().equals(PowerUp.class)) {
					if (((PowerUp) object).getType() == PowerUp.TYPE_INVINCIBLE_POWER_UP) {
						powerUpManager.setAndActivate(PowerUp.TYPE_INVINCIBLE_POWER_UP);
						shouldDrawInvincible = true;
					}

					object.flagForRemoval();
				}

				/*
				 * If the object isn't any of the ones above, stop the player
				 * from moving on the x-axis.
				 */
				else {
					velocity.x = 0;
					break;
				}
			}
		}

		// Reset the collision box to the player's current position.
		recBounds.x = position.x;
	}

	/**
	 * Handles the collision between the player and a list of objects along the
	 * y-axis.
	 * 
	 * @param alGameObjects
	 *            A list of objects to check collision with along the y-axis.
	 */
	private void checkCollisionY(ArrayList<GameObject> alGameObjects) {
		// Set the next y-position of the player to the player's collision box.
		recBounds.y += velocity.y;

		/*
		 * Cycle through the list of objects and check if there is a collision.
		 */
		for (GameObject object : alGameObjects) {
			if (recBounds.overlaps(object.getBounds())) {

				/*
				 * If the object is a coin, the player's coins are increased and
				 * the coin is removed.
				 * 
				 * The coin counter for the bullet power up is also increased if
				 * the player doesn't have the power up already.
				 */
				if (object.getClass().equals(Coin.class)) {
					object.flagForRemoval();
					nCoins++;

					if (!powerUpManager.isActive(PowerUp.TYPE_BULLET_POWER_UP) || !powerUpManager.hasPowerUp(PowerUp.TYPE_BULLET_POWER_UP)) {
						nBulletCoinCounter++;
					}
				}

				/*
				 * If the object is a heart, the player's lives are increased
				 * and the heart is removed.
				 */
				else if (object.getClass().equals(Heart.class)) {
					object.flagForRemoval();
					nLives++;
				}

				/*
				 * If the object is a power up, the power up is activated and
				 * the power up object is removed. The player will also be drawn
				 * as invincible.
				 */
				else if (object.getClass().equals(PowerUp.class)) {
					if (((PowerUp) object).getType() == PowerUp.TYPE_INVINCIBLE_POWER_UP) {
						powerUpManager.setAndActivate(PowerUp.TYPE_INVINCIBLE_POWER_UP);
						shouldDrawInvincible = true;
					}

					object.flagForRemoval();
				}

				else if (object.getClass().equals(Enemy.class)) {
					if (powerUpManager.isActive(PowerUp.TYPE_INVINCIBLE_POWER_UP)) {
						if (powerUpManager.getTimer(PowerUp.TYPE_INVINCIBLE_POWER_UP) < 0.3) {
							powerUpManager.setTimer(PowerUp.TYPE_INVINCIBLE_POWER_UP, powerUpManager.getTimer(PowerUp.TYPE_INVINCIBLE_POWER_UP) + 0.3f);
						}
						break;
					}

					level.setShouldShake(true);
					object.flagForRemoval();

					nLives--;
					powerUpManager.setTimer(PowerUp.TYPE_INVINCIBLE_POWER_UP, MAX_INVINCIBLE_TIME);
				}

				/*
				 * If the object isn't any of the ones above, stop the player
				 * from moving on the y-axis and make the player on ground.
				 */
				else {
					if (velocity.y < 0) {
						onGround = true;
						shouldFall = false;
					}
					velocity.y = 0;
					break;
				}
			}
		}

		// Reset the collision box to the player's current position.
		recBounds.y = position.y;
	}

	/**
	 * Handles the collision between the player and a list of platforms. These
	 * platforms are one-way platforms, so you can go through them from below
	 * but won't fall through them from above.
	 * 
	 * @param alPlatforms
	 *            A list of platforms that are currently in the game.
	 */
	private void checkCollisionWithPlatforms(ArrayList<GameObject> alPlatforms) {
		recBounds.y += velocity.y;

		/*
		 * Cycle through the list of platforms and only does the collision if
		 * the player is above the platform and doesn't want to fall through the
		 * platform.
		 */
		for (GameObject platform : alPlatforms) {
			if (recBounds.overlaps(platform.getBounds()) && position.y > platform.getPosition().y + platform.getHeight() && !shouldFall) {
				if (velocity.y < 0) {
					onGround = true;
					shouldFall = false;
				}
				velocity.y = 0;
				break;
			}
		}

		shouldFall = false;

		recBounds.y = position.y;
	}

	/**
	 * Handles the collision between the player's bullets and a list of game
	 * objects.
	 * 
	 * @param alGameObjects
	 *            A list of objects that are currently in the game.
	 */
	private void checkCollisionOfBullets(ArrayList<GameObject> alGameObjects) {
		/*
		 * Cycle through the list of objects and check if there is a collision.
		 */
		for (Bullet bullet : alBullets) {
			for (GameObject object : alGameObjects) {
				if (bullet.getBounds().overlaps(object.getBounds())) {

					/*
					 * If the bullet hits an enemy, remove both the bullet and
					 * the enemy and increase the score by the amount of points
					 * the enemy is worth.
					 * 
					 * If the bullet power up is active, don't remove the
					 * bullet.
					 */
					if (object.getClass().equals(Enemy.class)) {
						if (!(powerUpManager.isActive(PowerUp.TYPE_BULLET_POWER_UP))) {
							bullet.flagForRemoval();
						}

						object.flagForRemoval();

						lScore += ((Enemy) object).getPoints();
					}

					/*
					 * If the bullet hits an obstacle, remove the bullet.
					 * 
					 * If the bullet power up is active, don't remove the bullet
					 * and remove the obstacle.
					 */
					else if (object.getClass().equals(Obstacle.class)) {
						if (!(powerUpManager.isActive(PowerUp.TYPE_BULLET_POWER_UP))) {
							bullet.flagForRemoval();
						} else {
							object.flagForRemoval();
						}
					}
				}
			}
		}
	}

	/**
	 * Updates the various timers that the player has. Used for delays for the
	 * player.
	 * 
	 * @param delta
	 *            The time in seconds since the last frame.
	 */
	private void updateTimers(float delta) {
		fJumpTime = fJumpTime > 0 ? fJumpTime - delta : 0;
		fShotTime = fShotTime > 0 ? fShotTime - delta : 0;
	}

	/**
	 * Whether the player can currently shoot.
	 * 
	 * @return If the player's shot time delay is zero.
	 */
	private boolean canShoot() {
		return fShotTime <= 0;
	}

	/**
	 * Gets the number of lives the player has.
	 * 
	 * @return The number of lives the player currently has.
	 */
	public int getLives() {
		return nLives;
	}

	/**
	 * Gets the score the player has.
	 * 
	 * @return The score the player currently has.
	 */
	public long getScore() {
		return lScore;
	}

	/**
	 * Gets the coins the player has.
	 * 
	 * @return The number of coins the player currently has.
	 */
	public int getCoins() {
		return nCoins;
	}

	/**
	 * Gets the power up manager.
	 * 
	 * @return The player's power up manager.
	 */
	public PowerUpManager getPowerUpManager() {
		return powerUpManager;
	}
}
