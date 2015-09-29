package com.mygdx.game;
//Intersector.overlaps(bird.getBoundingCircle(), barUp);
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter implements ApplicationListener , InputProcessor {
	SpriteBatch batch;
	float delta;
	List<Pew> bullets = new ArrayList<Pew>();
	List<GameEntity> entities = new ArrayList<GameEntity>();
	
	
	
	
	@Override
	public void create () {
		//Texture spriteSheet1, spriteSheet2, bulletTexture;
		Gdx.graphics.setDisplayMode(1067, 600, false);
		batch = new SpriteBatch();
		Texture hero1Sheet = new Texture("Hero.jpg");
		Texture hero2Sheet = new Texture("Hero2.png");
		Texture enemy1Sheet = new Texture("Enemy1.jpg");
		
		Hero hero = new Hero(100, 100, 0, 1, hero1Sheet);
		entities.add(hero);//must be in position1
		Hero2 hero2 = new Hero2(150, 100, 0, 1, hero2Sheet);
		entities.add(hero2);//must be in position 2
		Enemy enemy1 = new Enemy(200, 200, 0, 1, enemy1Sheet);
		entities.add(enemy1);
		
		//Texture bulletTexture = new Texture("bullet.png");
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.9f, 0.6f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		checkKeysPressed();
		setDirection();
		calculatePosition();
		setDirection();
		updateBullets();
		batch.begin();
		drawBullets();
		drawEntities();
		batch.end();
	}
	
	public void checkKeysPressed(){
		for(GameEntity e: entities){
			e.respondToKeys();
		}
	}
	
	public void drawEntities(){
		for(GameEntity e: entities){
			batch.draw(e.getCurrentFrame(), e.x, e.y);
		}
	}
	
	public void calculatePosition(){
		for(GameEntity e: entities){
			e.updatePosition();
		}
	}
	
	public void setDirection(){
		for(GameEntity e: entities){
			e.updateDirection();
		}
	}
		
	public void updateBullets(){
		int i = 0;
		List<Integer> bulletsToDestroy = new ArrayList<Integer>();
		//This enhanced for loop updates the bullets, then notes the index of ones that should be removed.
		for(Pew tempPew: bullets){
			tempPew.update();
			if(!areCoordsInWindow(tempPew.x, tempPew.y))
				bulletsToDestroy.add(i);
			i++;
		}
		//Cycles through and removes them from the list.
		for(int x: bulletsToDestroy)
			bullets.remove(x);
	}
	
	public void drawBullets(){
		for(Pew tempPew: bullets){
			batch.draw(tempPew.bulletTexture, tempPew.x, tempPew.y);  
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
				Pew newPew = new Pew(entities.get(0).x, entities.get(0).y, entities.get(0).direction, 3);
				bullets.add(newPew);
			}
			if(keycode == Keys.SHIFT_RIGHT){
				Pew newPew = new Pew(entities.get(1).x, entities.get(1).y, entities.get(1).direction, 3);
				bullets.add(newPew);
			}
			return false;
		}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		/*hero.x = screenX;
		hero.y = Gdx.graphics.getHeight() - screenY - spriteSheet1.getHeight()/4;
		*/
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		/*hero.x = screenX;
		hero.y = Gdx.graphics.getHeight() - screenY - spriteSheet1.getHeight()/4;
		*/
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
