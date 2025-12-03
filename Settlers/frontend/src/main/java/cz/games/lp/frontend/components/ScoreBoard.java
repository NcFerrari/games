package cz.games.lp.frontend.components;

import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;

public class ScoreBoard extends Group {

    private final TranslateTransition roundTransition = new TranslateTransition();
    private final TranslateTransition scoreTransition = new TranslateTransition();
    private final CommonModel model;
    private ImageNode scoreBoardImage;
    private ImageNode roundPointer;
    private ImageNode factionToken;
    private int score;
    private double scoreXPosition;
    private double scoreYPosition;
    private int xMove;

    public ScoreBoard(CommonModel model) {
        this.model = model;
        initBoard();
        initRoundPointer();
    }

    private void initBoard() {
        scoreBoardImage = new ImageNode(
                model.getUIConfig().getScoreBoardWidth(),
                model.getUIConfig().getScoreBoardHeight(),
                30 * model.getUIConfig().getScoreBoardWidth() / 13,
                30 * model.getUIConfig().getScoreBoardHeight() / 13);
        scoreBoardImage.setImage("score_board");
        getChildren().add(scoreBoardImage.getImageView());
    }

    private void initRoundPointer() {
        roundPointer = new ImageNode(model.getUIConfig().getScoreBoardPointerWidth(), model.getUIConfig().getScoreBoardPointerHeight());
        roundPointer.setImage("round_pointer");
        roundPointer.getImageView().setX(scoreBoardImage.getImageView().getFitWidth() - model.getUIConfig().getScoreBoardPointerWidthSpace());
        roundPointer.getImageView().setY(model.getUIConfig().getScoreBoardPointerHeightSpace());
        roundTransition.setNode(roundPointer.getImageView());
        getChildren().add(roundPointer.getImageView());
    }


    public void setRound(int round) {
        if (round < 1 || round > 5) {
            return;
        }
        double newPosition = model.getUIConfig().getScoreYMove() * (round - 1);
        roundTransition.setFromY(roundPointer.getImageView().getTranslateY());
        roundTransition.setToY(newPosition);
        roundTransition.play();
    }

    public void createFactionToken() {
        createFactionToken(false);
    }

    private void createFactionToken(boolean fiftyPlus) {
        if (factionToken != null) {
            getChildren().remove(factionToken.getImageView());
        }
        score = 0;
        scoreXPosition = 0;
        scoreYPosition = 0;
        xMove = 1;
        factionToken = new ImageNode(model.getUIConfig().getFactionTokenWidth(), model.getUIConfig().getFactionTokenHeight());
        factionToken.getImageView().setX(model.getUIConfig().getFactionTokenXMove());
        factionToken.getImageView().setY(model.getUIConfig().getFactionTokenYMove());
        String fifty = fiftyPlus ? "50" : "";
        factionToken.setImage("factions/faction_tokens/" + model.getGameData().getSelectedFaction().getTokenImage() + fifty);
        scoreTransition.setNode(factionToken.getImageView());
        getChildren().add(factionToken.getImageView());
    }

    /**
     * recursion!
     *
     * @param scorePoints obtained score
     */
    public void scorePoint(int scorePoints) {
        if (scorePoints == 0) {
            return;
        }
        score++;
        if (score % 10 == 0) {
            scoreYPosition++;
            xMove = -xMove;
        } else {
            scoreXPosition += xMove;
            scoreTransition.setToX(model.getUIConfig().getScoreXMove() * scoreXPosition);
        }
        scoreTransition.setFromX(factionToken.getImageView().getTranslateX());
        scoreTransition.setFromY(factionToken.getImageView().getTranslateY());
        scoreTransition.setToY(scoreYPosition * model.getUIConfig().getScoreYMove());
        scoreTransition.setOnFinished(e -> {
            if (score == 50) {
                createFactionToken(true);
            }
            scorePoint(scorePoints - 1);
        });
        scoreTransition.play();
    }
}
