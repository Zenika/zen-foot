package com.zenika.zenfoot.gae.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by raphael on 02/05/14.
 */
public class ScoreSerializer extends JsonSerializer<Integer> {


    @Override
    public void serialize(Integer integer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (integer.equals(new Integer(-1))) {
            jsonGenerator.writeObject("");
        } else {
            jsonGenerator.writeObject(integer.toString());
        }
    }
}
