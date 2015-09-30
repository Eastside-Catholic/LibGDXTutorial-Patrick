package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Hero extends GameEntity{
	float timeSummary;
	boolean isPlayer = true;
	Hero(float x, float y, int direction, float speed, Texture spriteSheet1, int health, boolean isPlayer){
		super(x, y, direction, speed, spriteSheet1, health, isPlayer);
	}
	
	public ArrayList respondToKeys(ArrayList tempBulletsArray){
		timeSummary += Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && timeSummary > 0.3){
			tempBulletsArray = makeBullet(tempBulletsArray);
			timeSummary = 0;
		}
		setAllDirectionsFalse();
		moving = true;
		if(Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)){
			movingUpLeft = true;
			return tempBulletsArray;
		}else if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)){
			movingDownLeft = true;
			return tempBulletsArray;
		}else if(Gdx.input.isKeyPressed(Input.Keys.A)){
			movingLeft = true;	
			return tempBulletsArray;
		}else if(Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)){
			movingUpRight = true;
			return tempBulletsArray;
		}else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S)){
			movingDownRight = true;
			return tempBulletsArray;
		}else if(Gdx.input.isKeyPressed(Input.Keys.D)){
			movingRight = true;
			return tempBulletsArray;
		}else if(Gdx.input.isKeyPressed(Input.Keys.W)){
			movingUp = true;
			return tempBulletsArray;
		}else if(Gdx.input.isKeyPressed(Input.Keys.S)){
			movingDown = true;
			return tempBulletsArray;
		}
		moving = false;
		return tempBulletsArray;
	}
	
	public ArrayList makeBullet(ArrayList tempBulletsArray){
		Pew newPew = new Pew(x, y, direction, 3, new Texture("bullet-orange-icon.png"), 1, false);
		tempBulletsArray.add(newPew);
		return tempBulletsArray;
	}
}
