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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MyGdxGame extends ApplicationAdapter implements ApplicationListener , InputProcessor {
	
	SpriteBatch batch;
	Vector2 position = new Vector2(50, 50);
	float speed = 1, delta;
	Animation animation1;
	Texture spriteSheet1;
	TextureRegion currentFrame;
	TextureRegion[][] frames; //first row 2nd col
	boolean moving, movingDown, movingUp, movingRight, movingLeft;
	float frameTime = 0;
	
	final int DOWN = 0, LEFT = 1, RIGHT = 2, UP = 3, STOP = 4;
	int direction = 0, previousDirection = 0;
	
	
	@Override
	public void create () {
		Gdx.graphics.setDisplayMode(1000, 600, false);
		batch = new SpriteBatch();
		spriteSheet1 = new Texture("image.png");
		frames = TextureRegion.split(spriteSheet1, spriteSheet1.getWidth()/3, spriteSheet1.getHeight()/4);
		
		animation1 = new Animation(.10f, frames[0]);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		frameTime += Gdx.graphics.getDeltaTime();
		currentFrame = animation1.getKeyFrame(frameTime, true);
		
		Gdx.gl.glClearColor(0.9f, 0.6f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		setDirection();
		calculatePosition();
		batch.begin();
		//pew.draw(batch);
		batch.draw(currentFrame, position.x, position.y);
		batch.end();
	}
	
	

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.LEFT){
			movingLeft = true;
        }
        if(keycode == Keys.RIGHT){
        	movingRight = true;
        	position.x += speed;
        }
        if(keycode == Keys.UP){
        	movingUp = true;
        	position.y += speed;
        }
        if(keycode == Keys.DOWN){
        	movingDown = true;
        	position.y -= speed;
        }
        if(keycode == Keys.W){
            speed++;
        }
        if(keycode == Keys.S){
            speed--;
        }
		return false;
	}

	public void calculatePosition(){
		if(movingDown)
			position.y -= speed;
		if(movingLeft)
			position.x -= speed;
		if(movingRight)
			position.x += speed;
		if(movingUp)
			position.y += speed;
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
		else{
			animation1 = new Animation(.10f, frames[direction][0]);
			return;
		}
		animation1 = new Animation(.10f, frames[direction]);
	}
		
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.LEFT){
            movingLeft = false;
        }
        if(keycode == Keys.RIGHT){
        	movingRight = false;
        }
        if(keycode == Keys.UP){
        	movingUp = false;
        }
        if(keycode == Keys.DOWN){
        	movingDown = false;
        }
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		position.x = screenX;
		position.y = Gdx.graphics.getHeight() - screenY - spriteSheet1.getHeight()/4;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		position.x = screenX;
		position.y = Gdx.graphics.getHeight() - screenY - spriteSheet1.getHeight()/4;
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
}
