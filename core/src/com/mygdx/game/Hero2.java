package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Hero2 extends GameEntity {
	float timeSummary;
	boolean isPlayer = true;
	Texture blueBullet = new Texture("bullet-blue-icon.png");
	Hero2(float x, float y, int direction, float speed, Texture spriteSheet, int health, boolean isPlayer){
		super(x, y, direction, speed, spriteSheet, health, isPlayer);
	}
	
	@Override
	public void respondToKeys(){
		//make bullets and set the direction based on the keys that are pressed
		timeSummary += Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT) && timeSummary > 0.3){
			makeBullet(blueBullet);
			timeSummary = 0;
		}
		setAllDirectionsFalse();
		moving = true;
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)){//make method to set them all to nothing, then chnage one of the like you have alreadyf
			movingUpLeft = true;
			return;
		}else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			movingDownLeft = true;
			return;
		}else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			movingLeft = true;	
			return;
		}else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)){
			movingUpRight = true;
			return;
		}else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			movingDownRight = true;
			return;
		}else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			movingRight = true;
			return;
		}else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			movingUp = true;
			return;
		}else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			movingDown = true;
			return;
		}
		moving = false;
	}
}
