package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Pew{
	public float x, y, vectorX, vectorY, speed;
	final static int DOWN = 0, DOWNLEFT = 1, LEFT = 2, UPLEFT = 3, UP = 4, UPRIGHT = 5, RIGHT = 6, DOWNRIGHT = 7;
	public int damage;
	public boolean hurtPlayers;
	Rectangle rect;
	Texture bulletTexture;
	
	Pew(float x, float y, int direction, float speed, Texture texture, int damage, boolean hurtPlayers){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.damage = damage;
		this.hurtPlayers = hurtPlayers;
		vectorX = directionToXVector(direction) * speed;
		vectorY = directionToYVector(direction) * speed;
		bulletTexture = texture;
		rect = new Rectangle(x, y, 16, 16);
	}
	
	//Returns true if it updates successfully, or returns false if it travels out of bounds of the screen.
	public void update(){
		x += vectorX;
		y += vectorY;
		rect.setPosition(x, y);
	}
	
	public float directionToXVector(int direction){
		if(direction == DOWNLEFT || direction == LEFT || direction == UPLEFT)
			return -1;
		else if(direction == DOWNRIGHT || direction == RIGHT || direction == UPRIGHT)
			return 1;
		else
			return 0;
	}
	
	//Returns the x 
	public float directionToYVector(int direction){
		if(direction == DOWNLEFT || direction == DOWN || direction == DOWNRIGHT)
			return -1;
		else if(direction == UPLEFT || direction == UP || direction == UPRIGHT)
			return 1;
		else
			return 0;
	}
}
