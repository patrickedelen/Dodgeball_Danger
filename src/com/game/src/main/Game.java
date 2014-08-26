package com.game.src.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public static final int WIDTH=600;
	public static final int HEIGHT=360;
	public static final int SCALE=2;
	public final String TITLE="Dodgeball Danger";
	
	private boolean running=false;
	private Thread thread;
	
	//private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	
	private int offset = 0;
	private boolean throwing = false;
	
	private Player p;
	private Controller c;
	private AI ai;
	
	
	private void init(){
		requestFocus();
		
		BufferedImageLoader loader = new BufferedImageLoader();
		
		try{
			
			spriteSheet = loader.loadImage("/SpriteSheet_Dodgeball.png");
			background = loader.loadImage("/Background.png");
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		addKeyListener(new KeyInput(this));
		
		p = new Player(200, 200, this);
		c = new Controller(this);
		ai = new AI(this);
		ai.addEnemy(new Enemy((double)800, (double)350, this));
		
	}
	
	
	private synchronized void start(){
		if(running)
			return;
		
		running=true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop(){
		if(!running)
			return;
		
		running=false;
		
		try{
		thread.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		System.exit(1);
	}
	
	
	@Override
	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTics = 60.0;
		double ns = 1000000000 / amountOfTics;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer+=1000;
				System.out.println(updates + " Ticks, FPS " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	
	private void tick() {
		p.tick();
		c.tick();
		ai.tick();
	}
	
	private void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null){
			
			createBufferStrategy(2);
			return;
			
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(background, offset+0, 0, getWidth(), getHeight(), this);
		
		p.render(g);
		c.render(g);
		ai.render(g);
		
		g.dispose();
		bs.show();
		
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT){
			p.setvelX(1);
			offset -= 3;
		}else if(key == KeyEvent.VK_LEFT){
			p.setvelX(-1);
			offset += 3;
		}else if(key == KeyEvent.VK_DOWN){
			p.setvelY(5);
		}else if(key == KeyEvent.VK_UP){
			p.setvelY(-5);
		}else if(key == KeyEvent.VK_SPACE && !throwing){
			c.addBall(new Ball(p.getX()+40, p.getY()+15, 0, this));
			ai.addEnemy(new Enemy((double)350, (double)350, this));
			throwing = true;
		}else if(key == KeyEvent.VK_D){
			p.setvelX(1);
			offset -= 3;
		}else if(key == KeyEvent.VK_A){
			p.setvelX(-1);
			offset += 3;
		}else if(key == KeyEvent.VK_S){
			p.setvelY(5);
		}else if(key == KeyEvent.VK_W){
			p.setvelY(-5);
		}
		
		
		
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT){
			p.setvelX(0);
		}else if(key == KeyEvent.VK_LEFT){
			p.setvelX(0);
		}else if(key == KeyEvent.VK_DOWN){
			p.setvelY(0);
		}else if(key == KeyEvent.VK_UP){
			p.setvelY(0);
		}else if(key == KeyEvent.VK_SPACE){
			throwing = false;
		}else if(key == KeyEvent.VK_D){
			p.setvelX(0);
		}else if(key == KeyEvent.VK_A){
			p.setvelX(0);
		}else if(key == KeyEvent.VK_S){
			p.setvelY(0);
		}else if(key == KeyEvent.VK_W){
			p.setvelY(0);
		}
		
	}

	public static void main(String args[]){
		Game game = new Game();
		
		game.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		game.setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		game.setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		
		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
		
	}
	
	public BufferedImage getSpriteSheet(){
		return spriteSheet;
	}
	
	
	

}
