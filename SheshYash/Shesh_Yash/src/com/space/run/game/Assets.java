package com.space.run.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/*
 * References: 
 * 
 * SpriteBatch and Textures: https://github.com/libgdx/libgdx/wiki/Spritebatch%2C-textureregions%2C-and-sprite
 * 
 * Orthographic Camera: https://github.com/libgdx/libgdx/wiki/Orthographic-camera
 * 
 * Animation: https://github.com/libgdx/libgdx/wiki/Sprite-animation
 * 
 * Player Movement: http://obviam.net/index.php/getting-started-in-android-game-development-with-libgdx-tutorial-part-3-jumping-gravity-and-movement/
 * 
 * Collision: http://obviam.net/index.php/getting-started-in-android-game-development-with-libgdx-tutorial-part-4-collision-detection/
 * 
 * File IO: https://github.com/libgdx/libgdx/wiki/File-handling
 * 
 * Input Handling: https://github.com/libgdx/libgdx/wiki/Event-handling
 * 
 * Menus(Buttons, Labels, etc): https://github.com/libgdx/libgdx/wiki/Scene2d
 * 			   				    https://github.com/libgdx/libgdx/wiki/Scene2d.ui
 * 								https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests/src/com/badlogic/gdx/tests/UITest.java
 * 
 * Music: https://code.google.com/p/libgdx/wiki/AudioMusic
 * 
 * Sounds: https://code.google.com/p/libgdx/wiki/AudioSound
 * 
 * Example Game: https://github.com/libgdx/libgdx/wiki/A-simple-game
 * 
 */

/**
 * Loads in all of the assets that will be used in the game.
 * 
 * A texture is an image that can be drawn to the screen.
 * 
 * A texture region is a sub image within a texture. It is more efficient to
 * have all of your images in a single texture and get each sub image because of
 * the way OpenGL handles textures.
 * 
 * A texture atlas is the same as a texture but it is easier to use if you use
 * the TexturePacker tool.
 * 
 * An animation is an array of texture regions that changes the image based on
 * time.
 * 
 */
public class Assets {

	private static final float PLAYER_FRAME_DURATION = 0.06f;
	private static final float COIN_FRAME_DURATION = 0.07f;

	public static Texture texSplash;
	public static Texture texSpritesheetControls;
	public static TextureRegion texRegAndroidControls;
	public static TextureRegion texRegDesktopControls;

	public static Texture texSpritesheetBackgrounds;
	public static TextureRegion texRegBackground1;
	public static TextureRegion texRegBackground2;

	public static Texture texSpritesheetObjects;

	public static TextureRegion texRegObstacle1;
	public static TextureRegion texRegObstacle2;

	public static TextureRegion texRegPlatform;
	public static TextureRegion texRegGround;

	public static TextureRegion texRegEnemy1;
	public static TextureRegion texRegBullet1;
	public static TextureRegion texRegBullet2;

	public static TextureRegion texRegHeart;
	public static TextureRegion texRegBulletPowerUpOn;
	public static TextureRegion texRegBulletPowerUpOff;
	public static TextureRegion texRegInvinciblePowerUp;

	public static Animation animPlayerRunning;
	public static TextureRegion texRegPlayerJump;
	public static TextureRegion texRegPlayerFall;

	public static Animation animPlayerRunningInvincible;
	public static TextureRegion texRegPlayerJumpInvincible;
	public static TextureRegion texRegPlayerFallInvincible;

	public static Animation animCoin;

	public static BitmapFont fontWhite;
	public static BitmapFont fontBlack;

	public static TextureAtlas texTxtButtons;
	public static TextureAtlas texImgButton;
	public static TextureAtlas texTxtInput;

	public static Skin skinUI;

	public static Music musicGameLoop;
	public static Sound soundJump;
	public static Sound soundShoot;

	/**
	 * Loads in all of the assets for the game.
	 */
	public static void load() {
		loadSounds();
		loadPlayerSprites();
		loadObjectSprites();
		loadGameBackgrounds();
		loadUIObjects();
	}

	public static void loadSounds() {
		/*
		  Music: https://code.google.com/p/libgdx/wiki/AudioMusic
		 */
		musicGameLoop = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
		musicGameLoop.setVolume(0.2f);
		musicGameLoop.setLooping(true);
		musicGameLoop.play();
		musicGameLoop.stop();

		/*
		 * Sounds: https://code.google.com/p/libgdx/wiki/AudioSound
		 */
		soundJump = Gdx.audio.newSound(Gdx.files.internal("sounds/jumpSound.mp3"));
		soundShoot = Gdx.audio.newSound(Gdx.files.internal("sounds/shootSound.mp3"));
	}

