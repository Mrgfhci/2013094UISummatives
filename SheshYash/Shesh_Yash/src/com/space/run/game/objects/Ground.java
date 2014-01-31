package com.space.run.game.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.space.run.game.SideScroller;

public class Ground extends GameObject {

	/**
	 * The chance that the next ground block placed will not be solid.
	 */
	private static final float CHANCE_FOR_HOLE = 0.1f;

	/**
	 * The list of ground blocks that make up the ground.
	 */
	private ArrayList<GameObject> alGroundBlocks;

	/**
	 * The list of ground blocks that make up the ground that are solid.
	 */
	private ArrayList<GameObject> alSolidGroundBlocks;

	public Ground() {
		alGroundBlocks = new ArrayList<GameObject>();
		alSolidGroundBlocks = new ArrayList<GameObject>();

		// Initially create the ground across the entire width of the screen.
		for (int i = 0; i < (SideScroller.WIDTH / GroundBlock.GROUND_BLOCK_WIDTH); i++) {
			alGroundBlocks.add(new GroundBlock(new Vector2(GroundBlock.GROUND_BLOCK_WIDTH * i, 0)));
		}

		alSolidGroundBlocks.addAll(alGroundBlocks);
	}

	@Override
	public void update(float speed) {
		/*
		 * Cycle through the list of ground blocks and check if a ground block
		 * has gone past the left most part of the screen. If it has, put a new
		 * ground block just past the right most part of the screen. Once it is
		 * completely off the screen, remove it from the list.
		 * 
		 * There is a 10% chance that the ground block will not be solid,
		 * leaving a hole where the player can fall off the level.
		 */
		int nCount = 0;
		
		for (int i = 0; i < alGroundBlocks.size(); i++) {
			GroundBlock groundBlock = (GroundBlock) alGroundBlocks.get(i);

			groundBlock.update(speed);

			if (groundBlock.getPosition().x <= 0) {
				if (MathUtils.randomBoolean(CHANCE_FOR_HOLE) && nCount < 2) {
					alGroundBlocks.add(new GroundBlock(new Vector2(alGroundBlocks.get(alGroundBlocks.size() - 1).getPosition().x + GroundBlock.GROUND_BLOCK_WIDTH, 0), false));
					nCount++;
				} else {
					GroundBlock newGroundBlock = new GroundBlock(new Vector2(alGroundBlocks.get(alGroundBlocks.size() - 1).getPosition().x + GroundBlock.GROUND_BLOCK_WIDTH, 0));
					alGroundBlocks.add(newGroundBlock);
					alSolidGroundBlocks.add(newGroundBlock);
					nCount = 0;
				}
			}

			if (groundBlock.getPosition().x <= -GroundBlock.GROUND_BLOCK_WIDTH) {
				groundBlock.flagForRemoval();
			}

			if (groundBlock.needsRemoval()) {
				alGroundBlocks.remove(i);
				i--;
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		for (GameObject groundBlock : alGroundBlocks) {
			groundBlock.render(batch);
		}
	}

	/**
	 * Gets the list of ground blocks.
	 * 
	 * @return The array list of ground blocks.
	 */
	public ArrayList<GameObject> getGroundBlocks() {
		return alGroundBlocks;
	}

	/**
	 * Gets the list of solid ground blocks.
	 * 
	 * @return The array list of solid ground blocks.
	 */
	public ArrayList<GameObject> getSolidGroundBlocks() {
		return alSolidGroundBlocks;
	}
}
