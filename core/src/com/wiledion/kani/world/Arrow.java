package com.wiledion.kani.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.wiledion.kani.data.Loader;
import com.wiledion.kani.data.MapManager;

/**
 * Arrow class
 * 
 * @author wiledion
 * 
 */

public class Arrow extends KObject {

	Sprite sprite;
	Polygon poly;
	double vel;
	float velX;
	float velY;
	float hit;
	public static final double DIR_UP = Math.PI / 2;
	public static final double DIR_DOWN = -Math.PI / 2;
	public static final double DIR_LEFT = -Math.PI;
	public static final double DIR_RIGHT = 0;
	public static final double DIR_UPR = Math.PI / 4;
	public static final double DIR_UPL = 3 * Math.PI / 4;
	public static final double DIR_DOWNL = -3 * Math.PI / 4;
	public static final double DIR_DOWNR = -Math.PI / 4;
	MapManager map = MapManager.get();
	Loader loader = Loader.getLoader();
	float dir;

	Arrow(float ndir, float nx, float ny) {
		super();
		vel = 8. / 30;
		dir = ndir;
		sprite = loader.getSprite("arrow");
		posX = nx;
		posY = ny;
		updatePolygon();
		hit = 15;
	}

	public void draw(SpriteBatch batch) {
		sprite.setRotation(dir * MathUtils.radDeg);
		sprite.draw(batch);
	}

	void update(float timeloop) {
		velX = (float) (vel * Math.cos(dir));
		velY = (float) (vel * Math.sin(dir));
		posX += velX * timeloop;
		posY += velY * timeloop;
		updatePolygon();
		sprite.setPosition(posX, posY);
		sprite.setRotation(dir);
	}

	public float getHit() {
		return hit;
	}

	public double getDir() {
		return dir;
	}

	public boolean inMap(Polygon surface) {
		return Intersector.overlapConvexPolygons(surface, poly);
	}

	private void updatePolygon() {

		int width = (int) sprite.getWidth();
		int height = (int) sprite.getHeight();
		poly = new Polygon(new float[] { posX, posY, posX + width, posY,
				posX + width, posY + height, posX, posY + height });
	}

	public void drawPoly(ShapeRenderer shapeRenderer) {
		// To implement
	}

	public Polygon getPoly() {
		return poly;
	}
}
