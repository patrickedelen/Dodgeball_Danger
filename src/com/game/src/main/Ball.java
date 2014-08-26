package com.game.src.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Ball {

	private double x;
	private double y;
	private int v;
	
	BufferedImage image;
	
	public Ball(double x, double y, int v, Game game){
		this.x = x;
		this.y = y;
		this.v = v;
		
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		
		image = ss.grabImage(3, 1, 32, 32);
		
	}
	
	public void tick(){
		if(v==0){
			x += 5;
		}else if(v==1){
			x -= 5;
			System.out.println("Shooting Ball!");
		}
		
	}
	
	public void render(Graphics g){
		g.drawImage(image, (int)x, (int)y, null);
	}
	
	public double getX(){
		return x;
	}
	
	
}
