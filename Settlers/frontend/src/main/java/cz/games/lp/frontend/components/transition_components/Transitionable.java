package cz.games.lp.frontend.components.transition_components;

import cz.games.lp.frontend.models.CommonModel;
import javafx.animation.Animation;

public interface Transitionable {

    void playTransition();

    Animation getAnimation();

    default void execute(CommonModel model) {
        if (model.getCurrentTransition() != null) {
            return;
        }
        playTransition();
        model.setCurrentTransition(this);
        getAnimation().setOnFinished(evt -> {
            additionalFinish();
            model.setCurrentTransition(null);
        });
    }

    default void additionalFinish() {

    }
}
