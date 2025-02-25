package lp.piskvorky;

import javafx.scene.layout.Pane;

public enum Players {

    CROSS(new Cross()),
    CIRCLE(new Circle());

    private final Shape shape;

    Players(Shape shape) {
        this.shape = shape;
    }

    public Shape fillField(Pane pane, double x, double y, int width, int height) {
        shape.draw(pane, x, y, width, height);
        return shape;
    }
}
