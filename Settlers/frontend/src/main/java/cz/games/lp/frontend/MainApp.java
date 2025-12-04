package cz.games.lp.frontend;

import cz.games.lp.common.enums.Factions;
import cz.games.lp.frontend.api.IManager;
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
        StackPane stackPane = new StackPane(new UIPane(model), new FrontPane(model));
        Scene scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.setWidth(model.getUIConfig().getWidth());
        stage.setHeight(model.getUIConfig().getHeight());
        stage.setTitle(Texts.TITLE.get());
        stage.setResizable(false);
        stage.show();

        initModel();

        newGame();
    }

    private void initModel() {
        model.setManager(manager);
        model.setGameData(manager.getGameData());
    }

    private void newGame() {
        model.getGameData().setSelectedFaction(Factions.EGYPT_F);
        model.getGameData().newGame();
        model.getFactionBoard().setImage();
        model.getSourcePane().generateNewSources();
        model.getRoundPhases().reset();
        model.getFactionDeck().createCard(model.getGameData().getSelectedFaction().getFactionCardPath());
        model.getCommonDeck().createCard(Texts.COMMON.get());
        model.getTransitionableMap().put(TransitionKeys.FACTION_TOKEN, new FactionToken(model));
    }
}
