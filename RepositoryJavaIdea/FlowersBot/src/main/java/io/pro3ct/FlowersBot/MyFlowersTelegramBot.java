package io.pro3ct.FlowersBot;

import io.pro3ct.FlowersBot.AppConfig.BotConfig;
import io.pro3ct.FlowersBot.service.CreateStartMenu;
import io.pro3ct.FlowersBot.service.StartMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;


@Component
@Slf4j
public class MyFlowersTelegramBot extends TelegramLongPollingBot {
    @Autowired
    private final BotConfig botConfig;
    @Autowired
    private final CreateStartMenu menu;



    @Autowired
    private final StartMenu startMenu;

    public MyFlowersTelegramBot(BotConfig botConfig, CreateStartMenu menu, StartMenu startMenu) {
        this.botConfig = botConfig;
        this.menu = menu;
        this.startMenu = startMenu;
    }


    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.getMessage().hasText() && update.hasMessage()){
            String messageText=update.getMessage().getText();
            update.getMessage().getChat().getBio();
            long chatId=update.getMessage().getChatId();
            switch (messageText){
                case "/start":
                    startCommandReceived(chatId,update.getMessage().getChat().getFirstName(),update.getMessage().getChat().getLastName());
                    ReplyKeyboardMarkup replyMarkup = menu.createMenu();
                    sendToMessage(chatId, replyMarkup);
                    break;
                default:
                    try {
                        startMenu.handleButtonPress(chatId, messageText);
                    } catch (TelegramApiException | FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    break;

            }

        }
    }


    public void sendBouquetImage(long chatId, String imagePath) throws FileNotFoundException {
        //получаем название фотографии и оправляем ее через внутренний метод execute
        File imageFile = new File(imagePath);
        String imgPhoto = imageFile.getName();

        InputStream imageStream = new FileInputStream(imageFile);
        InputFile inputFile = new InputFile(imageStream, imgPhoto);


        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(inputFile);

        sendPhoto.setChatId(String.valueOf(chatId));

        try {
            execute(sendPhoto);
            log.info("отправляем фотки букетов");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } finally {
            try {
                imageStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void sendToMessage(long chatId, ReplyKeyboardMarkup replyMarkup) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите одну из опций:");
        message.setReplyMarkup(replyMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void startCommandReceived(long charId,String nameFirst,String nameLast) {
        String answer="Приветствуем в нашем боте!\n" +nameFirst+" "+ nameLast+
                "\n" +
                "По умолчанию гарантия на товары 4 часа, кроме товаров длительно использования. Могут быть исключения, они будут отдельно указаны в описании к товару. Обязательно их читайте. Там же находятся инструкции и разъяснения. Описание к товарам можно найти нажав кнопку \"✅ Купить\" и выбрав интересующий Вас товар. \n" +
                "Если не поняли читайте статьи ниже или спрашивайте у поддержки.\n" +
                "\n" +
                "\uD83D\uDD17Контакты:\n" +
                "├\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBBПоддержка: https://t.me/Sloycot\n" +
                "├\uD83D\uDD25 Наш канал: https://t.me/ItsFlowersOfLife\n" +
                "├\uD83D\uDCACНаш чат: https://t.me/OfFlowersOf";
        log.info("Ответил пользователю "+ nameFirst+ " "+ nameLast);

        sendToMessage(charId,answer);

    }
    public void sendToMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите одну из опций:");
        message.setText(text);

        try {
            execute(message);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
