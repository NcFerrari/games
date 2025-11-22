package cz.games.lp.components;

import cz.games.lp.panes.PaneModel;
import javafx.scene.Group;

public class FactionBoard extends Group {

    private final ImageNode imageNode;

    public FactionBoard(PaneModel model) {
        imageNode = new ImageNode(model.getCardWidth(), 3 * model.getCardHeight(), 5 * model.getCardWidth() / 3, 5 * model.getCardHeight());
        getChildren().add(imageNode.getImageView());
    }

    public void setImage(String factionBoard) {
        imageNode.setImage("factions/faction_boards/" + factionBoard);
    }
}
