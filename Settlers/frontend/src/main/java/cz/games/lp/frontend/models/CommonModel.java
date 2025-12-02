package cz.games.lp.frontend.models;

import cz.games.lp.common.enums.Sources;
import cz.games.lp.common.game.GameData;
import cz.games.lp.frontend.components.Supply;
import cz.games.lp.frontend.panes.SourcePane;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class CommonModel {

    private final Map<Sources, Supply> ownSupplies = new LinkedHashMap<>();
    private final UIConfig uIConfig = new UIConfig();
    private Pane frontPane;
    private GameData gameData;
    private SourcePane sourcePane;
}
