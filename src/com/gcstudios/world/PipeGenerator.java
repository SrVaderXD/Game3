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
			int y1 = Entity.rand.nextInt(70 - 30) + 30;
			Pipe pipe1 = new Pipe(Game.WIDTH,0,20,y1,0,null);
			
			int y2 = Entity.rand.nextInt(70 - 30) + 30;
			Pipe pipe2 = new Pipe(Game.WIDTH,Game.HEIGHT-y2,20,y2,0,null);
			
			Game.entities.add(pipe1);
			Game.entities.add(pipe2);
			
			time = 0;
		}
	}
}
