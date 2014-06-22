package com.wiledion.kani.world;

/**
 * Thing class (in the game)
 * 
 * @author wiledion
 * 
 */

abstract public class

Thing {

	int ID;
	String name;
	String description;
	float posX;
	float posY;
	int midX;
	int midY;

	abstract void init();

	abstract void setX(float X);

	abstract void setY(float Y);

	abstract float getX();

	abstract float getY();

	abstract void set(float X, float Y);

	abstract void translate(float X, float Y);

}
