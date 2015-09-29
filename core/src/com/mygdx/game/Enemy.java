package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Enemy extends GameEntity{
	Enemy(float x, float y, int direction, float speed, Texture spriteSheet1){
		super(x, y, direction, speed, spriteSheet1);
	}
	
	//override
	public void respondToKeys(){
		
	}
}
