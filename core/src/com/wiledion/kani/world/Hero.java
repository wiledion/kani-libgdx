package com.wiledion.kani.world;

import java.util.ArrayList;
import java.util.Random;

import com.wiledion.kani.data.AnimManager;
import com.wiledion.kani.data.MapManager;
import com.wiledion.kani.state.GameStage;
import com.wiledion.kani.utils.Block;
import com.wiledion.kani.utils.KaniMap;
import com.wiledion.kani.utils.Timer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

/**
 * Class of the player
 * 
 * @author wiledion
 * 
 */

public class Hero extends Perso {

	public static final float DIR_UP = MathUtils.PI / 2;
	public static final float DIR_DOWN = -MathUtils.PI / 2;
	public static final float DIR_LEFT = MathUtils.PI;
	public static final float DIR_RIGHT = 0;
	public static final float DIR_UPR = MathUtils.PI / 4;
	public static final float DIR_UPL = 3 * MathUtils.PI / 4;
	public static final float DIR_DOWNL = -3 * MathUtils.PI / 4;
	public static final float DIR_DOWNR = -MathUtils.PI / 4;
	AnimManager animMng = AnimManager.get();
	MapManager map = MapManager.get();
	Random r;
	float dir;
	float vel;
	float velX;
	float velY;
	float velR;
	BitmapFont font;
	Animation anim;
	ArrayList<Arrow> allArrow;
	Rectangle bandeh;
	Rectangle bandev;
	Timer clock;
	Timer clockRecover;
	Timer clockShoot;
	boolean shooting;
	double timeshoot;
	TextureRegion currentFrame;
	float statetime;

	public Hero() {
		super();

		vel = (float) (4. / 30);
		font = new BitmapFont(Gdx.files.internal("data/demo.fnt"),
				Gdx.files.internal("data/demo_00.tga"), false);
		r = new Random();
		pv = 150;
		maxPV = 150;
		attack = 15;
		dir = DIR_DOWN;

		allArrow = new ArrayList<Arrow>();

		// Position stand down
		anim = animMng.hero_stand_down;

		height = anim.getKeyFrame(0).getRegionHeight();
		width = anim.getKeyFrame(0).getRegionWidth();
		statetime = 0f;

		posX = 100;
		posY = 80;

		bandeh = new Rectangle(0, 100, 800, 250);
		bandev = new Rectangle(150, 0, 500, 450);

		midX = width / 2;
		midY = height / 2;

		updatePolygons();
		shooting = false;
		clock = new Timer();
		clockRecover = new Timer();
		clockShoot = new Timer();
		clock.reset();
		clockRecover.reset();

		statetime = 0f;
	}

	@Override
	public void draw(SpriteBatch batch) {

		for (int i = 0; i < allArrow.size(); i++) {

			Arrow arrow = allArrow.get(i);
			if (arrow.getDir() == DIR_UP) {
				arrow.draw(batch);
			}
		}

		statetime += Gdx.graphics.getDeltaTime();
		currentFrame = anim.getKeyFrame(statetime, true);
		batch.draw(currentFrame, posX, posY);
		for (int i = 0; i < allArrow.size(); i++) {

			Arrow arrow = allArrow.get(i);
			if (arrow.getDir() != DIR_UP) {
				arrow.draw(batch);
			}
		}
	}

	public void draw(SpriteBatch batch, float looptime) {

		for (int i = 0; i < allArrow.size(); i++) {

			Arrow arrow = allArrow.get(i);
			if (arrow.getDir() == DIR_UP) {
				arrow.draw(batch);

			}
		}

		if (!GameStage.isPaused())
			statetime += looptime / 1000f;
		currentFrame = anim.getKeyFrame(statetime, true);
		batch.draw(currentFrame, posX, posY);
		for (int i = 0; i < allArrow.size(); i++) {

			Arrow arrow = allArrow.get(i);
			if (arrow.getDir() != DIR_UP) {
				arrow.draw(batch);
			}
		}
	}

