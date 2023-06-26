package net.kalangos.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.kalangos.main.Game;
import net.kalangos.world.Camera;
import net.kalangos.world.World;

public class Player extends Entity {

	public boolean right, up, left, down;

	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	public boolean moved = false;
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;

	public BufferedImage sprite_left;
	public BufferedImage sprite_up;
	public BufferedImage sprite_down;

	public int lastDir = 1;

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

		sprite_left = Game.spritesheet.getSprite(32, 16, 16, 16);
		sprite_up = Game.spritesheet.getSprite(32, 32, 16, 16);
		sprite_down = Game.spritesheet.getSprite(32, 48, 16, 16);

		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];

		for (int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
		}

		for (int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
		}
		
		for(int i = 0; i < 4; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 32, 16, 16);
		}
		
		for(int i = 0; i < 4; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 48, 16, 16);
		}	
		
	}

	public void tick() {
		depth = 1;
		if (right && World.isFree((int) (x + speed), this.getY())) {
			moved = true;
			x += speed;
			lastDir = 1;
		} else if (left && World.isFree((int) (x - speed), this.getY())) {
			moved = true;
			x -= speed;
			lastDir = -1;
		}
		if (up && World.isFree(this.getX(), (int) (y - speed))) {
			moved = true;
			y -= speed;
			lastDir = -2;
		} else if (down && World.isFree(this.getX(), (int) (y + speed))) {
			moved = true;
			y += speed;
			lastDir = 2;
		}
		
		verificarPegarFruta();
		
		if(Game.countFruits == Game.atualFruits) {
			World.restartGame();
			return;
		}

		if (moved) {
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

	private void verificarPegarFruta() {
		// TODO Auto-generated method stub
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Apples) {
				if(Entity.isColidding(this, current)) {
					Game.atualFruits++;
					Game.entities.remove(i);
					return;
				}
			}
		}
	}

	public void render(Graphics g) {
		if (lastDir == 1) {
			//super.render(g);
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else if (lastDir == -1){
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else if(lastDir == 2) {
			g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else {
			g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}

}
