package com.wiledion.kani.world;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
 * Bird class
 * 
 * @author wiledion
 * 
 */

public class Bird extends Enemy {

	class Dest {

		public float x, y;
		int nb;

		Dest(float nx, float ny) {
			x = nx;
			y = ny;
		}
	}

	AnimManager animmng = AnimManager.get();
	Loader loader = Loader.getLoader();
	Sprite sprite;
	MapManager map = MapManager.get();
	ArrayList<Dest> allDest;
	float endX, endY;
	Polygon poly;
	Timer clockCible;
	float statetime;
	TextureRegion currentFrame;
	float desX;
	float desY;
	float vel;
	float velX;
	float velY;
	float dir;
	Random r;
	float p0[];
	float p1[];
	float p2[];
	float p3[];
	boolean cibled;

	@Override
	public void init() {
	}

	public Bird() {
		super();
		r = new Random();
		vel = (float) (6. / 30);
		pv = 30;
		randomDir();
		layer = 3;
		attack = 7;
		anim = animmng.bird;
		allDest = new ArrayList<Dest>();
		poly = new Polygon(new float[] { posX, posY, posX + width, posY,
				posX + width, posY + height, posX, posY + height });
		p0 = new float[2];
		p1 = new float[2];
		p2 = new float[2];
		p3 = new float[2];

		randomPos();

		clockCible = new Timer();
		clockCible.reset();
		cibled = false;
		randomDes();

		height = anim.getKeyFrame(0).getRegionHeight();
		width = anim.getKeyFrame(0).getRegionWidth();

		midX = width / 2;
		midY = height / 2;
		statetime = 0f;

	}

	public void draw(SpriteBatch batch, float looptime) {

		if (!GameStage.isPaused())
			statetime += looptime / 1000f;

		currentFrame = anim.getKeyFrame(statetime, true);
		Sprite tmpSprite = new Sprite(currentFrame);
		tmpSprite.setRotation(dir * MathUtils.radDeg + 90);
		tmpSprite.setPosition(posX, posY);
		tmpSprite.draw(batch);
	}

	public void updateDefault(float looptime) {

		float parcours = vel * looptime;
		float distStep = (float) Math.hypot(desX - posX, desY - posY);

		while (distStep < parcours) {
			if (allDest.size() == 0) {
				randomDes();
				if (cibled == true) {
					cibled = false;
				}
			}
			posX = desX;
			posY = desY;
			Dest d = allDest.get(0);
			desX = d.x;
			desY = d.y;
			allDest.remove(0);
			parcours -= distStep;
			distStep = (float) Math.hypot(desX - posX, desY - posY);
			updateDir();

		}

		if (allDest.size() == 0) {
			randomDes();
		}

		posX += parcours * Math.cos(dir);
		posY += parcours * Math.sin(dir);
		updatePolygon();
		updateDir();

	}

	public void receiveDamage(float damage) {
		pv -= damage;
		if (pv <= 0) {
			isDead = true;
		} else if (cibled) {
			float t = r.nextInt(5);
			if (t != 1) {
				randomDes();
				cibled = false;
			}
		}
	}

	public boolean isDead() {
		return isDead;
	}

	void randomDir() {

		dir = MathUtils.PI - r.nextFloat() * 2 * MathUtils.PI;

	}

	void randomDes() {

		float nx, ny;

		nx = r.nextInt(map.current.getMapWidth());
		ny = r.nextInt(map.current.getMapHeight());

		double a = 2 * Math.hypot(nx - posX, ny - posY) / 2;
		float dirEnd = MathUtils.atan2(ny - posY, nx - posX);

		p0[0] = posX;
		p0[1] = posY;
		p1[0] = (float) (posX + a * MathUtils.cos(dir));
		p1[1] = (float) (posY + a * MathUtils.sin(dir));
		float sdir = -MathUtils.PI / 2 - dirEnd - dir;
		p2[0] = (float) (nx + a * MathUtils.cos(sdir));
		p2[1] = (float) (ny + a * MathUtils.sin(sdir));
		p3[0] = nx;
		p3[1] = ny;

		endX = nx;
		endY = ny;

		pathUpdate();
		updateDir();
	}

