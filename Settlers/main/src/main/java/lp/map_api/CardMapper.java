package lp.map_api;

import cz.games.lp.components.Card;
import cz.games.lp.dto.CardDTO;
import cz.games.lp.enums.Colors;
import cz.games.lp.enums.Sources;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface CardMapper {

    void updateCardFromLoaded(CardDTO source, @MappingTarget Card target);

    default Sources mapStringToSources(String value) {
        if (value == null) {
            return null;
        }
        for (Sources source : Sources.values()) {
            if (source.getName().equalsIgnoreCase(value)) {
                return source;
            }
        }
        throw new IllegalArgumentException("Unknown source " + value);
    }

    default Colors mapStringToColors(String value) {
        if (value == null) {
            return null;
        }
        for (Colors color : Colors.values()) {
            if (color.name().equalsIgnoreCase(value)) {
                return color;
            }
        }
        throw new IllegalArgumentException("Unknown source " + value);
    }
}
