package com.petara.snake;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class ScoreBoard extends JWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4255728788079891116L;
	
	JLabel scoreLabel;
	Font scoreFont;
	Border borderGameOn;
	Border borderGameOver = BorderFactory.createLineBorder(new Color(102,0,0), 4);

	public ScoreBoard() {
		setLocation(0, 800);
		setSize(800,100);
		borderGameOn = BorderFactory.createLineBorder(new Color(0,102,52), 4);
		scoreLabel  = new JLabel("Score: 0",SwingConstants.CENTER);
		scoreFont = new Font("Courier New",Font.BOLD+Font.ITALIC,24);
		scoreLabel.setBorder(borderGameOn);
		scoreLabel.setFont(scoreFont);
		scoreLabel.setOpaque(true);
		scoreLabel.setBackground(Color.black);
		scoreLabel.setForeground(Color.green);
		add(scoreLabel);
		setVisible(true);
	}
}
