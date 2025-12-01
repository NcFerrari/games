package cz.games.lp.main.mapping;

import cz.games.lp.backend.json_object.CardJSON;
import cz.games.lp.main.dto.CardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Map;

@Mapper
public interface CardMapper {

    void mapToCardDTO(Map<String, CardJSON> source, @MappingTarget Map<String, CardDTO> target);

//    default Sources mapStringToSources(String value) {
//        if (value == null) {
//            return null;
//        }
//        for (Sources source : Sources.values()) {
//            if (source.getName().equalsIgnoreCase(value)) {
//                return source;
//            }
//        }
//        throw new IllegalArgumentException("Unknown source " + value);
//    }
//
//    default Colors mapStringToColors(String value) {
//        if (value == null) {
//            return null;
//        }
//        for (Colors color : Colors.values()) {
//            if (color.name().equalsIgnoreCase(value)) {
//                return color;
//            }
//        }
//        throw new IllegalArgumentException("Unknown color " + value);
//    }
}
