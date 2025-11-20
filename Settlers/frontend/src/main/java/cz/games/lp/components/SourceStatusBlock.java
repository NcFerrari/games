package cz.games.lp.components;

import cz.games.lp.enums.Sources;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class SourceStatusBlock extends Group {

    private final Label valueLabel;
    private int value = 0;

    public SourceStatusBlock(Sources source, double width, double height) {
        ImageNode imageNode = new ImageNode(width / 2, height);
        imageNode.setImage("source/" + source.getName());
        getChildren().add(imageNode.getImageView());

        valueLabel = new Label("" + value);
        valueLabel.setFont(new Font("StencilStd", 3 * height / 4));
        valueLabel.setStyle("-fx-text-fill: BLACK");
        valueLabel.setPrefWidth(width / 2);
        valueLabel.setPrefHeight(height);
        valueLabel.setLayoutX(imageNode.getImageView().getFitWidth());
        getChildren().add(valueLabel);
    }

    public int getValue() {
        return value;
    }

    public void add(int count) {
        setValue(getValue() + count);
    }

    public void remove(int count) {
        setValue(getValue() - count);
    }

    public void setValue(int value) {
        this.value = value;
        valueLabel.setText("" + value);
    }
}
