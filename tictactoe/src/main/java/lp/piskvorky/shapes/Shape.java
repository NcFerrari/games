package lp.piskvorky.shapes;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Shape {

    protected String name;
    protected final Color shapeColor;
    private Rectangle rectangle;

    Shape(String name, Color shapeColor) {
        this.name = name;
        this.shapeColor = shapeColor;
    }

    public Shape fillField(Pane pane, double x, double y, int width, int height) {
        rectangle = new Rectangle(x, y, width, height);
        rectangle.setOpacity(0);
        pane.getChildren().addFirst(rectangle);
        draw(pane, x, y, width, height);
        return this;
    }

    public String getName() {
        return name;
    }

    public void victoryBackground(String victoryColor) {
        fillAndAddBackground(victoryColor);
    }

    public void setUsedName(String colorForUsedShape) {
        name = "used";
        fillAndAddBackground(colorForUsedShape);
    }

    private void fillAndAddBackground(String color) {
        rectangle.setFill(Color.valueOf(color));
        rectangle.setOpacity(1);
    }

    abstract void draw(Pane pane, double x, double y, double width, double height);
}
