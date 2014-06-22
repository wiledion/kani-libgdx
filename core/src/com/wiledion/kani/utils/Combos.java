package com.wiledion.kani.utils;

import java.util.ArrayList;

import com.wiledion.kani.world.Hero;
import com.badlogic.gdx.Input.Keys;

/**
 * Defines and manages key combos
 * 
 * @author wiledion
 * 
 */

public class Combos {

	ArrayList<Integer> taps;
	ArrayList<Integer> up_3_arrows;
	ArrayList<Integer> down_3_arrows;
	ArrayList<Integer> left_3_arrows;
	ArrayList<Integer> right_3_arrows;
	ArrayList<Integer> card_arrows;
	boolean added;
	long lastTap;

	void initCombos() {
		up_3_arrows = new ArrayList<Integer>();
		down_3_arrows = new ArrayList<Integer>();
		left_3_arrows = new ArrayList<Integer>();
		right_3_arrows = new ArrayList<Integer>();
		card_arrows = new ArrayList<Integer>();

		for (int i = 1; i <= 2; i++) {
			up_3_arrows.add(Keys.UP);
			down_3_arrows.add(Keys.DOWN);
			left_3_arrows.add(Keys.LEFT);
			right_3_arrows.add(Keys.RIGHT);
		}

		up_3_arrows.add(Keys.Q);
		down_3_arrows.add(Keys.Q);
		left_3_arrows.add(Keys.Q);
		right_3_arrows.add(Keys.Q);

		up_3_arrows.add(Keys.UP);
		down_3_arrows.add(Keys.DOWN);
		left_3_arrows.add(Keys.LEFT);
		right_3_arrows.add(Keys.RIGHT);

		card_arrows.add(Keys.RIGHT);
		card_arrows.add(Keys.DOWN);
		card_arrows.add(Keys.LEFT);
		card_arrows.add(Keys.UP);
		card_arrows.add(Keys.Q);

	}

	public Combos() {
		reset();
		lastTap = 0;
		initCombos();
		added = false;
	}

	public void add(int key, Timer comboclock) {
		long time = comboclock.getElapsedTime();

		added = true;

		if (time - lastTap > 1000) {
			taps = new ArrayList<Integer>();
			lastTap = 0;
			comboclock.reset();
			taps.add(key);
		} else {
			lastTap = time;
			taps.add(key);
		}

		if (taps.size() > 5) {
			taps.remove(0);
		}
	}

	public void reset() {
		taps = new ArrayList<Integer>();
	}

	public void verify(Hero hkani) {

		if (!taps.isEmpty()) {

			if (taps.equals(up_3_arrows)) {
				reset();
				lastTap = -500;

				hkani.addShot(Hero.DIR_UP, -10, 0);
				hkani.addShot(Hero.DIR_UP, 10, 0);

			} else if (taps.equals(down_3_arrows)) {
				reset();
				lastTap = -500;

				hkani.addShot(Hero.DIR_DOWN, -10, 0);
				hkani.addShot(Hero.DIR_DOWN, 10, 0);

			} else if (taps.equals(left_3_arrows)) {
				reset();
				lastTap = -500;

				hkani.addShot(Hero.DIR_LEFT, 0, -10);
				hkani.addShot(Hero.DIR_LEFT, 0, 10);

			} else if (taps.equals(right_3_arrows)) {
				reset();
				lastTap = -500;

				hkani.addShot(Hero.DIR_RIGHT, 0, -10);
				hkani.addShot(Hero.DIR_RIGHT, 0, 10);

			} else if (taps.equals(card_arrows)) {
				reset();
				lastTap = -500;

				hkani.addShot(Hero.DIR_UP, 0, 0);
				hkani.addShot(Hero.DIR_DOWN, 0, 0);
				hkani.addShot(Hero.DIR_LEFT, 0, 0);
				hkani.addShot(Hero.DIR_RIGHT, 0, 0);

			} else if (taps.get(taps.size() - 1) == Keys.Q && added) {
				added = false;
				hkani.updateShots();
			}
		}
	}

	public int size() {
		return taps.size();
	}
}
