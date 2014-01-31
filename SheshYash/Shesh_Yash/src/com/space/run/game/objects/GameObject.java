package com.space.run.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {

	/**
	 * The position of this object.
	 */
	protected Vector2 position;

	/**
	 * The velocity of this object.
	 */
	protected Vector2 velocity;

	/**
	 * The width of this object.
	 */
	protected float width;
	
	/**
	 * The height of this object.
	 */
	protected float height;
	
	/**
	 * The rectangle that represents the collision box of this object.
	 */
	protected Rectangle recBounds = new Rectangle();

	/**
	 * Whether this object needs to be removed from the game.
	 */
	protected boolean needsRemoval;

	/**
	 * Updates the state of this object.
	 * 
	 * @param speed
	 *            The speed by which to scroll this object.
	 */
	public abstract void update(float speed);

	/**
	 * Draws this object to the screen.
	 * 
	 * @param batch
	 *            The SpriteBatch instance.
	 */
	public abstract void render(SpriteBatch batch);

	/**
	 * Gets the position of this object.
	 * 
	 * @return The position.
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Sets the position of this object.
	 * 
	 * @param position
	 *            The location to set this object's position.
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
		this.recBounds.set(position.x, position.y, recBounds.width, recBounds.height);
	}

	/**
	 * Gets the velocity of this object.
	 * 
	 * @return The velocity.
	 */
	public Vector2 getVelocity() {
		return velocity;
	}

	/**
	 * Sets the velocity of this object.
	 * 
	 * @param velocity
	 *            The vector to set the velocity of this object.
	 */
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	/**
	 * Gets the width of this object.
	 * 
	 * @return The width.
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Sets the width of this object.
	 * 
	 * @param width
	 *            The amount to set the width of this object.
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * Gets the height of this object.
	 * 
	 * @return The height.
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Sets the height of this object.
	 * 
	 * @param width
	 *            The amount to set the height of this object.
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * Gets the collision box of this object.
	 * 
	 * @return The rectangle that represents this object's collision box.
	 */
	public Rectangle getBounds() {
		return recBounds;
	}

	/**
	 * Sets the collision box of this object.
	 * 
	 * @param bounds
	 *            The rectangle to set this object's collision box.
	 * 
	 */
	public void setBounds(Rectangle bounds) {
		this.recBounds = bounds;
	}

	/**
	 * Checks whether this object need to be removed from the game.
	 * 
	 * @return Whether this object needs to be removed.
	 */
	public boolean needsRemoval() {
		return needsRemoval;
	}

	/**
	 * Flags that this object should be removed from the game.
	 */
	public void flagForRemoval() {
		this.needsRemoval = true;
	}
}
