package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wall extends ImageView{
	public int width;
	public int height;
	public double x;
	public double y;
	public int direction;
	public boolean isdead;
	
	
	public Wall(int locationX, int locationY){
		this.setLayoutX(locationX);
		this.setLayoutY(locationY);
		this.setImage(new Image("view/resources/wall.png", 54, 54, false, true));
		this.setViewOrder(100.0);
	}
}