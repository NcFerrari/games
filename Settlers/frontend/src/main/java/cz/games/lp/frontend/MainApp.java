package cz.games.lp.frontend;

import cz.games.lp.common.enums.Factions;
import cz.games.lp.frontend.api.IManager;
import cz.games.lp.frontend.components.RoundPhases;
import cz.games.lp.frontend.components.transition_components.FactionToken;
import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.enums.TransitionKeys;
import cz.games.lp.frontend.models.CommonModel;
import cz.games.lp.frontend.panes.FrontPane;
import cz.games.lp.frontend.panes.UIPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static IManager manager;

    private final CommonModel model = new CommonModel();

    public static void run(IManager manager) {
        MainApp.manager = manager;
        launch();
    }

    @Override
    public void start(Stage stage) {
        initModel();
        StackPane mainPane = new StackPane(new UIPane(model), new FrontPane(model));
        stage.setScene(new Scene(mainPane));
        stage.setWidth(model.getUIConfig().getWidth());
        stage.setHeight(model.getUIConfig().getHeight());
        stage.setTitle(Texts.TITLE.get());
        stage.setResizable(false);
        stage.show();

        newGame();
    }

    private void initModel() {
        model.setGameData(manager.getGameData());
        model.setRoundPhases(new RoundPhases(model));
    }

    private void newGame() {
        model.getGameData().newGame();
        model.getGameData().setSelectedFaction(Factions.ROMAN_F);
        model.getSourcePane().generateNewSources();
        model.getRoundPhases().reset();
        model.getFactionDeck().createCard(model.getGameData().getSelectedFaction().getFactionCardPath());
        model.getCommonDeck().createCard(Texts.COMMON_CARD.get());
        initTransitionMapInModel();
    }

    private void initTransitionMapInModel() {
        model.getTransitionableMap().put(TransitionKeys.ROUND_POINTER, model.getScoreBoard().createRoundPointer());
        model.getTransitionableMap().put(TransitionKeys.FACTION_TOKEN, new FactionToken(model));
    }
}
