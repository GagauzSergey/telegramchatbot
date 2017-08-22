package com.telebot.smartbot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 * @author Sergey Gagauz
 *         Created by user on 06.08.2017.
 */

public class Bot extends TelegramLongPollingBot {
    Config config = new Config();
    TimeHelper timeHelper = new TimeHelper();


    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    public String getBotUsername() {
        return config.getBotName();
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        JsonCurrencyParser jsonCurrencyParser = new JsonCurrencyParser();

        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendNewMessage(message, "Hello, wats up");
                    break;
                case "/time":
                    sendNewMessage(message, timeHelper.getTimeDate());
                    break;
                case "/currency":
                    sendNewMessage(message, jsonCurrencyParser.currencyRequest());
                    break;
                case "/alpha":
                    sendNewMessage(message, jsonCurrencyParser.getInfoFromJson(8));
                    break;
                case "/creditagricol":
                    sendNewMessage(message, jsonCurrencyParser.getInfoFromJson(35));
                    break;
                case "/privatebank":
                    sendNewMessage(message, jsonCurrencyParser.getInfoFromJson(63));
                    break;
                case "/ukrsibbank":
                    sendNewMessage(message, jsonCurrencyParser.getInfoFromJson(73));
                    break;
            }
        }
    }


    public void sendNewMessage(Message message, String messageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(messageText);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
