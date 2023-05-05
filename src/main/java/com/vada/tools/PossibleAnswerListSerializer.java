package com.vada.tools;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vada.models.PossibleAnswer;

public class PossibleAnswerListSerializer extends JsonSerializer<List<PossibleAnswer>> {
    @Override
    public void serialize(List<PossibleAnswer> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (PossibleAnswer answer : value) {
            gen.writeStartObject();
            gen.writeStringField("text", answer.getAnswerText());
            gen.writeBooleanField("accurate", answer.isAccurate());
            gen.writeEndObject();
        }
        gen.writeEndArray();
    }

}
