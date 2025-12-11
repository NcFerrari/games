package cz.games.lp.frontend.components;

import cz.games.lp.common.enums.CardEffects;
import cz.games.lp.common.enums.Sources;
import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Dialog;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.HBox;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ChoiceDialog extends Dialog<Void> {

    private final CommonModel model;
    private final HBox contentPane;

    public ChoiceDialog(CommonModel model) {
        this.model = model;
        setTitle(Texts.CHOICE.get());
        setResizable(false);
        contentPane = new HBox();
        getDialogPane().setContent(contentPane);
    }

    public void addItems(List<CardEffects>... listOfLists) {
        setWidth(model.getUIConfig().getCardWidth() * listOfLists.length + model.getUIConfig().getDialogAdditionalWidth());
        setHeight(model.getUIConfig().getCardHeight() + model.getUIConfig().getDialogAdditionalHeight());
        contentPane.getChildren().clear();
        for (List<CardEffects> list : listOfLists) {
            Group group = new Group();
            for (CardEffects effect : list) {
                ImageNode imageNode = new ImageNode(model.getUIConfig().getCardWidth(), model.getUIConfig().getCardHeight());
                if (Objects.requireNonNull(effect.getSource()) == Sources.FACTION_CARD) {
                    String cardPath = model.getGameData().getSelectedFaction().getFactionCardPath();
                    int cardNumber = model.getGameData().getFactionCards().get(contentPane.getChildren().size());
                    String cardId = String.format("%s/%s0%s%d", cardPath, cardPath.substring(0, 3), (cardNumber < 10 ? "0" : ""), cardNumber);
                    imageNode.setImage("cards/" + cardId);
                    group.setOnMouseEntered(e -> {
                        group.setEffect(new Lighting());
                        group.setCursor(Cursor.HAND);
                    });
                    group.setOnMouseExited(e -> {
                        group.setEffect(null);
                        group.setCursor(Cursor.DEFAULT);
                    });
                    group.setOnMousePressed(e -> {
                        model.getActionManager().drawFactionCard(cardNumber);
                        model.getGameData().getFactionCards().remove(Integer.valueOf(cardNumber));
                        Collections.shuffle(model.getGameData().getFactionCards());
                        getDialogPane().getScene().getWindow().hide();
                    });
                } else {
                    imageNode.setImage("source/" + effect.getSource().name().toLowerCase());
                }
                group.getChildren().add(imageNode.getImageView());
            }
            contentPane.getChildren().add(group);
        }
    }
}
