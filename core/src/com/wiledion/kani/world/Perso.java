package com.wiledion.kani.world;

import com.wiledion.kani.utils.KaniMap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;

/**
 * Game character
 * 
 * @author wiledion
 * 
 */

public class Perso extends Thing {

	float pv;
	int maxPV;
	int mana;
	int attack;
	float dx;
	float dy;
	int height;
	int width;
	int levelWidth;
	int levelHeight;
	int mode;
	Animation anim;
	Polygon poly;
	PolygonMapObject surface;
	boolean isDead;

	public Perso() {

	}

	public void init() {

	}

	public void dessiner() {

	}

	public void dessiner(float X, float Y) {

	}

	public void setX(float X) {
	}

	public void setY(float Y) {
	}

	public float getX() {
		return posX;
	}

	public float getY() {
		return posY;
	}

	public float getAttack() {
		return attack * 0.1f;
	}

	public void setLevelWidth(int levelw) {
		levelWidth = levelw;
	}

	public void setLevelHeight(int levelh) {
		{
			levelHeight = levelh;

		}
	}

	public void setAnim(Animation nAnim) {
		anim = nAnim;
		height = anim.getKeyFrame(0).getRegionHeight();
		width = anim.getKeyFrame(0).getRegionWidth();
	}

	void set(float X, float Y) {
	}

	void translate(float X, float Y) {
	}

	public void update(KaniMap map) {
	}

	public void setMap(KaniMap current2) {
		setLevelWidth(current2.getMapWidth());
		setLevelHeight(current2.getMapHeight());
		surface = current2.getSurface();
	}

	public Polygon getPolygon() {
		return poly;
	}

	public void draw(SpriteBatch batch) {

	}
}
