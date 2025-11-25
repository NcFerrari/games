package cz.games.lp.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CardDTO {

    private List<String> sourcesForBuild = new ArrayList<>();
    private List<String> sourcesFromDestroy = new ArrayList<>();
    private List<String> colors = new ArrayList<>();
    private String id;
    private String cardName;
    private String cardType;
    private String dealSource;
    private String cardEffect;
    private String cardEffectForPosition;
}
