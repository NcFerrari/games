package lp.fx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Cross implements Shape {

    @Override
    public void draw(Pane pane, double x, double y, double width, double height) {
        Line line = new Line(x, y, x, y);
        Line line2 = new Line(x + width, y, x + width, y);
        line.setStroke(Color.RED);
        line2.setStroke(line.getStroke());
        line.setStrokeWidth(5);
        line2.setStrokeWidth(line.getStrokeWidth());
        pane.getChildren().add(line2);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(200),
                        new KeyValue(line2.endXProperty(), x),
                        new KeyValue(line2.endYProperty(), y + height)
                )
        );
        timeline.setOnFinished(actionEvent -> pane.getChildren().add(line));
        SequentialTransition transition = new SequentialTransition(
                timeline,
                new Timeline(
                        new KeyFrame(Duration.millis(200),
                                new KeyValue(line.endXProperty(), x + width),
                                new KeyValue(line.endYProperty(), y + height)
                        )
                ));
        transition.play();
    }
}
