package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Pew{
	public float x, y, vectorX, vectorY, speed;
	Pew(float inX, float inY, float inVectorX, float inVectorY, float inSpeed){
		x = inX;
		y = inY;
		vectorX = inVectorX;
		vectorY = inVectorY;
		speed = inSpeed;
	}
	
	//Returns true if it updates succesfully, or returns false if it travels out of bounds of the screen.
	public void update(){
		x += vectorX;
		y += vectorY;
	}
}
