package com.gcstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class Pipe extends Entity{
	
	public Pipe(double x,double y,int width,int height,double speed,BufferedImage sprite) {
		super(x,y,width,height,speed,sprite);
	}

	public void tick() {
		x--;
		if(x+width <= 0) {
			Game.score+=0.5;
			Game.entities.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		
		if(sprite != null) {
			g.drawImage(sprite, this.getX(), this.getY(), 16, height, null);
		}
		
		else {
			g.setColor(Color.green);
			g.fillRect(this.getX(),this.getY(),width,height);
		}
	}

}
