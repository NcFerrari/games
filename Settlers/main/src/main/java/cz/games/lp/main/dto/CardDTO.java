package cz.games.lp.main.dto;

import cz.games.lp.main.enums.CardEffects;
import cz.games.lp.main.enums.CardTypes;
import cz.games.lp.main.enums.Colors;
import cz.games.lp.main.enums.Conditions;
import cz.games.lp.main.enums.Sources;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CardDTO {

    private List<CardEffects> cardEffect = new ArrayList<>();
    private CardEffects cardEffectForPosition;
    private String cardId;
    private String cardName;
    private CardTypes cardType;
    private List<Colors> colors = new ArrayList<>();
    private Conditions condition;
    private Sources dealSource;
    private List<CardEffects> orEffect = new ArrayList<>();
    private List<Sources> sourcesForBuild = new ArrayList<>();
    private List<Sources> sourcesFromDestroy = new ArrayList<>();
}
