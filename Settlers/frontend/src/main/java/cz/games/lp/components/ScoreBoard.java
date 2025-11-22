package cz.games.lp.components;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;

public class ScoreBoard extends Group {

    private final TranslateTransition roundTransition = new TranslateTransition();
    private final TranslateTransition scoreTransition = new TranslateTransition();
    private final ImageNode roundPointer;
    private final double roundMove;
    private final double scoreXMove;
    private final double scoreYMove;
    private ImageNode factionToken;
    private int score;
    private double scoreXPosition;
    private double scoreYPosition;
    private int xMove;
    private String faction;

    public ScoreBoard(double width, double height) {
        roundMove = height * 0.19157;
        scoreXMove = width * 0.07905;
        scoreYMove = height * 0.19157;
        ImageNode scoreBoardImage = new ImageNode(width, height, 30 * width / 13, 30 * height / 13);
        scoreBoardImage.setImage("score_board");

        roundPointer = new ImageNode(width * 0.11857, height * 0.15325);
        roundPointer.setImage("round_pointer");
        roundPointer.getImageView().setX(scoreBoardImage.getImageView().getFitWidth() - width * 0.1502);
        roundPointer.getImageView().setY(height * 0.0613);
        getChildren().addAll(scoreBoardImage.getImageView(), roundPointer.getImageView());
    }

    public void setRound(int round) {
        if (round < 0 || round > 5) {
            return;
        }
        double newPosition = roundMove * (round - 1);
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
        factionToken = new ImageNode(30, 30);
        String fifty = fiftyPlus ? "50" : "";
        factionToken.setImage("factions/faction_tokens/" + faction + "_token" + fifty);
        factionToken.getImageView().setX(17);
        factionToken.getImageView().setY(22);
        getChildren().add(factionToken.getImageView());
        scoreTransition.setNode(factionToken.getImageView());
    }

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
            scoreTransition.setToX(scoreXMove * scoreXPosition);
        }
        scoreTransition.setFromX(factionToken.getImageView().getTranslateX());
        scoreTransition.setFromY(factionToken.getImageView().getTranslateY());
        scoreTransition.setToY(scoreYPosition * scoreYMove);
        scoreTransition.play();
        scoreTransition.setOnFinished(e -> {
            if (score == 50) {
                setFactionToken(faction, true);
            }
            scorePoint(scorePoint - 1);
        });
    }
}
