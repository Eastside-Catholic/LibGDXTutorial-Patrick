package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Enemy extends GameEntity{
	boolean isPlayer = false;
	Enemy(float x, float y, int direction, float speed, Texture spriteSheet1, int health, boolean isPlayer){
		super(x, y, direction, speed, spriteSheet1, health, isPlayer);
	}
	
	//override
	public void respondToKeys(){
		
	}
}
