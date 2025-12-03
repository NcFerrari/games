package cz.games.lp.frontend.models;

import javafx.stage.Screen;
import lombok.Getter;

@Getter
public class UIConfig {

    private static final int ANIMATION_SPEED = 400;
    private static final String STYLE = "-fx-background: #078d6f; -fx-background-color: #078d6f";
    private static final String HEADER_STYLE = "-fx-background-color: #6ae7ba";
    private final double width = Screen.getPrimary().getBounds().getWidth() - 100;
    private final double height = Screen.getPrimary().getBounds().getHeight() - 100;
    private final double supplyWidth = width / 18.2;
    private final double supplyHeight = height / 12.25;
    private final double cardWidth = width * 0.078;
    private final double cardHeight = height * 0.204;
    private final double scoreBoardWidth = width * 0.278;
    private final double scoreBoardHeight = height * 0.2663;
    private final double scoreBoardPointerWidth = width * 0.03296246;
    private final double scoreBoardPointerHeight = height * 0.040810475;
    private final double scoreBoardPointerWidthSpace = width * 0.0417556;
    private final double scoreBoardPointerHeightSpace = height * 0.01632419;
    private final double space = width * 0.0204;
    private final double scoreXMove = width * 0.0219759;
    private final double scoreYMove = height * 0.051015091;
    private final double factionTokenWidth = width * 0.016483;
    private final double factionTokenHeight = height * 0.030612;
    private final double factionTokenXMove = width * 0.00934;
    private final double factionTokenYMove = height * 0.022449;
    private final double roundPhasesButtonsWidth = width * 0.0615;
    private final double phaseButtonWidth = width * 0.0615 - 10;
    private final double phaseButtonHeight = height * 0.02239;
    private final double dealSpacing = -cardHeight + height * 0.02612;
    private final double cardInHandsWidth = width * 0.3285;
    private final double bottomPaneHeight = height * 0.612;
    private final double widthForImperialCardStack = width * 0.41365;
    private final double dialogWidth = width * 0.164835;
    private final double dialogHeight = height * 0.306122;

    public int getAnimationSpeed() {
        return ANIMATION_SPEED;
    }

    public String getStyle() {
        return STYLE;
    }

    public String getHeaderStyle() {
        return HEADER_STYLE;
    }
}
