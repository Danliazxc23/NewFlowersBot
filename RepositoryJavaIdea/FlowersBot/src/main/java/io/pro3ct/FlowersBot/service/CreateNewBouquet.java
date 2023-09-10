package io.pro3ct.FlowersBot.service;

import io.pro3ct.FlowersBot.MyFlowersTelegramBot;
import io.pro3ct.FlowersBot.model.Bouquet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
public class CreateNewBouquet {

    private MyFlowersTelegramBot myFlowersTelegramBot;

    public CreateNewBouquet(@Lazy MyFlowersTelegramBot myFlowersTelegramBot) {
        this.myFlowersTelegramBot = myFlowersTelegramBot;
    }

    public void createBouquet(int number,long charId) {
        List<Bouquet> bouquetList = new ArrayList<>();
        int count=1;
        // Инициализация первого букета



        // Добавление букетов в список

        if(count==number){
            Bouquet bouquet1 = new Bouquet();
            bouquet1.setName("Букет №1");
            bouquet1.setPrice(2890);
            bouquet1.setDescription("\uD83D\uDCC3Букет из 11 красных роз премиального сорта в сочетании с зеленью эвкалипта.");

            bouquetList.add(bouquet1);
            String text=bouquet1.toString();
            log.info("создали букет #1");
            myFlowersTelegramBot.sendToMessage(charId,text);
        }
        if(count+1==number){
            Bouquet bouquet2 = new Bouquet();
            bouquet2.setName("Букет №2");
            bouquet2.setPrice(2890);
            bouquet2.setDescription("\uD83D\uDCC3Букет крутой.");

            bouquetList.add(bouquet2);
            log.info("создали букет #2");
            String text=bouquet2.toString();
            myFlowersTelegramBot.sendToMessage(charId,text);
        }

    }


}
