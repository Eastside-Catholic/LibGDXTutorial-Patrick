package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Enemy extends GameEntity{
	boolean isPlayer = false;
	float timeSummary;
	float timeGoal = (float)(4*Math.random()) +(float)3.5;
	Texture greenBullet = new Texture("bullet-green-icon.png");
	final static float POINTFOURONE = (float) 0.41421, TWOPOINTFOUR = (float)2.414; 
	Enemy(float x, float y, int direction, float speed, Texture spriteSheet1, int health, boolean isPlayer){
		super(x, y, direction, speed, spriteSheet1, health, isPlayer);
	}
	
	@Override
	public void respondToKeys(){
		timeSummary += Gdx.graphics.getDeltaTime();
		if(timeSummary > timeGoal){
			makeBullet();
			timeSummary = 0;
			timeGoal = (float)(4*Math.random()) +(float)3.5;
		}
		setAllDirectionsFalse();
		int i = 0;
		float x1 = -1, x2 = -1, y1 = -1, y2 = -1;//set to impossible values
		float targetXCoord = 0, targetYCoord = 0;
		boolean player1Exists = false, player2Exists = false;
		for(GameEntity e: MyGdxGame.entities){
			if(e.isPlayer && !e.dead){
				if(i==0){
					x1 = e.x;
					y1 = e.y;
					player1Exists = true;
					//System.out.println("1 player found");
				}
				if(i==1){
					x2 = e.x;
					y2 = e.y;
					player2Exists = true;
					//System.out.println("2 player found");
				}
				i++;
			}
		}
		if(!player1Exists && !player2Exists){
			targetXCoord = x;
			targetYCoord = y - 1;
			MyGdxGame.allPlayersKilled = true;
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
		float diffX = targetXCoord - x;//0
		float diffY = targetYCoord - y;//1
		float slope = diffY/diffX;		

		
		//find the 8-way direction closest to the bullet.
		if(slope < TWOPOINTFOUR && slope > POINTFOURONE && diffY < 0){
			movingDownLeft = true;
		}
		else if(slope > -TWOPOINTFOUR && slope < -POINTFOURONE && diffY > 0){
			movingUpLeft = true;
		}
		else if(slope > -TWOPOINTFOUR && slope < -POINTFOURONE && diffY < 0){
			movingDownRight = true;
		}
		else if(slope < POINTFOURONE && slope > -POINTFOURONE && diffX > 0){
			movingRight = true;
		}
		else if(slope > -POINTFOURONE && slope < POINTFOURONE && diffX < 0){
			movingLeft = true;
		}
		else if(slope < TWOPOINTFOUR && slope > POINTFOURONE && diffY > 0){
			movingUpRight = true;
		}
		else if((slope < -TWOPOINTFOUR || slope > TWOPOINTFOUR) && diffY > 0){
			movingUp =  true;
		}
		else if((slope > TWOPOINTFOUR || slope < -TWOPOINTFOUR) && diffY < 0){
			movingDown = true;
		}
	}
	
	public void makeBullet(){
		Pew newPew = new Pew(x, y, direction, 3, greenBullet, 1, true);
		MyGdxGame.bullets.add(newPew);
	}
}
