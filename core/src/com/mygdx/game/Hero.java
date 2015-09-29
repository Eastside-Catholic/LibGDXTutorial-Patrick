package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Hero extends GameEntity{
	Hero(float x, float y, int direction, float speed, Texture spriteSheet1){
		super(x, y, direction, speed, spriteSheet1);
	}
	
	public void respondToKeys(){
		setAllDirectionsFalse();
		moving = true;
		if(Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)){//make method to set them all to nothing, then chnage one of the like you have alreadyf
			movingUpLeft = true;
			return;
		}else if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)){
			movingDownLeft = true;
			return;
		}else if(Gdx.input.isKeyPressed(Input.Keys.A)){
			movingLeft = true;	
			return;
		}else if(Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)){
			movingUpRight = true;
			return;
		}else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S)){
			movingDownRight = true;
			return;
		}else if(Gdx.input.isKeyPressed(Input.Keys.D)){
			movingRight = true;
			return;
		}else if(Gdx.input.isKeyPressed(Input.Keys.W)){
			movingUp = true;
			return;
		}else if(Gdx.input.isKeyPressed(Input.Keys.S)){
			movingDown = true;
			return;
		}
		moving = false;
		return;
	}

}
