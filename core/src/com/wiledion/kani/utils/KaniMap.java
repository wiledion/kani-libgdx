package com.wiledion.kani.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.wiledion.kani.data.Loader;
import com.wiledion.kani.data.MapLoader;

/**
 * TiledMap class
 * 
 * @author Wiledio
 * 
 */

public class KaniMap {

	public class Prop {
		HashMap tilecfg;

		public Prop(String key, String val) {
			tilecfg = new HashMap();
			set(key, val);
		}

		void set(String key, String val) {
			tilecfg.put(key, val);
		}

		public String get(String property) {
			return (String) tilecfg.get(property);
		}
	}

	private static final int TILE_WIDTH = 32;
	private static final int TILE_HEIGHT = 32;
	HashMap properties;

	AssetManager manager = Loader.getManager();
	private int size;
	int tileHeight, tileWidth;
	int nbTileWidth, nbTileHeight, nbLayer, nbCollLayer;
	int mapWidth;
	int mapHeight;
	TiledMap map;
	ArrayList<Layer> layers;
	float posx;
	float posy;
	ArrayList<Object> spriteTiles;
	PolygonMapObject surface;
	MapLoader loader = new MapLoader();

	// Polygon surface;
	private int square[] = { 1, 1, 31, 1, 31, 31, 1, 31 };
	public ArrayList<Object> aqua;
	public ArrayList<Object> blocked;
	Texture tilesheet;

	public KaniMap(String filename) {

		// Map loading
		manager = Loader.getManager();
		layers = new ArrayList<Layer>();
		properties = new HashMap();
		loader.load(filename, this);
		tilesheet = manager.get("data/maps/kanitiles1.png", Texture.class);

		blocked = new ArrayList<Object>();
		aqua = new ArrayList<Object>();

		// Read textures
		spriteTiles = new ArrayList<Object>();
		ArrayList<Texture> arrayTextures = new ArrayList<Texture>();
		arrayTextures.add(manager
				.get("data/maps/kanitiles1.png", Texture.class));
		arrayTextures.add(manager
				.get("data/maps/kanitiles2.png", Texture.class));
		arrayTextures.add(manager
				.get("data/maps/kanitiles3.png", Texture.class));
		arrayTextures.add(manager
				.get("data/maps/kanitiles4.png", Texture.class));

		for (int i = 0; i < arrayTextures.size(); i++) {

			TextureRegion[][] tmp_tiles = TextureRegion.split(
					arrayTextures.get(i), TILE_WIDTH, TILE_HEIGHT);

			for (int l = 0; l < tmp_tiles.length; l++) {
				for (int c = 0; c < tmp_tiles[0].length; c++) {
					spriteTiles.add(tmp_tiles[l][c]);
				}
			}
		}

		posx = 0;
		posy = 0;

		// Load tiles properties
		for (int k = 0; k < nbLayer; k++) {
			for (int i = 0; i < nbTileHeight; i++) {
				for (int j = 0; j < nbTileWidth; j++) {

					int tileID = getTileID(i, j, k);
					String value = getProperty("blocked", tileID);
					if ("true".equals(value)) {
						blocked.add(new Block(j * tileWidth, mapHeight
								- TILE_WIDTH - i * tileHeight, square, "square"));
					}
					value = getProperty("aqua", tileID);
					if ("true".equals(value)) {
						aqua.add(new Block(j * tileWidth, mapHeight
								- TILE_WIDTH - i * tileHeight, square, "square"));
					}
				}
			}
		}

		blocked.addAll(aqua);

		surface = new PolygonMapObject(new float[] { 2, 2, mapWidth - 2, 2,
				mapWidth - 2, mapHeight - 2, 2, mapHeight - 2 });

	}

	public boolean isBlocked(float x, float y) {

		for (int i = 0; i < blocked.size(); i++) {
			Block entity = (Block) blocked.get(i);
			if (entity.poly.getPolygon().contains(x, y)) {
				return true;
			}
		}
		for (int i = 0; i < aqua.size(); i++) {
			Block entity = (Block) aqua.get(i);
			if (entity.poly.getPolygon().contains(x, y)) {
				return true;
			}
		}
		return false;
	}

	public boolean isAqua(float x, float y) {

		for (int i = 0; i < aqua.size(); i++) {
			Block entity1 = (Block) aqua.get(i);
			if (entity1.poly.getPolygon().contains(x, y)) {
				return true;
			}
		}
		return false;
	}

	public boolean isStop(float x, float y) {
		if ((isBlocked(x, y) || isAqua(x, y))) {
			return true;
		} else
			return false;
	}

	public int getWidthInTiles() {
		return tileWidth;
	}

	public int getHeightInTiles() {
		return tileHeight;
	}

	public void pathFinderVisited(int x, int y) {
	}

	public boolean blocked(Object pathfindingContext, int tx, int ty) {
		return false;
	}

	public float getCost(Object pathfindingContext, int tx, int ty) {
		return 1;
	}

	public void setPosX(int newx) {
		posx = newx;
	}

	public void setPosY(int newy) {
		posy = newy;
	}

	public float getPosX() {
		return posx;
	}

	public float getPosY() {
		return posy;
	}

	public void iteratePosX(int newx) {
		posx += newx;
	}

	public void iteratePosY(int newy) {
		posy += newy;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public PolygonMapObject getSurface() {
		return surface;
	}

	public void print() {
		Set cles = properties.keySet();
		Iterator it = cles.iterator();
		// System.out.println("size it " + cles.size());
		while (it.hasNext()) {
			String cle = it.next().toString();
			// System.out.println(cle);
		}
	}

	public void renderLayer(int layer, SpriteBatch batch) {

		for (int i = 0; i < nbTileHeight; i++) {
			for (int j = 0; j < nbTileWidth; j++) {
				int tileID = getTileID(i, j, layer);
				renderTile(batch, tileID, i, j);
			}
		}
	}

	private int getTileID(int i, int j, int k) {
		int id = layers.get(k).getTileID(i, j);
		return id;
	}

	public void setTileID(int i, int j, int k, int value) {
		layers.get(k).setTileID(i, j, value);
	}

	public void renderTile(SpriteBatch batch, int id, int i, int j) {
		TextureRegion spriteTile = (TextureRegion) spriteTiles.get(id);
		batch.draw(spriteTile, j * tileWidth, mapHeight - TILE_WIDTH - i
				* tileHeight);
	}

	public void setConfig(int stileHeight, int stileWidth, int snbTileHeight,
			int snbTileWidth, int nnbLayer) {
		tileHeight = stileHeight;
		tileWidth = stileWidth;
		nbTileHeight = snbTileHeight;
		nbTileWidth = snbTileWidth;
		nbLayer = nnbLayer;
		mapWidth = nbTileWidth * tileWidth;
		mapHeight = nbTileHeight * tileHeight;
	}

	public void addLayer(int[][] tilesID, String name) {
		// System.out.println("Ajout d'une couche "+ name);
		layers.add(new Layer(tilesID, name));
	}

	public void addProp(int tileID, Prop prop) {
		properties.put(tileID, prop);
	}

	public String getProperty(String property, int tileID) {
		// System.out.println(properties.size());
		Prop prop = (Prop) properties.get(tileID);
		if (prop != null) {
			// System.out.println(prop.toString());
			return prop.get(property);
		}
		return "null";
	}

	public void drawPoly(ShapeRenderer shapeRenderer) {
		for (int i = 0; i < blocked.size(); i++) {
			Block entity = (Block) blocked.get(i);
			entity.draw(shapeRenderer);
		}
	}

	public int getNbLayer() {
		return nbLayer;
	}

}
