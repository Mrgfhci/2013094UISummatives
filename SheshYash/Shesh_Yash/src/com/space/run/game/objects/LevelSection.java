package com.space.run.game.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.space.run.game.Level;
import com.space.run.game.SideScroller;

public class LevelSection extends GameObject {

	private static final float[] PLATFORM_HEIGHTS = new float[] { 120, 240, 360 };
	private static final float STARTING_X = SideScroller.WIDTH + 20;

	/**
	 * The normal chance there will be a heart in this section.
	 */
	private static final float CHANCE_FOR_HEART_LOW = 0.05f;

	/**
	 * The chance there will be a heart in this section when the player has only
	 * one life left.
	 */
	private static final float CHANCE_FOR_HEART_HIGH = 0.25f;

	/**
	 * The chance there will be a power up in this section.
	 */
	private static final float CHANCE_FOR_POWER_UP = 0.5f;

	private Level level;

	private ArrayList<GameObject> alPlatforms;
	private ArrayList<GameObject> alObstacles;
	private ArrayList<GameObject> alCoins;
	private ArrayList<GameObject> alHearts;
	private ArrayList<GameObject> alEnemies;
	private ArrayList<GameObject> alPowerUps;

	public LevelSection(Level level) {
		this.level = level;

		position = new Vector2(STARTING_X, 0);

		alPlatforms = new ArrayList<GameObject>();
		alObstacles = new ArrayList<GameObject>();
		alCoins = new ArrayList<GameObject>();
		alHearts = new ArrayList<GameObject>();
		alEnemies = new ArrayList<GameObject>();
		alPowerUps = new ArrayList<GameObject>();

		for (int i = 0; i < PLATFORM_HEIGHTS.length; i++) {
			placePlatforms(PLATFORM_HEIGHTS[i], MathUtils.random(1, 3));
		}

		generateObjects("Obstacle", MathUtils.random(0, 2), alObstacles, false);
		generateObjects("Obstacle", MathUtils.random(0, 2), alObstacles, true);

		placeCoins(MathUtils.random(5, 10), false);
		placeCoins(0, true);

		generateObjects("Enemy", MathUtils.random(0, 2), alEnemies, false);
		generateObjects("Enemy", MathUtils.random(0, 2), alEnemies, true);

		/*
		 * Only generate a heart if the player has 5 or less lives. If the
		 * player has 1 life left, there is a more likely chance of generating a
		 * heart.
		 */
		if (level.getPlayer().getLives() <= 5 && (level.getPlayer().getLives() == 1 && MathUtils.randomBoolean(CHANCE_FOR_HEART_HIGH)) || MathUtils.randomBoolean(CHANCE_FOR_HEART_LOW)) {
			generateObjects("Heart", 1, alHearts, true);
		}

		if (MathUtils.randomBoolean(CHANCE_FOR_POWER_UP)) {
			generateObjects("PowerUp", 1, alPowerUps, true);
		}

		ArrayList<GameObject> alAllGameObjects = new ArrayList<GameObject>();
		alAllGameObjects.addAll(alPlatforms);
		alAllGameObjects.addAll(alObstacles);
		alAllGameObjects.addAll(alCoins);
		alAllGameObjects.addAll(alHearts);
		alAllGameObjects.addAll(alEnemies);
		alAllGameObjects.addAll(alPowerUps);

		checkObjects(alAllGameObjects);
	}

