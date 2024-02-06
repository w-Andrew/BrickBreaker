package game.pack;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePlay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false;
	private int score = 0;

	private int totBricks = 21;

	private Timer timer;
	private int delay = 8;

	private int playerX = 310;

	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -3;

	private MapGenerator map;

	public GamePlay() {
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(1, 1, 692, 592);

		map.draw((Graphics2D) g);

		g.setColor(Color.black);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);

		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);

		g.setColor(Color.blue);
		g.fillOval(ballposX, ballposY, 20, 20);

		g.setColor(Color.black);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("" + score, 590, 30);

		if (totBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;

			g.setFont(new Font("serif", Font.BOLD, 40));
			g.drawString("You win, Score: " + score, 190, 300);

			g.drawString("Press Enter to Restart.", 200, 350);
		}

		if (ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;

			g.setFont(new Font("serif", Font.BOLD, 40));
			g.drawString("GameOver, Score: " + score, 190, 300);

			g.drawString("Press Enter to Restart.", 200, 350);
		}

		g.dispose();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();

		if (play) {
			// ball bounces off pedal
			if (new Rectangle(ballposX, ballposY, 20, 30).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir *= -1;
			}

			for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map[0].length; j++) {
					if (map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;

						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;

						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totBricks--;
							score += 5;
							if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir *= -1;
							} else {
								ballYdir *= -1;
							}
						}
					}
				}
			}

			// move ball
			ballposX += ballXdir;
			ballposY += ballYdir;

			// ball bounces off left wall
			if (ballposX < 0) {
				ballXdir *= -(Math.random() + 0.7);
				if (ballXdir < 0.5) {
					ballXdir = 2;
				}
			}
			// ball bounces off ceiling
			if (ballposY < 0) {
				ballYdir *= -(Math.random() + 0.7);
				if (ballYdir < 0.5) {
					ballYdir = 2;
				}
			}
			// ball bounces off right wall
			if (ballposX > 670) {
				ballXdir *= -(Math.random() + 0.7);
				if (ballXdir > -0.5) {
					ballXdir = -2;
				}
			}
		}

		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (play == false) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = 1;
				ballYdir = 3;
				totBricks = 21;
				map = new MapGenerator(3, 7);
				timer = new Timer(delay, this);
				timer.start();

				repaint();
			}
		}
	}

	public void moveRight() {
		play = true;
		playerX += 20;
	}

	public void moveLeft() {
		play = true;
		playerX -= 20;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
