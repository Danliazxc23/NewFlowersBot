package io.proj3ct.Flowers_OfLifeBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static jdk.javadoc.internal.tool.Main.execute;


@Slf4j
@Component
public class NewMessage {
    public void sendMessage(long charId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(charId));
        message.setText(textToSend);

        execute(String.valueOf(message));
    }
}
