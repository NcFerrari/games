package cz.games.lp.components;

import cz.games.lp.enums.Colors;
import cz.games.lp.enums.Sources;
import cz.games.lp.panes.PaneModel;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Card extends Group {

    private static final int BORDER_WIDTH = 4;
    private final ImageNode imageNode;
    private final String cardId;
    private final Rectangle border;
    private List<Sources> sourcesForBuild = new ArrayList<>();
    private List<Sources> sourcesFromDestroy = new ArrayList<>();
    private List<Colors> colors = new ArrayList<>();
    private List<String> cardEffect;
    private List<String> orEffect;
    private String condition;
    private String cardName;
    private String cardType;
    private String dealSource;
    private String cardEffectForPosition;
    private boolean samurai;

    public Card(String cardName, PaneModel model) {
        this("", cardName, model.getManager().getCardWidth(), model.getManager().getCardHeight());
    }

    public Card(String path, String cardName, PaneModel model) {
        this(path, cardName, model.getManager().getCardWidth(), model.getManager().getCardHeight());
    }

    public Card(String path, String cardName, double width, double height) {
        cardId = cardName;
        imageNode = new ImageNode(width, height);
        imageNode.setImage("cards/" + path + "/" + cardName);
        getChildren().add(imageNode.getImageView());
        border = new Rectangle(width, height - BORDER_WIDTH * 1.5);
        border.setFill(null);
        border.setStrokeWidth(BORDER_WIDTH);
        getChildren().add(border);
    }

    public void select() {
        border.setStroke(Color.RED);
    }

    public void deselect() {
        border.setStroke(null);
    }
}
