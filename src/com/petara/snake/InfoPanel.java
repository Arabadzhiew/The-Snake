package com.petara.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class InfoPanel extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1823358569816690317L;
	
	Thread drawer;
	boolean startThread = true;
	
	Color infoColor = Color.lightGray;
	Color mainColor = new Color(212,175,55);
	Font infoFont = new Font("Unispace",Font.BOLD, 24);
	
	int r = 255;
	int g = 0;
	int b = 0;
	Color rainbowColor = new Color(r,g,b);
	
	public InfoPanel() {
		setLookAndFeel();
		setBackground(Color.black);
		setVisible(true);
	}
	
	public void paintComponent(Graphics comp) {
		super.paintComponent(comp);
		Graphics2D comp2d = (Graphics2D)comp;
		comp2d.setColor(mainColor);
		comp2d.setFont(new Font("Unispace",Font.BOLD, 40));
		drawCenteredString("Info",comp2d,350);
		comp2d.setColor(infoColor);
		comp2d.setFont(infoFont);
		drawCenteredString("Dveloper(s)",comp2d,250);
		comp2d.setColor(rainbowColor);
		drawCenteredString("Petar Z. Arabadzhiev",comp2d,200);
		comp2d.setColor(infoColor);
		drawCenteredString("Version of the game",comp2d,50);
		comp2d.setColor(mainColor);
		drawCenteredString("v1.0",comp2d,0);
		comp2d.setColor(infoColor);
		drawCenteredString("Press [Backspace] to go back to the game",comp2d,-300);
	}
	
	private void drawCenteredString(String message, Graphics2D comp2d, int awayFromY) {
		int width = 0;
		int height = 0;
		int x = 0;
		int y = 0;
		FontMetrics fm = comp2d.getFontMetrics();
		width = fm.stringWidth(message)/2;
		height = fm.getAscent()/2;
		x = super.getWidth()/2 - width;
		y = super.getHeight()/2 + height - awayFromY;
		comp2d.drawString(message, x, y);
		
	}
	
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch(Exception oops) {
			//
		}
	}

	@Override
	public void run() {
		while(startThread) {
			if(r==255&&g<255&&b==0) {
				g++;
			}else if(r>0&&g==255&&b==0) {
				r--;
			}else if(r == 0&&g==255&&b<255) {
				b++;
			}else if(r==0&&g>0&&b==255) {
				g--;
			}else if(r<255&&g==0&&b==255) {
				r++;
			}else if(r==255&&g==0&&b>0) {
				b--;
			}
			rainbowColor = new Color(r,g,b);
			repaint();
			try {
				Thread.sleep(2);
			}catch(InterruptedException oops) {
				//
			}
		}
		drawer = null;
		
	}
}
