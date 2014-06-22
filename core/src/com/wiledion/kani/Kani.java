package com.wiledion.kani;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.wiledion.kani.state.GameStage;
import com.wiledion.kani.state.IntroStage;
import com.wiledion.kani.state.LoadStage;
import com.wiledion.kani.state.MenuStage;
import com.wiledion.kani.state.State;

/**
 * Main ApplicationListener, and constants defined
 * 
 * @author wiledion
 * 
 */

public class Kani implements ApplicationListener {

	public static final int LOADING = 0;
	public static final int INTRO = 1;
	public static final int MENU = 2;
	public static final int GAME = 3;

	public static final int DIR_UP = 0;
	public static final int DIR_DOWN = 1;
	public static final int DIR_LEFT = 2;
	public static final int DIR_RIGHT = 3;

	State stage;

	public void create() {
		stage = new LoadStage(this);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		stage.render();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {

	}

	public void enterStage(int idStage) {
		stage.dispose();

		switch (idStage) {
		case LOADING:
			stage = new LoadStage(this);
			break;

		case INTRO:
			stage = new IntroStage(this);
			break;

		case MENU:
			stage = new MenuStage(this);
			break;

		case GAME:
			stage = new GameStage(this);
			break;

		}

	}

	public void exit() {

		Gdx.app.exit();

	}

}
