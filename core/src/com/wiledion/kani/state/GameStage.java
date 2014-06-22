package com.wiledion.kani.state;

import java.util.ArrayList;

import com.wiledion.kani.data.Loader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wiledion.kani.utils.Combos;
import com.wiledion.kani.utils.KInputProcessor;
import com.wiledion.kani.utils.MessageBox;
import com.wiledion.kani.utils.Parser;
import com.wiledion.kani.utils.Timer;
import com.wiledion.kani.world.Arrow;
import com.wiledion.kani.world.Bird;
import com.wiledion.kani.world.Hero;
import com.wiledion.kani.world.Enemy;
import com.wiledion.kani.world.Perso;
import com.wiledion.kani.world.Statue;
import com.wiledion.kani.Kani;

/**
 * The gaming stage
 * 
 * @author wiledion
 * 
 */

public class GameStage extends State {

	boolean intro;
	boolean end;
	boolean isDead;
	boolean loopstarted = false;
	Timer loopclock;
	Timer comboclock;
	float looptime;
	String introMsg;
	String endMsg;
	String deadMsg;
	String levelName;
	int nbLevels;
	int nbLayer;
	int numberEnemies;
	int dead;
	Parser parser;
	BitmapFont font1;
	BitmapFont font2;
	Rectangle glViewport;
	Combos kcombo;
	int idLevel = 1;
	ArrayList<Integer[]> enemiesConfig;
	double velCam;
	Sprite panel;
	Sprite minilogo;

