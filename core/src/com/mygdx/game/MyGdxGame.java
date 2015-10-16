package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

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

public class MyGdxGame extends ApplicationAdapter implements ApplicationListener{
	static SpriteBatch batch;
	public static boolean allPlayersKilled;
	public static List<Pew> bullets = new ArrayList<Pew>();
	public static List<GameEntity> entities = new ArrayList<GameEntity>();
	public static List<PowerUp> powerUps = new ArrayList<PowerUp>();
	public float r = 0.5f, g = 0.9f, b = 0.3f;
	public int score = 0;
	private int i = 0;
	private BitmapFont font, font2;
	PowerUp pwrup;
	Texture hero1Sheet, hero2Sheet, enemy1Sheet, lowHealth, medHealth, mostHealth, allHealth, 
	bubbleShield, smallHeart;
	TextureRegion rect;
	
	
	@Override
	public void create (){
		font = new BitmapFont();
		font.setColor(Color.ORANGE);
		font2 = new BitmapFont();
		font2.setColor(Color.BLACK);
		Gdx.graphics.setDisplayMode(1067, 600, false); //set window size
		batch = new SpriteBatch();
		hero1Sheet = new Texture("Hero.jpg"); //load textures
		hero2Sheet = new Texture("Hero2.png");
		enemy1Sheet = new Texture("Enemy1.jpg");
		lowHealth = new Texture("lowHealth.png");
		medHealth = new Texture("someHealth.png");
		mostHealth = new Texture("mostHealth.png");
		allHealth = new Texture("allHealth.png");
		bubbleShield = new Texture("playershield.png");
		smallHeart = new Texture("heart_small.png");
		resetWorld();
	}

