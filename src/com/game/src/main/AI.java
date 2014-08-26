package com.game.src.main;

import java.awt.Graphics;
import java.util.LinkedList;

public class AI {
	
private LinkedList<Enemy> e = new LinkedList<Enemy>();
	
	Enemy TempEnemy;
	
	Game game;
	
	public AI(Game game){
		this.game = game;
	}
	
	public void tick(){
		for(int i=0; i<e.size(); i++){
			TempEnemy = e.get(i);
			
			if(TempEnemy.getX() < 0)
				removeEnemy(TempEnemy);
			
			TempEnemy.tick();
			
		}
		
	}
	
	public void render(Graphics g){
		for(int i=0; i<e.size(); i++){
			TempEnemy = e.get(i);
			TempEnemy.render(g);
			
		}
		
	}
	
	public void addEnemy(Enemy block){
		e.add(block);
	}
	public void removeEnemy(Enemy block){
		e.remove(block);
	}

}
