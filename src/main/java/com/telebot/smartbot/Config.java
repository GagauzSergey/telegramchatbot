package com.telebot.smartbot;

/**
 * Created by user on 06.08.2017.
 */
public class Config {
    private String botToken;
    private String botName;

    public String getBotToken() {
        return "";
    }

    public void setBotToken(String botToken) {
        this.botToken = "rockit_bot";
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    @Override
    public String toString() {
        return "Config{" +
                "botToken='" + botToken + '\'' +
                ", botName='" + botName + '\'' +
                '}';
    }
}