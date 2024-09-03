package com.example.cryptocheck.utils;

import android.util.Log;

import com.example.cryptocheck.models.CryptoCurrencyPairModel;
import com.example.cryptocheck.models.ResponseModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {

    public static ResponseModel parserForCryptoCurrencyPairs(byte[] bytes) {
        ResponseModel response = new ResponseModel();

        try {
            JSONArray jsonObject = new JSONArray(new String(bytes));
            ArrayList<CryptoCurrencyPairModel> pairs = new ArrayList<>();

            for (int i = 0; i < jsonObject.length(); i++) {
                JSONObject serverPair = jsonObject.getJSONObject(i);
                CryptoCurrencyPairModel pairModel = new CryptoCurrencyPairModel();
                pairModel.setSymbol(serverPair.getString("symbol"));
                pairModel.setPriceChange(serverPair.getString("priceChange"));
                pairModel.setPriceChangePercent(serverPair.getString("priceChangePercent"));
                pairModel.setWeightedAvgPrice(serverPair.getString("weightedAvgPrice"));
                pairModel.setPrevClosePrice(serverPair.getString("prevClosePrice"));
                pairModel.setLastPrice(serverPair.getString("lastPrice"));
                pairModel.setLastQty(serverPair.getString("lastQty"));
                pairModel.setBidPrice(serverPair.getString("bidPrice"));
                pairModel.setBidQty(serverPair.getString("bidQty"));
                pairModel.setAskPrice(serverPair.getString("askPrice"));
                pairModel.setAskQty(serverPair.getString("askQty"));
                pairModel.setOpenPrice(serverPair.getString("openPrice"));
                pairModel.setHighPrice(serverPair.getString("highPrice"));
                pairModel.setLowPrice(serverPair.getString("lowPrice"));
                pairModel.setVolume(serverPair.getString("volume"));
                pairModel.setQuoteVolume(serverPair.getString("quoteVolume"));
                pairModel.setOpenTime(serverPair.getLong("openTime"));
                pairModel.setCloseTime(serverPair.getLong("closeTime"));
                pairModel.setFirstId(serverPair.getLong("firstId"));
                pairModel.setLastId(serverPair.getLong("lastId"));
                pairModel.setCount(serverPair.getInt("count"));

                pairs.add(pairModel);
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("data", pairs);

            response.setData(data);
            response.setCode(ResponseModel.ResponseCode.CODE200);

        } catch (Exception e) {
            if (e.getMessage() != null) {
                Log.e("ERROR: ", e.getMessage());
            }
            response.setCode(ResponseModel.ResponseCode.CODE1103);
        }

        return response;
    }

}
