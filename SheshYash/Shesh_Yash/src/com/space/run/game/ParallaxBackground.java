package com.space.run.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParallaxBackground {

	/**
	 * The layers that make up this background.
	 */
	private ParallaxLayer[] arLayers;

	/**
	 * A copy of the layers for infinite scrolling.
	 */
	private ParallaxLayer[] arLayersCopy;

	public ParallaxBackground(ParallaxLayer... arLayers) {
		this.arLayers = arLayers;
		this.arLayersCopy = new ParallaxLayer[arLayers.length];

		for (int i = 0; i < arLayers.length; i++) {
			arLayersCopy[i] = new ParallaxLayer(arLayers[i]);
			arLayersCopy[i].getPosition().add(SideScroller.WIDTH, 0);
		}
	}

	/**
	 * Updates each layer within this background and scroll them infinitely.
	 * 
	 * @param speed
	 *            The speed by which to scroll this background.
	 */
	public void update(float speed) {
		for (int i = 0; i < arLayers.length; i++) {
			ParallaxLayer parallaxLayer = arLayers[i];
			ParallaxLayer parallaxLayerCopy = arLayersCopy[i];

			parallaxLayer.update(speed);
			parallaxLayerCopy.update(speed);

			if (parallaxLayer.getPosition().x + SideScroller.WIDTH < 0) {
				parallaxLayer.getPosition().add(2 * SideScroller.WIDTH, 0);
			}

			if (parallaxLayerCopy.getPosition().x + SideScroller.WIDTH < 0) {
				parallaxLayerCopy.getPosition().add(2 * SideScroller.WIDTH, 0);
			}
		}
	}

	/**
	 * Draws each layer to the screen.
	 * 
	 * @param batch
	 *            The SpriteBatch instance.
	 */
	public void render(SpriteBatch batch) {
		for(int i = 0; i < arLayers.length; i++){
			ParallaxLayer parallaxLayer = arLayers[i];
			ParallaxLayer parallaxLayerCopy = arLayersCopy[i];
			
			batch.draw(parallaxLayer.getTexture(), parallaxLayer.getPosition().x, parallaxLayer.getPosition().y, SideScroller.WIDTH, SideScroller.HEIGHT);
			batch.draw(parallaxLayerCopy.getTexture(), parallaxLayerCopy.getPosition().x, parallaxLayerCopy.getPosition().y, SideScroller.WIDTH, SideScroller.HEIGHT);
		}
	}
}