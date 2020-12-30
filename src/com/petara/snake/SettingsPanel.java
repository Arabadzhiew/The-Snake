package com.petara.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class SettingsPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4397814140175562889L;
	
	Color settingsColor = new Color(0,102,51);
	Color difficultyExtreme = new Color(212,175,55);
	Font difficultyFont = new Font("Unispace", Font.BOLD, 20);
	
	JLabel title;
	
	JPanel difficulty;
	JLabel difficultyLabel;
	JLabel currentDifficulty;
	JButton difficultyDown;
	JButton difficultyUp;
	
	JPanel emptyPanel3;
	JPanel emptyPanel4;
	JPanel emptyPanel5;
	
	JPanel backPanel;
	JButton backButton;
	
	
	
	public SettingsPanel() {
		setLookAndFeel();
		setBackground(Color.black);
		setLayout(new GridLayout(6,1));
		
		title = new JLabel("Settings", SwingConstants.CENTER);
		title.setFont(new Font("Unispace", Font.BOLD+Font.ITALIC, 40));
		title.setOpaque(true);
		title.setBackground(Color.black);
		title.setForeground(settingsColor);
		add(title);
		
		difficulty = new JPanel();
		difficulty.setBackground(Color.black);
		currentDifficulty = new JLabel("Difficulty: ");
		currentDifficulty.setFont(difficultyFont);
		currentDifficulty.setOpaque(true);
		currentDifficulty.setBackground(Color.black);
		currentDifficulty.setForeground(settingsColor);
		difficulty.add(currentDifficulty);
		
		difficultyDown = new JButton("-");
		difficultyDown.setFont(difficultyFont);
		difficultyDown.setForeground(settingsColor);
		difficultyDown.setBackground(Color.gray);
		difficulty.add(difficultyDown);
		
		currentDifficulty = new JLabel("  Normal  ");
		currentDifficulty.setFont(difficultyFont);
		currentDifficulty.setForeground(Color.yellow);
		currentDifficulty.setBackground(Color.black);
		difficulty.add(currentDifficulty);
		
		difficultyUp = new JButton("+");
		difficultyUp.setFont(difficultyFont);
		difficultyUp.setForeground(settingsColor);
		difficultyUp.setBackground(Color.gray);
		difficulty.add(difficultyUp);
		add(difficulty);
		
		emptyPanel3 = new JPanel();
		emptyPanel3.setBackground(Color.black);
		emptyPanel4 = new JPanel();
		emptyPanel4.setBackground(Color.black);
		emptyPanel5 = new JPanel();
		emptyPanel5.setBackground(Color.black);
		add(emptyPanel3);
		add(emptyPanel4);
		add(emptyPanel5);
		
		backPanel = new JPanel();
		backPanel.setBackground(Color.black);
		backButton = new JButton("Back");
		backButton.setFont(difficultyFont);
		backButton.setForeground(settingsColor);
		backButton.setBackground(Color.gray);
		backPanel.add(backButton);
		add(backPanel);
	}
	
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch(Exception oops) {
			//
		}
	}

}
