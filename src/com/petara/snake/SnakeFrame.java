package com.petara.snake;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class SnakeFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4446616080610704595L;
	
	SnakePanel gamePanel;
	SnakeBrain brain;
	SettingsPanel settings;
	InfoPanel info;
	Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	int height = screenSize.height;
	StringBuilder roundHeight = new StringBuilder(""+height);
	
	public SnakeFrame() {
		super("The Snake");
		setLookAndFeel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/frameIcon.png")));
		roundHeight.setCharAt(roundHeight.length()-1, '0');
		roundHeight.setCharAt(roundHeight.length()-2, '0');
		height = Integer.parseInt(roundHeight.toString());
		setSize(height-84,height-62);
		setWindowCentered();
		setResizable(false);
		gamePanel = new SnakePanel();
		settings = new SettingsPanel();
		info = new InfoPanel();
		add(gamePanel);
		brain = new SnakeBrain(this,gamePanel);
		super.addKeyListener(brain);
		settings.difficultyDown.addActionListener(brain);
		settings.difficultyUp.addActionListener(brain);
		settings.backButton.addActionListener(brain);
		setVisible(true);
	}
	
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch(Exception oops) {
			//
		}
	}
	
	private void setWindowCentered(){
		int x = 0;
		int y = 0;
		x = (screenSize.width/2)-(super.getWidth()/2);
		y = (screenSize.height/2)-(super.getHeight()/2);
		super.setLocation(x, y);
	}
	
	public void toSettings() {
		remove(gamePanel);
		add(settings);
		repaint();
	}
	
	public void toInfo() {
		remove(gamePanel);
		add(info);
		repaint();
	}
	
	public void toGame() {
		remove(settings);
		remove(info);
		add(gamePanel);
		repaint();
		requestFocus();
	}
	
	public static void main(String[] args) {
		new SnakeFrame();
	}

}
