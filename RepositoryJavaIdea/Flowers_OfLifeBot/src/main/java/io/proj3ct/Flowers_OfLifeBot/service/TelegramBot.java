package io.proj3ct.Flowers_OfLifeBot.service;
import io.proj3ct.Flowers_OfLifeBot.service.MainMenuService;
import io.proj3ct.Flowers_OfLifeBot.config.BotConfig;
import io.proj3ct.Flowers_OfLifeBot.model.Bouquet;
import io.proj3ct.Flowers_OfLifeBot.model.Flower;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;


import org.telegram.telegrambots.meta.api.objects.Update;



@Slf4j
@Data
@Component
public class TelegramBot extends TelegramLongPollingBot {


    final BotConfig botConfig;
    MainMenuService mainMenuService=new MainMenuService();
    NewMessage newMessage=new NewMessage();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chartId = update.getMessage().getChatId();
            switch (messageText) {
                case "\uD83C\uDFE1Главное меню":
                    startCommandReceived(chartId,update.getMessage().getText());
                    mainMenuService.handleMainMenuCommand(chartId);
                    break;
                case "\uD83C\uDFE1О нас":
                    InformationText(chartId);
                    break;
                case "\uD83C\uDFE1Букеты":
                    // обработка нажатия кнопки "Букеты"
                    break;
                default:
                    newMessage.sendMessage(chartId, "Sorry, I don't understand");
            }
        }
    }
    private void InformationText(long chartId){
        String answer="почта";
        newMessage.sendMessage(chartId,answer);

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

        newMessage.sendMessage(charId,answer);

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
