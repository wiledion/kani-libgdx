package com.wiledion.kani.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.wiledion.kani.data.Loader;

/**
 * Elementary tile of a map
 * 
 * @author wiledion
 * 
 */

public class Tile {

	TextureAtlas tilesheet;
	AssetManager manager;

	public void create() {

		manager = Loader.getManager();
		tilesheet = manager.get("kanitiles1", TextureAtlas.class);
	}

}
