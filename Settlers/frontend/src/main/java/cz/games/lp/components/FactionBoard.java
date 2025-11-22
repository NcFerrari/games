package cz.games.lp.components;

import javafx.scene.Group;

public class FactionBoard extends Group {

    private final ImageNode imageNode;

    public FactionBoard(double width, double height) {
        imageNode = new ImageNode(width, height, 5 * width / 3, 5 * height / 3);
        getChildren().add(imageNode.getImageView());
    }

    public void setImage(String factionBoard) {
        imageNode.setImage("factions/faction_boards/" + factionBoard);
    }
}
