package com.mygdx.game;

public class Character {
	public float x, y, speed;
	public int direction;

	Character(){
		x = 100;
		y = 100;
		direction = 0;
		speed = 1;
	}
	
	Character(float inX, float inY, int inDirection, float speed){
		x = inX;
		y = inY;
		direction = inDirection;
		speed = 1;
	}
}
