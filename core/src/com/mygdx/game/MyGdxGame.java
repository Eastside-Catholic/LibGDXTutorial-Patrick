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
	public static int score = 0;
	private int i = 0;
	private BitmapFont font, font2;
	PowerUp pwrup;
	Texture hero1Sheet, hero2Sheet, enemy1Sheet, lowHealth, medHealth, mostHealth, allHealth, 
	bubbleShield, smallHeart, background, startBackground;
	TextureRegion rect;
	boolean paused;
	
	
	@Override
	public void create (){
		font = new BitmapFont();
		font.setColor(Color.RED);
		font2 = new BitmapFont();
		font2.setColor(Color.BLACK);
		Gdx.graphics.setDisplayMode(1067, 600, false); //set window size
		batch = new SpriteBatch();
		//load all textures that you are using. I do not own these textures! Please see Google Images.
		hero1Sheet = new Texture("Hero.jpg"); //load textures
		hero2Sheet = new Texture("Hero2.png");
		enemy1Sheet = new Texture("Enemy1.jpg");
		lowHealth = new Texture("lowHealth.png");
		medHealth = new Texture("someHealth.png");
		mostHealth = new Texture("mostHealth.png");
		allHealth = new Texture("allHealth.png");
		bubbleShield = new Texture("playershield.png");
		smallHeart = new Texture("heart_small.png");
		background = new Texture("6.png");
		startBackground = new Texture("startbackground.png");
		paused = true;
		resetWorld();
	}

	@Override
	public void render() {	
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(Gdx.input.isKeyPressed(Input.Keys.P)){
			paused = true; //pause the game
		}
		if(!paused){//if p is held, the game is paused
			allRespondToKeys();   //Have each entity respond to any keys
			allUpdateDirection(); //Have each entity set its new direction, if applicable
			allUpdatePosition();  //Have each entity update its position based on its new direction
			updateBullets();	  //Have each bullet update its position based on its vector
			checkCollision();	  //Go through the hit-boxes and see if they collide and act accordingly
			randomGeneration();   //Generate power-ups and enemies
		}
		batch.begin();
		//if paused, show the other background and not the game.
		if(paused){
			batch.draw(startBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				paused = false;
			}
		}else{
			batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //background texture
			drawGameElements();//Draw all the things to the screen
			if(allPlayersKilled){ //Displays the YOU DIED screen for a short time, then reset the world.
				i++;
				font.draw(batch, "YOU DIED", 500, 300);
				if(i == 200)
					resetWorld();
			}
		}
		batch.end();
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
	
	//This method goes through and looks at overlapping hit-boxes and responds or ignores the collision
	public void checkCollision(){
		//For every game entity, ge, do this
		for(int entityCounter = entities.size()-1; entityCounter >=0; entityCounter--){
			GameEntity ge = entities.get(entityCounter);
			boolean isAlive = true;
			//the following line deals with bullet collisions and health etc. returns false if the entity died
			isAlive = CollisionManager.entityBulletCheck(ge, entityCounter); 
			if(isAlive){
				CollisionManager.entityPowerUpCheck(ge); //check this entity with all the powerups
				CollisionManager.entityEntityCheck(ge); //check this entity with all the entities
			}
		}//end gameentity loop		
	}
		
	//randomly determine if there should be generation of a new enemy or powerup
	public void randomGeneration(){
		int randInt = (int)(400 * Math.random());
		if(randInt == 150){ //powerups
			randInt = (int)(3*Math.random());
			int randX = (int)((Gdx.graphics.getWidth()-32) * Math.random());
			int randY = (int)((Gdx.graphics.getHeight()-32)* Math.random());
			pwrup= new PowerUp(randInt, randX, randY); //id, x, y
			powerUps.add(pwrup);
		}
		randInt = (int)(120 * Math.random());
		if(randInt == 30){ //enemy
			spawnEnemy(1); //only spawn one
		}
	}

	//simple test used to see if the coordinates are indeed in the window
	public boolean areCoordsInWindow(float x, float y){
		if(x < 0 || x > Gdx.graphics.getWidth() || y < 0 || y > Gdx.graphics.getHeight())
			return false;
		else
			return true;
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
	
	//called at the beginning and whenever you make a new game to make a fresh start
	public void resetWorld(){ 
		entities.clear();
		bullets.clear();
		powerUps.clear();
		score = 0;
		//Entity constructor is x, y, direction number, speed, sprite sheet, health, isPlayer
		Hero hero = new Hero(100, Gdx.graphics.getHeight()/2, 0, 1, hero1Sheet, 7, true);
		entities.add(hero);
		Hero2 hero2 = new Hero2(Gdx.graphics.getWidth() -100, Gdx.graphics.getHeight()/2, 0, 1, 
				hero2Sheet, 7, true);
		entities.add(hero2);
		allPlayersKilled = false;
		i =  0;
		spawnEnemy(5);
	}
}