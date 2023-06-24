package net.kalangos.entities;

import java.awt.image.BufferedImage;

import net.kalangos.main.Game;
import net.kalangos.world.Camera;
import net.kalangos.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	public boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;

	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		

		for (int i = 0; i < 4; i++) {
			
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
			System.out.println("direita");
		}

		for (int i = 0; i < 4; i++) {
		moved = true;
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
			System.out.println("esquerda");
		}
	}
	
	public void tick(){
		depth = 1;
		if(right && World.isFree((int)(x+speed),this.getY())) {
			moved = true;
			x+=speed;
		}
		else if(left && World.isFree((int)(x-speed),this.getY())) {
			moved = true;
			x-=speed;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed))){
			y-=speed;
		}
		else if(down && World.isFree(this.getX(),(int)(y+speed))){
			y+=speed;
		}
		
		if(moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, (World.WIDTH * 16) - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, (World.HEIGHT * 16) - Game.HEIGHT);
	}
	
	


}
