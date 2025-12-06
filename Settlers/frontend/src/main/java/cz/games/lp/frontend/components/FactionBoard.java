package cz.games.lp.frontend.components;

import cz.games.lp.common.dto.FactionDTO;
import cz.games.lp.common.enums.Factions;
import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.Group;

public class FactionBoard extends Group {

    private final CommonModel model;
    private final ImageNode imageNode;
    private Factions faction;

    public FactionBoard(CommonModel model) {
        this.model = model;
        imageNode = new ImageNode(
                model.getUIConfig().getCardWidth(),
                3 * model.getUIConfig().getCardHeight(),
                5 * model.getUIConfig().getCardWidth() / 3,
                5 * model.getUIConfig().getCardHeight());
        getChildren().add(imageNode.getImageView());
    }

    public void setImage(Factions faction) {
        this.faction = faction;
        imageNode.setImage("factions/faction_boards/" + faction.getBoardImage());
    }

    public FactionDTO getFactionData() {
        return model.getManager().getFaction(faction.name());
    }
}
