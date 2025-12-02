package cz.games.lp.backend.mapping;

import cz.games.lp.backend.json_object.CardJSON;
import cz.games.lp.common.dto.CardDTO;
import cz.games.lp.common.enums.CardEffects;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Map;

@Mapper
public interface CardMapper {

    void mapToCardDTO(Map<String, CardJSON> source, @MappingTarget Map<String, CardDTO> target);

    default CardEffects mapStringToCardEffect(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "SAMURAI,SAMURAI" -> CardEffects.TWO_SAMURAIS;
            case "SCORE_POINT,SCORE_POINT,SCORE_POINT,SCORE_POINT,SCORE_POINT,SCORE_POINT,CARD" ->
                    CardEffects.SIX_SCORE_POINTS_WITH_CARD;
            case "SCORE_POINT,SCORE_POINT,SCORE_POINT,SCORE_POINT,SCORE_POINT,SCORE_POINT" ->
                    CardEffects.SIX_SCORE_POINT;
            case "SCORE_POINT,SCORE_POINT,SCORE_POINT,SCORE_POINT" -> CardEffects.FOUR_SCORE_POINTS;
            case "CARD,CARD" -> CardEffects.TWO_CARDS;
            case "SETTLER,SETTLER" -> CardEffects.TWO_SETTLERS;
            default -> {
                for (CardEffects effect : CardEffects.values()) {
                    if (effect.name().equalsIgnoreCase(value)) {
                        yield effect;
                    }
                }
                yield null;
            }
        };
    }
}
