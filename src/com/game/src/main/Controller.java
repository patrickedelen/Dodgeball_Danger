package com.game.src.main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Controller {

	private LinkedList<Ball> b = new LinkedList<Ball>();
	
	Ball TempBall;
	
	Game game;
	
	public Controller(Game game){
		this.game = game;
		
	}
	
	public void tick(){
		
		for(int i=0; i<b.size(); i++){
			TempBall = b.get(i);
			
			if(TempBall.getX() > 1200)
				removeBall(TempBall);
			
			TempBall.tick();
			
		}
		
	}
	
	public void render(Graphics g){
		for(int i=0; i<b.size(); i++){
			TempBall = b.get(i);
			TempBall.render(g);
			
		}
		
	}
	
	public void addBall(Ball block){
		b.add(block);
	}
	public void removeBall(Ball block){
		b.remove(block);
	}
	
}
