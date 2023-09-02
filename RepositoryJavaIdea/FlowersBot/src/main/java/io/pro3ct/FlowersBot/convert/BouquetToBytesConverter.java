package io.pro3ct.FlowersBot.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pro3ct.FlowersBot.model.Bouquet;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;


public class BouquetToBytesConverter implements Converter<Bouquet, byte[]> {
    private final Jackson2JsonRedisSerializer<Bouquet> serializer;

    public BouquetToBytesConverter() {
        serializer = new Jackson2JsonRedisSerializer<>(Bouquet.class);
        serializer.setObjectMapper(new ObjectMapper());
    }

    @Override
    public byte[] convert(Bouquet bouquet) {
        return serializer.serialize(bouquet);
    }

}