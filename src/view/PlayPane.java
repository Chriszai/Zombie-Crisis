package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public class PlayPane extends Pane {
    private Canvas canvas = new Canvas(800,600);
    public PlayPane()  {
        this.getChildren().add(canvas);
    }
}
