package lp.fx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;

public class Circle implements Shape {

    @Override
    public void draw(Pane pane, double x, double y, double width, double height) {
        Arc arc = new Arc(x + width / 2, y + height / 2, width / 2, height / 2, 0, 0);
        arc.setType(ArcType.OPEN);
        arc.setStroke(Color.BLUE);
        arc.setStrokeWidth(5);
        arc.setFill(null);
        pane.getChildren().add(arc);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2), evt -> arc.setLength(arc.getLength() - 1)));
        timeline.setCycleCount(360);
        timeline.play();
    }
}
