package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Hero2 extends GameEntity {
	float timeSummary;
	boolean isPlayer = true;
	Hero2(float x, float y, int direction, float speed, Texture spriteSheet, int health, boolean isPlayer){
		super(x, y, direction, speed, spriteSheet, health, isPlayer);
	}
	
	//Override
	public ArrayList respondToKeys(ArrayList tempBulletsArray){
		timeSummary += Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT) && timeSummary > 0.3){
			tempBulletsArray = makeBullet(tempBulletsArray);
			timeSummary = 0;
		}
		setAllDirectionsFalse();
		moving = true;
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)){//make method to set them all to nothing, then chnage one of the like you have alreadyf
			movingUpLeft = true;
			return tempBulletsArray;
		}else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			movingDownLeft = true;
			return tempBulletsArray;
		}else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			movingLeft = true;	
			return tempBulletsArray;
		}else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)){
			movingUpRight = true;
			return tempBulletsArray;
		}else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			movingDownRight = true;
			return tempBulletsArray;
		}else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			movingRight = true;
			return tempBulletsArray;
		}else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			movingUp = true;
			return tempBulletsArray;
		}else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			movingDown = true;
			return tempBulletsArray;
		}
		moving = false;
		return tempBulletsArray;
	}
	
	public ArrayList makeBullet(ArrayList tempBulletsArray){
		Pew newPew = new Pew(x, y, direction, 3, new Texture("bullet-blue-icon.png"), 1, false);
		tempBulletsArray.add(newPew);
		return tempBulletsArray;
	}
}
