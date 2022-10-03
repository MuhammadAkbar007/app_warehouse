package uz.pdp.appwarehouse.bot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.appwarehouse.repository.UserRepository;

@Component
public class WarehouseBot extends TelegramLongPollingBot {

    @Autowired
    TelegramService telegramService;
    @Autowired
    UserRepository userRepository;

    @Value("1691899054:AAFJ9FvKQi8gTPnaZtIRDe9gfL9DoIGejpc")
    String botToken;
    @Value("WarehouseSpringFirstBot")
    String botUsername;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String text = message.getText();
            if (message.hasText()) {
                if (text.equals("/start")) {
                    execute(telegramService.login(update));
                } else {
                    String state = userRepository.findByChatId(update.getMessage().getChatId()).get().getState();
                    switch (state) {
                        case BotState.SHARE_CONTACT:
                            switch (text) {
                                case Constant.MAIN_MENU:
                                    break;
                                case Constant.KATALOG:
                                    execute(telegramService.katalogMenu(update));
                                    break;
                            }
                            break;
                        case BotState.KATALOG_MENU:
                            break;
                        case BotState.LOGIN:
                            break;
                    }
                }
            } else if (message.hasContact()) {
                execute(telegramService.shareContact(update));
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
