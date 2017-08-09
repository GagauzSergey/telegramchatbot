package com.telebot.smartbot;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultHttpClientConnectionOperator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Gagauz Sergey
 *         Created by user on 09.08.2017.
 */
public class JsonCurrencyParser {
    String uri = "http://resources.finance.ua/ru/public/currency-cash.json";
    static InputStream inputStream;
    static JSONObject jsonObject;
    static String json;
    HashMap<String, String> currencyChoice;

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

    public String getInfoFromJson(int i) {

        JSONObject object = getJsonFromUrl(uri);
        JSONArray currencyArray = object.getJSONArray("organizations");

        JSONObject objectUniversalBank = currencyArray.getJSONObject(i);
        String titleUniversalBank = objectUniversalBank.getString("title");
        JSONObject currencyUniversalBankObject = objectUniversalBank.getJSONObject("currencies");

        JSONObject currencyUSDUniversalBank = currencyUniversalBankObject.getJSONObject("USD");
        String usdCurAskUniversalBank = currencyUSDUniversalBank.getString("ask");
        String usdCurBidUniversalBank = currencyUSDUniversalBank.getString("bid");

        JSONObject currencyEUROUniversalBank = currencyUniversalBankObject.getJSONObject("EUR");
        String euroCurAskUniversalBank = currencyEUROUniversalBank.getString("ask");
        String euroCurBidUniversalBank = currencyEUROUniversalBank.getString("bid");

        return formatCurrencyAnswer(titleUniversalBank, usdCurAskUniversalBank, usdCurBidUniversalBank,
                euroCurAskUniversalBank, euroCurBidUniversalBank);
    }

    public String formatCurrencyAnswer(String title, String usdAsk, String usdBid,
                                       String eurAsk, String eurBid) {
        String formattedUSDCurrency = "USD ask: " + usdAsk + " USD bid: " + usdBid + usdBid;
        String formattedEURCurrency = "EUR ask: " + eurAsk + " EUR bid: " + usdBid + eurBid;
        String currencyResponse = title + ":"+"\n" + formattedUSDCurrency +"\n"+ formattedEURCurrency;
        titlesOfBanks();
        return currencyResponse;
    }

    public void titlesOfBanks() {
        JSONObject object = getJsonFromUrl(uri);
        JSONArray currencyArray = object.getJSONArray("organizations");
        for (int i = 0; i < currencyArray.length(); i++) {
            JSONObject objectUniversalBank = currencyArray.getJSONObject(i);
            String titleUniversalBank = objectUniversalBank.getString("title");
//            System.out.println(i+" "+ titleUniversalBank);
        }
    }

    public String currencyRequest() {
        String allBanksTitle = "";
        currencyChoice = new HashMap<String, String>();
        currencyChoice.put("Alpha Bank:", "/alpha");
        currencyChoice.put("Credit Agricol Bank:", "/creditagricol");
        currencyChoice.put("Private Bank:", "/privatebank");
        currencyChoice.put("UkrSib Bank:", "/ukrsibbank");

        Iterator<HashMap.Entry<String, String>> it = currencyChoice.entrySet().iterator();

        while (it.hasNext()) {
            HashMap.Entry<String, String> pair = it.next();
            String responseTitleBanks = pair.getKey() + pair.getValue();
            allBanksTitle += responseTitleBanks + "\n";
        }
        return allBanksTitle;
    }
}
