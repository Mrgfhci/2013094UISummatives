package com.space.run.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ParallaxLayer {

	/**
	 * The image of this layer.
	 */
	private TextureRegion texRegBackground;

	/**
	 * The position of this layer.
	 */
	private Vector2 position;

	/**
	 * The factor by which to scroll this layer.
	 */
	private float fScrollFactorX, fScrollFactorY;

	public ParallaxLayer(ParallaxLayer parallaxLayer) {
		this(parallaxLayer.getTexture(), parallaxLayer.getPosition().cpy(), parallaxLayer.getScrollFactorX(), parallaxLayer.getScrollFactorY());
	}

	public ParallaxLayer(TextureRegion texRegBackground, Vector2 position, float scrollFactorX, float scrollFactorY) {
		this.texRegBackground = texRegBackground;
		this.position = position;
		this.fScrollFactorX = scrollFactorX;
		this.fScrollFactorY = scrollFactorY;
	}

	/**
	 * Multiplies the speed by the scroll factor to make this layer scroll
	 * faster or slower in order to achieve the parallax scrolling effect. This
	 * layer scrolls in the opposite direction to the player to make it seem
	 * like the player is moving.
	 * 
	 * @param speed
	 *            The speed by which to scroll this layer.
	 */
	public void update(float speed) {
		position.x -= speed * fScrollFactorX;
		position.y -= speed * fScrollFactorY;
	}

	/**
	 * Gets this layer's image.
	 * 
	 * @return The texture.
	 */
	public TextureRegion getTexture() {
		return texRegBackground;
	}

	/**
	 * Sets this layer's image.
	 * 
	 * @param texRegBackground
	 *            The image to set this layer's to.
	 */
	public void setTexture(TextureRegion texRegBackground) {
		this.texRegBackground = texRegBackground;
	}

	/**
	 * Gets the position of this layer.
	 * 
	 * @return The position.
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Sets the position of this layer.
	 * 
	 * @param position
	 *            The location to set this layer to.
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	/**
	 * Gets the scroll factor along the x-axis.
	 * 
	 * @return The scroll factor x.
	 */
	public float getScrollFactorX() {
		return fScrollFactorX;
	}

	/**
	 * Sets the scroll factor along the x-axis.
	 * 
	 * @param fScrollFactorX
	 *            The rate at which to scroll by along the x-axis.
	 */
	public void setScrollFactorX(float fScrollFactorX) {
		this.fScrollFactorX = fScrollFactorX;
	}

	/**
	 * Gets the scroll factor along the y-axis.
	 * 
	 * @return The scroll factor y.
	 */
	public float getScrollFactorY() {
		return fScrollFactorY;
	}

	/**
	 * Sets the scroll factor along the y-axis.
	 * 
	 * @param fScrollFactorY
	 *            The rate at which to scroll by along the y-axis.
	 */
	public void setScrollFactorY(float fScrollFactorY) {
		this.fScrollFactorY = fScrollFactorY;
	}
}
