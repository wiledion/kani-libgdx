package com.wiledion.kani.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wiledion.kani.Kani;
import com.wiledion.kani.data.Loader;

/**
 * Introduction stage (splash-screens)
 * 
 * @author wiledion
 * 
 */

public class IntroStage extends State {

	boolean fx = true;
	int i = 0;
	int img = 0;

	Sprite splashWil, splashGdx, splashJava, splash;

	public IntroStage(Kani nMain) {
		super(nMain);
		create();
	}

	public void create() {

		cam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		cam.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);

		splashWil = new Sprite(Loader.getSprite("wiledion"));
		splashGdx = new Sprite(Loader.getSprite("libgdx"));
		splashJava = new Sprite(Loader.getSprite("java"));
		splashJava.setPosition((800 - splashJava.getWidth()) / 2,
				(600 - splashJava.getHeight()) / 2);
		splashWil.setPosition((800 - splashWil.getWidth()) / 2,
				(600 - splashWil.getHeight()) / 2);
		splashGdx.setPosition((800 - splashGdx.getWidth()) / 2,
				(600 - splashGdx.getHeight()) / 2);
		batch = new SpriteBatch();

		splash = splashJava;

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

		splash.draw(batch, (float) i / 360);

		batch.end();
		update();
	}

	public void update() {

		// Affichage splash
		if (fx) {
			if (i < 360)
				i += 8;
			else
				fx = false;

		}

		else {
			i -= 8;
			if (i == 0) {
				fx = true;
				img++;
			}
		}

		switch (img) {
		case 0:
			splash = splashJava;
			break;
		case 1:
			splash = splashGdx;
			break;
		case 2:
			splash = splashWil;
			break;
		case 3:
			main.enterStage(Kani.MENU);
			break;

		}

	}

}
