package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Pew{
	public float x, y, vectorX, vectorY, speed;
	final static int DOWN = 0, DOWNLEFT = 1, LEFT = 2, UPLEFT = 3, UP = 4, UPRIGHT = 5, RIGHT = 6, DOWNRIGHT = 7;
	Texture bulletTexture;
	
	Pew(float x, float y, int direction, float speed){
		this.x = x;
		this.y = y;
		this.speed = speed;
		vectorX = directionToXVector(direction) * speed;
		vectorY = directionToYVector(direction) * speed;
		bulletTexture = new Texture("bullet.png");
	}
	
	//Returns true if it updates successfully, or returns false if it travels out of bounds of the screen.
	public void update(){
		x += vectorX;
		y += vectorY;
	}
	
	public float directionToXVector(int direction){
		if(direction == DOWNLEFT || direction == LEFT || direction == UPLEFT)
			return -1;
		else if(direction == DOWNRIGHT || direction == RIGHT || direction == UPRIGHT)
			return 1;
		else
			return 0;
	}
	public float directionToYVector(int direction){
		if(direction == DOWNLEFT || direction == DOWN || direction == DOWNRIGHT)
			return -1;
		else if(direction == UPLEFT || direction == UP || direction == UPRIGHT)
			return 1;
		else
			return 0;
	}
}
