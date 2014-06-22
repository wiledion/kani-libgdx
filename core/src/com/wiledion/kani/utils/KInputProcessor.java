package com.wiledion.kani.utils;

import java.util.HashSet;
import com.badlogic.gdx.InputProcessor;

/**
 * InputProcessor implemented
 * 
 * @author wiledion
 * 
 */

public class KInputProcessor implements InputProcessor {

	public HashSet<Integer> keycontrol;
	public HashSet<Integer> oldControl, oldControl2;

	int cursorx, cursory;

	public KInputProcessor() {
		keycontrol = new HashSet();
		oldControl = (HashSet) keycontrol.clone();
		oldControl2 = (HashSet) keycontrol.clone();
		cursorx = 0;
		cursory = 0;
	}

	@Override
	public boolean keyDown(int keycode) {
		return keycontrol.add(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		return keycontrol.remove(keycode);
	}

	public void updateOld() {
		oldControl2 = (HashSet) oldControl.clone();
		oldControl = (HashSet) keycontrol.clone();
	}

	public boolean isKeyPressed(int keycode) {
		if (keycontrol.contains(keycode) && !(oldControl.contains(keycode))) {
			return true;
		}
		return false;

	}

	public boolean isKeyReleased(int keycode) {
		if (oldControl.contains(keycode) && !keycontrol.contains(keycode)) {
			return true;
		}
		return false;
	}

	public boolean isKeyDown(int keycode) {
		return keycontrol.contains(keycode);
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		cursorx = screenX;
		cursory = 600 - screenY; // TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update() {

	}

	public int getX() {
		return cursorx;
	}

	public int getY() {
		return cursory;
	}

}