	public void updateDefault(float timeloop) {

		if (clock.getElapsedTime() > 200) {
			if (shooting) {
				if (dir == DIR_DOWN || dir == DIR_DOWNR) {
					anim = animMng.hero_attack_down_w;
				}

				if (dir == DIR_RIGHT || dir == DIR_UPR) {
					anim = animMng.hero_attack_right_w;
				}

				if (dir == DIR_UP || dir == DIR_UPL) {
					anim = animMng.hero_attack_up_w;
				}

				if (dir == DIR_LEFT || dir == DIR_DOWNL) {
					anim = animMng.hero_attack_left_w;
				}
			}

			else if (clock.getElapsedTime() > 1000) {
				if (dir == DIR_DOWN || dir == DIR_DOWNR) {
					anim = animMng.hero_stand_down;
				}

				if (dir == DIR_RIGHT || dir == DIR_UPR) {
					anim = animMng.hero_stand_right;
				}

				if (dir == DIR_UP || dir == DIR_UPL) {
					anim = animMng.hero_stand_up;
				}

				if (dir == DIR_LEFT || dir == DIR_DOWNL) {
					anim = animMng.hero_stand_left;
				}
			}
		}
		updateArrows(timeloop);
		updateRecovering();
	}

	public void updateRecovering() {
		if (clockRecover.getElapsedTime() > 2500 && pv >0) {
			pv += 1;
			if (pv > maxPV)
				pv = maxPV;
			clockRecover.reset();
		}
	}

	public void setStand() {

		if (dir == DIR_DOWN || dir == DIR_DOWNR) {
			anim = animMng.hero_stand_down;
		}

		if (dir == DIR_RIGHT || dir == DIR_UPR) {
			anim = animMng.hero_stand_right;
		}

		if (dir == DIR_UP || dir == DIR_UPL) {
			anim = animMng.hero_stand_up;
		}

		if (dir == DIR_LEFT || dir == DIR_DOWNL) {
			anim = animMng.hero_stand_left;
		}
		updateRecovering();
	}

	public void setMove() {

		if (dir == DIR_DOWN || dir == DIR_DOWNR) {
			anim = animMng.hero_move_down;
		}

		if (dir == DIR_RIGHT || dir == DIR_UPR) {
			anim = animMng.hero_move_right;
		}

		if (dir == DIR_UP || dir == DIR_UPL) {
			anim = animMng.hero_move_up;
		}

		if (dir == DIR_LEFT || dir == DIR_DOWNL) {
			anim = animMng.hero_move_left;
		}

	}

	public void updateMove(OrthographicCamera cam, float timeloop) {

		float oldX = posX;
		float oldY = posY;
		shooting = false; // To verify if we have to change animation mode
		velX = (vel * MathUtils.cos(dir));
		velY = (vel * MathUtils.sin(dir));

		posX += velX * timeloop;
		posY += velY * timeloop;
		setMove();
		updatePolygons();

		if (isColl() || !inMap()) {
			posX = oldX;
			posY = oldY;
			shooting = true;
			setStand();
			updatePolygons();
		}

		// Movement of the camera relative to the player

		if (oldY < posY && cam.position.y + 518 / 2 < levelHeight
				&& (posY - (cam.position.y - 518 / 2)) > 100) {
			cam.translate(0, velY * timeloop, 0);
		}

		if (oldY > posY && cam.position.y >= 518 / 2
				&& ((cam.position.y + 518 / 2) - posY) > 100) {
			cam.translate(0, velY * timeloop, 0);
		}

		if (oldX > posX && cam.position.x > 800 / 2
				&& ((cam.position.x + 800 / 2) - posX) > 150) {
			cam.translate(velX * timeloop, 0, 0);
		}

		if (oldX < posX && cam.position.x + 800 / 2 < levelWidth
				&& (posX - (cam.position.x - 800 / 2)) > 150) {
			cam.translate(velX * timeloop, 0, 0);
		}

		if (cam.position.y - 518 / 2 < 0) {
			cam.position.y = 518 / 2;
		}
		if (cam.position.y + 518 / 2 > levelHeight) {
			cam.position.y = levelHeight - 518 / 2;
		}

		if (cam.position.x - 800 / 2 < 0) {
			cam.position.x = 800 / 2;
		}
		if (cam.position.x + 800 / 2 > levelWidth) {
			cam.position.x = levelWidth - 800 / 2;
		}

		updateArrows(timeloop);
	}

	public void updateArrows(float looptime) {

		for (int i = 0; i < allArrow.size(); i++) {
			Arrow arrow = allArrow.get(i);
			arrow.update(looptime);
			if (!arrow.inMap(surface.getPolygon())) {
				allArrow.remove(i);
				i--;
			}
		}
	}

