package com.wiledion.kani.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Show a message in the bottom-board
 * 
 * @author wiledion
 * 
 */

public class MessageBox {

	String str;
	StringBuffer buff;
	int index;
	BitmapFont font;
	int x, y, width, xtext, ytext, height, numbLine;
	Timer tac;
	Timer wait;
	Boolean end, noText;
	Boolean ifWait;

	public MessageBox(int px, int py, int pwidth, int pheight) {

		width = pwidth;
		height = pheight;
		font = new BitmapFont(Gdx.files.internal("data/demo.fnt"),
				Gdx.files.internal("data/demo_00.tga"), false);
		x = px;
		y = py;

		xtext = x + 2;
		ytext = (int) (py + pheight - font.getCapHeight() + 2);
		clean();
	}

	public void draw(SpriteBatch batch, ShapeRenderer renderer) {

		if (!noText) {
			batch.end();

			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

			Gdx.gl.glDisable(GL20.GL_BLEND);
			batch.begin();
			font.setColor(Color.WHITE);
			font.drawMultiLine(batch, buff.toString(), xtext, ytext);

		}
	}

	public void addText(StringBuffer text) {

		StringBuffer word = new StringBuffer();
		StringBuffer line = new StringBuffer();

		for (int i = 0; i < text.length(); i++) {

			// Take a word
			while ((i < text.length()) && (text.charAt(i) != ' ')
					&& (text.charAt(i) != '\n')) {
				word.append((text.charAt(i)));
				i++;
			}

			// If there is a return at the end of the word
			if (text.charAt(i) == '\n') {

				if (font.getBounds(line + " " + word).width < width) { // If
																		// there
																		// is
																		// enough
																		// place
																		// to
																		// add a
																		// word
					str = str.concat(line.toString() + word + "\n");
					line.delete(0, line.length());
					word.delete(0, word.length());

				} else {
					str = str.concat(line.toString() + "\n" + word + "\n");

					line.delete(0, line.length());
					word.delete(0, word.length());
				}

			}

			// Else if all the text is not formatted
			else if (i < text.length()) {

				if (font.getBounds(line + " " + word).width < width) { // If
																		// there
																		// is
																		// enough
																		// place
																		// to
																		// add a
																		// word
					line.append(word.append(" "));
					word.delete(0, word.length());
				} else {
					str = str.concat(line.toString() + "\n");
					line.delete(0, line.length());
					line.append(word + " ");
					word.delete(0, word.length());
				}
			}

		}

		if (font.getBounds(line + " " + word).width < width) {
			str = str.concat(line + " " + word.toString());
		} else {
			str = str.concat(line.toString() + "\n" + word.toString());
		}

	}

	public void clean() {
		str = new String();
		buff = new StringBuffer();
		tac = new Timer();
		wait = new Timer();
		index = 0;
		end = false;
		noText = true;
		ifWait = false;
		numbLine = 0;
	}

	public void start() {
	}

	public void update() {

		if (noText)
			noText = false;

		if (tac.getElapsedTime() > 30 && (!end)) {

			if (numbLine == 3) {
				ifWait = true;
			}

			if (ifWait == false) {
				char c = str.charAt(index);
				if (c == '\n') {
					numbLine++;
				}
				buff.append(str.charAt(index));
				index++;
				tac.reset();
				if (index >= str.length()) {
					end = true;
				}
			}
		}
	}

	public boolean isEnded() {
		return end;
	}

	public void setWaitingFalse() {
		ifWait = false;
		numbLine = 0;
		buff.delete(0, buff.length());

	}

	public void endReading() {
		noText = true;

	}

	public boolean isWaiting() {
		return ifWait;
	}
}
