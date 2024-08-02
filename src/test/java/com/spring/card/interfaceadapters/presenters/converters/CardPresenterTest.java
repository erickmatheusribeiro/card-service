package com.spring.card.interfaceadapters.presenters.converters;

import com.spring.card.TestUtils;
import com.spring.card.cards.Card;
import com.spring.card.interfaceadapters.presenters.dto.CardDto;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class CardPresenterTest extends TestUtils {

    private static final String BASE_PATH = "src/test/java/com/spring/card/interfaceadapters/presenters/converters/cardpresenter";

    @Autowired
    private CardPresenter presenter;

    @BeforeEach
    public void setUp() {
        presenter = new CardPresenter();
    }



    void convertDtoToClass(String fileToConvert, String fileResult) throws IOException, JSONException {
        CardDto dto = objectMapper.readValue(getMock(BASE_PATH + fileToConvert), CardDto.class);

        Card shouldBe = presenter.mapToEntity(dto);

        Card expected = objectMapper.readValue(getMock(BASE_PATH + fileResult), Card.class);

        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(expected),
                objectMapper.writeValueAsString(shouldBe),
                false);
    }

    void convertClassToDto(String fileToConvert, String fileResult) throws IOException, JSONException {
        Card entity = objectMapper.readValue(getMock(BASE_PATH + fileToConvert), Card.class);

        CardDto shouldBe = presenter.mapToDto(entity);

        CardDto expected = objectMapper.readValue(getMock(BASE_PATH + fileResult), CardDto.class);

        JSONAssert.assertEquals(
                objectMapper.writeValueAsString(expected),
                objectMapper.writeValueAsString(shouldBe),
                false);
    }

    @Test
    void convertDtoToClass() throws IOException, JSONException {
        convertDtoToClass("/Dto.json", "/Card.json");
    }

    @Test
    void convertClassToDto() throws IOException, JSONException {
        convertClassToDto("/Card.json", "/Dto.json");
    }
}
