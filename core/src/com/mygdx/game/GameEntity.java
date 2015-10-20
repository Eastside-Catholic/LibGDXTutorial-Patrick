package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameEntity {
	public Rectangle rect;
	public boolean invincible;
	public float x, y, speed, tripleShotTimer, invincibleTimer;
	public int direction, health, maxHealth;
	public int extraLifeCount = 0;
	public boolean dead;
	public Animation animation;
	boolean moving, movingDown, movingDownLeft, movingLeft, movingUpLeft, movingUp, movingUpRight, movingRight, 
		movingDownRight;
	boolean isPlayer, tripleShot;
	final static int DOWN = 0, DOWNLEFT = 1, LEFT = 2, UPLEFT = 3, UP = 4, UPRIGHT = 5, RIGHT = 6, DOWNRIGHT = 7;
	TextureRegion currentFrame;	
	float frameTime = 0;
	
	//constructor used for all game entities
	GameEntity(float x, float y, int direction, float speed, Texture spriteSheet1, int health, boolean isPlayer){
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.speed = speed;
		this.health = health;
		this.isPlayer = isPlayer;
		maxHealth = health;
		rect = new Rectangle(x, y, 32, 32);
		
		//store all the animation frames
		downFrames[0] = new TextureRegion(spriteSheet1, 0, 0, 32, 32);
		downFrames[1] = new TextureRegion(spriteSheet1, 32, 0, 32, 32);
		downFrames[2] = new TextureRegion(spriteSheet1, 64, 0, 32, 32);
		downLeftFrames[0] = new TextureRegion(spriteSheet1, 96, 0, 32, 32);
		downLeftFrames[1] = new TextureRegion(spriteSheet1, 128, 0, 32, 32);
		downLeftFrames[2] = new TextureRegion(spriteSheet1, 160, 0, 32, 32);
		leftFrames[0] = new TextureRegion(spriteSheet1, 0, 32, 32, 32);
		leftFrames[1] = new TextureRegion(spriteSheet1, 32, 32, 32, 32);
		leftFrames[2] = new TextureRegion(spriteSheet1, 64, 32, 32, 32);
		upLeftFrames[0] = new TextureRegion(spriteSheet1, 96, 32, 32, 32);
		upLeftFrames[1] = new TextureRegion(spriteSheet1, 128, 32, 32, 32);
		upLeftFrames[2] = new TextureRegion(spriteSheet1, 160, 32, 32, 32);
		rightFrames[0] = new TextureRegion(spriteSheet1, 0, 64, 32, 32);
		rightFrames[1] = new TextureRegion(spriteSheet1, 32, 64, 32, 32);
		rightFrames[2] = new TextureRegion(spriteSheet1, 64, 64, 32, 32);
		downRightFrames[0] = new TextureRegion(spriteSheet1, 96, 64, 32, 32);
		downRightFrames[1] = new TextureRegion(spriteSheet1, 128, 64, 32, 32);
		downRightFrames[2] = new TextureRegion(spriteSheet1, 160, 64, 32, 32);
		upFrames[0] = new TextureRegion(spriteSheet1, 0, 96, 32, 32);
		upFrames[1] = new TextureRegion(spriteSheet1, 32, 96, 32, 32);
		upFrames[2] = new TextureRegion(spriteSheet1, 64, 96, 32, 32);
		upRightFrames[0] = new TextureRegion(spriteSheet1, 96, 96, 32, 32);
		upRightFrames[1] = new TextureRegion(spriteSheet1, 128, 96, 32, 32);
		upRightFrames[2] = new TextureRegion(spriteSheet1, 160, 96, 32, 32);
		deadFrames[0] = new TextureRegion(spriteSheet1, 96, 224, 32, 32);
		
		animation = new Animation(.10f, downFrames);
		tripleShot = false;
		invincible = false;
	}
	
	public void respondToKeys(){
		//Make sure to put this in the other classes
	}
	
	public void setInvincible(){
		//set invincible to true
		invincibleTimer = 0;
		invincible = true;
	}
	
	public void setTripleShot(){
		//set tripleshot to true
		tripleShotTimer = 0;
		tripleShot = true;
		
	}
	
	public void updateDirection(){
		//based on the movement direction set the direction and animation
		if(dead){
			setAllDirectionsFalse();
			animation = new Animation(.10f, deadFrames[0]);
		}else if(movingDown){
			direction = DOWN;
			animation = new Animation(.10f, downFrames);
		}else if(movingLeft){
			direction = LEFT;
			animation = new Animation(.10f, leftFrames);
		}else if(movingRight){
			direction = RIGHT;
			animation = new Animation(.10f, rightFrames);
		}else if(movingUp){
			direction = UP;
			animation = new Animation(.10f, upFrames);
		}else if(movingDownRight){
			direction = DOWNRIGHT;
			animation = new Animation(.10f, downRightFrames);
		}else if(movingDownLeft){
			direction = DOWNLEFT;
			animation = new Animation(.10f, downLeftFrames);
		}else if(movingUpLeft){
			direction = UPLEFT;
			animation = new Animation(.10f, upLeftFrames);
		}else if(movingUpRight){
			direction = UPRIGHT;
			animation = new Animation(.10f, upRightFrames);
		}
		
		//Find the frame for each direction to hold it still if it is not moving
		if(!moving){
			if(dead)
				animation = new Animation(.10f, deadFrames[0]);
			else if(direction == DOWN)
				animation = new Animation(.10f, downFrames[0]);
			else if(direction == LEFT)
				animation = new Animation(.10f, leftFrames[0]);
			else if(direction == RIGHT)
				animation = new Animation(.10f, rightFrames[0]);
			else if(direction == UP)
				animation = new Animation(.10f, upFrames[0]);
			else if(direction == DOWNRIGHT)
				animation = new Animation(.10f, downRightFrames[0]);
			else if(direction == DOWNLEFT)
				animation = new Animation(.10f, downLeftFrames[0]);
			else if(direction == UPLEFT)
				animation = new Animation(.10f, upLeftFrames[0]);
			else if(direction == UPRIGHT)
				animation = new Animation(.10f, upRightFrames[0]);
		}
	}
	
	public void updatePosition(){
		//actually update the coordinates of the entity based on the movement direction
		if(movingDown){
			attemptYCoordChange(-speed);
		}
		if(movingDownLeft){		
			attemptYCoordChange(-speed);
			attemptXCoordChange(-speed);
		}			
		if(movingLeft){
			attemptXCoordChange(-speed);
		}
		if(movingUpLeft){
			attemptYCoordChange(speed);
			attemptXCoordChange(-speed);
		}
		if(movingUp){
			attemptYCoordChange(speed);
		}
		if(movingUpRight){
			attemptYCoordChange(speed);
			attemptXCoordChange(speed);
		}
		if(movingRight){
			attemptXCoordChange(speed);
		}
		if(movingDownRight){
			attemptXCoordChange(speed);
			attemptYCoordChange(-speed);
		}
		rect.setPosition(x, y);
		tripleShotTimer += Gdx.graphics.getDeltaTime();
		invincibleTimer += Gdx.graphics.getDeltaTime();
		if(invincibleTimer > 10){
			invincible= false;
			invincibleTimer = 0;
		}
		if(tripleShotTimer > 10){
			tripleShot = false;
			tripleShotTimer = 0;
		}
	}
	
	public void makeBullet(Texture bullet){
		//this method is only called by players, and the enemy has a different, simple, makeBullet mathod
		//x, y, direction, speed, texture, damage, hurts players
		Pew newPew = new Pew(x, y, direction, 4, bullet, 1, false);
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
			Pew newPew2 = new Pew(x, y, direction1, 4, bullet, 1, false);
			Pew newPew3 = new Pew(x, y, direction2, 4, bullet, 1, false);
			MyGdxGame.bullets.add(newPew2);
			MyGdxGame.bullets.add(newPew3);
		}
	}
	
	public TextureRegion getCurrentFrame(){
		frameTime += Gdx.graphics.getDeltaTime();
		currentFrame = animation.getKeyFrame(frameTime, true);
		return currentFrame;
	}
	
	public void attemptYCoordChange(float y){
		//projects the y coordinate change and then changes it if within the screen
		float projectedY = this.y + y;
		if(projectedY >= 0 && projectedY <= Gdx.graphics.getHeight() - 32){
			this.y = projectedY;
		}
	}
	
	public void attemptXCoordChange(float x){
		//projects the x coordinate change and then changes it if within the screen
		float projectedX = this.x + x;
		if(projectedX >= 0 && projectedX <= Gdx.graphics.getWidth() -32){
			this.x = projectedX;
		}
	}
	
	public void setAllDirectionsFalse(){
		//set all movement to false
		movingDown = false;
		movingDownLeft = false;
		movingLeft = false;
		movingUpLeft = false;
		movingUp = false;
		movingUpRight = false;
		movingRight = false;
		movingDownRight = false;
	}
	
	//the arrays to store animations
	TextureRegion[] downFrames = new TextureRegion[3];
	TextureRegion[] upFrames = new TextureRegion[3];
	TextureRegion[] rightFrames = new TextureRegion[3];
	TextureRegion[] leftFrames = new TextureRegion[3];
	TextureRegion[] downLeftFrames = new TextureRegion[3];
	TextureRegion[] downRightFrames = new TextureRegion[3];
	TextureRegion[] upLeftFrames = new TextureRegion[3];
	TextureRegion[] upRightFrames = new TextureRegion[3];
	TextureRegion[] deadFrames = new TextureRegion[1];
	
}
