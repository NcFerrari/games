package cz.games.lp.components;

import javafx.scene.Group;

public class Card extends Group {

    public Card(String cardImage, double width, double height) {
        ImageNode imageNode = new ImageNode(width, height);
        imageNode.setImage("cards/" + cardImage);
        getChildren().add(imageNode.getImageView());
    }
}
