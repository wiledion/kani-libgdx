package com.wiledion.kani.state;

import com.badlogic.gdx.assets.AssetManager;
import com.wiledion.kani.Kani;
import com.wiledion.kani.data.AnimManager;
import com.wiledion.kani.data.Loader;

/**
 * Loading stage (loading assets and data)
 * 
 * @author wiledion
 * 
 */

public class LoadStage extends State {

	AnimManager animManager = AnimManager.get();
	AssetManager assetManager;

	public LoadStage(Kani nMain) {

		super(nMain);
		Loader.start();
		assetManager = Loader.getManager();
	}

	public void render() {

		if (assetManager.update()) {
			animManager.load();
			// Next stage
			main.enterStage(Kani.INTRO);
		}

		// Progress of loading
		float progress = assetManager.getProgress();

	}

}
