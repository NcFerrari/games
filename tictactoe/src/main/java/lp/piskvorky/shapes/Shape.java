package lp.piskvorky.shapes;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Shape {

    protected String name;
    private Pane pane;
    private Node[] nodes;
    private Rectangle rectangle;

    Shape(String name) {
        this.name = name;
    }

    public Shape fillField(Pane pane, double x, double y, int width, int height) {
        this.pane = pane;
        rectangle = new Rectangle(x, y, width, height);
        rectangle.setOpacity(0);
        pane.getChildren().addFirst(rectangle);
        nodes = draw(pane, x, y, width, height);
        return this;
    }

    public void removeFromPane() {
        pane.getChildren().removeAll(nodes);
        pane.getChildren().remove(rectangle);
    }

    public String getName() {
        return name;
    }

    public void victoryBackground() {
        fillAndAddBackground(Color.YELLOW);
    }

    public void setUsedName() {
        name = "used";
        fillAndAddBackground(Color.LIGHTGRAY);
    }

    private void fillAndAddBackground(Color color) {
        rectangle.setFill(color);
        rectangle.setOpacity(1);
    }

    abstract Node[] draw(Pane pane, double x, double y, double width, double height);
}
