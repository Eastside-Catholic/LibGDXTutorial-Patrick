package com.mygdx.game;
//Intersector.overlaps(bird.getBoundingCircle(), barUp);
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter implements ApplicationListener , InputProcessor {
	SpriteBatch batch;
	float delta;
	public ArrayList<Pew> bullets = new ArrayList<Pew>();
	public ArrayList<GameEntity> entities = new ArrayList<GameEntity>();
	
	@Override
	public void create (){
		Gdx.graphics.setDisplayMode(1067, 600, false);
		batch = new SpriteBatch();
		Texture hero1Sheet = new Texture("Hero.jpg");
		Texture hero2Sheet = new Texture("Hero2.png");
		Texture enemy1Sheet = new Texture("Enemy1.jpg");
		
		Hero hero = new Hero(100, 100, 0, 1, hero1Sheet, 15, true);
		entities.add(hero);//must be in position1
		Hero2 hero2 = new Hero2(150, 100, 0, 1, hero2Sheet, 15, true);
		entities.add(hero2);//must be in position 2
		Enemy enemy1 = new Enemy(200, 200, 0, 1, enemy1Sheet, 1, false);
		entities.add(enemy1);
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.9f, 0.6f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		checkKeysPressed(); //Have each entity respond to any keys
		setDirection();		//Have each entity set its new direction, if applicable
		calculatePosition();//Have each entity update its position based on its new direction
		updateBullets();	//Have each bullet update its position based on its vector
		checkCollision();
		batch.begin();
		drawEntitiesAndBullets();//Draw all the things to the screen
		System.out.println("Entity size: " + entities.size() +"/nBullets size: " + bullets.size());
		batch.end();
	}
	
	public void checkKeysPressed(){
		for(GameEntity e: entities){
			bullets = e.respondToKeys(bullets);
		}
	}
	
	public void drawEntitiesAndBullets(){
		for(GameEntity e: entities){
			batch.draw(e.getCurrentFrame(), e.x, e.y);
		}
		for(Pew tempPew: bullets){
			batch.draw(tempPew.bulletTexture, tempPew.x, tempPew.y);  
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
	
	public void checkCollision(){
		int entityCounter = 0;
		int bulletCounter = 0;
		List<Integer> entitiesToRemove = new ArrayList<Integer>();
		List<Integer> bulletsToRemove = new ArrayList<Integer>();
		for(GameEntity ge: entities){
			for(Pew bullet: bullets){
				if(bullet.rect.overlaps(ge.rect)){
					System.out.println("collision with entity: " + entityCounter);
					if(!bullet.hurtPlayers && !ge.isPlayer){
						System.out.println("Entity " + entityCounter + " being injured.");
						ge.health -= bullet.damage;  
						if(ge.health <= 0){
							System.out.println("Death of an entity. Index of entity: " + entityCounter);
							entitiesToRemove.add(entityCounter);
						}
						bulletsToRemove.add(bulletCounter);//make sure to delete the bullet if it hits something
					}
				}
				bulletCounter++;
			}
			entityCounter++;
		}
		
		for(int x = bulletsToRemove.size() - 1; x >= 0; x--){
			bullets.remove(bulletsToRemove.get(x));
		}
		for(int x = entitiesToRemove.size() - 1; x >= 0; x--){
			entities.remove(entitiesToRemove.get(x));
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
		//Cycles through and removes them from the list from the top down
		for(int x = bulletsToDestroy.size() - 1; x >= 0; x--)
			bullets.remove(bulletsToDestroy.get(x));
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
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
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
