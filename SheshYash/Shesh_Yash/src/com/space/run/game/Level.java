package com.space.run.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.space.run.game.objects.Ground;
import com.space.run.game.objects.LevelSection;
import com.space.run.game.objects.Player;

public class Level {

	/**
	 * The delay between level section generations.
	 */
	private static final float NEW_SECTION_DELAY = 5;

	/**
	 * The background images of this level. Can have multiple layers scrolling
	 * at different speeds.
	 */
	private ParallaxBackground background;

	/**
	 * The player of this level.
	 */
	private Player player;

	/**
	 * The ground of this level. Can have holes in the ground.
	 */
	private Ground ground;

	/**
	 * The list of level sections. Makes up the platforms, enemies, coins, and
	 * obstacles that randomly appear.
	 */
	private ArrayList<LevelSection> alLevelSections;

	/**
	 * A timer that keeps track of how long it has been since a new level
	 * section was made.
	 */
	private float fTimer = 0;

	/**
	 * Whether the level should shake the game's camera.
	 */
	private boolean shouldShake = false;

	public Level() {
		player = new Player(new Vector2(100, 60), this);

		ground = new Ground();

		alLevelSections = new ArrayList<LevelSection>();
		alLevelSections.add(new LevelSection(this));

		background = new ParallaxBackground(new ParallaxLayer(Assets.texRegBackground2, new Vector2(0, 0), 0.125f, 0), new ParallaxLayer(Assets.texRegBackground1, new Vector2(0, -171), 0.25f, 0));
	}

	/**
	 * Updates the state of everything within the level.
	 * 
	 * @param delta
	 *            The time in seconds since the last frame.
	 */
	public void update(float delta) {
		fTimer += delta;

		if (fTimer >= NEW_SECTION_DELAY) {
			fTimer = 0;
			alLevelSections.add(new LevelSection(this));
		}

		player.update(delta);
		background.update(player.getVelocity().x * delta);
		ground.update(player.getVelocity().x * delta);

		for (int i = 0; i < alLevelSections.size(); i++) {
			LevelSection levelSection = alLevelSections.get(i);

			levelSection.update(player.getVelocity().x * delta);

			if (levelSection.needsRemoval()) {
				alLevelSections.remove(i);
				i--;
			}
		}
	}

	/**
	 * Draws everything within the level to the screen.
	 * 
	 * @param batch
	 *            The SpriteBatch instance.
	 */
	public void render(SpriteBatch batch) {
		background.render(batch);

		for (LevelSection levelSection : alLevelSections) {
			levelSection.render(batch);
		}

		ground.render(batch);
		player.render(batch);
	}

	/**
	 * Gets the level's player.
	 * 
	 * @return The player.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the ground of the level.
	 * 
	 * @return The level's ground.
	 */
	public Ground getGround() {
		return ground;
	}

	/**
	 * Gets the list of level sections that are currently in the game.
	 * 
	 * @return The list of level sections.
	 */
	public ArrayList<LevelSection> getLevelSections() {
		return alLevelSections;
	}

	/**
	 * Whether the level should make the game's camera shake.
	 * 
	 * @return Whether the level should shake.
	 */
	public boolean shouldShake() {
		return shouldShake;
	}

	/**
	 * Sets whether the level should shake the game's camera.
	 * 
	 * @param shouldShake
	 *            If the level should shake.
	 */
	public void setShouldShake(boolean shouldShake) {
		this.shouldShake = shouldShake;
	}
}
