package cz.games.lp.frontend.components;

import cz.games.lp.common.enums.Factions;
import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.Group;

public class FactionBoard extends Group {

    private final ImageNode imageNode;

    public FactionBoard(CommonModel model) {
        imageNode = new ImageNode(
                model.getUIConfig().getCardWidth(),
                3 * model.getUIConfig().getCardHeight(),
                5 * model.getUIConfig().getCardWidth() / 3,
                5 * model.getUIConfig().getCardHeight());
        getChildren().add(imageNode.getImageView());
    }

    public void setImage(Factions faction) {
        imageNode.setImage("factions/faction_boards/" + faction.getBoardImage());
    }
}
