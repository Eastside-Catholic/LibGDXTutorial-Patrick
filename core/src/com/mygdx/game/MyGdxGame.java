package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.List;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter implements ApplicationListener{
	static SpriteBatch batch;
	public static boolean allPlayersKilled;
	public static List<Pew> bullets = new ArrayList<Pew>();
	public static List<GameEntity> entities = new ArrayList<GameEntity>();
	public static List<PowerUp> powerUps = new ArrayList<PowerUp>();
	public float r = 0.5f, g = 0.9f, b = 0.3f;
	private int i = 0;
	private BitmapFont font;
	PowerUp pwrup;
	Texture hero1Sheet, hero2Sheet, enemy1Sheet, lowHealth, medHealth, mostHealth, allHealth;
	TextureRegion rect;
	
	
	@Override
	public void create (){
		font = new BitmapFont();
		font.setColor(Color.ORANGE);
		Gdx.graphics.setDisplayMode(1067, 600, false);
		batch = new SpriteBatch();
		hero1Sheet = new Texture("Hero.jpg");
		hero2Sheet = new Texture("Hero2.png");
		enemy1Sheet = new Texture("Enemy1.jpg");
		lowHealth = new Texture("lowHealth.png");
		medHealth = new Texture("someHealth.png");
		mostHealth = new Texture("mostHealth.png");
		allHealth = new Texture("allHealth.png");
		resetWorld();
	}

	@Override
	public void render() {	
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!Gdx.input.isKeyPressed(Input.Keys.P)){
			checkKeysPressed(); //Have each entity respond to any keys
			setDirection();		//Have each entity set its new direction, if applicable
			calculatePosition();//Have each entity update its position based on its new direction
			updateBullets();	//Have each bullet update its position based on its vector
			checkCollision();
			randomGeneration();
		}
		batch.begin();
		drawGameElements();//Draw all the things to the screen
		if(allPlayersKilled){
			i++;
			font.draw(batch, "YOU DIED", 450, 275);
			r = .9f; g = 0f; b = 0f;
			if(i == 200)
				resetWorld();
		}
		batch.end();
	}
		
	public void randomGeneration(){
		int randInt = (int)(300 * Math.random());
		if(randInt == 150){
			randInt = (int)(4*Math.random());
			int randX = (int)((Gdx.graphics.getWidth()-32) * Math.random());
			int randY = (int)((Gdx.graphics.getHeight()-32)* Math.random());
			pwrup= new PowerUp(randInt, randX, randY);
			powerUps.add(pwrup);
		}
	}
	
	public void checkKeysPressed(){
		for(GameEntity e: entities){
			if(!e.dead)
				e.respondToKeys();
		}
	}
	
	public void drawGameElements(){
		for(GameEntity e: entities){
			batch.draw(e.getCurrentFrame(), e.x, e.y);
			float healthPercent = ((float)e.health/(float)e.maxHealth);
			Texture useTexture = allHealth;//by default
			if(healthPercent <= .35)
				useTexture = lowHealth;
			else if (healthPercent <= .65)
				useTexture = medHealth;
			else if (healthPercent <= .90)
				useTexture = mostHealth;
			batch.draw(useTexture, e.x+8, e.y-5, 16, 6);
		}
		for(Pew tempPew: bullets){
			batch.draw(tempPew.bulletTexture, tempPew.x, tempPew.y);  
		}
		for(PowerUp p: powerUps){
			batch.draw(p.texture, p.x, p.y, 32, 32);
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
		int powerUpCounter = 0;
		List<Integer> entitiesToRemove = new ArrayList<Integer>();
		List<Integer> bulletsToRemove = new ArrayList<Integer>();
		List<Integer> powerUpsToRemove = new ArrayList<Integer>();
		for(GameEntity ge: entities){
			bulletCounter = 0;
			for(Pew bullet: bullets){
				if(bullet.rect.overlaps(ge.rect)){
					if((!bullet.hurtPlayers && !ge.isPlayer) || (bullet.hurtPlayers && ge.isPlayer)){
						if(!ge.invincible){
							ge.health -= bullet.damage;  
							if(ge.health <= 0){
								if(ge.isPlayer)
									ge.dead = true;
								else
									entitiesToRemove.add(entityCounter);
							}
							bulletsToRemove.add(bulletCounter);
						}
					}
				}
				bulletCounter++;
			}
			powerUpCounter = 0;
			for(PowerUp p: powerUps){
				if(p.rect.overlaps(ge.rect)){
					if(ge.isPlayer){
						if(p.id == 0){ //Revive
							System.out.println("revive");
							powerUpsToRemove.add(powerUpCounter);
						}else if (p.id == 1){ //Triple shot
							ge.setTripleShot();
							powerUpsToRemove.add(powerUpCounter);
						}else if(p.id == 2){ //Invincible
							ge.setInvincible();
							powerUpsToRemove.add(powerUpCounter);
						}else if(p.id == 3){ //Freeze
							System.out.println("freeze");
							powerUpsToRemove.add(powerUpCounter);
						}
					}
				}
				powerUpCounter++;
			}
			entityCounter++;
		}
		
		for(int x = bulletsToRemove.size() - 1; x >= 0; x--){
			try{
			bullets.remove(bulletsToRemove.get(x).intValue());
			}catch(Exception e){
				System.out.println("You know what you need to fix. Enemies should not be able to become one.");
			}
		}
		for(int x = entitiesToRemove.size() - 1; x >= 0; x--){
			try{
				entities.remove(entitiesToRemove.get(x).intValue());
			}catch(Exception e){
				
			}
		}
		
		for(int x = powerUpsToRemove.size() - 1; x >=0; x--){
			try{
			powerUps.remove(powerUpsToRemove.get(x).intValue());
			}catch(Exception e){
				System.out.println("error with removing powerups");
			}
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
			bullets.remove(bulletsToDestroy.get(x).intValue());
	}

	public boolean areCoordsInWindow(float x, float y){
		if(x < 0 || x > Gdx.graphics.getWidth() || y < 0 || y > Gdx.graphics.getHeight())
			return false;
		else
			return true;
	}
	
	public void resetWorld(){
		entities.clear();
		bullets.clear();
		powerUps.clear();
		//Entity constructor is x, y, direction number, speed, sprite sheet, health, isPlayer
		Hero hero = new Hero(100, 100, 0, 1, hero1Sheet, 10, true);
		entities.add(hero);
		Hero2 hero2 = new Hero2(150, 100, 0, 1, hero2Sheet, 10, true);
		entities.add(hero2);
		PowerUp pwr = new PowerUp(2, 400, 200);
		powerUps.add(pwr);
		allPlayersKilled = false;
		i =  0; r = .5f; g = .9f; b = .3f;
		int randX, randY;
		for(int x = 0; x < 5; x++){
			randX =(int)(Math.random() * (Gdx.graphics.getWidth() - 32));
			randY =(int)(Math.random() * (Gdx.graphics.getHeight() - 32));
			Enemy enemy1 = new Enemy(randX, randY, 0, (float)0.5, enemy1Sheet, 23, false);
			entities.add(enemy1);
		}
	}
}
