package view;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.PlayConfig;
import model.Player;
import model.Zombies;
import model.Bullet5;


public class PlayerView4 extends ImageView {
	public Player player;
	public int width;
	public int height;
	public ImageView hpimg;
	public Pane pane;
	public int scores;
	boolean up, down, left, right;
	public int state;
	boolean shootState = true;
	ArrayList<Bullet5> bullets = new ArrayList<>();
	ArrayList<Zombies> zombies;
	ArrayList<ZombiesView1> zombiesView;
	Direction d = null;
	public double[] offx;
	public double[] offy;

	public enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

	public Direction direction;

	public PlayerView4(Pane pane, ArrayList<Zombies> zombies, ArrayList<ZombiesView1> zombiesView, int scores) {

//		this.zombiesview = zombiesView.clone();
		this.zombiesView = zombiesView;

		this.zombies = zombies;
		this.pane = pane;
		player = new Player(3, 3);
		width = PlayConfig.instance().getWidth() / PlayConfig.instance().getColumn();
		height = PlayConfig.instance().getHeight() / PlayConfig.instance().getRow();
		this.setImage(new Image("view/resources/player.png", width, height, false, true));
		this.setLayoutX(player.getxIndex() * width);
		this.setLayoutY(player.getyIndex() * height);
		this.setViewOrder(100.0);
		direction = Direction.UP;
		this.scores = scores;
		offx = new double[] { 200 - width, 300 - width, 400 - width, 600 - width, 600 - width, 170 - width,
				290 - width };
		offy = new double[] { 200 - height, 300 - height, 400 - height, 300 - height, 100 - height, 470 - height, 0 };
		/*
		 * ImageView baseboard = new ImageView(); baseboard.setImage(new
		 * Image(BOARD_URI)); baseboard.setFitWidth(CommonConst.GAME_WIDTH);
		 * baseboard.setFitHeight(CommonConst.GAME_HEIGHT);
		 * baseboard.setLayoutX(CommonConst.GAME_X);
		 * baseboard.setLayoutY(CommonConst.GAME_Y); baseboard.setViewOrder(100.0);
		 * board.getChildren().add(baseboard);
		 */
	}

	public void handlePressed(KeyEvent keyEvent) {
		if (keyEvent.getCode().equals(KeyCode.S)) {
			SoundManager.playSound("file:src/model/resources/foot.wav");
			down = true;
		}
		if (keyEvent.getCode().equals(KeyCode.W)) {
			SoundManager.playSound("file:src/model/resources/foot.wav");
			up = true;
		}
		if (keyEvent.getCode().equals(KeyCode.A)) {
			SoundManager.playSound("file:src/model/resources/foot.wav");
			left = true;

		}
		if (keyEvent.getCode().equals(KeyCode.D)) {
			SoundManager.playSound("file:src/model/resources/foot.wav");
			right = true;
		}

		if (keyEvent.getCode().equals(KeyCode.SHIFT) && shootState == true) {
			SoundManager.playSound("file:src/model/resources/shot.wav");
			shoot(player);
			shootState = false;
		}

	}

	public void handleReleased(KeyEvent keyEvent) {
		shootState = true;

		switch (keyEvent.getCode()) {
		case A:
			left = false;
			break;
		case D:
			right = false;
			break;
		case W:
			up = false;
			break;
		case S:
			down = false;
			break;
		case SHIFT:
			shootState = true;
		default:
			break;
		}
	}

	public void turnRight() {

	}

	public void shoot(Player who) {
		Bullet5 bullet = new Bullet5(who, width, height, direction);
		pane.getChildren().add(bullet);
		bullets.add(bullet);
	}

	public Player getplayer() {
		return player;
	}

	public void update() {

		if ((player.getyIndex() * height >= offy[0] && player.getyIndex() * height <= 254)
				&& (player.getxIndex() * width >= offx[0] && player.getxIndex() * width <= 254)
				|| (player.getyIndex() * height >= offy[1] && player.getyIndex() * height <= 354)
						&& (player.getxIndex() * width >= offx[1] && player.getxIndex() * width <= 354)
				|| (player.getyIndex() * height >= offy[2] && player.getyIndex() * height <= 454)
						&& (player.getxIndex() * width >= offx[2] && player.getxIndex() * width <= 454)
				|| (player.getyIndex() * height >= offy[3] && player.getyIndex() * height <= 354)
						&& (player.getxIndex() * width >= offx[3] && player.getxIndex() * width <= 654)
				|| (player.getyIndex() * height >= offy[4] && player.getyIndex() * height <= 154)
						&& (player.getxIndex() * width >= offx[4] && player.getxIndex() * width <= 654)
				|| (player.getyIndex() * height >= offy[5] && player.getyIndex() * height <= 524)
						&& (player.getxIndex() * width >= offx[5] && player.getxIndex() * width <= 224)
				|| (player.getyIndex() * height >= -10 && player.getyIndex() * height <= 54)
						&& (player.getxIndex() * width >= offx[6] && player.getxIndex() * width <= 344)) {
//			System.out.println(player.getyIndex() * height);

			if (d == null) {
				d = direction;
			}
			switch (d) {
			case UP:
				up = false;
				break;
			case DOWN:
				down = false;
				break;
			case LEFT:
				left = false;
				break;
			case RIGHT:
				right = false;
				break;
			default:
				break;
			}
		} else {
			d = null;
		}
		
//      ||||||||||||||||||||||||||||||||||||||||||||||||||||||
		if (down) {
			if (direction == Direction.DOWN && PlayScene2.DOWN == false) {
				if (player.getyIndex() < PlayConfig.instance().getRow() - 1) {
					player.setyIndex(player.getyIndex() + 0.05);
					this.setLayoutY(player.getyIndex() * height);
					this.setRotate(180);
				}
			} else {
				this.setRotate(180);
				direction = Direction.DOWN;
			}
		} else if (up) {
			if (direction == Direction.UP && PlayScene2.UP == false) {
				if (player.getyIndex() > 0) {
					player.setyIndex(player.getyIndex() - 0.05);
					this.setLayoutY(player.getyIndex() * height);
				}
			} else {
				this.setRotate(0);
				direction = Direction.UP;
			}
		} else if (left) {
			if (direction == Direction.LEFT && PlayScene2.LEFT == false) {
				if (player.getxIndex() > 0) {
					player.setxIndex(player.getxIndex() - 0.05);
					this.setLayoutX(player.getxIndex() * width);
				}
			} else {
				this.setRotate(270);
				direction = Direction.LEFT;
			}
		} else if (right) {
			if (direction == Direction.RIGHT && PlayScene2.RIGHT == false) {
				if (player.getxIndex() < PlayConfig.instance().getColumn() - 1) {
					player.setxIndex(player.getxIndex() + 0.05);
					this.setLayoutX(player.getxIndex() * width);
				}
			} else {
				direction = Direction.RIGHT;
				this.setRotate(90);
			}
		}

	}

