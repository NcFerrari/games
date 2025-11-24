package cz.games.lp.components;

import cz.games.lp.enums.Sources;
import cz.games.lp.panes.PaneModel;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import lombok.Getter;

public class SourceStatusBlock extends Group {

    private final PaneModel model;
    private Label valueLabel;
    @Getter
    private int value = 0;

    public SourceStatusBlock(Sources source, PaneModel model) {
        this.model = model;
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
