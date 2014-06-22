package com.wiledion.kani.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.wiledion.kani.data.MapManager;
import com.wiledion.kani.state.GameStage;
import com.wiledion.kani.utils.Layer;
import com.wiledion.kani.utils.KaniMap;

/**
 * Loads a map file
 * 
 * @author wiledion
 * 
 */

public class MapLoader {

	class AliasTileID {

		int i;
		int j;
		int layer;
		String alias;

		AliasTileID(int ny, int nx, int lay, String tileID) {

			i = ny;
			j = nx;
			layer = lay;
			alias = tileID;

		}
	}

	Scanner scanner;
	MapManager mapmanager = MapManager.get();
	GameStage game;
	int[][] tilesID;
	Pattern assignStringrgx;
	Pattern digitsrgx;
	Pattern layerRgx;
	Pattern proprgx;
	Matcher massign;
	Matcher mvar;
	Matcher mlayer;
	Matcher digits;
	File file;
	int mapheight, mapwidth, tileheight, tilewidth, nblayer;
	int idxLayer = 0;
	int nbProp = 0;
	ArrayList<AliasTileID> postTreat;

	public void load(String fileName, KaniMap map) {

		FileHandle hfile = Gdx.files.internal("data/maps/" + fileName + ".kni");

		String sfile = hfile.readString("UTF-8");
		file = hfile.file();
		scanner = new Scanner(sfile);
		postTreat = new ArrayList<AliasTileID>();
		assignStringrgx = Pattern.compile("^(\\w*)\\s*=\\s*(\\d*)(\\s*)$");

		digitsrgx = Pattern.compile("\\d\\d\\d");
		layerRgx = Pattern.compile("layer\\s*(\\w*)");
		proprgx = Pattern.compile("^(\\w*)\\s*=\\s*(\\w*)(\\s*)$");
		while (scanner.hasNextLine() && nbProp < 5) {
			String param;
			param = scanner.nextLine();

			if (param.length() > 0) {
				// System.out.println("ligne :" + param);
				massign = assignStringrgx.matcher(param);

				if (massign.matches()) {
					String var = massign.group(1);
					int val = Integer.parseInt(massign.group(2));

					if (var.equals("mapheight")) {
						// System.out.println("var :" + var +"val :" + val ) ;
						mapheight = val;
						nbProp++;
					} else if (var.equals("mapwidth")) {
						// System.out.println("var :" + var +"val :" + val ) ;
						mapwidth = val;
						nbProp++;
					} else if (var.equals("tileheight")) {
						// System.out.println("var :" + var +"val :" + val ) ;
						tileheight = val;
						nbProp++;
					} else if (var.equals("tilewidth")) {
						// System.out.println("var :" + var +"val :" + val ) ;
						tilewidth = val;
						nbProp++;
					} else if (var.equals("nblayer")) {
						// System.out.println("var :" + var +"val :" + val ) ;
						nblayer = val;
						nbProp++;
					}
				}
			}
			// System.out.println("nbPropr :" + nbProp);
		}

		map.setConfig(tileheight, tilewidth, mapheight, mapwidth, nblayer);

		// Read layers
		Boolean finishRead = false;
		while (scanner.hasNextLine() && !finishRead) {
			String line = scanner.nextLine();
			mvar = layerRgx.matcher(line);
			if (mvar.matches()) {
				readLayer(mvar.group(1), map);
			}
			if (idxLayer == nblayer) {
				finishRead = true;
			}
		}

		// System.out.println("Reading layer");
		for (int i = 0; i < postTreat.size(); i++) {

			AliasTileID aTileID = postTreat.get(i);
			if (aTileID.alias.equals("aaa")) {

				map.setTileID(aTileID.i, aTileID.j - 1, aTileID.layer, 653);
				map.setTileID(aTileID.i - 1, aTileID.j, aTileID.layer, 638);
				map.setTileID(aTileID.i, aTileID.j + 1, aTileID.layer, 655);

				map.setTileID(aTileID.i - 1, aTileID.j - 1, aTileID.layer + 1,
						637);
				map.setTileID(aTileID.i - 2, aTileID.j - 1, aTileID.layer + 1,
						621);
				map.setTileID(aTileID.i - 2, aTileID.j, aTileID.layer + 1, 622);
				map.setTileID(aTileID.i - 2, aTileID.j + 1, aTileID.layer + 1,
						623);
				map.setTileID(aTileID.i - 1, aTileID.j + 1, aTileID.layer + 1,
						639);
			}
		}

		// Reading tile properties
		String line = scanner.nextLine();
		while (scanner.hasNextLine()) {
			// System.out.println("line:" + line);
			mvar = proprgx.matcher(line);
			if (mvar.matches()) {
				String key = mvar.group(1);
				String value = mvar.group(2);
				// System.out.println("key, var " + key + "," + value);
				line = scanner.nextLine();
				while (line.matches("\\d(\\s*\\d*)*\\s*")) {
					// System.out.println("linescanner:" + line);
					Scanner linescanner = new Scanner(line);
					while (linescanner.hasNext()) {
						int tID = linescanner.nextInt();
						map.addProp(tID, map.new Prop(key, value));
					}
					if (scanner.hasNext()) {
						line = scanner.nextLine();
					} else
						break;
				}
			} else {
				line = scanner.nextLine();
			}
		}
		map.print();
	}

	private void readLayer(String nameLayer, KaniMap map) {

		// System.out.println("----Layer reading");
		String line = scanner.nextLine();
		tilesID = new int[mapheight][mapwidth];
		int i, j;
		for (i = 0; i < mapheight; i++) {
			// System.out.println("line : " + line);
			Scanner linescanner = new Scanner(line);
			for (j = 0; j < mapwidth; j++) {
				String elt = linescanner.next();
				if (elt.matches("\\d\\d\\d")) {

					tilesID[i][j] = Integer.parseInt(elt);
				} else {
					if (elt.equals("aaa")) {
						tilesID[i][j] = 654;
						postTreat.add(new AliasTileID(i, j, idxLayer, elt));
					}

					else if (elt.equals("___")) {
						tilesID[i][j] = 70;
					}
				}
			}
			line = scanner.nextLine();
		}
		map.addLayer(tilesID, nameLayer);
		idxLayer++;
	}
}