	@Override
	public void update(float speed) {
		position.x -= speed;

		if (position.x <= 0) {
			flagForRemoval();
		}

		for (GameObject platform : alPlatforms) {
			platform.update(speed);
		}

		for (int i = 0; i < alObstacles.size(); i++) {
			Obstacle obstacle = (Obstacle) alObstacles.get(i);

			obstacle.update(speed);

			if (obstacle.needsRemoval()) {
				alObstacles.remove(i);
				i--;
			}
		}

		for (int i = 0; i < alCoins.size(); i++) {
			Coin coin = (Coin) alCoins.get(i);

			coin.update(speed);

			if (coin.needsRemoval()) {
				alCoins.remove(i);
				i--;
			}
		}

		for (int i = 0; i < alHearts.size(); i++) {
			Heart heart = (Heart) alHearts.get(i);

			heart.update(speed);

			if (heart.needsRemoval()) {
				alHearts.remove(i);
				i--;
			}
		}

		for (int i = 0; i < alEnemies.size(); i++) {
			Enemy enemy = (Enemy) alEnemies.get(i);

			enemy.update(speed);

			if (enemy.needsRemoval()) {
				alEnemies.remove(i);
				i--;
			}
		}

		for (int i = 0; i < alPowerUps.size(); i++) {
			PowerUp powerUp = (PowerUp) alPowerUps.get(i);

			powerUp.update(speed);

			if (powerUp.needsRemoval()) {
				alPowerUps.remove(i);
				i--;
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		for (GameObject platform : alPlatforms) {
			platform.render(batch);
		}

		for (GameObject obstacle : alObstacles) {
			obstacle.render(batch);
		}

		for (GameObject coin : alCoins) {
			coin.render(batch);
		}

		for (GameObject heart : alHearts) {
			heart.render(batch);
		}

		for (GameObject enemy : alEnemies) {
			enemy.render(batch);
		}

		for (GameObject powerUp : alPowerUps) {
			powerUp.render(batch);
		}
	}

	/**
	 * Places random platforms in the section.
	 * 
	 * @param fHeight
	 *            The height to place the platform.
	 * @param nPlatforms
	 *            The number of platforms to place.
	 */
	private void placePlatforms(float fHeight, int nPlatforms) {
		float fInitialPositionX = STARTING_X + MathUtils.random(0, 20);

		for (int i = 0; i < nPlatforms; i++) {
			float fPlatformLength = 0;
			float fPositionX = 0;

			if (fHeight == PLATFORM_HEIGHTS[0]) {
				fPlatformLength = MathUtils.random(150, 300);
				fPositionX = MathUtils.random(0, 25);
			} else if (fHeight == PLATFORM_HEIGHTS[1]) {
				fPlatformLength = MathUtils.random(200, 400);
				fPositionX = MathUtils.random(25, 50);
			} else {
				fPlatformLength = MathUtils.random(150, 300);
				fPositionX = MathUtils.random(40, 55);
			}

			Platform platform = new Platform(new Vector2(fInitialPositionX + fPositionX, fHeight), fPlatformLength);
			alPlatforms.add(platform);

			fInitialPositionX = platform.getPosition().x + platform.getWidth() + MathUtils.random(20, 70);
		}
	}

	/**
	 * Places coins in the section.
	 * 
	 * @param nCoins
	 *            The number of coins to place.
	 * @param onPlatform
	 *            Whether the coins should be placed on a platform.
	 */
	private void placeCoins(int nCoins, boolean onPlatform) {
		Vector2 position = new Vector2();

		if (onPlatform) {
			Platform platform = getRandomPlatform();
			position = platform.getPosition().cpy().add(0, platform.getHeight() + 20);
			nCoins = (int) (platform.getWidth() / Coin.COIN_WIDTH);
		} else {
			position.set(STARTING_X + MathUtils.random(100, 500), GroundBlock.GROUND_BLOCK_HEIGHT + 10);
		}

		for (int i = 0; i < nCoins; i++) {
			alCoins.add(new Coin(position.cpy()));
			position.add(Coin.COIN_WIDTH, 0);
		}
	}

	/**
	 * Creates objects to be placed in the section.
	 * 
	 * @param sType
	 *            The type of object.
	 * @param nObjects
	 *            The number of objects to place.
	 * @param alObjects
	 *            The array list to put the object in.
	 * @param onPlatform
	 *            Whether the object should be placed on a platform.
	 */
	private void generateObjects(String sType, int nObjects, ArrayList<GameObject> alObjects, boolean onPlatform) {
		for (int i = 0; i < nObjects; i++) {
			GameObject gameObject = null;

			if (sType == "Obstacle") {
				gameObject = new Obstacle(MathUtils.random(Obstacle.TYPE_SINGLE_BOX, Obstacle.TYPE_THREE_BOX), new Vector2());
			} else if (sType == "PowerUp") {
				gameObject = new PowerUp(0, new Vector2());
			} else if (sType == "Heart") {
				gameObject = new Heart(new Vector2());
			} else {
				gameObject = new Enemy(new Vector2(), level);
			}

			if (onPlatform) {
				placeObjectOnPlatform(alObjects, gameObject);
			} else {
				placeObjectOnGround(alObjects, gameObject);
			}
		}
	}

	/**
	 * Places the object on the ground, in a random location.
	 * 
	 * @param alObjects
	 *            The array list to put the object in.
	 * @param gameObject
	 *            The object to place.
	 */
	private void placeObjectOnGround(ArrayList<GameObject> alObjects, GameObject gameObject) {
		gameObject.setPosition(new Vector2(STARTING_X + MathUtils.random(200, 500), GroundBlock.GROUND_BLOCK_HEIGHT));

		alObjects.add(gameObject);

	}

	/**
	 * Places the object on a random platform.
	 * 
	 * @param alObjects
	 *            The array list to put the object in.
	 * @param gameObject
	 *            The object to place.
	 */
	private void placeObjectOnPlatform(ArrayList<GameObject> alObjects, GameObject gameObject) {
		Platform platform = getRandomPlatform();

		gameObject.setPosition(new Vector2(platform.getPosition().x + MathUtils.random(0, platform.getWidth() / 2), platform.getPosition().y + platform.getHeight()));
		alObjects.add(gameObject);
	}

	/**
	 * Updates the position of the level section to the object farthest to the
	 * right's position and removes objects in the level section that are
	 * overlapping or in places where a hole is located.
	 * 
	 * @param alAllGameObjects
	 *            The list of all the objects in this section.
	 */
	private void checkObjects(ArrayList<GameObject> alAllGameObjects) {
		for (int i = 0; i < alAllGameObjects.size(); i++) {
			GameObject object1 = alAllGameObjects.get(i);

			if (object1.getPosition().x + object1.getWidth() > position.x) {
				position.x += object1.getPosition().x + object1.getWidth();
			}

			for (int j = i + 1; j < alAllGameObjects.size(); j++) {
				GameObject object2 = alAllGameObjects.get(j);

				/*
				 * If 2 objects overlap and don't need removal, randomly remove
				 * one of them.
				 */
				if (!object1.needsRemoval() && !object2.needsRemoval() && object1.getBounds().overlaps(object2.getBounds())) {
					if (MathUtils.randomBoolean()) {
						object1.flagForRemoval();
					} else {
						object2.flagForRemoval();
					}
				}
			}

			for (GameObject groundBlock : level.getGround().getGroundBlocks()) {
				/*
				 * Move the object's bounds to make sure it collides with the
				 * ground block.
				 */
				object1.getBounds().y -= groundBlock.getHeight() / 2;

				// If it's not solid, remove the object.
				if (!((GroundBlock) groundBlock).isSolid() && object1.getBounds().overlaps(groundBlock.getBounds())) {
					object1.flagForRemoval();
				}

				// Change the object's bounds to what it was before.
				object1.getBounds().y += groundBlock.getHeight() / 2;
			}
		}
	}

	/**
	 * Gets a random platform from the array list of platforms.
	 * 
	 * @return The random platform.
	 */
	private Platform getRandomPlatform() {
		return (Platform) alPlatforms.get(MathUtils.random(0, alPlatforms.size() - 1));
	}

	public ArrayList<GameObject> getPlatforms() {
		return alPlatforms;
	}

	public ArrayList<GameObject> getObstacles() {
		return alObstacles;
	}

	public ArrayList<GameObject> getCoins() {
		return alCoins;
	}

	public ArrayList<GameObject> getHearts() {
		return alHearts;
	}

	public ArrayList<GameObject> getEnemies() {
		return alEnemies;
	}

	public ArrayList<GameObject> getPowerUps() {
		return alPowerUps;
	}
}
