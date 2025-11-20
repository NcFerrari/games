package cz.games.lp.components;

import javafx.scene.Group;

public class FractionBoard extends Group {

    private final ImageNode imageNode;

    public FractionBoard(double width, double height) {
        imageNode = new ImageNode(width, height, 5 * width / 3, 5 * height / 3);
        getChildren().add(imageNode.getImageView());
    }

    public void setImage(String fractionBoard) {
        imageNode.setImage("fractions/fraction_boards/" + fractionBoard);
    }
}
