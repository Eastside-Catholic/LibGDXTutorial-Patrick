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
		timeSummary += Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT) && timeSummary > 0.3){
			makeBullet();
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
	
	@Override
	public void makeBullet(){
		//x, y, direction, speed, texture, damage, hurts players
		Pew newPew = new Pew(x, y, direction, 3, new Texture("bullet-blue-icon.png"), 1, false);
		MyGdxGame.bullets.add(newPew);
		if(tripleShot){
			int direction1, direction2;
			if(direction == 0){
				direction1 = 1;
				direction2 = 7;
			}else if (direction == 7){
				direction1 = 0;
				direction2 = 6;
			}else{
				direction1 = direction + 1;
				direction2 = direction -1;
			}
			Pew newPew2 = new Pew(x, y, direction1, 3, blueBullet, 1, false);
			Pew newPew3 = new Pew(x, y, direction2, 3, blueBullet, 1, false);
			MyGdxGame.bullets.add(newPew2);
			MyGdxGame.bullets.add(newPew3);
		}
	}
}
