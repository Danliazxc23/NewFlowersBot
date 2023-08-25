package io.proj3ct.Flowers_OfLifeBot.service;

import io.proj3ct.Flowers_OfLifeBot.config.BotConfig;
import io.proj3ct.Flowers_OfLifeBot.model.Bouquet;
import io.proj3ct.Flowers_OfLifeBot.model.Flower;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@Slf4j
@Data
@Component
public class TelegramBot extends TelegramLongPollingBot {


    final BotConfig botConfig;



    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText=update.getMessage().getText();
            long chartId=update.getMessage().getChatId();

            switch (messageText){
                case "\uD83C\uDFE1Главное меню":
                        startCommandReceived(chartId,update.getMessage().getChat().getFirstName());
                        break;
                case "О нас":
                    InformationText(chartId);
                        break;
                case "Букеты":
                    displayBouquets(chartId,addBouquet(addFlower()));
                    break;
                default: sendMessage(chartId,"Sorry gues, i don't undestnat it");
            }
        }

    }

    private List<Flower> addFlower(){
        List<Flower> flowerList=Arrays.asList(new Flower(150),
                new Flower(150));
        return  flowerList;
    }
    private List<Bouquet> addBouquet(List<Flower> flowerList) {
        List<Bouquet> bouquetList = new ArrayList<>();
        bouquetList.add(new Bouquet( "Букет 1", "C:\\Users\\Alex\\Downloads\\Flowers_OfLifeBot\\Flowers_OfLifeBot\\src\\main\\resources\\static\\images\\bouquets1.jpg"));
        bouquetList.add(new Bouquet( "Букет 2", "C:\\Users\\Alex\\Downloads\\Flowers_OfLifeBot\\Flowers_OfLifeBot\\src\\main\\resources\\static\\images\\bouquets2.jpg"));
        return bouquetList;
    }
    public void sendImageUploadingAFile(String filePath, String chatId) {
        // Create send method
        SendPhoto sendPhotoRequest = new SendPhoto();
        // Set destination chat id
        sendPhotoRequest.setChatId(chatId);
        // Set the photo file as a new photo (You can also use InputStream with a constructor overload)
        sendPhotoRequest.setPhoto(new InputFile(new File(filePath)));
        try {
            // Execute the method
            execute(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void displayBouquets(long chartId,List<Bouquet> bouquetList) {
        StringBuilder information = new StringBuilder();

        for (Bouquet bouquet : bouquetList) {
            String bouquetInfo = "Цена: " + bouquet.getName() + " | Изображение: " + bouquet.getImg() + "\n";
            information.append(bouquetInfo);
        }

        sendMessage(chartId, String.valueOf(information));
    }

    private void InformationText(long chartId){
        String answer="почта";
        sendMessage(chartId,answer);

    }

    private void startCommandReceived(long charId,String name) {//ответ на /start
        String answer="Приветствуем в нашем боте!\n" +
                "\n" +
                "По умолчанию гарантия на товары 4 часа, кроме товаров длительно использования. Могут быть исключения, они будут отдельно указаны в описании к товару. Обязательно их читайте. Там же находятся инструкции и разъяснения. Описание к товарам можно найти нажав кнопку \"✅ Купить\" и выбрав интересующий Вас товар. \n" +
                "Если не поняли читайте статьи ниже или спрашивайте у поддержки.\n" +
                "\n" +
                "\uD83D\uDD17Контакты:\n" +
                "├\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBBПоддержка: https://t.me/Sloycot\n" +
                "├\uD83D\uDD25 Наш канал: https://t.me/ItsFlowersOfLife\n" +
                "├\uD83D\uDCACНаш чат: https://t.me/OfFlowersOf";
        log.info("Ответил пользователю "+ name);


        sendMessage(charId,answer);
        handleMainMenuCommand(charId);

    }
    private void handleMainMenuCommand(Long chatId) {//метод создания 3 категорий
        // Отправить категории в виде кнопок на основе вашего имеющегося кода
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);

        KeyboardButton bouquetsButton = new KeyboardButton("Букеты");
        KeyboardButton aboutButton = new KeyboardButton("О нас");
        KeyboardButton deliveryButton = new KeyboardButton("Доставка");

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(bouquetsButton);

        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add(aboutButton);
        secondRow.add(deliveryButton);

        replyKeyboardMarkup.setKeyboard(Arrays.asList(keyboardRow, secondRow));

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите категорию:");
        message.setReplyMarkup(replyKeyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка" + e.getMessage());
        }
    }


    private void sendMessage(long charId,String textToSend){
        SendMessage message=new SendMessage();
        message.setChatId(String.valueOf(charId));
        message.setText(textToSend);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        KeyboardButton menuButton= new KeyboardButton("\uD83C\uDFE1Главное меню");
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(menuButton);

        replyKeyboardMarkup.setKeyboard(Arrays.asList(keyboardRow));
        message.setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(message);

        } catch (TelegramApiException e) {
            log.error("Произошла ошибка"+ e.getMessage());
        }

    }


    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }
    @Override
    public String getBotToken(){
        return botConfig.getToken();
    }
}
