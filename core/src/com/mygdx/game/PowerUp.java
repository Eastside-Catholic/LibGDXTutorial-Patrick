package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class PowerUp {
	int id;
	float x , y;
	Texture texture;
	Rectangle rect;
	static Texture invincibleTex = new Texture("shield.png");
	static Texture reviveTex = new Texture("heart_small.png");
	static Texture tripleTex = new Texture("triple.png");
	static Texture freezeTex = new Texture("ice.png");
			
	final static int REVIVEPLAYER = 0, TRIPLESHOT = 1, INVINCIBILITY = 2, FREEZE = 3;
	public PowerUp(int id, float x, float y){
		this.id = id;
		this.x = x;
		this.y = y;
		rect = new Rectangle(x, y, 32, 32);
		if(id == 0)
			texture = reviveTex;
		if(id == 1)
			texture = tripleTex;
		if(id == 2)
			texture = invincibleTex;
		if(id == 3)
			texture = freezeTex;
		

	}
}