	public void update1() {
		
		for (int i = bullets.size() - 1; i >= 0; i--) {
			if (bullets.get(i).getdirection() == 3) {
				bullets.get(i).moveRight();
			} else if (bullets.get(i).getdirection() == 2) {
				bullets.get(i).moveLeft();
			} else if (bullets.get(i).getdirection() == 0) {
				bullets.get(i).moveUp();
			} else {
				bullets.get(i).moveDown();
			}

			if (bullets.get(i).getx() * width > 800 || bullets.get(i).gety() * height > 600
					|| bullets.get(i).getx() * width < -20 || bullets.get(i).gety() * height < -20) {
				pane.getChildren().remove(bullets.get(i));
				bullets.remove(bullets.get(i));
			}

		}
	}

	public void update2() {
		for (int j = zombies.size() - 1; j >= 0; j--) {

			for (int i = bullets.size() - 1; i >= 0; i--) {
				
				if(zombies.size() <= j) {
					break;
				}
				double zbx = Double.parseDouble(String.format("%.3f", (zombies.get(j).getxIndex())));
				double zby = Double.parseDouble(String.format("%.3f", (zombies.get(j).getyIndex())));
				double blx = Double.parseDouble(String.format("%.3f", (bullets.get(i).getx())));
				double bly = Double.parseDouble(String.format("%.3f", (bullets.get(i).gety())));

				if (Double.parseDouble(String.format("%.3f", (Math.abs(bly - zby)))) < 0.3
						&& Double.parseDouble(String.format("%.3f", (Math.abs(blx - zbx)))) < 0.3) {

					pane.getChildren().remove(bullets.get(i));
					bullets.remove(bullets.get(i));

					SoundManager.playSound("file:src/model/resources/hit.wav");
					zombies.get(j).setHp(zombies.get(j).getHp() - 10);

					if (zombies.get(j).getHp() <= 0) {
						SoundManager.playSound("file:src/model/resources/zombie.wav");
						pane.getChildren().remove(zombiesView.get(j));
						this.scores += zombies.get(j).getID() * 10;
						zombies.remove(zombies.get(j));
						zombiesView.remove(zombiesView.get(j));

					}
				}
			}
		}
	}

	public void update3() {

		for (int i = bullets.size() - 1; i >= 0; i--) {

			if ((bullets.get(i).gety() * height >= 185 && bullets.get(i).gety() * height <= 244)
					&& (bullets.get(i).getx() * width >= 185 && bullets.get(i).getx() * width <= 244)
					|| (bullets.get(i).gety() * height >= 285 && bullets.get(i).gety() * height <= 344)
							&& (bullets.get(i).getx() * width >= 285 && bullets.get(i).getx() * width <= 344)
					|| (bullets.get(i).gety() * height >= 385 && bullets.get(i).gety() * height <= 444)
							&& (bullets.get(i).getx() * width >= 385 && bullets.get(i).getx() * width <= 444)
					|| (bullets.get(i).gety() * height >= 285 && bullets.get(i).gety() * height <= 344)
							&& (bullets.get(i).getx() * width >= 585 && bullets.get(i).getx() * width <= 644)
					|| (bullets.get(i).gety() * height >= 85 && bullets.get(i).gety() * height <= 144)
							&& (bullets.get(i).getx() * width >= 585 && bullets.get(i).getx() * width <= 644)
					|| (bullets.get(i).gety() * height >= 455 && bullets.get(i).gety() * height <= 514)
							&& (bullets.get(i).getx() * width >= 155 && bullets.get(i).getx() * width <= 214)
					|| (bullets.get(i).gety() * height >= -10 && bullets.get(i).gety() * height <= 44)
							&& (bullets.get(i).getx() * width >= 275 && bullets.get(i).getx() * width <= 334)) {
				pane.getChildren().remove(bullets.get(i));
				bullets.remove(bullets.get(i));
			}
		}
	}

	public int getScores() {
		return scores;
	}

}
