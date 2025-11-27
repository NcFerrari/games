package cz.games.lp.components;

import cz.games.lp.enums.Sources;
import cz.games.lp.panes.PaneModel;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;

public class SourceStatusBlock extends Group {

    private final PaneModel model;
    private final Rectangle rectangle;
    private Label valueLabel;
    @Getter
    private int value = 0;

    public SourceStatusBlock(Sources source, PaneModel model) {
        this.model = model;
        rectangle = new Rectangle(0, 0, model.getManager().getWidth() / 18.2, model.getManager().getHeight() / 12.25);
        rectangle.setStrokeWidth(4);
        rectangle.setFill(null);
        rectangle.setStroke(Color.RED);
        ImageNode imageNode = new ImageNode(model.getManager().getWidth() / 18.2, model.getManager().getHeight() / 12.25);
        imageNode.setImage("source/" + source.getName());
        getChildren().add(imageNode.getImageView());

        createLabel(imageNode.getImageView().getFitWidth());
    }

    private void createLabel(double imageWidth) {
        valueLabel = new Label("" + value);
        valueLabel.setFont(new Font("StencilStd", 3 * model.getManager().getHeight() / 49));
        valueLabel.setStyle("-fx-text-fill: BLACK");
        valueLabel.setPrefWidth(model.getManager().getWidth() / 18.2);
        valueLabel.setPrefHeight(model.getManager().getHeight() / 12.25);
        valueLabel.setLayoutX(imageWidth);
        getChildren().add(valueLabel);
    }

    public void addOne() {
        setValue(getValue() + 1);
    }

    public void removeOne() {
        setValue(getValue() - 1);
    }

    public void setValue(int value) {
        this.value = value;
        valueLabel.setText("" + value);
    }

    public CompletableFuture<SourceStatusBlock> waitForSelection() {
        select();
        CompletableFuture<SourceStatusBlock> future = new CompletableFuture<>();
        setOnMouseClicked(event -> {
            future.complete(this);
            setOnMouseClicked(null);
        });
        return future;
    }

    private void select() {
        setEffect(new DropShadow());
        getChildren().add(rectangle);
        setCursor(Cursor.HAND);
    }

    public void deselect() {
        setEffect(null);
        getChildren().remove(rectangle);
        setCursor(Cursor.DEFAULT);
    }
}
