import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public InlineKeyboardButton back = InlineKeyboardButton.builder()
            .text("Назад")
            .callbackData("назад")
            .build();
    public InlineKeyboardButton moduleOne = InlineKeyboardButton.builder()
            .text("Вводный модуль")
            .callbackData("вводный модуль")
            .build();
    public InlineKeyboardButton downloadIntellijIdea = InlineKeyboardButton.builder()
            .text("Скачать среду разработки")
            .callbackData("скачать среду разработки")
            .url("https://www.jetbrains.com/ru-ru/idea/download/?section=windows")
            .build();

    public InlineKeyboardButton jarFile = InlineKeyboardButton.builder()
            .text("Упаковка jar-файла")
            .callbackData("упаковка jar-файла")
            .url("https://docs.google.com/document/d/1YM1mylggI9d1ZkxenWfHRGxn_NK38FJ-1JrHj2eeR1E/edit#heading=h.9yiytdaelm8b")
            .build();

    public InlineKeyboardButton moduleTwo = InlineKeyboardButton.builder()
            .text("Синтаксис языка, часть 1")
            .callbackData("синтаксис языка, часть 1")
            .build();

    public InlineKeyboardButton moduleThree = InlineKeyboardButton.builder()
            .text("Синтаксис языка, часть 2")
            .callbackData("синтаксис языка, часть 2")
            .build();

    public InlineKeyboardButton moduleFour = InlineKeyboardButton.builder()
            .text("Методы и классы")
            .callbackData("методы и классы")
            .build();

    public InlineKeyboardButton moduleFive = InlineKeyboardButton.builder()
            .text("Инкапсуляция")
            .callbackData("инкапсуляция")
            .build();

    public InlineKeyboardButton moduleSix = InlineKeyboardButton.builder()
            .text("Примитивы")
            .callbackData("примитивы")
            .build();
    private InlineKeyboardMarkup keyboardM1 = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(moduleOne))
            .keyboardRow(List.of(moduleTwo))
            .keyboardRow(List.of(moduleThree))
            .keyboardRow(List.of(moduleFour))
            .keyboardRow(List.of(moduleFive))
            .keyboardRow(List.of(moduleFive))
            .build();

    private InlineKeyboardMarkup sendModuleOne = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(downloadIntellijIdea))
            .keyboardRow(List.of(jarFile))
            .keyboardRow(List.of(back))
            .build();
    @Override
    public String getBotUsername() {
        return "@study_tg_java_bot";
    }

    @Override
    public String getBotToken() {
        return "7154507074:AAGgAs43pzip_1L_UldR_KWYAPp6jFWCKqQ";
    }

    @Override
    public void onUpdateReceived(Update update) {
        buttonTab(update);
        isCommand(update.getMessage());
    }

    public void isCommand(Message message) {
        String text = message.getText();
        if (text.equals("/menu")) {
            sendMenu(message.getFrom().getId(), "<b>Модули</b>", keyboardM1);
        }
    }

    public void buttonTab(Update update) {
        if (update.hasCallbackQuery()) {
            String idUser = update.getCallbackQuery().getMessage().getChatId().toString();
            int idMessage = update.getCallbackQuery().getMessage().getMessageId();
            String data = update.getCallbackQuery().getData();
            String queryId = update.getCallbackQuery().getId();

            EditMessageText editMessageText = EditMessageText.builder()
                    .chatId(idUser)
                    .messageId(idMessage)
                    .text("")
                    .build();

            EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
                    .chatId(idUser.toString())
                    .messageId(idMessage)
                    .build();


            if (data.equals("вводный модуль")) {
                editMessageText.setText("скачать среду разработки");
                editMessageReplyMarkup.setReplyMarkup(sendModuleOne);
            } else if (data.equals("назад")) {
                editMessageText.setText("вводный модуль");
                editMessageReplyMarkup.setReplyMarkup(keyboardM1);
            }

            AnswerCallbackQuery answerCallbackQuery = AnswerCallbackQuery.builder()
                    .callbackQueryId(queryId)
                    .build();

            try {
                execute(answerCallbackQuery);
                execute(editMessageText);
                execute(editMessageReplyMarkup);
            } catch (Exception ex) {
                ex.getMessage();
            }
        }
    }

    public void sendMenu(Long who, String txt, InlineKeyboardMarkup km) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .parseMode("HTML")
                .text(txt)
                .replyMarkup(km)
                .build();

        try {
            execute(sm);
        } catch (TelegramApiException tae) {
            throw new RuntimeException(tae);
        }
    }
}
