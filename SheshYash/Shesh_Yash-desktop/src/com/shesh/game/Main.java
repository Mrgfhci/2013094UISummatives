package com.shesh.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.space.run.game.SideScroller;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Space Run!";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 480;
		cfg.resizable = true;
		
		new LwjglApplication(new SideScroller(), cfg);
	}
}
