package cz.games.lp.panes;

import cz.games.lp.components.SourceStatusBlock;
import cz.games.lp.enums.Sources;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;

import java.util.List;

public class SourcePane extends FlowPane {

    public SourcePane(PaneModel model) {
        setAlignment(Pos.CENTER);
        setStyle(model.getHeaderStyle());

        generateNewSources(model);
        addSourcesIntoPane(model);
    }

    private void generateNewSources(PaneModel model) {
        for (Sources source : model.getManager().getSources()) {
            model.getSources().put(source, new SourceStatusBlock(source, model));
        }
    }

    private void addSourcesIntoPane(PaneModel model) {
        List<SourceStatusBlock> sourceStatusBlocks = model.getSources()
                .values()
                .stream()
                .toList();
        getChildren().addAll(sourceStatusBlocks);
    }
}
