package cz.games.lp.files;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import cz.games.lp.dto.CardDTO;

import java.io.File;
import java.util.List;

public class JsonCreator {

    public List<CardDTO> loadCardData() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(getClass().getClassLoader().getResourceAsStream("cards.json"), new TypeReference<>() {
        });
    }

    public void createJsonFile() {
        ObjectMapper mapper = new ObjectMapper();

        CardDTO loadedCard = new CardDTO();
        loadedCard.setId("egy026");
        loadedCard.setCardName("Kamenolom");
        loadedCard.setCardType("PRODUCTION");
        loadedCard.getSourcesForBuild().add("stone");
        loadedCard.getSourcesForBuild().add("location");
        loadedCard.getColors().add("gray");
        loadedCard.setDealSource("stone");
        loadedCard.setCardEffect("stone&&stone");

        CardDTO loadedCard2 = new CardDTO();
        loadedCard2.setId("egy011");
        loadedCard2.setCardName("Karavana");
        loadedCard2.setCardType("PRODUCTION");
        loadedCard2.getSourcesForBuild().add("food");
        loadedCard2.getColors().add("gray");
        loadedCard2.setDealSource("card");
        loadedCard2.setCardEffect("food&&settler");

        File file = new File("person.json");
        mapper.writeValue(file, List.of(loadedCard, loadedCard2));
    }
}
