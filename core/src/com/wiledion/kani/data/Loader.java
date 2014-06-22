package com.wiledion.kani.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Resources loader
 * 
 * @author wiledion
 * 
 */

public class Loader {

	private static AssetManager manager = new AssetManager();
	private static Loader loader;

	public static TextureAtlas atlas;

	public static void start() {
		atlas = new TextureAtlas(Gdx.files.internal("pack/kani.atlas"));
		manager.load("data/maps/kanitiles1.png", Texture.class);
		manager.load("data/maps/kanitiles2.png", Texture.class);
		manager.load("data/maps/kanitiles3.png", Texture.class);
		manager.load("data/maps/kanitiles4.png", Texture.class);
		manager.load("pack/kani.atlas", TextureAtlas.class);
	}

	public static AssetManager getManager() {
		return manager;
	}

	public static Loader getLoader() {
		return loader;
	}

	public static Sprite getSprite(String strSprite) {
		return atlas.createSprite(strSprite);
	}

	public static TextureAtlas getTextureAtlas(String strText) {
		return atlas;
	}

	public static Sprite getSprite(String strSprite, int index) {
		return atlas.createSprite(strSprite, index);
	}

}
