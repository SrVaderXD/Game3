package com.gcstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import com.gcstudios.entities.Entity;
import com.gcstudios.entities.Player;
import com.gcstudios.graphics.Spritesheet;
import com.gcstudios.graphics.UI;
import com.gcstudios.world.PipeGenerator;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 180;
	public static final int SCALE = 3;
	
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static Player player;
	
	public static PipeGenerator pipeGen;
	
	public UI ui;
	
	private Spritesheet backGround;
	
	public static double score = 0;
	
	public static String GameState = "Menu";
	
	public Menu menu;
	public End end;
	public Tutorial tuto;
	
	public static boolean restart = false;
	
	public static boolean GameOver;
	
	private int framesGameOver = 0;
	
	public Game(){
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		//Initializing the game
		spritesheet = new Spritesheet("/spritesheet.png");
		entities = new ArrayList<Entity>();
		player = new Player(WIDTH/2-30,HEIGHT/2,12,8,2,spritesheet.getSprite(0,0,16,16));
		ui = new UI();
		backGround = new Spritesheet("/background.png");
		pipeGen = new PipeGenerator();
		entities.add(player);
		menu = new Menu();
		end = new End();
		tuto = new Tutorial();
		
	}
	
	public void initFrame(){
		frame = new JFrame("Flop Bird");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		Game game = new Game();
		game.start();
	}
	
	public void tick(){
				
		if(GameState == "Normal") {
		
			pipeGen.tick();
			
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
		}
		
		else if(GameState == "GameOver") {
			entities.clear();
			framesGameOver++;
			if(framesGameOver == 30) {
				framesGameOver = 0;
				if(GameOver)
					GameOver = false;
				else
					GameOver = true;
			}
		}
		
		else if(GameState == "Menu") {
			menu.tick();
		}
		
		else if(GameState == "Tutorial") {
			tuto.tick();
		}
		
		else if(GameState == "End") {
			entities.clear();
			end.tick();
		}
		
		if(restart) {
			 GameState = "Normal";
			 restart();
		}
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		
		
		//BackGround render
		
		g.drawImage(backGround.getSprite(0, 0, 240, 180), 0, 0,null); 
		
		//Game Render
		
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		
		if(GameState != "GameOver" && GameState != "Menu")
			ui.render(g);
		
		if(GameState == "GameOver") {
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(new Color(0,0,0,200));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial", Font.BOLD, 72));
			g.setColor(Color.white);
			g.drawString("GAME OVER", WIDTH/2 + 15, HEIGHT/2 + 130);
			g.setFont(new Font("arial", Font.BOLD, 36));
			g.setColor(Color.white);
			if(GameOver)
				g.drawString("Press 'R' to restart", WIDTH/2 + 73, HEIGHT/2 + 170);
		}
		
		else if(GameState == "Menu") {
			menu.render(g);
		}
		
		else if(GameState == "Tutorial") {
			tuto.render(g);
		}
		
		else if(GameState == "End") {
			entities.clear();
			end.render(g);
		}
		
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
	}
	
	public static void restart() {
		score = 0;
		player = new Player(WIDTH/2-30,HEIGHT/2,16,16,2,spritesheet.getSprite(0,0,16,16));
		entities.clear();
		entities.add(player);
		restart = false;
		return;
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.isPressed = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
			
			if(GameState == "Menu")
				menu.up = true;
			
			if(GameState == "End")
				end.up = true;
			
			if(GameState == "Tutorial")
				tuto.up = true;
			
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			
			if(GameState == "Menu")
				menu.down = true;
			
			if(GameState == "End")
				end.down = true;
			
			if(GameState == "Tutorial")
				tuto.down = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_R) {
			if(GameState != "Normal" && GameState != "Menu")
				restart = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(GameState == "Menu")
				menu.enter = true;
			
			if(GameState == "End")
				end.enter = true;
			
			if(GameState == "Tutorial")
				tuto.enter = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			if(GameState == "Normal") {
				GameState = "Menu";
				menu.pause = true;
			}
		}
			
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.isPressed = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
			
			if(GameState == "Menu")
				menu.up = false;
			
			if(GameState == "End")
				end.up = false;
			
			if(GameState == "Tutorial")
				tuto.up = false;
			
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			
			if(GameState == "Menu")
				menu.down = false;
			
			if(GameState == "End")
				end.down = false;
			
			if(GameState == "Tutorial")
				tuto.down = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_R) {
			if(GameState != "Normal" && GameState != "Menu")
				restart = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(GameState == "Menu")
				menu.enter = false;
			
			if(GameState == "End")
				end.enter = false;
			
			if(GameState == "Tutorial")
				tuto.enter = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			if(GameState == "Normal") {
				GameState = "Menu";
				menu.pause = false;
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent arg0) {
		
	}

	public void mouseExited(MouseEvent arg0) {
		
	}

	public void mousePressed(MouseEvent e) {	
	}

	public void mouseReleased(MouseEvent arg0) {
		
	}

	public void mouseDragged(MouseEvent arg0) {
		
	}

	public void mouseMoved(MouseEvent e) {
	
	}
}
