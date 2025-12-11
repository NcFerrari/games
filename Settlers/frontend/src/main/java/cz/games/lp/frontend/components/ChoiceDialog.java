package cz.games.lp.frontend.components;

import cz.games.lp.common.enums.CardEffects;
import cz.games.lp.common.enums.Sources;
import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.models.CommonModel;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Dialog;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ChoiceDialog extends Dialog<List<CardEffects>> {

    private final CommonModel model;
    private final HBox contentPane;

    public ChoiceDialog(CommonModel model) {
        this.model = model;
        setTitle(Texts.CHOICE.get());
        setResizable(false);
        contentPane = new HBox();
        contentPane.setStyle("-fx-background-color: black;");
        getDialogPane().setContent(contentPane);
    }

    @SafeVarargs
    public final void addItems(Card selectedCard, double delay, List<CardEffects>... listOfLists) {
        contentPane.getChildren().clear();
        for (List<CardEffects> list : listOfLists) {
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            for (CardEffects effect : list) {
                ImageNode imageNode;
                if (Objects.requireNonNull(effect.getSource()) == Sources.FACTION_CARD) {
                    setWidth(model.getUIConfig().getCardWidth() * listOfLists.length + model.getUIConfig().getDialogAdditionalWidth());
                    setHeight(model.getUIConfig().getCardHeight() + model.getUIConfig().getDialogAdditionalHeight());
                    String cardPath = model.getGameData().getSelectedFaction().getFactionCardPath();
                    int cardNumber = model.getGameData().getFactionCards().get(contentPane.getChildren().size());
                    String cardId = String.format("%s/%s0%s%d", cardPath, cardPath.substring(0, 3), (cardNumber < 10 ? "0" : ""), cardNumber);
                    imageNode = new ImageNode(model.getUIConfig().getCardWidth(), model.getUIConfig().getCardHeight());
                    imageNode.setImage("cards/" + cardId);
                    vBox.setOnMousePressed(e -> {
                        model.getActionManager().drawFactionCard(cardNumber);
                        model.getGameData().getFactionCards().remove(Integer.valueOf(cardNumber));
                        Collections.shuffle(model.getGameData().getFactionCards());
                        getDialogPane().getScene().getWindow().hide();
                    });
                } else {
                    setWidth(model.getUIConfig().getFactionTokenWidth() * listOfLists.length + model.getUIConfig().getDialogAdditionalWidth());
                    setHeight(model.getUIConfig().getFactionTokenHeight() + model.getUIConfig().getDialogAdditionalHeight() * list.size());
                    imageNode = new ImageNode(model.getUIConfig().getFactionTokenWidth(), model.getUIConfig().getFactionTokenHeight());
                    imageNode.setImage("source/" + effect.getSource().name().toLowerCase());
                    vBox.setOnMousePressed(e -> {
                        model.getActionManager().addSourceWithEffect(list.stream().map(CardEffects::getSource).toList(), CardEffects.SCORE_POINT.equals(list.getFirst()), selectedCard, delay);
                        getDialogPane().getScene().getWindow().hide();
                    });
                }
                vBox.getChildren().add(imageNode.getImageView());
            }
            vBox.setOnMouseEntered(e -> {
                vBox.setEffect(new Lighting());
                vBox.setCursor(Cursor.HAND);
            });
            vBox.setOnMouseExited(e -> {
                vBox.setEffect(null);
                vBox.setCursor(Cursor.DEFAULT);
            });
            contentPane.getChildren().add(vBox);
        }
    }
}
