package com.telebot.smartbot;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Gagauz Sergey
 *         Created by user on 09.08.2017.
 */
public class JsonCurrencyParser {
    String uri = "http://resources.finance.ua/ru/public/currency-cash.json";
    static InputStream inputStream;
    static JSONObject jsonObject;
    static String json;

    public JsonCurrencyParser() {
    }

    public JSONObject getJsonFromUrl(String uri) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(uri);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                System.out.println(line);
            }
            inputStream.close();
            json = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // parsing to JSON
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("parsing error");
        }
        return jsonObject;
    }

    public String getInfoFromJson() {
        JSONObject object = getJsonFromUrl(uri);
        String tempKeyResponse = object.getString("sourceId");
        return tempKeyResponse;
    }
}
