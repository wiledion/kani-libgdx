package com.wiledion.kani.utils;

/**
 * Layer of a map
 * 
 * @author wiledion
 * 
 */

public class Layer {

	int[][] tiles;
	String name;

	public Layer(int[][] nt) {
		tiles = nt;
	}

	public Layer(int[][] nt, String nname) {
		tiles = nt;
		name = nname;

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				// System.out.print(tiles[i][j] + " ");
			}
			// System.out.println();
		}

	}

	public int getTileID(int i, int j) {
		return tiles[i][j];
	}

	public void setTileID(int i, int j, int value) {
		tiles[i][j] = value;
	}

}
