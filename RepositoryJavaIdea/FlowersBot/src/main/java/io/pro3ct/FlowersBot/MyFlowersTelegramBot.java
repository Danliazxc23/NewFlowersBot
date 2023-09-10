package io.pro3ct.FlowersBot;

import io.pro3ct.FlowersBot.AppConfig.BotConfig;
import io.pro3ct.FlowersBot.model.Bouquet;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


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




    public List<Bouquet> createBouquet() {
        List<Bouquet> bouquetList = new ArrayList<>();

        // Инициализация первого букета
        Bouquet bouquet1 = new Bouquet();
        bouquet1.setName("Розы");
        bouquet1.setPrice(1000);
        bouquet1.setDescription("Букет из 10 роз разных цветов");
        bouquet1.setImagePath("scr/static/img/flowers1.jpg");

        // Инициализация второго букета
        Bouquet bouquet2 = new Bouquet();
        bouquet2.setName("Тюльпаны");
        bouquet2.setPrice(800);
        bouquet2.setDescription("Букет из 15 тюльпанов");
        bouquet2.setImagePath("scr/static/img/flowers1.jpg");

        // Добавление букетов в список
        bouquetList.add(bouquet1);
        bouquetList.add(bouquet2);
        log.info("создали букеты");
        return bouquetList;
    }
    public void sendBouquetImage(long chatId, String imagePath) throws FileNotFoundException {
        String src="src/main/resources/static/img/flowers1.jpg";
        InputStream imageStream = new FileInputStream(src);
        InputFile inputFile = new InputFile(imageStream, "flowers1.jpg");
        FileInputStream fileInputStream=new FileInputStream("src/main/resources/static/img/flowers1.jpg");
        InputFile file=new InputFile(fileInputStream,"flowers1.jpg");

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(inputFile);

        sendPhoto.setChatId(String.valueOf(chatId));



        List<Bouquet> bouquetList=createBouquet();
        SendMessage message=new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(bouquetList.toString());//нужно переопределить метод toString

        try {
                execute(message);
                log.info("отправляем данные о букетах");
                execute(sendPhoto);
                log.info("отправляем фотки букетов");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } finally {
            try {
                assert imageStream != null;
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
