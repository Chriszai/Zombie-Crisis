package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.PlayerView1.Direction;

public class Bullet4 extends ImageView{
	public int width;
	public int height;
	public double x;
	public double y;
	public int direction;
	public boolean isdead;
	
	public Bullet4(Player player,int width,int height,Direction d){
		this.setLayoutX(player.getxIndex() * width);
		this.setLayoutY(player.getyIndex() * height);
		this.x = player.getxIndex();
		this.y = player.getyIndex();
		this.width = width;
		this.height = height;
		this.setImage(new Image("view/resources/bullet1.png", 20, 20, false, true));
		this.setViewOrder(100.0);
		if(d == Direction.UP) {
			direction = 0;
		}
		else if (d == Direction.DOWN) {
			direction = 1;
		}
		else if (d == Direction.LEFT) {
			direction = 2;
		}
		else {
			direction = 3;
		}
	}
	
	public void moveLeft() {
		setx(getx() - 0.1);
		this.setLayoutX(getx() * width);
		this.setLayoutY(gety() * height + 10);
	}
	public void moveRight() {
		setx(getx() + 0.1);
		this.setLayoutX(getx() * width);
		this.setLayoutY(gety() * height + 10);
	}
	public void moveUp() {
		sety(gety() - 0.1);
		this.setLayoutY(gety() * height);
		this.setLayoutX(getx() * width + 10);
	}
	public void moveDown() {
		sety(gety() + 0.1);
		this.setLayoutY(gety() * height );
		this.setLayoutX(getx() * width + 10);
	}
	
    public double getx() {
        return x;
    }
    public void setx(double x) {
        this.x = x;
    }
    public double gety() {
        return y;
    }
    public void sety(double y) {
        this.y = y;
    }
    public int getdirection() {
        return direction;
    }
    public boolean isDead(){
    	return isdead;
    };
}
