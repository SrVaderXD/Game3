package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;


public class Player extends Entity{
	
	public boolean isPressed = false;
	
	private BufferedImage falling;
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		falling = Game.spritesheet.getSprite(16, 0, 16, 16);
		depth = 2;
	}
	
	public void tick(){
		//Gravity
		if(!isPressed) {
			y+=2;
		}
		
		else {
			if(y > 0)
				y+=-2;
		}
		
		if(y > Game.HEIGHT - 35) {
			Game.restart();
		}
		
		//Collision with pipe
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			
			if( e != this) {
				if(Entity.isColidding(this, e)) {
					//iscolliding gameover
					Game.restart();
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(!isPressed) {
			g.drawImage(falling, this.getX(),this.getY(),null);
		}
		
		else {
			g.drawImage(sprite, this.getX(),this.getY(),null);
		}
	}
}
