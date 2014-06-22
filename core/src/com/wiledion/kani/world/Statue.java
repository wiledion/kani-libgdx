package com.wiledion.kani.world;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.wiledion.kani.data.AnimManager;
import com.wiledion.kani.data.Loader;
import com.wiledion.kani.data.MapManager;
import com.wiledion.kani.state.GameStage;
import com.wiledion.kani.utils.Block;
import com.wiledion.kani.utils.KaniMap;
import com.wiledion.kani.utils.Timer;

/**
 * Statue class
 * 
 * @author wiledion
 * 
 */

public class Statue extends Enemy {

	AnimManager animMng = AnimManager.get();
	MapManager map = MapManager.get();
	Loader loader = Loader.getLoader();
	Sprite sprite;
	Polygon poly;
	Timer clock;
	Timer clockColl;
	Timer clockCible;
	float desX;
	float desY;
	float vel;
	float velX;
	float velY;
	float dir;
	float statetime;
	Random r;
	boolean ifWait;
	boolean cibled;
	TextureRegion currentFrame;

	public Statue() {
		super();
		r = new Random();
		vel = (float) (2. / 30);
		pv = 20;
		attack = 9;
		layer = 2;

		sprite = loader.getSprite("statue");

		poly = new Polygon(new float[] { posX, posY, posX + width, posY,
				posX + width, posY + height, posX, posY + height });

		anim = animMng.statue_stand_down;
		height = anim.getKeyFrame(0).getRegionHeight();
		width = anim.getKeyFrame(0).getRegionWidth();
		randomPos();

		clock = new Timer();
		clockColl = new Timer();
		clockCible = new Timer();
		clockCible.reset();
		ifWait = false;
		cibled = false;
		randomDest();

		midX = width / 2;
		midY = height / 2;

		setMove();

		updateDim();
		statetime = 0f;
	}

	public void draw(SpriteBatch batch, float looptime) {

		if (!GameStage.isPaused())
			statetime += looptime / 1000f;

		currentFrame = anim.getKeyFrame(statetime, true);
		batch.draw(currentFrame, posX, posY);
	}

	public void updateDim() {
		height = anim.getKeyFrame(0).getRegionHeight();
		width = anim.getKeyFrame(0).getRegionWidth();
	}

	public void updateDefault(float looptime) {

		float ox = posX;
		float oy = posY;

		velX = (float) (vel * Math.cos(dir));
		velY = (float) (vel * Math.sin(dir));

		dx = velX * looptime;
		dy = velY * looptime;

		if (ifWait == true) {
			if (clock.getElapsedTime() > 1000) {
				ifWait = false;
				randomDest();
			}
		} else {
			posX += velX * looptime;
			posY += velY * looptime;
			updatePolygon();

			if (isColl() || distToGo() <= vel * looptime) {
				posX = ox;
				posY = oy;
				randomDest();
				updatePolygon();
				if (clockColl.getElapsedTime() > 1000) {
					ifWait = false;
				}

				if (isColl()) {
					clockColl.reset();
				}
				clock.reset();
			}

			else {
				setMove();
			}

		}

		if (cibled && clockCible.getElapsedTime() > 3000) {
			cibled = false;
			vel = (float) (2. / 30);
		}

	}

	public void receiveDamage(float damage) {
		pv -= damage;
		if (pv <= 0) {
			isDead = true;
		}
	}

	public boolean isDead() {

		return isDead;
	}

	void randomDest() {
		boolean founded = false;
		while (!founded) {
			desX = posX;
			desY = posY;
			int k = r.nextInt(2);

			if (k == 1) {
				desX = r.nextInt(map.current.getMapWidth());
			} else {
				desY = r.nextInt(map.current.getMapHeight());
			}
			if (!isColl(desX, desY)) {
				founded = true;
			}
		}

		dir = MathUtils.atan2(desY - posY, desX - posX);
		clock.reset();
		setMove();
	}

	void randomPos() {

		boolean founded = false;
		while (!founded) {
			posX = r.nextInt(map.current.getMapWidth());
			posY = r.nextInt(map.current.getMapHeight());
			updatePolygon();
			if (!isColl()) {
				founded = true;
			}
		}
	}

	public boolean isBlocked() {
		return map.current.isStop(midX, midY);
	}

	public boolean isCibled() {
		return cibled;
	}

	public void updatePolygon() {

		updateDim();
		poly = new Polygon(new float[] { posX, posY, posX + width, posY,
				posX + width, posY + height, posX, posY + height });
	}

	public void drawPolygon(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		batch.end();
		KaniMap tmap = map.current;
		for (int i = 0; i < tmap.blocked.size(); i++) {
			Block entity = (Block) tmap.blocked.get(i);
			entity.draw(shapeRenderer);
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.GREEN);
			shapeRenderer.polygon(poly.getVertices());
			shapeRenderer.end();
			batch.begin();
		}
	}

	public Polygon getPolygon() {
		return poly;
	}

	public boolean isColl() {
		updateDim();
		KaniMap tmap = map.current;
		for (int i = 0; i < tmap.blocked.size(); i++) {
			Block entity = (Block) tmap.blocked.get(i);
			if (Intersector.overlapConvexPolygons(poly, entity.getPoly())) {
				return true;
			}
		}
		return false;
	}

	public boolean isColl(float tx, float ty) {
		return map.current.isBlocked(tx, ty);
	}

	public float getdX() {
		return desX;
	}

	public float getdY() {
		return desY;
	}

	public void setCibled(float hx, float hy) {
		desX = hx;
		desY = hy;
		updateDir();
		ifWait = false;
		clockCible.reset();
		cibled = true;
		vel = (float) (3.5 / 30);
		setMove();
	}

	public double distToGo() {
		return Math.hypot(desX - posX, desY - posY);
	}

	public void updateDir() {
		dir = MathUtils.atan2(desY - posY, desX - posX);
	}

	@Override
	public void init() {

	}

	public void setMove() {

		if (dir > Hero.DIR_DOWNL && dir <= Hero.DIR_DOWNR) {
			anim = animMng.statue_move_down;
		}

		else if (dir > Hero.DIR_DOWNR && dir <= Hero.DIR_UPR) {
			anim = animMng.statue_move_right;
		}

		else if (dir > Hero.DIR_UPR && dir <= Hero.DIR_UPL) {
			anim = animMng.statue_move_up;
		}

		else if (dir > Hero.DIR_UPL || dir <= Hero.DIR_DOWNL) {
			anim = animMng.statue_move_left;
		}

	}

	public void setStand() {

		if (dir > Hero.DIR_DOWNL && dir <= Hero.DIR_DOWNR) {
			anim = animMng.statue_stand_down;
		}

		if (dir > Hero.DIR_DOWNL && dir <= Hero.DIR_UPR) {
			anim = animMng.statue_stand_right;
		}

		if (dir > Hero.DIR_UPR && dir <= Hero.DIR_UPL) {
			anim = animMng.statue_stand_up;
		}

		if (dir > Hero.DIR_UPL || dir <= Hero.DIR_DOWNL) {
			anim = animMng.statue_stand_down;
		}
	}

	public void pause() {
		clock.pause();
		clockColl.pause();
		clockCible.pause();
	}

	public void unpause() {
		clock.play();
		clockColl.play();
		clockCible.play();
	}

}
