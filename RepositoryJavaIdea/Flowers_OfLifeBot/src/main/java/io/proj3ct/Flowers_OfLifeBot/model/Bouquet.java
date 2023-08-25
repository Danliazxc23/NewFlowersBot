package io.proj3ct.Flowers_OfLifeBot.model;

import lombok.Data;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.List;

@Data
public class Bouquet {
    private final String name;
    private final String img;
    private List<Flower> bouquetsList;

}
