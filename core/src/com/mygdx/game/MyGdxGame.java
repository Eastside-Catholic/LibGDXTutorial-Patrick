package com.mygdx.game;

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
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MyGdxGame extends ApplicationAdapter implements ApplicationListener , InputProcessor {
	
	SpriteBatch batch;
	BitmapFont font;
	Texture img, img2;
	Sprite pew;
	float pewx = 50, pewy = 50;
	float speed = 5;
	
	@Override
	public void create () {
		Gdx.graphics.setDisplayMode(1720, 850, false);
		batch = new SpriteBatch();
		//font = new BitmapFont();
		//font.setColor(Color.CYAN);
		img2 = new Texture("download.jpg");
		pew = new Sprite(img2);
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		pew.setPosition(pewx, pewy);
		batch.begin();
		pew.draw(batch);
		//pew.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		batch.end();
	}
	
	@Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        img2.dispose();
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

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.LEFT){
            pewx = pewx - speed;
        }
        if(keycode == Keys.RIGHT){
        	pewx = pewx + speed;
        }
        if(keycode == Keys.UP){
        	pewy = pewy + speed;
        }
        if(keycode == Keys.DOWN){
        	pewy = pewy - speed;
        }
        if(keycode == Keys.W){
            speed++;
        }
        if(keycode == Keys.S){
            speed--;
        }
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		pewx = screenX - pew.getWidth()/2;
		pewy = Gdx.graphics.getHeight() - screenY - pew.getHeight()/2;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		pewx = screenX - pew.getWidth()/2;
		pewy = Gdx.graphics.getHeight() - screenY - pew.getHeight()/2;
		//pew.setPosition(screenX, screenY);
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
}
