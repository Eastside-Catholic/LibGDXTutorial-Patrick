package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class PowerUp {
	int id;
	float x , y;
	Texture texture;
	Texture invincibleTex = new Texture("shield.png");
	
	final static int REVIVEPLAYER = 0, TRIPLESHOT = 1, INVINCIBILITY = 2, FREEZE = 3;
	public PowerUp(int id, float x, float y){
		this.id = id;
		this.x = x;
		this.y = y;
		if(id == 2)
			texture = invincibleTex;
		

	}
}
