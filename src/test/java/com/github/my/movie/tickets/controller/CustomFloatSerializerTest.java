package com.github.my.movie.tickets.controller;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomFloatSerializerTest {
    private CustomFloatSerializer customFloatSerializer = new CustomFloatSerializer();
    @Mock
    private JsonGenerator jsonGenerator;
    @Captor
    private ArgumentCaptor<String> formattedFloatString;
    @Test
    public void testSerialize() throws IOException {
        customFloatSerializer.serialize(2.0f, jsonGenerator, null);
        verify(jsonGenerator).writeNumber(formattedFloatString.capture());
        Assert.assertEquals("2.00", formattedFloatString.getValue());
    }
}