	public void updateShots() {

		shooting = true;
		float varx = -6 + r.nextInt(13);
		float vary = -6 + r.nextInt(13);

		if (clock.getElapsedTime() > 300) {
			allArrow.add(new Arrow(dir, varx + posX + width / 2, vary + posY
					+ height / 2));

			if (dir == DIR_DOWN || dir == DIR_DOWNR) {
				anim = animMng.hero_attack_down;
			}

			if (dir == DIR_RIGHT || dir == DIR_UPR) {
				anim = animMng.hero_attack_right;
			}

			if (dir == DIR_UP || dir == DIR_UPL) {
				anim = animMng.hero_attack_up;
			}

			if (dir == DIR_LEFT || dir == DIR_DOWNL) {
				anim = animMng.hero_attack_left;
			}

			clock.reset();
		}
	}

	public void addShot(float dir, float decx, float decy) {
		shooting = true;
		allArrow.add(new Arrow(dir, posX + width / 2 + decx, posY + height / 2
				+ decy));
	}

	public boolean isBlocked() {

		return map.current.isStop(midX, midY);

	}

	public void updatePolygons() {
		poly = new Polygon(new float[] { posX, posY, posX + width, posY,
				posX + width, posY + height, posX, posY + height });
	}

	public void drawPolygons(SpriteBatch batch, ShapeRenderer shapeRenderer) {

		batch.end();
		KaniMap tmap = map.current;
		for (int i = 0; i < tmap.blocked.size(); i++) {
			Block entity = (Block) tmap.blocked.get(i);
			entity.draw(shapeRenderer);

			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.GREEN);
			shapeRenderer.polygon(poly.getVertices());
			shapeRenderer.end();

		}

		for (int i = 0; i < allArrow.size(); i++) {
			Arrow arrow = allArrow.get(i);
			arrow.drawPoly(shapeRenderer);
		}

		batch.begin();
	}

	public void drawInf(ShapeRenderer renderer, SpriteBatch batch,
			BitmapFont font) {

		font.draw(batch, "PV : " + (int) pv + "/" + maxPV, 32, 66);
		batch.end();

		renderer.begin(ShapeType.Filled);
		renderer.setColor(new Color((1 - (float) pv / maxPV), (float) pv
				/ maxPV, 0, 1));
		renderer.rect(32, 28, pv * 100 / maxPV, 20);
		renderer.end();
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.BLACK);
		renderer.rect(32, 28, 100, 20);
		renderer.end();
		batch.begin();

	}

	public boolean isColl() {

		KaniMap tmap2 = map.current;

		for (int i = 0; i < tmap2.blocked.size(); i++) {
			Block entity = (Block) tmap2.blocked.get(i);
			if (Intersector.overlapConvexPolygons(entity.getPoly(), poly)) {
				return true;
			}
		}
		return false;
	}

	public boolean inMap() {
		if (posX + width > levelWidth || posX < 0
				|| posY + height > levelHeight || posY < 0) {
			return false;
		}
		return true;
	}

	public void receiveDamage(float damage) {
		pv -= damage;		
	}

	public float getPv() {
		return pv;
	}

	public ArrayList<Arrow> getAllArrows() {
		return allArrow;
	}

	public void removeArrow(int i) {
		allArrow.remove(i);

	}

	public void setDir(int vx, int vy) {

		if (vx == 1 && vy == 1) {
			dir = DIR_UPR;
		} else if (vx == 1 && vy == -1) {
			dir = DIR_DOWNR;
		} else if (vx == -1 && vy == -1) {
			dir = DIR_DOWNL;
		} else if (vx == -1 && vy == 1) {
			dir = DIR_UPL;
		} else if (vx == 1) {
			dir = DIR_RIGHT;
		} else if (vx == -1) {
			dir = DIR_LEFT;
		} else if (vy == 1) {
			dir = DIR_UP;
		} else if (vy == -1) {
			dir = DIR_DOWN;
		}
	}

	public float getmidY() {
		return midY;
	}

	public float getmidX() {
		return midX;
	}

	public void pause() {
		clock.pause();
		clockRecover.pause();
		clockShoot.pause();
	}

	public void unpause() {
		clock.play();
		clockRecover.play();
		clockShoot.play();
	}
}
