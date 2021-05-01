package com.petara.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.UIManager;
//import javax.swing.border.EmptyBorder;


public class SnakePanel extends JPanel {
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4018230271876651812L;

	JPanel[][] gameField = new JPanel[100][100]; 
	String message = "";
	String difficulty = "Normal";
	Font fontOver = new Font("Unispace", Font.BOLD+Font.ITALIC, 40);
	Font wellcomeFont = new Font("Unispace", Font.BOLD+Font.ITALIC, 34);
	Font fontInstructions = new Font("Unispace", Font.BOLD, 20);
	Font counterFont = new Font("Impact", Font.BOLD,30);
	Color counterColor;
	Color overColor = new Color(104,0,0);
	Color difficultyColor = Color.yellow;
	int score =  0;
	int counter = 3;
	boolean justStarted = true;
	boolean gameStarting = false;
	boolean gameOver = false;
	

	public SnakePanel() {
		setLookAndFeel();
		GridLayout mainLayout = new GridLayout(100,100);
		setLayout(mainLayout);
		//setBorder(new EmptyBorder(-14,-3,-14,-3));
		for(int x = 0; x<100; x++) {
			for(int y = 0; y<100; y++) {
				gameField[x][y] = new JPanel();
				gameField[x][y].setBackground(Color.black);
				add(gameField[x][y]);
			}
		}
	}
	
	public void paint(Graphics comp) {
		super.paint(comp);
		if(justStarted) {
			Graphics2D comp2d = (Graphics2D)comp;
			comp2d.setColor(new Color(212,175,55));
			comp2d.setFont(wellcomeFont);
			drawCenteredString("Welcome to \"The Snake\"!",comp2d,300);
			comp2d.setColor(new Color(104,0,52));
			comp2d.setFont(fontInstructions);
			drawCenteredString("Press [Enter] to play",comp2d,0);
			drawCenteredString("Press [S] to enter settings",comp2d,-100);
			drawCenteredString("Press [I] for info about the game",comp2d,-200);
		}
		if(gameStarting) {
			Graphics2D comp2d = (Graphics2D)comp;
			comp2d.setColor(counterColor);
			comp2d.setFont(counterFont);
			drawCenteredString(""+counter, comp2d, 0);
		}
		if(gameOver) {
			Graphics2D comp2d = (Graphics2D)comp;
			comp2d.setColor(overColor);
			comp2d.setFont(fontOver);
			drawCenteredString("GAME OVER!",comp2d,300);
			comp2d.setFont(fontInstructions);
			comp2d.setColor(difficultyColor);
			drawCenteredString("Difficulty: " + difficulty, comp2d, 250);
			drawCenteredString("Score: " + score,comp2d,200);
			comp2d.setColor(overColor);
			drawCenteredString("Press [R] to restart",comp2d,-100);
			drawCenteredString("Press [Esc] to quit",comp2d,-200);
		}
	}
	
	private void drawCenteredString(String message, Graphics comp, int distanceFromYCenter) {
		int textCenteredX = 0;
		int textCenteredY = 0;
		int coordinateX = 0;
		int coordinateY = 0;
		
		FontMetrics size = comp.getFontMetrics();
		textCenteredX = size.stringWidth(message)/2;
		textCenteredY = size.getAscent()/2;
		
		coordinateX = getWidth()/2 - textCenteredX;
		coordinateY = getHeight()/2 + textCenteredY - distanceFromYCenter;
		
		comp.drawString(message, coordinateX, coordinateY);
	}
	
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch(Exception oops) {
			//
		}
	}
	
	
	

}
