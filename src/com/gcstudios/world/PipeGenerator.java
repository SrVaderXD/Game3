package com.gcstudios.world;

import com.gcstudios.entities.Entity;
import com.gcstudios.entities.Pipe;
import com.gcstudios.main.Game;

public class PipeGenerator {
	
	public int time = 0;
	public int targetTime = 60;
	
	public void tick() {
		time++;
		if(time == targetTime) {
			// upper pipe
			int y1 = Entity.rand.nextInt(60 - 30) + 35;
			Pipe pipe1 = new Pipe(Game.WIDTH,0,20,y1,0,Game.spritesheet.getSprite(0, 32, 20, 16));
			
			// lower pipe
			int y2 = Entity.rand.nextInt(60 - 30) + 35;
			Pipe pipe2 = new Pipe(Game.WIDTH,Game.HEIGHT-y2,20,y2,0,Game.spritesheet.getSprite(0, 16, 20, 16));
			
			Game.entities.add(pipe1);
			Game.entities.add(pipe2);
			
			time = 0;
		}
	}
}
