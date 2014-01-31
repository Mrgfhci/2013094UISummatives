package com.space.run.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

/*
 * Input Handling: https://github.com/libgdx/libgdx/wiki/Event-handling
 */
public class Input extends InputAdapter implements GestureListener {

	/**
	 * Holds the state of which keys are currently down.
	 */
	private static boolean[] arbKeys = new boolean[256];

	/**
	 * Holds the state of the last keys that were down.
	 */
	private static boolean[] arbPreviousKeys = new boolean[256];

	/**
	 * The timer that keeps track of the time after the player has swiped down
	 * on the screen.
	 */
	private static float fSwipeDelay = 0;

	/**
	 * Updates the array of keys that are currently down.
	 */
	public static void update() {
		/*
		 * If on Android and the player swipes down, create a 0.005 second delay
		 * until the down key is set to false.
		 */
		fSwipeDelay = fSwipeDelay > 0 ? fSwipeDelay - Gdx.graphics.getDeltaTime() : 0;
		if (arbKeys[Keys.DOWN] && fSwipeDelay <= 0 && Gdx.app.getType().equals(ApplicationType.Android)) {
			arbKeys[Keys.DOWN] = false;
		}

		for (int i = 0; i < arbKeys.length; i++) {
			arbPreviousKeys[i] = arbKeys[i];
		}
	}

	/**
	 * Sets all of the keys to up.
	 */
	public static void resetKeys() {
		for (int i = 0; i < arbKeys.length; i++) {
			arbKeys[i] = arbPreviousKeys[i] = false;
		}
	}

	/**
	 * Checks if a key is being held down.
	 * 
	 * @param keycode
	 *            The keycode for the key.
	 * 
	 * @return If the key is being held down.
	 */
	public static boolean isKeyDown(int keycode) {
		return arbKeys[keycode];
	}

	/**
	 * Checks if a key has been pressed.
	 * 
	 * @param keycode
	 *            The keycode for the key.
	 * 
	 * @return If the key is pressed.
	 */
	public static boolean isKeyPressed(int keycode) {
		return arbKeys[keycode] && !arbPreviousKeys[keycode];
	}

	@Override
	public boolean keyDown(int keycode) {
		arbKeys[keycode] = true;
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		arbKeys[keycode] = false;
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android)) {
			return false;
		}

		if (screenX < Gdx.graphics.getWidth() / 2 - 100 && screenY > Gdx.graphics.getHeight() - 350) {
			arbKeys[Keys.UP] = true;
		}

		if (screenX > Gdx.graphics.getWidth() / 2 + 100 && screenY > Gdx.graphics.getHeight() - 350) {
			arbKeys[Keys.Z] = true;
		}

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android)) {
			return false;
		}

		if (arbKeys[Keys.X]) {
			arbKeys[Keys.X] = false;
		}

		if (screenX < Gdx.graphics.getWidth() / 2 - 100 && screenY > Gdx.graphics.getHeight() - 350) {
			arbKeys[Keys.UP] = false;
		}

		if (screenX > Gdx.graphics.getWidth() / 2 + 100 && screenY > Gdx.graphics.getHeight() - 350) {
			arbKeys[Keys.Z] = false;
		}

		return false;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android)) {
			return false;
		}

		if (count == 2) {
			arbKeys[Keys.X] = true;
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		if (!Gdx.app.getType().equals(ApplicationType.Android)) {
			return false;
		}

		if (Math.abs(velocityX) > Math.abs(velocityY)) {
			if (velocityX > 0) {
				// Gdx.app.log("tag", "swiped right");
			} else {
				// Gdx.app.log("tag", "swiped left");
			}
		} else {
			if (velocityY > 0) {
				// Gdx.app.log("tag", "swiped down");
				arbKeys[Keys.DOWN] = true;
				fSwipeDelay = 0.005f;
			} else {
				// Gdx.app.log("tag", "swiped up");
			}
		}

		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
}