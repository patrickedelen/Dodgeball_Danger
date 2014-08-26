package com.game.src.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Enemy {
	
	private double x;
	private double y;
	
	private int sCount = 0;;
	
	BufferedImage image;
	
	private Controller c;
	Game game;
	
	public Enemy(double x, double y, Game game){
		this.x = x;
		this.y = y;
		this.game = game;
		
		c = new Controller(game);
		
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		
		image = ss.grabImage(4, 1, 64, 64);
		
	}
	
	public void tick(){
		x -= 2;
		sCount++;
		
		if(sCount==30){
			sCount = 0;
			c.addBall(new Ball((double)x-40, (double)y-15, 1, game));
			System.out.println("Shooting!");
		}
		
	}
	
	public void render(Graphics g){
		g.drawImage(image, (int)x, (int)y, null);
	}
	
	public double getX(){
		return x;
	}

}
