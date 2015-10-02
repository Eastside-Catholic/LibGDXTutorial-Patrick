package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameEntity {
	public Rectangle rect;
	public float x, y, speed;
	public int direction, health;
	public Animation animation;
	boolean moving, movingDown, movingDownLeft, movingLeft, movingUpLeft, movingUp, movingUpRight, movingRight, movingDownRight;
	boolean isPlayer;
	final static int DOWN = 0, DOWNLEFT = 1, LEFT = 2, UPLEFT = 3, UP = 4, UPRIGHT = 5, RIGHT = 6, DOWNRIGHT = 7;
	TextureRegion currentFrame;	
	float frameTime = 0;
	
	GameEntity(float x, float y, int direction, float speed, Texture spriteSheet1, int health, boolean isPlayer){
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.speed = speed;
		this.health = health;
		this.isPlayer = isPlayer;
		rect = new Rectangle(x, y, 32, 32);
		
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
		
		animation = new Animation(.10f, downFrames);
	}
	
	public void respondToKeys(){
		//Make sure to put this in the other classes
	}
	
	public void updateDirection(){
		if(movingDown){
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
		
		//Find the frame for each direction to hold it still
		if(!moving){
			if(direction == DOWN)
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
	}
	
	public void makeBullet(){
		//Be sure to fill this out if you want the entity to make the bullets!
	}
	
	public TextureRegion getCurrentFrame(){
		frameTime += Gdx.graphics.getDeltaTime();
		currentFrame = animation.getKeyFrame(frameTime, true);
		return currentFrame;
	}
	
	public void attemptYCoordChange(float y){
		float projectedY = this.y + y;
		if(projectedY >= 0 && projectedY <= Gdx.graphics.getHeight() - 32)
			this.y = projectedY;
	}
	
	public void attemptXCoordChange(float x){
		float projectedX = this.x + x;
		if(projectedX >= 0 && projectedX <= Gdx.graphics.getWidth() -32)
			this.x = projectedX;
	}
	
	public void setAllDirectionsFalse(){
		movingDown = false;
		movingDownLeft = false;
		movingLeft = false;
		movingUpLeft = false;
		movingUp = false;
		movingUpRight = false;
		movingRight = false;
		movingDownRight = false;
	}
	
	
	TextureRegion[] downFrames = new TextureRegion[3];
	TextureRegion[] upFrames = new TextureRegion[3];
	TextureRegion[] rightFrames = new TextureRegion[3];
	TextureRegion[] leftFrames = new TextureRegion[3];
	TextureRegion[] downLeftFrames = new TextureRegion[3];
	TextureRegion[] downRightFrames = new TextureRegion[3];
	TextureRegion[] upLeftFrames = new TextureRegion[3];
	TextureRegion[] upRightFrames = new TextureRegion[3];
}
