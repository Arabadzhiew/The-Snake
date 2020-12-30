package com.petara.snake;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SnakeBrain implements Runnable, KeyListener, ActionListener{
	private final int SNAKE_LEFT = 0;
	private final int SNAKE_RIGHT = 1;
	private final int SNAKE_UP = 2;
	private final int SNAKE_DOWN = 3;
	
	private final int COORDINATE_X = 4;
	private final int COORDINATE_Y = 5;
	
	private final int INCREASE = 6;
	private final int DECREASE = 7;
	
	SnakePanel game;
	SnakeFrame gameFrame;
	Thread action;
	
	int snakeX = 34;
	int snakeY = 4;
	int direction = SNAKE_UP;
	int baseLenghtHolder = 16;
	int snakeLenght = baseLenghtHolder;
	int scoreAdder = 50;
	long slowDown = 75L;
	String difficulty = "Normal";
	
	int appleX; 
	int appleY;
	
	boolean stopThread = false;
	boolean justStarted = true;
	boolean gameOver = false;
	boolean closeGame = false;
	
	boolean inSettings = false;
	boolean inInfo = false;
	Map<Integer,Integer> mapX = new HashMap<Integer,Integer>(10);
	Map<Integer,Integer> mapY = new HashMap<Integer,Integer>(10);
	
	public SnakeBrain(SnakeFrame gameFrame, SnakePanel game) {
		this.gameFrame = gameFrame;
		this.game = game;
		generateSnake(snakeX, snakeY);
		action = new Thread(this);
		action.start();
		
	}
	@Override
	public void run() {
		main: while(stopThread == false) {
			while(justStarted == true) {
				if(snakeX<=4 && snakeY<96) {
					turnRight();
					direction = SNAKE_RIGHT;
				}else if(snakeX<96 && snakeY==96) {
					turnDown();
					direction = SNAKE_DOWN;
				}else if(snakeX==96 && snakeY>4) {
					turnLeft();
					direction = SNAKE_LEFT;
				}else if(snakeX>4 && snakeY==4) {
					turnUp();
					direction = SNAKE_UP;
				}
				threadSlowDown(slowDown);
				if(stopThread) {
					break main;
				}
			}
			if(game.gameStarting) {
				for(int i = 3; i > 0; i--) {
					game.counter = i;
					game.counterColor = new Color(0,240/i,100);
					game.repaint();
					threadSlowDown(1000);
					game.gameStarting = false;
					game.repaint();
					game.gameStarting = true;
				}
				game.gameStarting = false;
			}
			while(gameOver == false) {
				switch(direction) {
					case SNAKE_LEFT:
						turnLeft();
						break;
					case SNAKE_RIGHT:
						turnRight();
						break;
					case SNAKE_UP:	
						turnUp();
						break;
					case SNAKE_DOWN:
						turnDown();
						break;
				}
				checkForBite();
				checkApple();
				threadSlowDown(slowDown);
			}
			threadSlowDown(50);
		}
		action = null;
		if(closeGame) {
			System.exit(0);
		}
	}
	
	private void generateSnake(int x, int y) {
		game.gameField[x][y].setBackground(Color.green);
		mapX.put(0, x);
		mapY.put(0, y);
		for(int i = 0; i<snakeLenght; i++) {
			switch(direction) {
			case SNAKE_LEFT:
				y++;
				if(y>99) {
					y = 0;
				}
				game.gameField[x][y].setBackground(Color.lightGray);
				mapX.put(i+1, x);
				mapY.put(i+1, y);
				break;
			case SNAKE_RIGHT:
				y--;
				if(y<0) {
					y = 99;
				}
				game.gameField[x][y].setBackground(Color.lightGray);
				mapX.put(i+1, x);
				mapY.put(i+1, y);
				break;
			case SNAKE_UP:
				x++;
				if(x>99) {
					x = 0;
				}
				game.gameField[x][y].setBackground(Color.lightGray);
				mapX.put(i+1, x);
				mapY.put(i+1, y);
				break;
			case SNAKE_DOWN:
				x--;
				if(x<0) {
					x = 99;
				}
				game.gameField[x][y].setBackground(Color.lightGray);
				mapX.put(i+1, x);
				mapY.put(i+1, y);
			}
		}
		
	}
	
	private void changeDifficulty(String difficulty, long slowDown, int scoreAdder, int snakeLenght) {
		mapX.clear();
		mapY.clear();
		game.difficulty = difficulty;
		this.slowDown = slowDown;
		this.scoreAdder = scoreAdder;
		this.baseLenghtHolder = snakeLenght;
		this.snakeLenght = this.baseLenghtHolder;
		for(int x = 0; x < 100; x++) {
			for(int y = 0; y < 100; y++) {
				game.gameField[x][y].setBackground(Color.black);
			}
		}
		generateSnake(snakeX, snakeY);
	}
	
	private void generateApple() {
		appleX = (int)(Math.random()*100);
		appleY = (int)(Math.random()*100);
		while(game.gameField[appleX][appleY].getBackground()== Color.lightGray ||
				game.gameField[appleX][appleY].getBackground() == Color.green) {
			appleX++;
			appleY++;
			if(appleX > 99) {
				appleX = 0;
			}
			if(appleY > 99) {
				appleY = 0;
			}
		}
		
		game.gameField[appleX][appleY].setBackground(Color.red);
	}
	
	private void newGame() {
		for(int x = 0; x < 100; x++) {
			for(int y = 0; y < 100; y++) {
				game.gameField[x][y].setBackground(Color.black);
			}
		}
		snakeX = (int)(Math.random()*100);
		snakeY = (int)(Math.random()*100);
		direction = (int)(Math.random()*4);
		generateSnake(snakeX,snakeY);
		generateApple();
	}
	
	private void checkApple() {
		if(snakeX == appleX && snakeY == appleY) {
			snakeLenght++;
			game.score += scoreAdder;
			generateApple();
		}
	}
	
	private void coordinateEditor(int coordinate, int increaseOrDecrease) {
		game.gameField[snakeX][snakeY].setBackground(Color.black);
		switch(coordinate) {
			case COORDINATE_X:
				switch(increaseOrDecrease) {
					case INCREASE:
						snakeX++;
						if(snakeX>99) {
							snakeX = 0;
						}
						game.gameField[snakeX][snakeY].setBackground(Color.green);
						break;
					case DECREASE:
						snakeX--;
						if(snakeX<0) {
							snakeX = 99;
						}
						game.gameField[snakeX][snakeY].setBackground(Color.green);
						break;
				}
				break;
				case COORDINATE_Y:
					switch(increaseOrDecrease) {
					case INCREASE:
						snakeY++;
						if(snakeY>99) {
							snakeY = 0;
						}
						game.gameField[snakeX][snakeY].setBackground(Color.green);
						break;
					case DECREASE:
						snakeY--;
						if(snakeY<0) {
							snakeY = 99;
						}
						game.gameField[snakeX][snakeY].setBackground(Color.green);
						break;
					}
					break;
					
		}
		
	}

	
	private void moveTail() {
		Map<Integer,Integer> transferMapX = new HashMap<Integer,Integer>(10);
		Map<Integer,Integer>transferMapY = new HashMap<Integer,Integer>(10);
		for(int i = 0; i < mapX.size() || i < mapY.size(); i++) {
			if(mapX.get(i) != null && mapY.get(i) != null) {
				transferMapX.put(i, mapX.get(i));
				transferMapY.put(i, mapY.get(i));
			}
		}
		for(int i = 0; i < transferMapX.size() || i < transferMapY.size(); i++) {
			if(transferMapX.get(i) != null && transferMapY.get(i) != null) {
				if(mapX.get(i+1)!=null&&mapY.get(i+1)!=null) {
					game.gameField[mapX.replace(i+1, transferMapX.get(i))][mapY.replace(i+1, transferMapY.get(i))].setBackground(Color.black);
				}else{
					mapX.replace(i+1, transferMapX.get(i));
					mapY.replace(i+1, transferMapY.get(i));
				}
				
			}
			
		}
		
		for(int i = 1; mapX.get(i) != null && mapY.get(i) != null; i++) {
			game.gameField[mapX.get(i)][mapY.get(i)].setBackground(Color.lightGray);
		}
		
		mapX.replace(0, snakeX);
		mapY.replace(0, snakeY);
	}
	
	private void checkForBite() {
		if(game.gameField[snakeX][snakeY].getBackground()==Color.lightGray) {
			game.gameField[snakeX][snakeY].setBackground(new Color(0,102,52));
			gameOver = true;
			game.gameOver = true;
			game.repaint();
		}
	}
	
	private void addToTail() {
		if(mapX.get(snakeLenght) == null && mapY.get(snakeLenght) == null) {
			int xDifference = mapX.get(snakeLenght-1) - mapX.get(snakeLenght-2);
			int yDifference = mapY.get(snakeLenght-1) - mapY.get(snakeLenght-2);
			
			if(xDifference == -1|| xDifference == 99) {
				if(mapX.get(snakeLenght-1) - 1==-1) {
					mapX.put(snakeLenght, 99);
				}else {
					mapX.put(snakeLenght, mapX.get(snakeLenght-1) - 1);
				}
				mapY.put(snakeLenght, mapY.get(snakeLenght-1));
			}else if(xDifference == 1||xDifference == -99) {
				if(mapX.get(snakeLenght-1) + 1==100) {
					mapX.put(snakeLenght, 0);
				}else {
					mapX.put(snakeLenght, mapX.get(snakeLenght-1) + 1);
				}
				mapY.put(snakeLenght, mapY.get(snakeLenght-1));
			}else if(yDifference == -1||yDifference == 99) {
				mapX.put(snakeLenght, mapX.get(snakeLenght-1));
				if(mapY.get(snakeLenght-1) - 1==-1) {
					mapY.put(snakeLenght, 99);
				}else {
					mapY.put(snakeLenght, mapY.get(snakeLenght-1) - 1);
				}
			}else if(yDifference == 1||yDifference == -99) {
				mapX.put(snakeLenght, mapX.get(snakeLenght-1));
				if(mapY.get(snakeLenght-1) + 1==100) {
					mapY.put(snakeLenght, 0);
				}else {
					mapY.put(snakeLenght, mapY.get(snakeLenght-1) + 1);
				}
			}
			game.gameField[mapX.get(snakeLenght)][mapY.get(snakeLenght)].setBackground(Color.lightGray);
		}
	}
	
	private void threadSlowDown(long milSecs) {
		try {
			Thread.sleep(milSecs);
		}catch(InterruptedException oops) {
			//
		}
	}
	
	
	private void turnLeft() {
		coordinateEditor(COORDINATE_Y, DECREASE);
		moveTail();
		addToTail();
	}
	
	private void turnRight() {
		coordinateEditor(COORDINATE_Y, INCREASE);
		moveTail();
		addToTail();
	}
	
	private void turnUp() {
		coordinateEditor(COORDINATE_X, DECREASE);
		moveTail();
		addToTail();
	}
	
	private void turnDown() {
		coordinateEditor(COORDINATE_X, INCREASE);
		moveTail();
		addToTail();
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();
		if(justStarted == true&&inSettings == false&&inInfo==false) {
			switch(key) {
				case KeyEvent.VK_ENTER:
					game.gameStarting = true;
					justStarted = false;
					game.justStarted = false;
					newGame();
					game.repaint();
					break;
				case KeyEvent.VK_S:
					inSettings = true;
					stopThread = true;
					gameFrame.toSettings();
					break;
				case KeyEvent.VK_I:
					inInfo = true;
					gameFrame.info.startThread = true;
					gameFrame.info.drawer = new Thread(gameFrame.info);
					gameFrame.info.drawer.start();
					gameFrame.toInfo();
					break;
			}
			
		}
		if(gameOver == false&&justStarted==false) {
			switch(key) {
				case KeyEvent.VK_LEFT:
					if(direction == SNAKE_RIGHT) {
						//
					}else {
						direction = SNAKE_LEFT;
					}
					threadSlowDown(slowDown);
					break;
				case KeyEvent.VK_RIGHT:
					if(direction == SNAKE_LEFT) {
						//
					}else {
						direction = SNAKE_RIGHT;
					}
					threadSlowDown(slowDown);
					break;
				case KeyEvent.VK_UP:
					if(direction == SNAKE_DOWN) {
						//
					}else {
						direction = SNAKE_UP;
					}
					threadSlowDown(slowDown);
					break;
				case KeyEvent.VK_DOWN:
					if(direction == SNAKE_UP) {
						//
					}else {
						direction = SNAKE_DOWN;
					}
					threadSlowDown(slowDown);
					break;
			}
		}else if(gameOver == true&&justStarted==false) {
			switch(key) {
				case KeyEvent.VK_ESCAPE:
					closeGame = true;
					stopThread = true;
					break;
				case KeyEvent.VK_R:
					snakeLenght = baseLenghtHolder;
					mapX.clear();
					mapY.clear();
					game.score = 0;
					newGame();
					game.gameStarting = true;
					gameOver = false;
					game.gameOver = false;
					break;
			}
		}else if(inInfo==true) {
			if(key==KeyEvent.VK_BACK_SPACE) {
				gameFrame.toGame();
				gameFrame.info.startThread = false;
				inInfo = false;
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// 
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// 
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		String difficulty = gameFrame.settings.currentDifficulty.getText();
		ArrayList<String> difficulties = new ArrayList<String>();
		difficulties.add("  Easy  ");
		difficulties.add("  Normal  ");
		difficulties.add("  Hard  ");
		difficulties.add("  Extreme  ");
		
		if(source==gameFrame.settings.difficultyDown) {
			if(difficulty.equals(difficulties.get(1))){
				gameFrame.settings.currentDifficulty.setText(difficulties.get(0));
				gameFrame.settings.currentDifficulty.setForeground(Color.green);
				gameFrame.settings.difficultyDown.setEnabled(false);
				changeDifficulty("Easy", 100, 25, 5);
				game.difficultyColor = Color.green;
			}else if(difficulty.equals(difficulties.get(2))) {
				gameFrame.settings.currentDifficulty.setText(difficulties.get(1));
				gameFrame.settings.currentDifficulty.setForeground(Color.yellow);
				changeDifficulty("Normal", 75, 50, 15);
				game.difficultyColor = Color.yellow;
			}else if(difficulty.equals(difficulties.get(3))) {
				gameFrame.settings.currentDifficulty.setText(difficulties.get(2));
				gameFrame.settings.currentDifficulty.setForeground(Color.red);
				gameFrame.settings.difficultyUp.setEnabled(true);
				changeDifficulty("Hard", 50, 100, 20);
				game.difficultyColor = Color.red;
			}
		}else if(source==gameFrame.settings.difficultyUp) {
			if(difficulty.equals(difficulties.get(0))) {
				gameFrame.settings.currentDifficulty.setText(difficulties.get(1));
				gameFrame.settings.currentDifficulty.setForeground(Color.yellow);
				gameFrame.settings.difficultyDown.setEnabled(true);
				changeDifficulty("Normal", 75, 50, 15);
				game.difficultyColor = Color.yellow;
			}else if(difficulty.equals(difficulties.get(1))) {
				gameFrame.settings.currentDifficulty.setText(difficulties.get(2));
				gameFrame.settings.currentDifficulty.setForeground(Color.red);
				changeDifficulty("Hard", 50, 100, 20);
				game.difficultyColor = Color.red;
			}else if(difficulty.equals(difficulties.get(2))) {
				gameFrame.settings.currentDifficulty.setText(difficulties.get(3));
				gameFrame.settings.currentDifficulty.setForeground(gameFrame.settings.difficultyExtreme);
				gameFrame.settings.difficultyUp.setEnabled(false);
				changeDifficulty("Extreme", 25, 250, 25);
				game.difficultyColor = gameFrame.settings.currentDifficulty.getForeground();
			}
		}
		
		if(source==gameFrame.settings.backButton) {
			stopThread = false;
			action = new Thread(this);
			action.start();
			inSettings = false;
			gameFrame.toGame();
		}
		
	}
	
	
}
