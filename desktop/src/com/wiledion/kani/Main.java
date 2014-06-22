package com.wiledion.kani;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.Files;

/**
 * Main class for desktop application
 * 
 * @author wiledion
 * 
 */

public class Main {

	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Kani";
		cfg.width = 800;
		cfg.height = 600;
		cfg.addIcon("arc.png", Files.FileType.Internal);
		new LwjglApplication(new Kani(), cfg);
	}
}
