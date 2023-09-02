package io.pro3ct.FlowersBot.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pro3ct.FlowersBot.model.Bouquet;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

public class BytesToBouquetConverter implements Converter<byte[], Bouquet> {
    private final Jackson2JsonRedisSerializer<Bouquet> serializer;

    public BytesToBouquetConverter() {
        serializer = new Jackson2JsonRedisSerializer<>(Bouquet.class);
        serializer.setObjectMapper(new ObjectMapper());
    }

    @Override
    public Bouquet convert(byte[] value) {
        return serializer.deserialize(value);
    }

}
