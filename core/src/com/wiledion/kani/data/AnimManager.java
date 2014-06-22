package com.wiledion.kani.data;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Manager of sprites animations
 * 
 * @author wiledion
 * 
 */

public class AnimManager {

	private static AnimManager animManager = new AnimManager();
	Loader loader = Loader.getLoader();
	TextureRegion[][] tmp;

	private AnimManager() {
		super();
	}

	public static AnimManager get() {
		return animManager;
	}

	public Animation hero_move_up;
	public Animation hero_move_down;
	public Animation hero_move_left;
	public Animation hero_move_right;
	public Animation hero_stand_up;
	public Animation hero_stand_down;
	public Animation hero_stand_left;
	public Animation hero_stand_right;
	public Animation hero_attack_up;
	public Animation hero_attack_down;
	public Animation hero_attack_left;
	public Animation hero_attack_right;
	public Animation hero_attack_up_w;
	public Animation hero_attack_down_w;
	public Animation hero_attack_left_w;
	public Animation hero_attack_right_w;
	public Animation statue_move_up;
	public Animation statue_move_down;
	public Animation statue_move_left;
	public Animation statue_move_right;
	public Animation statue_stand_up;
	public Animation statue_stand_down;
	public Animation statue_stand_left;
	public Animation statue_stand_right;
	public Animation bird;

	float intFrame_hero_move = 0.2f;
	float intFrame_hero_attack = 0.1f;
	float intFrame_statue_move = 0.2f;

