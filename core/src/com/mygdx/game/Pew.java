package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Pew{
	final int DOWN = 0, DOWNLEFT = 1, LEFT = 2, UPLEFT = 3, UP = 4, UPRIGHT = 5, RIGHT = 6, DOWNRIGHT = 7;
	public float x, y, vectorX, vectorY, speed;
	
	Pew(float x, float y, int direction, float speed){
		this.x = x;
		this.y = y;
		this.speed = speed;
		vectorX = MyGdxGame.directionToXVector(direction) * speed;
		vectorY = MyGdxGame.directionToYVector(direction) * speed;
	}
	
	//Returns true if it updates successfully, or returns false if it travels out of bounds of the screen.
	public void update(){
		x += vectorX;
		y += vectorY;
	}
}
