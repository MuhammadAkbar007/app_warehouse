package uz.pdp.appwarehouse.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramService {

    SendMessage login(Update update);

    SendMessage shareContact(Update update);

    SendMessage katalogMenu(Update update);
}
