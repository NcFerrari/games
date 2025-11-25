package cz.games.lp.components;

import cz.games.lp.enums.Colors;
import cz.games.lp.enums.Sources;
import cz.games.lp.panes.PaneModel;
import javafx.scene.Group;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Card extends Group {

    private final String cardId;
    private List<Sources> sourcesForBuild = new ArrayList<>();
    private List<Sources> sourcesFromDestroy = new ArrayList<>();
    private List<Colors> colors = new ArrayList<>();
    private String cardName;
    private String cardType;
    private String dealSource;
    private String cardEffect;
    private String cardEffectForPosition;

    public Card(String cardName, PaneModel model) {
        this("", cardName, model.getManager().getCardWidth(), model.getManager().getCardHeight());
    }

    public Card(String path, String cardName, PaneModel model) {
        this(path, cardName, model.getManager().getCardWidth(), model.getManager().getCardHeight());
    }

    public Card(String path, String cardName, double width, double height) {
        cardId = cardName;
        ImageNode imageNode = new ImageNode(width, height);
        imageNode.setImage("cards/" + path + "/" + cardName);
        getChildren().add(imageNode.getImageView());
    }
}
