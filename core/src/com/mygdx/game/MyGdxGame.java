package com.mygdx.game;
//Intersector.overlaps(bird.getBoundingCircle(), barUp);
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.*;
import java.util.List;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter implements ApplicationListener , InputProcessor {
	
	SpriteBatch batch;
	Character hero;
	float speed = 2, delta;
	List<Pew> bullets = new ArrayList<Pew>();
	Animation animation1;
	Texture spriteSheet1, bulletTexture;
	TextureRegion currentFrame;
	TextureRegion[][] frames; //first row 2nd column
	boolean moving, movingDown, movingDownLeft, movingLeft, movingUpLeft, movingUp, movingUpRight, movingRight, movingDownRight;
	float frameTime = 0;
	
	final static int DOWN = 0;
	final static int DOWNLEFT = 1;
	final static int LEFT = 2;
	final static int UPLEFT = 3;
	final static int UP = 4;
	final static int UPRIGHT = 5;
	final static int RIGHT = 6;
	final static int DOWNRIGHT = 7;
	int direction = 0, previousDirection = 0;
	
	
	@Override
	public void create () {
		Gdx.graphics.setDisplayMode(1000, 600, false);
		batch = new SpriteBatch();
		hero = new Character();
		spriteSheet1 = new Texture("image.png");
		bulletTexture = new Texture("bullet.png");
		frames = TextureRegion.split(spriteSheet1, spriteSheet1.getWidth()/3, spriteSheet1.getHeight()/4);
		
		animation1 = new Animation(.10f, frames[0]);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render() {
		frameTime += Gdx.graphics.getDeltaTime();
		currentFrame = animation1.getKeyFrame(frameTime, true);
		
		Gdx.gl.glClearColor(0.9f, 0.6f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		getKeysPressed();
		setDirection();
		calculatePosition();
		setDirection();
		updateBullets();
		batch.begin();
		drawBullets();
		batch.draw(currentFrame, hero.x, hero.y);
		batch.end();
	}
	
	public void getKeysPressed(){
		setAllDirectionsFalse();
		moving = true;
		if(Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)){//make method to set them all to nothing,  then chnage one of the like you have alreadyf
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

	public void calculatePosition(){
		if(movingDown)
			hero.y -= hero.speed;
		if(movingDownLeft){		
			hero.y -= hero.speed;
			hero.x -= hero.speed;
		}			
		if(movingLeft)
			hero.x -= hero.speed;
		if(movingUpLeft){
			hero.y += hero.speed;
			hero.x -= hero.speed;
		}
		if(movingUp)
			hero.y += hero.speed;
		if(movingUpRight){
			hero.y += hero.speed;
			hero.x += hero.speed;
		}
		if(movingRight)
			hero.x += hero.speed;
		if(movingDownRight){
			hero.x += hero.speed;
			hero.y -= hero.speed;
		}
		
	}
	
	public void setDirection(){
		if(movingDown)
			direction = DOWN;
		else if(movingLeft)
			direction = LEFT;
		else if(movingRight)
			direction = RIGHT;
		else if(movingUp)
			direction = UP;
		else if(movingDownRight)
			direction = DOWNRIGHT;
		else if(movingDownLeft)
			direction = DOWNLEFT;
		else if(movingUpLeft)
			direction = UPLEFT;
		else if(movingUpRight)
			direction = UPRIGHT;
		else{
			//animation1 = new Animation(.10f, frames[direction][0]);
			//return;
		}
		if(moving)
			animation1 = new Animation(.10f, frames[0]);
	}
		
	public void updateBullets(){
		int i = 0;
		List<Integer> bulletsToDestroy = new ArrayList<Integer>();
		//This enhanced for loop updates the bullets, then notes the index of ones that should be removed.
		for(Pew tempPew: bullets){
			tempPew.update();
			if(!areCoordsInWindow(tempPew.x, tempPew.y)){
				bulletsToDestroy.add(i);
			}
			i++;
		}
		//Cycles through and removes them from the list.
		for(int x: bulletsToDestroy){
			bullets.remove(x);
		}
	}
	
	public void drawBullets(){
		for(Pew tempPew: bullets){
			batch.draw(bulletTexture, tempPew.x, tempPew.y);  
		}
	}
	
	public boolean areCoordsInWindow(float x, float y){
		if(x < 0 || x > Gdx.graphics.getWidth() || y < 0 || y > Gdx.graphics.getHeight())
			return false;
		else
			return true;
	}
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
		public boolean keyDown(int keycode) {
			if(keycode == Keys.SPACE){
				Pew newPew = new Pew(hero.x, hero.y, direction, 3);
				bullets.add(newPew);
			}
			return false;
		}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		hero.x = screenX;
		hero.y = Gdx.graphics.getHeight() - screenY - spriteSheet1.getHeight()/4;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		hero.x = screenX;
		hero.y = Gdx.graphics.getHeight() - screenY - spriteSheet1.getHeight()/4;
		return true;	
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
    public void dispose() {
        batch.dispose();
    }
	
	 @Override
	 public void resize(int width, int height) {
	 }
	
	@Override
    public void resume() {
    }
	
	@Override
    public void pause() {
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
	public static float directionToXVector(int direction){
		if(direction == DOWNLEFT || direction == LEFT || direction == UPLEFT)
			return -1;
		else if(direction == DOWNRIGHT || direction == RIGHT || direction == UPRIGHT)
			return 1;
		else
			return 0;
	}
	public static float directionToYVector(int direction){
		if(direction == DOWNLEFT || direction == DOWN || direction == DOWNRIGHT)
			return -1;
		else if(direction == UPLEFT || direction == UP || direction == UPRIGHT)
			return 1;
		else
			return 0;
	}
	
}
