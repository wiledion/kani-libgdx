package com.wiledion.kani.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wiledion.kani.Kani;
import com.wiledion.kani.data.Loader;
import com.wiledion.kani.utils.KInputProcessor;

/**
 * Stage of the main menu
 * 
 * @author wiledion
 * 
 */

public class MenuStage extends State {

	public enum Modemenu {
		MENUG, JEUSLCT, QUITSLCT
	};

	public Modemenu modeMenu = null;

	Sprite screenMenu, logo;
	Sprite startButton, startButtonHover;
	Sprite quitButton, quitButtonHover;

	public MenuStage(Kani nMain) {

		super(nMain);
		create();
	}

	public void create() {

		cam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		cam.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);
		kInput = new KInputProcessor();
		Gdx.input.setInputProcessor(kInput);
		modeMenu = Modemenu.MENUG;

		screenMenu = new Sprite(Loader.getSprite("menu"));
		startButton = new Sprite(Loader.getSprite("bttplay1"));
		startButtonHover = new Sprite(Loader.getSprite("bttplay2"));
		quitButton = new Sprite(Loader.getSprite("bttquit1"));
		quitButtonHover = new Sprite(Loader.getSprite("bttquit2"));
		logo = new Sprite(Loader.getSprite("minilogo"));
		screenMenu.setPosition(0, 0);
		startButton.setPosition(100, 40);
		startButtonHover.setPosition(100, 40);
		quitButton.setPosition(356, 40);
		quitButtonHover.setPosition(356, 40);
		logo.setPosition(720, 50);

		batch = new SpriteBatch();

	}

	public void render() {

		GL20 gl = Gdx.graphics.getGL20();

		// Camera --------------------- /
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gl.glViewport(0, 0, 800, 600);

		// update our camera
		cam.update();

		batch.setProjectionMatrix(cam.combined);

		batch.begin();

		screenMenu.draw(batch);

		switch (modeMenu) {
		case MENUG:
			startButton.draw(batch);
			quitButton.draw(batch);
			break;

		case JEUSLCT:
			startButtonHover.draw(batch);
			quitButton.draw(batch);
			break;

		case QUITSLCT:
			startButton.draw(batch);
			quitButtonHover.draw(batch);
			break;
		}

		logo.draw(batch);

		batch.end();

		update();

	}

	public void update() {

		modeMenu = Modemenu.MENUG;
		// Vérifications boutton Jeu
		if ((kInput.getX() > 100) && kInput.getX() < 231 && kInput.getY() < 172
				&& kInput.getY() > 40) {
			modeMenu = Modemenu.JEUSLCT;
			if (Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT)) {
				main.enterStage(Kani.GAME);
			}
		}

		// Vérifications boutton Quitter
		if (kInput.getX() > 356 && kInput.getX() < 487 && kInput.getY() < 172
				&& kInput.getY() > 40) {
			modeMenu = Modemenu.QUITSLCT;
			if (Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT)) {
				main.exit();
			}
		}

	}

}
