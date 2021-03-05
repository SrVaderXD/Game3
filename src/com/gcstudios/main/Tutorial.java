package com.gcstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Tutorial {
	
	public String[] options = {"Back"};
	
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
			
			if(options[currentOption] == "Back") {
				Menu.tutorial = false;
				Game.GameState = "Menu";
			}
			
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(new Color(0,0,0,200));
		g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 26));

		//option
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.drawString("Back", Game.WIDTH/2 + 500, Game.HEIGHT/2 + 420);
		
		if(options[currentOption] == "Back") {
			g.drawString(">", Game.WIDTH/2 + 460, Game.HEIGHT/2 + 420);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 32));
		g.drawString("* SPACE - GO UP", Game.WIDTH/2 - 100, Game.HEIGHT/2 - 30);
		g.drawString("* ESC - PAUSE", Game.WIDTH/2 - 100, Game.HEIGHT/2 + 30 );
		g.drawString("* HAVE FUN =)", Game.WIDTH/2 - 100, Game.HEIGHT/2 + 90 );
	}
}