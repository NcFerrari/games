package cz.games.lp.components;

import cz.games.lp.panes.PaneModel;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;

public class ScoreBoard extends Group {

    private final PaneModel model;
    private final TranslateTransition roundTransition = new TranslateTransition();
    private final TranslateTransition scoreTransition = new TranslateTransition();
    private final ImageNode roundPointer;
    private ImageNode factionToken;
    private int score;
    private double scoreXPosition;
    private double scoreYPosition;
    private int xMove;
    private String faction;

    public ScoreBoard(PaneModel model) {
        this.model = model;
        ImageNode scoreBoardImage = new ImageNode(
                model.getWidth() * 0.278,
                model.getHeight() * 0.2663,
                30 * (model.getWidth() * 0.278) / 13,
                30 * (model.getHeight() * 0.2663) / 13);
        scoreBoardImage.setImage("score_board");

        roundPointer = new ImageNode(model.getWidth() * 0.03296246, model.getHeight() * 0.040810475);
        roundPointer.setImage("round_pointer");
        roundPointer.getImageView().setX(scoreBoardImage.getImageView().getFitWidth() - model.getWidth() * 0.0417556);
        roundPointer.getImageView().setY(model.getHeight() * 0.01632419);
        getChildren().addAll(scoreBoardImage.getImageView(), roundPointer.getImageView());
    }

    public void setRound(int round) {
        if (round < 0 || round > 5) {
            return;
        }
        double newPosition = model.getScoreYMove() * (round - 1);
        roundTransition.setFromY(roundPointer.getImageView().getTranslateY());
        roundTransition.setToY(newPosition);
        roundTransition.setNode(roundPointer.getImageView());
        roundTransition.play();
    }

    public void setFactionToken(String faction, boolean fiftyPlus) {
        this.faction = faction;
        if (factionToken != null) {
            getChildren().remove(factionToken.getImageView());
        }
        score = 0;
        scoreXPosition = 0;
        scoreYPosition = 0;
        xMove = 1;
        System.out.println(model.getWidth());
        System.out.println(model.getHeight());
        factionToken = new ImageNode(30, 30);
        factionToken.getImageView().setX(17);
        factionToken.getImageView().setY(22);
        String fifty = fiftyPlus ? "50" : "";
        factionToken.setImage("factions/faction_tokens/" + faction + "_token" + fifty);
        getChildren().add(factionToken.getImageView());
        scoreTransition.setNode(factionToken.getImageView());
    }

    /**
     * recursion!
     *
     * @param scorePoint obtained score
     */
    public void scorePoint(int scorePoint) {
        if (scorePoint == 0) {
            return;
        }
        score++;
        if (score % 10 == 0) {
            scoreYPosition++;
            xMove = -xMove;
        } else {
            scoreXPosition += xMove;
            scoreTransition.setToX(model.getScoreXMove() * scoreXPosition);
        }
        scoreTransition.setFromX(factionToken.getImageView().getTranslateX());
        scoreTransition.setFromY(factionToken.getImageView().getTranslateY());
        scoreTransition.setToY(scoreYPosition * model.getScoreYMove());
        scoreTransition.setOnFinished(e -> {
            if (score == 50) {
                setFactionToken(faction, true);
            }
            scorePoint(scorePoint - 1);
        });
        scoreTransition.play();
    }
}
