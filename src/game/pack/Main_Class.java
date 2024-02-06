package game.pack;

import javax.swing.JFrame;

public class Main_Class {

	public static void main(String args[]) {

		// initialize JFrame
		JFrame obj = new JFrame();

		// initialize Game
		GamePlay gamePlay = new GamePlay();

		// set JFrame variables
		obj.setBounds(10, 10, 710, 600);
		obj.setTitle("Brick Breaker");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
	}
}
