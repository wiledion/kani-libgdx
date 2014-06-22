package com.wiledion.kani.data;

import com.badlogic.gdx.assets.AssetManager;
import com.wiledion.kani.utils.KaniMap;

/**
 * Manager of maps
 * 
 * @author wiledion
 * 
 */

public class MapManager {

	AssetManager manager = Loader.getManager();
	private static MapManager mapmanager = new MapManager();

	public KaniMap current;

	private MapManager() {
		super();
	}

	public static MapManager get() {
		return mapmanager;
	}

	public void load() {

	}

	public class view {

	}

	public void setMap(String path_map) {
		current = new KaniMap("map");
	}

}