	public void load() {

		Array hero_move_up_a = new Array();
		Array hero_move_down_a = new Array();
		Array hero_move_left_a = new Array();
		Array hero_move_right_a = new Array();
		Array hero_stand_up_a = new Array();
		Array hero_stand_down_a = new Array();
		Array hero_stand_left_a = new Array();
		Array hero_stand_right_a = new Array();
		Array hero_attack_up_a = new Array();
		Array hero_attack_down_a = new Array();
		Array hero_attack_left_a = new Array();
		Array hero_attack_right_a = new Array();
		Array hero_attack_up_a_w = new Array();
		Array hero_attack_down_a_w = new Array();
		Array hero_attack_left_a_w = new Array();
		Array hero_attack_right_a_w = new Array();

		Array statue_move_up_a = new Array();
		Array statue_move_down_a = new Array();
		Array statue_move_left_a = new Array();
		Array statue_move_right_a = new Array();
		Array statue_stand_up_a = new Array();
		Array statue_stand_down_a = new Array();
		Array statue_stand_left_a = new Array();
		Array statue_stand_right_a = new Array();

		Array bird_a = new Array();

		Sprite hero1 = loader.getSprite("hero1");
		Sprite hero2 = loader.getSprite("hero2");
		Sprite hero3 = loader.getSprite("hero3");
		Sprite statue1 = loader.getSprite("statue1");
		Sprite statue2 = loader.getSprite("statue2");

		tmp = hero1.split((int) hero1.getWidth() / 7,
				(int) hero1.getHeight() / 4);

		for (int j = 5; j >= 0; j--) {
			hero_move_up_a.add(tmp[0][j]);
			hero_move_down_a.add(tmp[1][j]);
		}

		for (int j = 4; j >= 0; j--) {
			hero_move_left_a.add(tmp[2][j]);
			hero_move_right_a.add(tmp[3][j]);
		}

		hero_stand_up_a.add(tmp[0][6]);
		hero_stand_down_a.add(tmp[1][6]);
		hero_stand_left_a.add(tmp[2][6]);
		hero_stand_right_a.add(tmp[3][6]);

		tmp = hero2.split((int) hero2.getWidth() / 4,
				(int) hero2.getHeight() / 2);

		hero_attack_left_a.add(tmp[0][0]);
		hero_attack_left_a.add(tmp[0][3]);
		hero_attack_right_a.add(tmp[1][3]);
		hero_attack_right_a.add(tmp[1][0]);

		hero_attack_left_a_w.add(tmp[0][3]);
		hero_attack_right_a_w.add(tmp[1][0]);

		tmp = hero3.split((int) hero3.getWidth() / 4,
				(int) hero3.getHeight() / 2);

		hero_attack_up_a.add(tmp[0][2]);
		hero_attack_up_a.add(tmp[0][3]);
		hero_attack_down_a.add(tmp[1][2]);
		hero_attack_down_a.add(tmp[1][3]);

		hero_attack_up_a_w.add(tmp[0][3]);
		hero_attack_down_a_w.add(tmp[1][3]);

		// Statues animation

		tmp = statue1.split((int) statue1.getWidth() / 7,
				(int) statue1.getHeight() / 2);

		for (int j = 5; j >= 0; j--) {
			statue_move_left_a.add(tmp[0][j]);
			statue_move_right_a.add(tmp[1][5 - j]);
		}

		statue_stand_left_a.add(tmp[0][6]);
		statue_stand_right_a.add(tmp[1][6]);

		tmp = statue2.split((int) statue2.getWidth() / 7,
				(int) statue2.getHeight() / 2);

		for (int j = 5; j >= 0; j--) {
			statue_move_down_a.add(tmp[0][j]);
			statue_move_up_a.add(tmp[1][j]);
		}

		statue_stand_down_a.add(tmp[0][6]);
		statue_stand_down_a.add(tmp[1][6]);

		// Bird animation

		bird_a.add(loader.getSprite("bird1"));
		bird_a.add(loader.getSprite("bird2"));

		hero_move_up = new Animation(intFrame_hero_move, hero_move_up_a);
		hero_move_down = new Animation(intFrame_hero_move, hero_move_down_a);
		hero_move_left = new Animation(intFrame_hero_move, hero_move_left_a);
		hero_move_right = new Animation(intFrame_hero_move, hero_move_right_a);
		hero_stand_up = new Animation(intFrame_hero_move, hero_stand_up_a);
		hero_stand_down = new Animation(intFrame_hero_move, hero_stand_down_a);
		hero_stand_left = new Animation(intFrame_hero_move, hero_stand_left_a);
		hero_stand_right = new Animation(intFrame_hero_move, hero_stand_right_a);
		hero_attack_up = new Animation(intFrame_hero_attack, hero_attack_up_a);
		hero_attack_down = new Animation(intFrame_hero_attack,
				hero_attack_down_a);
		hero_attack_left = new Animation(intFrame_hero_attack,
				hero_attack_left_a);
		hero_attack_right = new Animation(intFrame_hero_attack,
				hero_attack_right_a);
		hero_attack_up_w = new Animation(intFrame_hero_attack,
				hero_attack_up_a_w);
		hero_attack_down_w = new Animation(intFrame_hero_attack,
				hero_attack_down_a_w);
		hero_attack_left_w = new Animation(intFrame_hero_attack,
				hero_attack_left_a_w);
		hero_attack_right_w = new Animation(intFrame_hero_attack,
				hero_attack_right_a_w);

		statue_move_up = new Animation(intFrame_statue_move, statue_move_up_a);
		statue_move_down = new Animation(intFrame_statue_move,
				statue_move_down_a);
		statue_move_left = new Animation(intFrame_statue_move,
				statue_move_left_a);
		statue_move_right = new Animation(intFrame_statue_move,
				statue_move_right_a);
		statue_stand_up = new Animation(intFrame_statue_move, statue_stand_up_a);
		statue_stand_down = new Animation(intFrame_statue_move,
				statue_stand_down_a);
		statue_stand_left = new Animation(intFrame_statue_move,
				statue_stand_left_a);
		statue_stand_right = new Animation(intFrame_statue_move,
				statue_stand_right_a);

		bird = new Animation(0.3f, bird_a);

	}
}
