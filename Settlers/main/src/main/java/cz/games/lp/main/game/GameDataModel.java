package cz.games.lp.main.game;

import cz.games.lp.main.enums.Faction;
import cz.games.lp.main.enums.Phases;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameDataModel {

    private Faction selectedFaction;
    private int round;
    private Phases currentPhase;

    public void reset() {
        setRound(1);
        currentPhase = Phases.PRODUCTION;
    }
}
