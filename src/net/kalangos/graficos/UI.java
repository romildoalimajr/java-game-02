package net.kalangos.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import net.kalangos.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 18));
		g.drawString("Apples.: " + Game.atualFruits + "/" + Game.countFruits, 30, 25);
	}
	
}
