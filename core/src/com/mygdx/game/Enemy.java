package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Enemy extends GameEntity{
	boolean isPlayer = false;
	float timeSummary;
	Enemy(float x, float y, int direction, float speed, Texture spriteSheet1, int health, boolean isPlayer){
		super(x, y, direction, speed, spriteSheet1, health, isPlayer);
	}
	
	@Override
	public void respondToKeys(){
		timeSummary += Gdx.graphics.getDeltaTime();
		if(timeSummary > 5){
			makeBullet();
			timeSummary = 0;
		}
		int i = 0;
		float x1 = -1, x2 = -1, y1 = -1, y2 = -1;//set to impossible values
		float targetXCoord = 0, targetYCoord = 0;
		boolean player1Exists = false, player2Exists = false;
		for(GameEntity e :MyGdxGame.entities){
			if(e.isPlayer){
				if(x==0){
					x1 = e.x;
					y1 = e.y;
					player1Exists = true;
				}
				if(x==1){
					x2 = e.x;
					y2 = e.y;
					player2Exists = true;
				}
				i++;
			}
		}
		if(!player1Exists && !player2Exists){
			targetXCoord = x;
			targetYCoord = y + 1;
		}
		if(player1Exists && !player2Exists){
			targetXCoord = x1;
			targetYCoord = y1;
		}
		if(player1Exists && player2Exists){
			float distanceTo1 = (float)Math.sqrt((x1 - x)*(x1 - x) + (y1 - y)*(y1 - y));
			float distanceTo2 = (float)Math.sqrt((x2 - x)*(x2 - x) + (y2 - y)*(y2 - y));
			if(distanceTo1 > distanceTo2){
				targetXCoord = x2;
				targetYCoord = y2;
			}else{
				targetXCoord = x1;
				targetYCoord = y1;
			}
		}
		float diffX = targetXCoord - x;
		float diffY = targetYCoord - y;
		
		
	}
	
	public void makeBullet(){
		Pew newPew = new Pew(x, y, direction, 3, new Texture("bullet-green-icon.png"), 1, true);
		MyGdxGame.bullets.add(newPew);
	}
}
