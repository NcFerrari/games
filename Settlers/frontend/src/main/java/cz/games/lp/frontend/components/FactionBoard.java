package cz.games.lp.frontend.components;

import cz.games.lp.frontend.panes.PaneModel;
import javafx.scene.Group;

public class FactionBoard extends Group {

//    private final ImageNode imageNode;

    public FactionBoard(PaneModel model) {
//        imageNode = new ImageNode(
//                model.getManager().getCardWidth(),
//                3 * model.getManager().getCardHeight(),
//                5 * model.getManager().getCardWidth() / 3,
//                5 * model.getManager().getCardHeight());
//        getChildren().add(imageNode.getImageView());
    }

    public void setImage(String factionBoard) {
//        imageNode.setImage("factions/faction_boards/" + factionBoard);
    }
}
