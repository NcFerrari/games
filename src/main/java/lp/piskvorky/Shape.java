package lp.piskvorky;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Shape {

    protected String name;
    private Pane pane;
    private double x;
    private double y;
    private double width;
    private double height;

    Shape(String name) {
        this.name = name;
    }

    abstract void draw(Pane pane, double x, double y, double width, double height);

    public String getName() {
        return name;
    }

    public void victoryBackground() {
        Rectangle rectangle = new Rectangle(x, y, width, height);
        rectangle.setFill(Color.YELLOW);
        pane.getChildren().addFirst(rectangle);
    }

    public void setUsedName() {
        name = "used";
        Rectangle rectangle = new Rectangle(x, y, width, height);
        rectangle.setFill(Color.GRAY);
        pane.getChildren().addFirst(rectangle);
    }

    public Shape fillField(Pane pane, double x, double y, int width, int height) {
        this.pane = pane;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        draw(pane, x, y, width, height);
        return this;
    }
}
