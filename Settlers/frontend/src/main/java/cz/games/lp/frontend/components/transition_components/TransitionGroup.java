package cz.games.lp.frontend.components.transition_components;

import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.Animation;

public abstract class TransitionGroup {

    protected final CommonModel model;

    protected TransitionGroup(CommonModel model) {
        this.model = model;
    }

    protected abstract void playTransition();

    protected abstract Animation getAnimation();

    public void execute() {
        if (model.isTransitionRunning()) {
            return;
        }
        playTransition();
        model.setTransitionRunning(true);
        getAnimation().setOnFinished(evt -> {
            additionalFinish();
            model.setTransitionRunning(false);
        });
    }

    protected void additionalFinish() {
    }
}
