package com.mygdx.game;

import java.util.List;

public class CollisionManager {
	
	public static boolean entityBulletCheck(GameEntity ge, int entityCounter){
		List<Pew> bullets = MyGdxGame.bullets;
		for(int bulletCounter = bullets.size()-1; bulletCounter >= 0; bulletCounter--){
			Pew bullet = bullets.get(bulletCounter);
			//if the bullet is hitting the game entity
			if(bullet.rect.overlaps(ge.rect)){
				//if the bullet hurts the players and it is a player, or if it does not hurt 
				//players and it is not a player, then continue to look at the collision
				if((!bullet.hurtPlayers && !ge.isPlayer) || (bullet.hurtPlayers && ge.isPlayer)){
					//only continue if the entity is not invincible or dead
					if(!ge.invincible && !ge.dead){
						//hurt the entity
						ge.health -= bullet.damage;  
						//since the entity has been hurt by the bullet, even if not killed, remove bullet.
						bullets.remove(bulletCounter);
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
								MyGdxGame.score -= 50; //subtract 50 points for dying
							}else{ //if the entity is not a player...
								MyGdxGame.entities.remove(entityCounter);
								MyGdxGame.score += 10;
								return false; //since the entity was removed, stop looking at it and
										//return that it died.
							}
						}						
					}
				}
			}// end looking at their overlapping rectangles 
		} // end looking at each bullet loop
		return true;
	}//end method
	
	
	
	public static void entityPowerUpCheck(GameEntity ge){
		List<PowerUp> powerUps = MyGdxGame.powerUps;
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
	}//end method
	
	
	
	public static void entityEntityCheck(GameEntity ge){
		List<GameEntity> entities = MyGdxGame.entities;
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
	}//end method
}//end class
