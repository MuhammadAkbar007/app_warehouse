package uz.pdp.appwarehouse.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.pdp.appwarehouse.entity.User;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.payload.UserDto;
import uz.pdp.appwarehouse.repository.UserRepository;
import uz.pdp.appwarehouse.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TelegramServiceImpl implements TelegramService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Override
    public SendMessage login(Update update) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setParseMode(ParseMode.MARKDOWN);

        Optional<User> optionalUser = userRepository.findByChatId(update.getMessage().getChatId());
        if (optionalUser.isPresent()) {
            sendMessage.setText(Constant.WELCOME_TEXT);
            User user = optionalUser.get();
            user.setState(BotState.LOGIN);
            userRepository.save(user);
        } else {
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setOneTimeKeyboard(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setSelective(true);

            List<KeyboardRow> keyboardRows = new ArrayList<>();
            KeyboardRow keyboardRow = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton();
            keyboardButton.setRequestContact(true);
            keyboardButton.setText(Constant.SHARE_CONTACT);
            keyboardRow.add(keyboardButton);
            keyboardRows.add(keyboardRow);
            replyKeyboardMarkup.setKeyboard(keyboardRows);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setText(Constant.CONTACT_TEXT);
        }

        return sendMessage;
    }

    @Override
    public SendMessage shareContact(Update update) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setParseMode(ParseMode.MARKDOWN);

        UserDto userDto = new UserDto();
        userDto.setFirstName(update.getMessage().getContact().getFirstName());
        userDto.setLastName(update.getMessage().getContact().getLastName());
        userDto.setPhoneNumber(update.getMessage().getContact().getPhoneNumber());
        userDto.setChatId(update.getMessage().getChatId());
        userDto.setActive(true);
        userDto.setPassword("123");

        Result added = userService.add(userDto);
        Optional<User> optionalUser = userRepository.findByChatId(userDto.getChatId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setState(BotState.SHARE_CONTACT);
            userRepository.save(user);
        }
        sendMessage.setText(added.getMessage());

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        KeyboardButton keyboardButton1 = new KeyboardButton();

        keyboardButton.setText(Constant.MAIN_MENU);
        keyboardButton1.setText(Constant.KATALOG);
        keyboardRow.add(keyboardButton);
        keyboardRow1.add(keyboardButton1);
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }

    @Override
    public SendMessage katalogMenu(Update update) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setParseMode(ParseMode.MARKDOWN);

        Optional<User> optionalUser = userRepository.findByChatId(update.getMessage().getChatId());
        User user = optionalUser.get();
        user.setState(BotState.KATALOG_MENU);
        userRepository.save(user);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();

        KeyboardButton keyboardButton = new KeyboardButton();
        KeyboardButton keyboardButton1 = new KeyboardButton();
        KeyboardButton keyboardButton2 = new KeyboardButton();
        KeyboardButton keyboardButton3 = new KeyboardButton();
        KeyboardButton keyboardButton4 = new KeyboardButton();
        KeyboardButton keyboardButton5 = new KeyboardButton();
        KeyboardButton keyboardButton6 = new KeyboardButton();

        keyboardButton.setText(Constant.ADD_CATEGORY);
        keyboardButton1.setText(Constant.ADD_MEASUREMENT);
        keyboardButton2.setText(Constant.ADD_CURRENCY);
        keyboardButton3.setText(Constant.ADD_SUPPLIER);
        keyboardButton4.setText(Constant.ADD_USER);
        keyboardButton5.setText(Constant.ADD_WAREHOUSE);
        keyboardButton6.setText(Constant.ADD_PRODUCT);

        keyboardRow.add(keyboardButton5);
        keyboardRow.add(keyboardButton);
        keyboardRow.add(keyboardButton1);

        keyboardRow1.add(keyboardButton2);
        keyboardRow1.add(keyboardButton3);
        keyboardRow1.add(keyboardButton4);

        keyboardRow2.add(keyboardButton6);

        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        sendMessage.setText(Constant.KATALOG_MENU_TEXT);

        return sendMessage;
    }
}
