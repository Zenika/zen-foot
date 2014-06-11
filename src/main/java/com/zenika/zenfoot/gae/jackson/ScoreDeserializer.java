package com.zenika.zenfoot.gae.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Created by raphael on 02/05/14.
 */
public class ScoreDeserializer extends JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String string = jsonParser.getValueAsString();
        if (string.replace(" ", "").equals("")) {
            return -1;
        } else {
            try {
                Integer integerValue = Integer.parseInt(string);
                return integerValue;
            } catch (Exception e) {
                return -1;
            }

        }
    }
}
