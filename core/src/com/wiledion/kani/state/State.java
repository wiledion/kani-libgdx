package com.wiledion.kani.state;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wiledion.kani.Kani;
import com.wiledion.kani.data.Loader;
import com.wiledion.kani.data.MapManager;
import com.wiledion.kani.utils.KInputProcessor;
import com.wiledion.kani.utils.MessageBox;
import com.wiledion.kani.utils.Timer;
import com.wiledion.kani.world.Hero;
import com.wiledion.kani.world.Enemy;

/**
 * Parent class of the different stages of the application.
 * 
 * Is implemented for the loading step, the introduction, the menu and the game
 * 
 * @author wiledion
 * 
 */

public class State {

	Kani main;

	Hero player;
	boolean running;
	static boolean paused;
	boolean victory;
	MapManager map = MapManager.get();
	AssetManager data = Loader.getManager();
	KInputProcessor kInput;
	OrthographicCamera cam;
	OrthographicCamera hudCam;
	SpriteBatch batch;
	SpriteBatch hudBatch;
	ShapeRenderer shapeRenderer;
	Stage stage;
	public ArrayList<Enemy> enemies;
	Timer clockPause;
	MessageBox msg;

	public State(Kani nMain) {
		main = nMain;
	}

	public void create() {

		cam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		hudCam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		stage = new Stage();

	}

	public void update() {
	}

	public void render() {
	}

	public void next() {
	}

	public void dispose() {
	}

	boolean keyDown(int keycode) {

		return paused;

	}

}
