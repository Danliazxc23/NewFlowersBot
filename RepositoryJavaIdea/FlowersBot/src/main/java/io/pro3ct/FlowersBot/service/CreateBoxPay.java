package io.pro3ct.FlowersBot.service;

import io.pro3ct.FlowersBot.MyFlowersTelegramBot;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateBoxPay {
    private MyFlowersTelegramBot bot;

    public CreateBoxPay(@Lazy MyFlowersTelegramBot bot) {
        this.bot = bot;
    }

    public void create(long chatId) throws TelegramApiException {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

// Создаем список рядов
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();


        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Хочу купить!");
        button.setUrl("https://t.me/Sloycot");

// Создаем клавиатуру и добавляем в нее кнопку
        // Создаем список и добавляем в него кнопку
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);

// Добавляем список кнопок в список строк
        rows.add(row);

// Устанавливаем список строк в клавиатуру
        keyboardMarkup.setKeyboard(rows);

// Устанавливаем клавиатуру в сообщение
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Чтобы купить,нажмите кнопку ниже!");
        message.setReplyMarkup(keyboardMarkup);

        bot.execute(message);
    }
}
