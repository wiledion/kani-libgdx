package com.wiledion.kani.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;

/**
 * Represents a tile, to manage collision in a map
 * 
 * @author wiledion
 * 
 */

public class Block {
	public PolygonMapObject poly;

	public Block(int x, int y, int test[], String type) {
		poly = new PolygonMapObject(new float[] { x + test[0], y + test[1],
				x + test[2], y + test[3], x + test[4], y + test[5],
				x + test[6], y + test[7], });
	}

	public void update(int delta) {
	}

	public void draw(ShapeRenderer renderer) {

		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.GREEN);
		renderer.polygon(poly.getPolygon().getVertices());
		renderer.end();
	}

	public Polygon getPoly() {
		return poly.getPolygon();
	}
}
