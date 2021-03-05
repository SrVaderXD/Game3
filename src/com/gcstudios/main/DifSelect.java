package com.gcstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class DifSelect { 
		
	public String[] options = {"Normal", "Hard", "Back"};
	
	public int currentOption = 0, maxOption = (options.length)-1;

	public boolean up, down, enter;
	
	public void tick() {
		
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		
		if(enter) {
			enter = false;
			
			if(options[currentOption] == "Normal") {
				Game.difficulty = 0;
			}
			
			else if(options[currentOption] == "Hard") {
				Game.difficulty = 1;
			}
			
			else if(options[currentOption] == "Back") {
				Menu.tutorial = false;
				Game.GameState = "Menu";
			}
			
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(new Color(0,0,0,200));
		g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			
		g.setColor(Color.gray);
		g.setFont(new Font("arial", Font.BOLD, 40));
		g.drawString("Select the Difficulty Below", Game.WIDTH/2, Game.HEIGHT/2 - 20);
		g.drawString("Thank you for playing", Game.WIDTH/2 + 50, Game.HEIGHT/2+ 50);
			
		//Menu options
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 36));
		
		g.drawString("Normal", Game.WIDTH/2 + 170, Game.HEIGHT/2 + 180);
		g.drawString("Hard", Game.WIDTH/2 + 190, Game.HEIGHT/2 + 300);
		g.drawString("Back", Game.WIDTH/2 + 500, Game.HEIGHT/2 + 420);
		
		//Difficulty display
		g.setFont(new Font("arial", Font.BOLD, 22));
		
		if(Game.difficulty == 0)
			g.drawString("Current Difficulty: Normal", Game.WIDTH/2 - 100, Game.HEIGHT/2 + 420);
		
		else
			g.drawString("Current Difficulty: Hard", Game.WIDTH/2 - 100, Game.HEIGHT/2 + 420);

		g.setFont(new Font("arial", Font.BOLD, 40));
		
		if(options[currentOption] == "Normal") {
			g.drawString(">", Game.WIDTH/2 + 135, Game.HEIGHT/2 + 180);
		}
			
		else if(options[currentOption] == "Hard") {
			g.drawString(">", Game.WIDTH/2 + 155, Game.HEIGHT/2 + 300);
		}
		
		else if(options[currentOption] == "Back") {
			g.drawString(">", Game.WIDTH/2 + 460, Game.HEIGHT/2 + 420);
		}
	}
}