	void setDest(float nx, float ny) {

		double a = Math.hypot(nx - posX, ny - posY) / 2;
		float dirEnd = MathUtils.atan2(ny - posY, nx - posX);
		p0[0] = posX;
		p0[1] = posY;
		p1[0] = (float) (posX + a * MathUtils.cos(dir));
		p1[1] = (float) (posY + a * MathUtils.sin(dir));
		float sdir = -MathUtils.PI / 2 - dirEnd - dir;
		p2[0] = (float) (nx + a * MathUtils.cos(sdir));
		p2[1] = (float) (ny + a * MathUtils.sin(sdir));
		p3[0] = nx;
		p3[1] = ny;
		endX = nx;
		endY = ny;

		pathUpdate();
		updateDir();
	}

	void randomPos() {

		posX = r.nextInt(map.current.getMapWidth());
		posY = r.nextInt(map.current.getMapHeight());
		updatePolygon();

	}

	double bezierLength() {
		double t;
		int i;
		int steps = 10;
		float[] dot = { 0, 0 };
		float[] previousDot = { 0, 0 };
		double length = 0.0;
		for (i = 0; i <= steps; i++) {
			t = (double) i / (double) steps;
			dot[0] = (float) (p0[0] * Math.pow(1 - t, 3) + 3 * p1[0] * t
					* (1 - t) * (1 - t) + 3 * p2[0] * t * t * (1 - t) + p3[0]
					* t * t * t);
			dot[1] = (float) (p0[1] * Math.pow(1 - t, 3) + 3 * p1[1] * t
					* (t - 1) * (t - 1) + 3 * p2[1] * t * t * (t - 1) + p3[1]
					* t * t * t);

			if (i > 0) {
				double xDiff = dot[0] - previousDot[0];
				double yDiff = dot[1] - previousDot[1];
				length += Math.sqrt(xDiff * xDiff + yDiff * yDiff);
			}
			previousDot = dot;
		}
		return length;
	}

	void pathUpdate() {

		allDest = new ArrayList<Dest>();
		float step = 50;

		for (int i = 1; i <= step; i++) {
			float t = i / step;
			float px = (float) (p0[0] * Math.pow(1 - t, 3) + 3 * p1[0] * t
					* (1 - t) * (1 - t) + 3 * p2[0] * t * t * (1 - t) + p3[0]
					* t * t * t);
			float py = (float) (p0[1] * Math.pow(1 - t, 3) + 3 * p1[1] * t
					* (1 - t) * (1 - t) + 3 * p2[1] * t * t * (1 - t) + p3[1]
					* t * t * t);
			allDest.add(new Dest(px, py));
		}
		Dest dest = allDest.get(0);
		desX = dest.x;
		desY = dest.y;
		updateDir();

	}

	public boolean isBlocked() {
		return map.current.isStop(midX, midY);
	}

	public void updatePolygon() {
		poly = new Polygon(new float[] { posX, posY, posX + width, posY,
				posX + width, posY + height, posX, posY + height });
	}

	public void drawPolygon(ShapeRenderer shapeRenderer) {

		KaniMap tmap = map.current;
		for (int i = 0; i < tmap.blocked.size(); i++) {
			Block entity = (Block) tmap.blocked.get(i);
			entity.draw(shapeRenderer);
		}
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.polygon(poly.getVertices());
		shapeRenderer.end();

	}

	public Polygon getPolygon() {
		return poly;
	}

	public float getdX() {
		return desX;
	}

	public float getdY() {
		return desY;
	}

	public double distToGo() {
		return Math.hypot(desX - posX, desY - posY);
	}

	public void updateDir() {
		dir = MathUtils.atan2(desY - posY, desX - posX);
	}

	public void drawPath(SpriteBatch batch, ShapeRenderer renderer) {

		batch.end();
		if (allDest.size() > 0) {

			for (int i = 0; i < allDest.size() - 1; i++) {

				Dest d = allDest.get(i);
				Dest dd = allDest.get(i + 1);
				renderer.begin(ShapeType.Filled);
				renderer.line(d.x, d.y, dd.x, dd.y);
				renderer.end();

				batch.begin();
			}
		}
	}

	@Override
	public boolean isCibled() {
		return cibled;
	}

	@Override
	public void setCibled(float hx, float hy) {

		if (clockCible.getElapsedTime() > 10000) {
			int lancer = r.nextInt(3);

			if (lancer == 1) {
				setDest(hx, hy);
				updateDir();
				clockCible.reset();
				cibled = true;
			}
		}
	}

	public void pause() {
		clockCible.pause();
	}

	public void unpause() {
		clockCible.play();
	}
}
