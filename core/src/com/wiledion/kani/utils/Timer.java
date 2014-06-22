package com.wiledion.kani.utils;

/**
 * Class for time operations
 * 
 * @author wiledion
 * 
 */

public class Timer {

	long start;
	long end;
	long elapsedBeforePause;

	public Timer() {
		reset();
		elapsedBeforePause = 0;
	}

	public long getElapsedTime() {
		return (System.currentTimeMillis() - start);
	}

	public void reset() {
		start = System.currentTimeMillis();
	}

	public void pause() {
		elapsedBeforePause = System.currentTimeMillis() - start;
	}

	public void play() {
		start = System.currentTimeMillis() - elapsedBeforePause;
	}

}
