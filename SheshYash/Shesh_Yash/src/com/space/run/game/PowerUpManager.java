package com.space.run.game;

import com.space.run.game.objects.PowerUp;

public class PowerUpManager {

	/**
	 * The array of booleans that tells which power ups the player currently
	 * has.
	 */
	private boolean[] arbPowerUps = new boolean[2];

	/**
	 * The array of timers that correspond to the power ups.
	 */
	private float[] arfTimers = new float[2];

	/**
	 * Updates the power up timers.
	 * 
	 * @param delta
	 *            The time in seconds since the last frame.
	 */
	public void update(float delta) {
		for (int i = 0; i < arfTimers.length; i++) {
			arfTimers[i] = arfTimers[i] > 0 ? arfTimers[i] - delta : 0;
		}
	}

	/**
	 * Sets a power up to be collected.
	 * 
	 * @param nType
	 *            The type of power up.
	 */
	public void set(int nType) {
		arbPowerUps[nType] = true;
	}

	/**
	 * Activates a power up that the player has.
	 * 
	 * @param nType
	 *            The type of power up.
	 */
	public void activate(int nType) {
		// If the player doesn't have the power up, don't activate anything.
		if (!arbPowerUps[nType]) {
			return;
		}

		// Use the power up and set the timer.
		arbPowerUps[nType] = false;
		arfTimers[nType] = PowerUp.POWER_UP_LENGTHS[nType];
	}

	/**
	 * Sets and activates a power up.
	 * 
	 * @param nType
	 *            The type of power up.
	 */
	public void setAndActivate(int nType) {
		set(nType);
		activate(nType);
	}

	/**
	 * Checks whether the player has a power up.
	 * 
	 * @param nType
	 *            The type of power up.
	 * @return Whether the player has the power up.
	 */
	public boolean hasPowerUp(int nType) {
		return arbPowerUps[nType];
	}

	/**
	 * Checks whether a power up is currently active.
	 * 
	 * @param nType
	 *            The type of power up.
	 * @return Whether the power up is active.
	 */
	public boolean isActive(int nType) {
		for (int i = 0; i < arbPowerUps.length; i++) {
			if (nType == i && arfTimers[i] > 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the current state of the timer of a power up.
	 * 
	 * @param nType
	 *            The type of power up.
	 * @return The value of the timer.
	 */
	public float getTimer(int nType) {
		return arfTimers[nType];
	}

	/**
	 * Sets the timer of a power up.
	 * 
	 * @param nType
	 *            The type of power up.
	 * @param time
	 *            The value to set the timer.
	 */
	public void setTimer(int nType, float time) {
		arfTimers[nType] = time;
	}
}
