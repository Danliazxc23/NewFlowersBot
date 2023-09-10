package io.pro3ct.FlowersBot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateStartMenu {

    public ReplyKeyboardMarkup createMenu(){

        ReplyKeyboardMarkup markup=new ReplyKeyboardMarkup();
        List<KeyboardRow> list=new ArrayList<>();

        KeyboardRow row=new KeyboardRow();

        row.add("Товар");
        row.add("О нас");
        row.add("Поддержка");
        list.add(row);
        // Устанавливаем клавиатуру для использования в ответе
        markup.setKeyboard(list);
        return markup;

    }
}
