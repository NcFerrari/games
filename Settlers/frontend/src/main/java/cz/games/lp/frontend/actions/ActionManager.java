package cz.games.lp.frontend.actions;

import cz.games.lp.common.enums.Sources;
import cz.games.lp.frontend.components.ImageNode;
import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.enums.CardDeckTypes;
import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ActionManager extends AnimationTimer {

    private final CardMoveActions cardMoveActions;
    private final ProductionActions productionActions;
    private final CommonModel model;
    private final AtomicInteger counter = new AtomicInteger();
    private final AnimationTimer pointAnimation;
    @Getter
    private boolean pointAnimationIsRunning;
    @Setter
    private int scorePointToAdd;
    @Setter
    @Getter
    private boolean animationRunning;
    private Consumer<Long> consumerMethod;

    public ActionManager(CommonModel model) {
        this.model = model;
        cardMoveActions = new CardMoveActions(model);
        productionActions = new ProductionActions(model);
        pointAnimation = new AnimationTimer() {
            @Override
            public void handle(long time) {
                if (scorePointToAdd == 0) {
                    pointAnimationIsRunning = false;
                    stop();
                    return;
                }
                if (!model.isTransitionRunning()) {
                    model.getFactionToken().execute();
                    scorePointToAdd--;
                }
            }
        };
    }

    public void drawFactionCard(Integer cardNumber) {
        cardMoveActions.drawCard(model.getFactionDeck(), model.getGameData().getSelectedFaction().getFactionCardPath(), cardNumber);
    }

    public void drawCommonCard(Integer cardNumber) {
        cardMoveActions.drawCard(model.getCommonDeck(), Texts.COMMON.get(), cardNumber);
    }

    public void prepareFirstFourCards() {
        dealCards(4);
    }

    public void lookoutPhase() {
        dealCards(3);
    }

    private void dealCards(int numberOfCards) {
        counter.set(numberOfCards);
        consumerMethod = time -> {
            switch (counter.getAndDecrement()) {
                case 3, 4 -> CardDeckTypes.FACTION.drawCard(model);
                case 1, 2 -> CardDeckTypes.COMMON.drawCard(model);
                default -> stop();
            }
        };
        start();
    }

    @Override
    public void handle(long time) {
        if (model.isTransitionRunning()) {
            return;
        }
        consumerMethod.accept(time);
    }

    public void productionPhase() {
        if (animationRunning) {
            return;
        }
        setAnimationRunning(true);
        consumerMethod = productionActions.proceedProduction();
        start();
    }

    public void addSourceWithEffect(List<Sources> sources, boolean points, Card selectedCard, double delay) {
        SequentialTransition sequentialTransition = new SequentialTransition();
        sources.forEach(source -> {
            ImageNode imageNode = new ImageNode(model.getUIConfig().getFactionTokenWidth(), model.getUIConfig().getFactionTokenHeight());
            imageNode.setImage("source/" + source.name().toLowerCase());
            imageNode.getImageView().setX(model.getUIConfig().getCardWidth() / 2 - imageNode.getImageView().getFitWidth() / 2);
            imageNode.getImageView().setY(3 * model.getUIConfig().getCardHeight() / 4 - imageNode.getImageView().getFitHeight() / 2);
            imageNode.getImageView().setVisible(false);
            selectedCard.getChildren().add(imageNode.getImageView());

            Transition transition = new Transition() {
                @Override
                protected void interpolate(double v) {
                    imageNode.getImageView().setVisible(true);
                    if (points) {
                        model.getActionManager().setScorePointToAdd(1);
                        pointAnimationIsRunning = true;
                        pointAnimation.start();
                    } else if (Sources.COMMON_CARD.equals(source)) {
                        model.getActionManager().drawCommonCard(model.getGameData().getCommonCards().getFirst());
                        model.getGameData().getCommonCards().removeFirst();
                    } else {
                        model.getOwnSupplies().get(source).addOne();
                    }
                }
            };

            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(delay / sources.size()), imageNode.getImageView());
            translateTransition.setToY(-2 * model.getUIConfig().getFactionTokenHeight());

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(delay / sources.size()), imageNode.getImageView());
            fadeTransition.setToValue(0);

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(delay / sources.size()), imageNode.getImageView());
            scaleTransition.setToX(2);
            scaleTransition.setToY(2);

            ParallelTransition parallelTransition = new ParallelTransition();
            parallelTransition.getChildren().addAll(transition, translateTransition, fadeTransition, scaleTransition);
            parallelTransition.setOnFinished(e -> selectedCard.getChildren().remove(imageNode.getImageView()));

            sequentialTransition.getChildren().add(parallelTransition);
        });
        sequentialTransition.play();
    }
}
