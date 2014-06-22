package com.wiledion.kani.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.wiledion.kani.data.MapManager;
import com.wiledion.kani.state.GameStage;

/**
 * Parser to read data from files
 * 
 * @author wiledion
 * 
 */

public class Parser {

	Scanner scanner;
	MapManager map = MapManager.get();
	GameStage game;
	Matcher mAssign;
	Matcher mVar;
	Pattern assignStringRgx = Pattern
			.compile("^(\\w*)\\s*=\\s*'(.*)'(\\s*#.*)?$");
	Pattern titleRgx = Pattern.compile("^(\\w*)\\s*=\\s*(#.*)?$");


	public void interp(String fileName, GameStage game) {

		FileHandle hfile = Gdx.files.internal("data/maps/" + fileName + ".kni");

		String sfile = hfile.readString("UTF-8");
		scanner = new Scanner(sfile);
		scanner.nextLine();

		while (scanner.hasNextLine()) {

			String param;
			param = scanner.nextLine();
			if (param.length() > 0) {
				mAssign = assignStringRgx.matcher(param);
				mVar = titleRgx.matcher(param);
				if (mVar.matches()) {
					String var = mVar.group(1);
					if (var.equals("intro_msg")) {
						setIntroMsg(game);
						scanner.nextLine();
					} else if (var.equals("end_msg")) {
						setEndMsg(game);
						scanner.nextLine();
					} else if (var.equals("dead_msg")) {
						setDeadMsg(game);
						scanner.nextLine();
					} else if (var.equals("enemies")) {
						setEnnCfg(game);
						scanner.nextLine();
					} else if (param.equals("_endfinal")) {
						break;
					} else if (var == "zones") {
						scanner.nextLine();
					}
				}

				else if (mAssign.matches()) {

					String var = mAssign.group(1);
					String val = mAssign.group(2);
					if (var.equals("name_level")) {
						scanner.nextLine();
					} else if (var.equals("current_map")) {
						map.setMap(val);
						scanner.nextLine();
					}
				}
			}
		}
	}

	private void setIntroMsg(GameStage game) {

		StringBuffer introMsg = new StringBuffer();
		String line = new String();
		while (!(line = scanner.nextLine()).equals("_end")) {
			introMsg.append(line + '\n');
		}
		game.setIntroMsg(introMsg.toString());
	}

	private void setEndMsg(GameStage game) {
		StringBuffer endMsg = new StringBuffer();
		String line = new String();
		while (!(line = scanner.nextLine()).equals("_end")) {
			endMsg.append(line + '\n');
		}
		game.setEndMsg(endMsg.toString());
	}

	private void setDeadMsg(GameStage game) {

		StringBuffer endMsg = new StringBuffer();
		String line = new String();
		while (!(line = scanner.nextLine()).equals("_end")) {
			endMsg.append(line + '\n');
		}
		game.setDeadMsg(endMsg.toString());
	}

	private void setEnnCfg(GameStage game) {

		ArrayList<Integer[]> allCfg = new ArrayList<Integer[]>();
		String[] cfg = new String[2];
		Integer[] intCfg = new Integer[2];
		String line;
		line = scanner.nextLine();
		// System.out.println(line);
		while (!line.equals("_end")) {
			cfg = line.split("-");
			intCfg[0] = Integer.parseInt(cfg[0]);
			intCfg[1] = Integer.parseInt(cfg[1]);
			allCfg.add(intCfg.clone());
			line = scanner.nextLine();
		}
		game.setEnemiesCfg(allCfg, allCfg.size());
		for (int i = 0; i < allCfg.size(); i++) {
		}
	}
}
