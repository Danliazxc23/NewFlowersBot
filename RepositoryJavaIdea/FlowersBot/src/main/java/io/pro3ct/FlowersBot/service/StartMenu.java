package io.pro3ct.FlowersBot.service;

import io.pro3ct.FlowersBot.MyFlowersTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileNotFoundException;

@Component
@Slf4j
public class StartMenu {


    private MyFlowersTelegramBot bot;

    public StartMenu(@Lazy MyFlowersTelegramBot bot){
        this.bot=bot;
    }
    public void handleButtonPress(long chatId, String buttonText) throws TelegramApiException, FileNotFoundException {
        switch (buttonText) {
            case "Товар":
                // Логика для обработки нажатия кнопки "Товар"
                log.info("Зашли в товар:");
                bot.sendBouquetImage(chatId,"src/main/resources/static/img/flowers1.jpg");
                break;
            case "О нас":
                // Логика для обработки нажатия кноп

                break;
            case "Поддежка":
                // Логика для обработки нажатия кнопки "Поддержка"

                break;

            default:

                break;
        }
    }

}