	public static void loadPlayerSprites() {
		texSpritesheetObjects = new Texture(Gdx.files.internal("textures/spritesheetObjects.png"));

		TextureRegion[] arTexRunning = new TextureRegion[3];
		arTexRunning[0] = new TextureRegion(texSpritesheetObjects, 628, 272, 34, 41);
		arTexRunning[1] = new TextureRegion(texSpritesheetObjects, 697, 272, 25, 44);
		arTexRunning[2] = new TextureRegion(texSpritesheetObjects, 755, 272, 35, 41);

		animPlayerRunning = new Animation(PLAYER_FRAME_DURATION, arTexRunning);

		texRegPlayerJump = new TextureRegion(texSpritesheetObjects, 628, 272, 34, 41);
		texRegPlayerFall = new TextureRegion(texSpritesheetObjects, 628, 272, 34, 41);

		TextureRegion[] arTexRunningInvincible = new TextureRegion[3];
		arTexRunningInvincible[0] = new TextureRegion(texSpritesheetObjects, 627, 319, 35, 40);
		arTexRunningInvincible[1] = new TextureRegion(texSpritesheetObjects, 697, 319, 26, 43);
		arTexRunningInvincible[2] = new TextureRegion(texSpritesheetObjects, 757, 319, 35, 40);

		animPlayerRunningInvincible = new Animation(PLAYER_FRAME_DURATION, arTexRunningInvincible);

		texRegPlayerJumpInvincible = new TextureRegion(texSpritesheetObjects, 627, 319, 35, 40);
		texRegPlayerFallInvincible = new TextureRegion(texSpritesheetObjects, 627, 319, 35, 40);
	}

	public static void loadObjectSprites() {
		texRegObstacle1 = new TextureRegion(texSpritesheetObjects, 22, 248, 68, 62);
		texRegObstacle2 = new TextureRegion(texSpritesheetObjects, 120, 178, 56, 128);

		texRegPlatform = new TextureRegion(texSpritesheetObjects, 236, 274, 156, 31);
		texRegGround = new TextureRegion(texSpritesheetObjects, 287, 392, 75, 75);

		texRegEnemy1 = new TextureRegion(texSpritesheetObjects, 134, 387, 57, 76);
		texRegBullet1 = new TextureRegion(texSpritesheetObjects, 292, 197, 47, 20);
		texRegBullet2 = new TextureRegion(texSpritesheetObjects, 434, 196, 47, 20);

		texRegHeart = new TextureRegion(texSpritesheetObjects, 16, 396, 80, 75);
		texRegBulletPowerUpOff = new TextureRegion(texSpritesheetObjects, 534, 185, 34, 39);
		texRegBulletPowerUpOn = new TextureRegion(texSpritesheetObjects, 579, 187, 34, 35);
		texRegInvinciblePowerUp = new TextureRegion(texSpritesheetObjects, 447, 253, 80, 84);

		TextureRegion[] arTexCoins = new TextureRegion[10];
		arTexCoins[0] = new TextureRegion(texSpritesheetObjects, 23, 28, 83, 91);
		arTexCoins[1] = new TextureRegion(texSpritesheetObjects, 129, 28, 67, 92);
		arTexCoins[2] = new TextureRegion(texSpritesheetObjects, 240, 28, 47, 91);
		arTexCoins[3] = new TextureRegion(texSpritesheetObjects, 350, 28, 28, 91);
		arTexCoins[4] = new TextureRegion(texSpritesheetObjects, 457, 29, 15, 90);
		arTexCoins[5] = new TextureRegion(texSpritesheetObjects, 551, 29, 27, 90);
		arTexCoins[6] = new TextureRegion(texSpritesheetObjects, 641, 29, 47, 90);
		arTexCoins[7] = new TextureRegion(texSpritesheetObjects, 731, 28, 67, 91);
		arTexCoins[8] = new TextureRegion(texSpritesheetObjects, 822, 29, 84, 90);
		arTexCoins[9] = new TextureRegion(texSpritesheetObjects, 918, 29, 92, 91);

		animCoin = new Animation(COIN_FRAME_DURATION, arTexCoins);
	}

	public static void loadGameBackgrounds() {
		texSpritesheetBackgrounds = new Texture(Gdx.files.internal("textures/spritesheetBackgrounds.png"));

		texRegBackground1 = new TextureRegion(texSpritesheetBackgrounds, 8, 176, 797, 480);
		texRegBackground2 = new TextureRegion(texSpritesheetBackgrounds, 5, 495, 3468, 1098);
	}

	public static void loadUIObjects() {
		texSplash = new Texture(Gdx.files.internal("textures/pie.jpg"));
		texSpritesheetControls = new Texture(Gdx.files.internal("textures/spritesheetControls.png"));
		texRegAndroidControls = new TextureRegion(texSpritesheetControls, 5, 5, 569, 416);
		texRegDesktopControls = new TextureRegion(texSpritesheetControls, 584, 5, 569, 416);

		fontWhite = new BitmapFont(Gdx.files.internal("data/fonts/font_white.fnt"), false);
		fontBlack = new BitmapFont(Gdx.files.internal("data/fonts/font_black.fnt"), false);

		/*
		 * Skin gotten from:
		 * https://github.com/libgdx/libgdx/tree/master/tests/gdx
		 * -tests-android/assets/data
		 */
		skinUI = new Skin(Gdx.files.internal("data/skins/uiskin.json"));
	}
}
