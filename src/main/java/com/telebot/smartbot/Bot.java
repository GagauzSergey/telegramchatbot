package com.telebot.smartbot;

import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.RequestLine;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.HashMap;

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

        //replace with switch-case
        if (message != null && message.hasText()) {
            if (message.getText().equals("/help")) {
                sendNewMessage(message, "Hello, wats up");
            } else if (message.getText().equals("How are you?")) {
                sendNewMessage(message, "I am fine! Thank you!");
            } else if (message.getText().equals("/time")) {
                sendNewMessage(message, timeHelper.getTimeDate());
            } else if (message.getText().equals("/currency")) {
                sendNewMessage(message, jsonCurrencyParser.currencyRequest());
            } else if (message.getText().equals("/alpha")) {
                sendNewMessage(message, jsonCurrencyParser.getInfoFromJson(8));
            } else if (message.getText().equals("/creditagricol")) {
                sendNewMessage(message, jsonCurrencyParser.getInfoFromJson(35));
            } else if (message.getText().equals("/privatebank")) {
                sendNewMessage(message, jsonCurrencyParser.getInfoFromJson(63));
            } else if (message.getText().equals("/ukrsibbank")) {
                sendNewMessage(message, jsonCurrencyParser.getInfoFromJson(73));
            } else sendNewMessage(message, "I'am sinking, wait just a second");
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
