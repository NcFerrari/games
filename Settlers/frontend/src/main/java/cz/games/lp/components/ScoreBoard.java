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
    private ImageNode fractionToken;
    private int round = 1;
    private int score = 0;
    private double scoreXPosition = 0;
    private double scoreYPosition = 0;
    private int xMove = 1;
    private String fraction;

    public ScoreBoard(double width, double height) {
        roundMove = height * 0.19157;
        scoreXMove = 40.0;
        scoreYMove = 50.0;
        ImageNode scoreBoardImage = new ImageNode(width, height, 30 * width / 13, 30 * height / 13);
        scoreBoardImage.setImage("score_board");

        roundPointer = new ImageNode(width * 0.11857, height * 0.15325);
        roundPointer.setImage("round_pointer");
        roundPointer.getImageView().setX(scoreBoardImage.getImageView().getFitWidth() - width * 0.1502);
        roundPointer.getImageView().setY(height * 0.0613);
        getChildren().addAll(scoreBoardImage.getImageView(), roundPointer.getImageView());
    }

    public void nextRound() {
        if (round > 0 && round < 5) {
            round++;
            double newPosition = roundMove * (round - 1);
            roundTransition.setFromY(roundPointer.getImageView().getTranslateY());
            roundTransition.setToY(newPosition);
            roundTransition.setNode(roundPointer.getImageView());
            roundTransition.play();
        }
    }

    public void setFractionToken(String fraction, boolean fiftyPlus) {
        this.fraction = fraction;
        fractionToken = new ImageNode(30, 30);
        String fifty = fiftyPlus ? "50" : "";
        fractionToken.setImage("fractions/fraction_tokens/" + fraction + "_token" + fifty);
        fractionToken.getImageView().setX(17);
        fractionToken.getImageView().setY(22);
        getChildren().add(fractionToken.getImageView());
        scoreTransition.setNode(fractionToken.getImageView());
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
            double newPosition = scoreXMove * scoreXPosition;
            scoreTransition.setToX(newPosition);
        }
        scoreTransition.setFromX(fractionToken.getImageView().getTranslateX());
        scoreTransition.setFromY(fractionToken.getImageView().getTranslateY());
        scoreTransition.setToY(scoreYPosition * scoreYMove);
        scoreTransition.play();
        scoreTransition.setOnFinished(e -> {
            if (score == 50) {
                getChildren().remove(fractionToken.getImageView());
                score = 0;
                scoreXPosition = 0;
                scoreYPosition = 0;
                xMove = 1;
                setFractionToken(fraction, true);
            }
            scorePoint(scorePoint - 1);
        });
    }
}
