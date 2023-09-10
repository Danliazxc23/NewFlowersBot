package io.pro3ct.FlowersBot.service;

import io.pro3ct.FlowersBot.model.Bouquet;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CreateNewBouquet {

    public List<Bouquet> createBouquet() throws FileNotFoundException {
        List<Bouquet> bouquetList=new ArrayList<>();
        // Инициализация первого букета
        Bouquet bouquet1 = new Bouquet();
        bouquet1.setName("Розы");
        bouquet1.setPrice(1000);
        bouquet1.setDescription("Букет из 10 роз разных цветов");




        // Инициализация второго букета
        Bouquet bouquet2 = new Bouquet();
        bouquet2.setName("Тюльпаны");
        bouquet2.setPrice(800);
        bouquet2.setDescription("Букет из 15 тюльпанов");

        bouquetList.add(bouquet1);
        bouquetList.add(bouquet2);

        return bouquetList;
    }


}