	public GameStage(Kani nMain) {

		super(nMain);
		create();
		cam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight() - 82);
		cam.position.set(Gdx.graphics.getWidth() / 2,
				(Gdx.graphics.getHeight()) / 2, 0);
		kInput = new KInputProcessor();
		Gdx.input.setInputProcessor(kInput);
		nbLevels = 1;
	}

	public void create() {
		startLevel("level1");
		panel = Loader.getSprite("menupatt");
		// panel.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		minilogo = new Sprite(Loader.getSprite("minilogo"));
		minilogo.setPosition(700, 10);
		velCam = 0.06f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kani.state.State#render()
	 */
	public void render() {

		GL20 gl = Gdx.graphics.getGL20();
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gl.glViewport(0, 80, 800, 520);

		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();

		for (int layer = 0; layer < 4; layer++) {

			if (layer == 2) {
				player.draw(batch, looptime);
			}

			for (int i = 0; i < enemies.size(); i++) {
				Enemy kmonst = (Enemy) enemies.get(i);
				if (kmonst.getLayer() == layer) {
					kmonst.draw(batch, looptime);
				}
			}

			if (layer < nbLayer) {
				map.current.renderLayer(layer, batch);
			}
		}

		batch.end();
		gl.glViewport(0, 0, 800, 600);

		hudCam.update();

		hudBatch.setProjectionMatrix(hudCam.combined);
		hudBatch.begin();
		for (int i = 0; i * panel.getWidth() < Gdx.graphics.getWidth(); i++) {

			hudBatch.draw(panel, i * panel.getWidth(), 0);

		}

		minilogo.draw(hudBatch);
		font1.draw(hudBatch, "Restants :" + enemies.size(), 32, 24);
		player.drawInf(shapeRenderer, hudBatch, font1);

		msg.draw(hudBatch, shapeRenderer);
		if (paused) {
			font1.draw(hudBatch, "PAUSE", 350, 180);
		}

		if (victory) {
			font1.draw(hudBatch, endMsg, 350, 180);
		}
		hudBatch.end();

		update();
	}

	public void startLevel(String levelName) {

		cam = new OrthographicCamera(800, Gdx.graphics.getHeight());
		cam.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);
		cam.update();
		hudCam = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		hudCam.update();

		glViewport = new Rectangle(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		parser = new Parser();
		parser.interp(levelName, this);
		loopclock = new Timer();
		comboclock = new Timer();

		levelReset();
		intro = true;
		end = false;
		isDead = false;

		font1 = new BitmapFont();
		font2 = new BitmapFont(Gdx.files.internal("data/demo.fnt"),
				Gdx.files.internal("data/demo_00.tga"), false);

		msg = new MessageBox(150, 10, 540, 60);
		msg.addText(new StringBuffer(introMsg));
		msg.start();

		kcombo = new Combos();

	}

	private void levelReset() {

		player = new Hero();
		running = true;
		paused = false;
		victory = false;
		enemies = new ArrayList<Enemy>();
		initEnemies();
		clockPause = new Timer();
		clockPause.reset();
		cam.position.set(800 / 2, 600 / 2, 0);
		hudCam.position.set(800 / 2, 600 / 2, 0);
		velCam = 0.05f;
		player.setMap(map.current);
		nbLayer = map.current.getNbLayer();
		loopclock.pause();
	}

	public void update() {

		if (loopstarted == false) {
			loopclock.reset();
			loopstarted = true;
		}

		if (!paused) {
			if (intro) {
				msg.update();
				if (msg.isWaiting() && Gdx.input.isKeyPressed(Keys.SPACE)) {
					msg.setWaitingFalse();
				}
				intro = !(msg.isEnded() && Gdx.input.isKeyPressed(Keys.SPACE));
				if (intro == false) {
					msg.clean();
					loopclock.play();
				}
			} else if (end) {
				msg.update();
				if (msg.isWaiting() && Gdx.input.isKeyPressed(Keys.SPACE)) {
					msg.setWaitingFalse();
				}
				if (msg.isEnded() && Gdx.input.isKeyPressed(Keys.SPACE)) {
					idLevel++;
					if (idLevel < nbLevels) { // Put the number of levels
						startLevel("level" + String.valueOf(idLevel));
					} else {
						idLevel = 1;
						startLevel("level" + String.valueOf(idLevel));
						main.enterStage(2);
					}
				}

			} else if (isDead) {
				if (Math.hypot(player.getmidY() - cam.position.x,
						player.getmidX() - cam.position.y) > velCam * looptime) {
					float dcamx, dcamy;
					double angleTranslation = Math.atan2(player.getY()
							- cam.position.y, player.getX() - cam.position.x);
					dcamx = (float) (velCam * Math.cos(angleTranslation));
					dcamy = (float) (velCam * Math.sin(angleTranslation));
					cam.translate(dcamx, dcamy);
				}

				cam.rotate(looptime * 0.005f);
				cam.zoom = cam.zoom
						- Math.min(0.001f, (cam.zoom - 0.5f) * 0.001f);
				player.setStand();
				player.updateArrows(looptime);
				msg.update();
				monstersUpdate();
				if (msg.isWaiting() && Gdx.input.isKeyPressed(Keys.SPACE)) {
					msg.setWaitingFalse();
				}
				if (msg.isEnded() && Gdx.input.isKeyPressed(Keys.SPACE)) {
					idLevel = 1;
					startLevel("level" + String.valueOf(idLevel));
					main.enterStage(2);
				}

			} else {

				looptime = loopclock.getElapsedTime();
				loopclock.reset();

				if (!Gdx.input.isKeyPressed(Keys.UP)
						&& !Gdx.input.isKeyPressed(Keys.DOWN)
						&& !Gdx.input.isKeyPressed(Keys.LEFT)
						&& !Gdx.input.isKeyPressed(Keys.RIGHT)) {
					player.updateDefault(looptime);

				} else {
					int vx = 0;
					int vy = 0;

					if (kInput.isKeyDown(Keys.UP)) {
						vy++;
					}
					if (kInput.isKeyDown(Keys.DOWN)) {
						vy--;
					}
					if (kInput.isKeyDown(Keys.LEFT)) {
						vx--;
					}
					if (kInput.isKeyDown(Keys.RIGHT)) {
						vx++;
					}

					// Combos verifications
					if (kInput.isKeyPressed(Keys.UP)) {
						kcombo.add(Keys.UP, comboclock);
					}
					if (kInput.isKeyPressed(Keys.DOWN)) {
						kcombo.add(Keys.DOWN, comboclock);
					}
					if (kInput.isKeyPressed(Keys.LEFT)) {
						kcombo.add(Keys.LEFT, comboclock);
					}
					if (kInput.isKeyPressed(Keys.RIGHT)) {
						kcombo.add(Keys.RIGHT, comboclock);
					}
					player.setDir(vx, vy);
					player.updateMove(cam, looptime);
				}

				if (kInput.isKeyPressed(Keys.Q)) {
					kcombo.add(Keys.Q, comboclock);
				}

				// Pause
				if (Gdx.input.isKeyPressed(Keys.P)) {

					if (clockPause.getElapsedTime() > 1000) {
						paused = true;
						clockPause.reset();
						loopclock.pause();
						player.pause();
						for (int i = 0; i < enemies.size(); i++) {
							enemies.get(i).pause();
						}

					}
				}

				heroCollUpdate();
				monstersUpdate();

				kcombo.verify(player);

				if (player.getPv() <= 0) {
					isDead = true;
					msg.clean();
					msg.addText(new StringBuffer(deadMsg));
				}

				if (enemies.isEmpty()) {
					end = true;
					player.setStand();
					msg.clean();
					msg.addText(new StringBuffer(endMsg));
					player.updateDefault(looptime);
				}
			}
		} else if (Gdx.input.isKeyPressed(Keys.P)) {
			if (clockPause.getElapsedTime() > 1000) {
				paused = false;
				loopclock.play();
				clockPause.reset();
				player.unpause();
				for (int i = 0; i < enemies.size(); i++) {
					enemies.get(i).unpause();
				}
			}
		}
		kInput.updateOld();
	}

	void initEnemies() {
		for (int i = 0; i < enemiesConfig.size(); i++) {
			switch (enemiesConfig.get(i)[0]) {

			case 1:
				for (int j = 0; j < enemiesConfig.get(i)[1]; j++) {
					enemies.add(new Statue());
					enemies.get(j).setMap(map.current);
				}
				break;

			case 2:
				for (int j = 0; j < enemiesConfig.get(i)[1]; j++) {
					enemies.add(new Bird());
					enemies.get(j).setMap(map.current);
				}
				break;
			}
		}
	}

	private void heroCollUpdate() {
		Polygon heropoly = player.getPolygon();
		for (int i = 0; i < enemies.size(); i++) {
			Perso kmonst = enemies.get(i);
			if (Intersector
					.overlapConvexPolygons(heropoly, kmonst.getPolygon())) {
				player.receiveDamage(kmonst.getAttack());
				break;
			}
		}
	}

	private void monstersUpdate() {

		for (int i = 0; i < enemies.size(); i++) {
			Enemy kmonst = (Enemy) enemies.get(i);
			kmonst.updateDefault(looptime);
			float hx = player.getX();
			float hy = player.getY();
			if ((Math.abs(kmonst.getX() - hx) < 200)
					&& (Math.abs(kmonst.getY() - hy) < 200)
					&& !kmonst.isCibled()) {
				kmonst.setCibled(hx, hy);
			}
		}

		if (enemies.size() != 0) {
			ArrayList<Arrow> allArrows = player.getAllArrows();
			for (int i = 0; i < enemies.size() && enemies.size() != 0; i++) {
				for (int j = 0; j < allArrows.size() && enemies.size() != 0; j++) {
					Enemy kmonst = (Enemy) enemies.get(i);
					Arrow karrow = allArrows.get(j);
					Polygon enemiesPolygon = kmonst.getPolygon();
					Polygon arrowsPolygon = karrow.getPoly();

					if (Intersector.overlapConvexPolygons(enemiesPolygon,
							arrowsPolygon)) {
						kmonst.receiveDamage(karrow.getHit());
						if (kmonst.isDead()) {
							enemies.remove(i);
						}
						player.removeArrow(j);
						if (i > 0) {
							i--;
						}
						if (j > 0) {
							j--;
						}
					}
				}
			}
		}
	}

	private boolean isDead() {
		return isDead;
	}

	public void setIntroMsg(String msg) {
		introMsg = msg;
	}

	public void setEndMsg(String msg) {
		endMsg = msg;
	}

	public void setDeadMsg(String msg) {
		deadMsg = msg;
	}

	public void setEnemies(int numbEnemies) {
		numberEnemies = numbEnemies;
	}

	public void setEnemiesCfg(ArrayList<Integer[]> allCfg, int size) {
		enemiesConfig = allCfg;
	}

	public static boolean isPaused() {

		return paused;
	}

}
