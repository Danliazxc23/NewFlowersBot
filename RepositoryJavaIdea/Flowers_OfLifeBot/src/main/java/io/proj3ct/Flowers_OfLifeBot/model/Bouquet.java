package io.proj3ct.Flowers_OfLifeBot.model;

import lombok.Data;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.List;


public class Bouquet extends Flower{
    private  String name;
    private  String img;

    private int price;
    private List<Flower> bouquetsList;

    public Bouquet(int x, int y) {
        super(x, y);
    }
}
