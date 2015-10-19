package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Hero extends GameEntity{
	float timeSummary;
	boolean isPlayer = true;
	Texture orangeBullet = new Texture("bullet-orange-icon.png");
	
	//Constructs it as a GameEntity
	Hero(float x, float y, int direction, float speed, Texture spriteSheet1, int health, boolean isPlayer){
		super(x, y, direction, speed, spriteSheet1, health, isPlayer);
	}
	
	@Override
	public void respondToKeys(){
		//respond to keys by setting direction and making bullets
		timeSummary += Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && timeSummary > 0.3){
			makeBullet(orangeBullet);
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
	}
}
