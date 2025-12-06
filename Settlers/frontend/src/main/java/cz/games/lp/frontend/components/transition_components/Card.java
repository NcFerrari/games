package cz.games.lp.frontend.components.transition_components;

import cz.games.lp.frontend.components.ImageNode;
import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

public class Card extends TransitionGroup {

    private static final int BORDER_WIDTH = 4;
    private final TranslateTransition cardTransition = new TranslateTransition();
    @Getter
    private final Rectangle border;
    @Getter
    private String cardId;
    @Setter
    @Getter
    private boolean samurai;
    private Runnable runnable;

    public Card(CommonModel model) {
        super(model);

        border = new Rectangle(model.getUIConfig().getCardWidth(), model.getUIConfig().getCardHeight() - BORDER_WIDTH * 1.5);
        border.setFill(null);
        border.setStrokeWidth(BORDER_WIDTH);

        getChildren().add(border);
    }

    public Card(String path, String cardId, CommonModel model) {
        this(model);
        this.cardId = cardId;
        ImageNode imageNode = new ImageNode(model.getUIConfig().getCardWidth(), model.getUIConfig().getCardHeight());
        imageNode.setImage("cards/" + path);
        getChildren().addFirst(imageNode.getImageView());
        cardTransition.setNode(this);
    }

    public void select() {
        border.setStroke(Color.RED);
    }

    public void deselect() {
        border.setStroke(null);
    }

    public void setGoalX(double x) {
        cardTransition.setToX(x);
    }

    public void setGoalY(double y) {
        cardTransition.setToY(y);
    }

    @Override
    protected void playTransition() {
        cardTransition.setDuration(Duration.millis(model.getManager().getAnimationSpeed()));
        cardTransition.play();
    }

    @Override
    protected Animation getAnimation() {
        return cardTransition;
    }

    @Override
    protected void additionalFinish() {
        runnable.run();
    }

    public void setOnFinishedAdditional(Runnable runnable) {
        this.runnable = runnable;
    }
}
