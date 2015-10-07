package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Hero extends GameEntity{
	float timeSummary;
	boolean isPlayer = true;
	Hero(float x, float y, int direction, float speed, Texture spriteSheet1, int health, boolean isPlayer){
		super(x, y, direction, speed, spriteSheet1, health, isPlayer);
	}
	
	@Override
	public void respondToKeys(){
		timeSummary += Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && timeSummary > 0.3){
			makeBullet();
			timeSummary = 0;
		}
		setAllDirectionsFalse();
		moving = true;
		if(Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)){
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
	
	@Override
	public void makeBullet(){
		//x, y, direction, speed, texture, damage, hurts players
		Pew newPew = new Pew(x, y, direction, 3, new Texture("bullet-orange-icon.png"), 1, false);
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
			Pew newPew2 = new Pew(x, y, direction1, 3, new Texture("bullet-orange-icon.png"), 1, false);
			Pew newPew3 = new Pew(x, y, direction2, 3, new Texture("bullet-orange-icon.png"), 1, false);
			MyGdxGame.bullets.add(newPew2);
			MyGdxGame.bullets.add(newPew3);
		}
		
	}
}
