package com.wiledion.kani.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wiledion.kani.utils.KaniMap;

/**
 * Enemy class
 * 
 * @author wiledion
 * 
 */

public abstract class Enemy extends Perso {

	int layer;

	public Enemy() {
		super();
	}

	public void setX(float X) {
		posX = X;
	}

	public void setY(float Y) {
		posY = Y;
	}

	public float getX() {
		return posX;
	}

	public float getY() {
		return posY;
	}

	public int getLayer() {
		return layer;
	}

	public void setLevelWidth(int levelw) {
		levelWidth = levelw;
	}

	public void setLevelHeight(int levelh) {
		levelHeight = levelh;
	}

	void set(float X, float Y) {
		posX = X;
		posY = Y;
	}

	void translate(float X, float Y) {
		posX += X;
		posY += Y;
	}

	public void setMap(KaniMap maptest) {
		setLevelWidth(maptest.getMapWidth());
		setLevelHeight(maptest.getMapHeight());
		surface = maptest.getSurface();
	}

	abstract public void init();

	abstract public void draw(SpriteBatch batch, float looptime);

	abstract public boolean isCibled();

	abstract public void setCibled(float hx, float hy);

	abstract public void receiveDamage(float damage);

	abstract public boolean isDead();

	abstract public void updateDefault(float looptime);

	abstract public void pause();

	abstract public void unpause();

}
