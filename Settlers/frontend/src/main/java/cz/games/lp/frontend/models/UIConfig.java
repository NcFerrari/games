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
    private final double scoreXMove = width * 0.0219759;
    private final double scoreYMove = height * 0.051015091;

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