	@Override
	public void render() {	
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!Gdx.input.isKeyPressed(Input.Keys.P)){
			allRespondToKeys();   //Have each entity respond to any keys
			allUpdateDirection(); //Have each entity set its new direction, if applicable
			allUpdatePosition();  //Have each entity update its position based on its new direction
			updateBullets();	  //Have each bullet update its position based on its vector
			checkCollision();	  //Go through the hit-boxes and see if they collide and act accordingly
			randomGeneration();   //Generate power-ups and enemies
		}
		batch.begin();
		drawGameElements();//Draw all the things to the screen
		if(allPlayersKilled){ //Displays the YOU DIED screen for a short time, then reset the world.
			i++;
			font.draw(batch, "YOU DIED", 550, 300);
			r = .9f; g = 0f; b = 0f;
			if(i == 200)
				resetWorld();
		}
		batch.end();
	}
	
	public void spawnEnemy(int num){
		int side, randX = 0, randY = 0;
		final int  LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
		//for every number of entities that you call to spawn
		for(int x = num; x > 0; x--){
			side = (int)(Math.random() * 4);//choose a random side to spawn on
			if(side == LEFT){
				randX = 0;
				randY =(int)(Math.random() * (Gdx.graphics.getHeight() - 32));
			}else if (side == UP){
				randX =(int)(Math.random() * (Gdx.graphics.getWidth() - 32));     //find out the random x and y for
				randY = Gdx.graphics.getHeight()-32;							  //each side of the wall
			}else if (side == RIGHT){
				randX = Gdx.graphics.getWidth() - 32;
				randY =(int)(Math.random() * (Gdx.graphics.getHeight() - 32));
			}else if (side == DOWN){
				randX =(int)(Math.random() * (Gdx.graphics.getWidth() - 32));
				randY = 0;
			}
			//Entity constructor is x, y, direction number, speed, sprite sheet, health, isPlayer
			Enemy enemy1 = new Enemy(randX, randY, 0, (float).5, enemy1Sheet, 5, false);
			entities.add(enemy1);
		}
	}
	
	public void drawGameElements(){
		//start by drawing all entities with their health bar, extra lives, and invincible bubble, as applicable.
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
			if(e.extraLifeCount >= 1){
				batch.draw(smallHeart, e.x+7, e.y+32, 8, 8);
			}
			if(e.extraLifeCount == 2){
				batch.draw(smallHeart, e.x+18, e.y+32, 8, 8);
			}
			if(e.invincible)
				batch.draw(bubbleShield, e.x -5, e.y-5, 42, 42);//slow???
		}
		//draw each bullet using its own texture
		for(Pew tempPew: bullets){
			batch.draw(tempPew.bulletTexture, tempPew.x, tempPew.y);  
		}
		//draw each power-up using its own texture
		for(PowerUp p: powerUps){
			batch.draw(p.texture, p.x, p.y, 32, 32);
		}
		//draw the scoreboard
		font2.draw(batch, "Score: ", 4, Gdx.graphics.getHeight() - 5);
		font2.draw(batch, new Integer(score).toString(), 4, Gdx.graphics.getHeight() - 20);
	}
	
	//call each entity to respond to keys. the method is blank by default and requires an override to do anything
	public void allRespondToKeys(){
		for(GameEntity e: entities){
			if(!e.dead)
				e.respondToKeys();
		}
	}
	
	//call each entity's update direction method, which also updates its animation to be displayed
	public void allUpdateDirection(){
		for(GameEntity e: entities){
			e.updateDirection();
		}
	}
	
	//calls each entity's update position method which updates the position based on the direction and if moving
	public void allUpdatePosition(){
		for(GameEntity e: entities){
			e.updatePosition();
		}
	}
	
	//calls each bullet to update its own position and if it has traveled out of screen, remove it.
	public void updateBullets(){
		for(int bulletCounter = bullets.size()-1; bulletCounter >=0; bulletCounter--){
			Pew tempPew = bullets.get(bulletCounter);
			tempPew.update(); //updates coords
			if(!areCoordsInWindow(tempPew.x, tempPew.y))
				bullets.remove(bulletCounter);
		}
	}
	
	//This lengthly method goes through and looks at overlapping hit-boxes and responds or ignores the collision
	public void checkCollision(){
		//For every game entity, ge, do this
		//eachEntity: //this is called the each entity loop so that if the eni
		for(int entityCounter = entities.size()-1; entityCounter >=0; entityCounter--){
			GameEntity ge = entities.get(entityCounter);
			//for every bullet, for each game entity, do this
			eachEntity: //this is called the each entity loop so that if an enemy is removed, it
						//immediately stops looking at the bullets relating to the enemy.
			for(int bulletCounter = bullets.size()-1; bulletCounter >= 0; bulletCounter--){
				Pew bullet = bullets.get(bulletCounter);
				//if the bullet is hitting the game entity
				if(bullet.rect.overlaps(ge.rect)){
					//if the bullet hurts the players and it is a player, or if it does not hurt 
					//players and it is not a player, then continue to look at the collision
					if((!bullet.hurtPlayers && !ge.isPlayer) || (bullet.hurtPlayers && ge.isPlayer)){
						//only continue if the entity is not invincible
						if(!ge.invincible && !ge.dead){
							//hurt the entity
							ge.health -= bullet.damage;  
							//if the health is less than zero, continue
							if(ge.health <= 0){
								if(ge.isPlayer){
									//if they have an extra life, take it from them and 
									//return them to their original health
									if(ge.extraLifeCount > 0){
										ge.extraLifeCount--;
										ge.health = ge.maxHealth;
									}else
										ge.dead = true; //they die, locking them out of controls and movement.
									score -= 50; //subtract 50 points for dying
								}else{ //if the entity is not a player...
									entities.remove(entityCounter);
									score += 10;
									break eachEntity; //since the entity was removed, stop looking at it and
														  //bullets and go on to the next entity.
								}
							}
							//since the entity has been hurt by the bullet, even if not killed, remove bullet.
							bullets.remove(bulletCounter);
						}
					}
				}// end looking at their overlapping rectangles 
			} // end looking at each bullet loop
			for(int powerUpCounter = powerUps.size()-1; powerUpCounter >= 0; powerUpCounter--){ //for every powerup
				PowerUp p = powerUps.get(powerUpCounter);
				if(p.rect.overlaps(ge.rect)){
					if(ge.isPlayer && !ge.dead){ //if the powerup overlaps the entity box and the entity is a player
						if(p.id == 0){ //Revive
							if(ge.extraLifeCount < 2){ //Only add if you have less than two extra lives. 
								ge.extraLifeCount++;   //only remove if used
								powerUps.remove(powerUpCounter);
							}
						}else if (p.id == 1){ //Triple shot
							ge.setTripleShot();
							powerUps.remove(powerUpCounter);
						}else if(p.id == 2){ //Invincible
							ge.setInvincible();
							powerUps.remove(powerUpCounter);
						}else if(p.id == 3){ //Freeze
							System.out.println("freeze");
							powerUps.remove(powerUpCounter);
						}
					}
				}
			}
			for(GameEntity ge2: entities){ 			//goes through and see if it is a player touching dead player, 
				if(ge.rect.overlaps(ge2.rect)){		//then see if it should revive them
					if((ge.isPlayer && !ge.dead) && (ge2.isPlayer && ge2.dead)){
						if(ge.extraLifeCount > 0){
							ge.extraLifeCount--;
							ge2.dead = false;
							ge2.health = ge2.maxHealth;
						}
					}
				}
			}
		}		
	}
		
	public void randomGeneration(){
		int randInt = (int)(300 * Math.random());
		if(randInt == 150){
			randInt = (int)(3*Math.random());
			int randX = (int)((Gdx.graphics.getWidth()-32) * Math.random());
			int randY = (int)((Gdx.graphics.getHeight()-32)* Math.random());
			pwrup= new PowerUp(randInt, randX, randY);
			powerUps.add(pwrup);
		}
		randInt = (int)(120 * Math.random());
		if(randInt == 30){
			spawnEnemy(1);
		}
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
		score = 0;
		//Entity constructor is x, y, direction number, speed, sprite sheet, health, isPlayer
		Hero hero = new Hero(100, 100, 0, 1, hero1Sheet, 7, true);
		entities.add(hero);
		Hero2 hero2 = new Hero2(150, 100, 0, 1, hero2Sheet, 7, true);
		entities.add(hero2);
		PowerUp pwr = new PowerUp(2, 400, 200);
		powerUps.add(pwr);
		allPlayersKilled = false;
		i =  0; r = .5f; g = .9f; b = .3f;
		spawnEnemy(5);
	}
}