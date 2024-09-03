package com.example.cryptocheck.services;

import com.example.cryptocheck.models.CryptoCurrencyPairModel;

import java.util.ArrayList;

public class DataManager {

    private static DataManager instance = null;

    private ArrayList<CryptoCurrencyPairModel> cryptoCurrencyPairs;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public DataManager() {
        cryptoCurrencyPairs = new ArrayList<>();
    }

    public ArrayList<CryptoCurrencyPairModel> getCryptoCurrencyPairs() {
        return cryptoCurrencyPairs;
    }

    public void setCryptoCurrencyPairs(ArrayList<CryptoCurrencyPairModel> cryptoCurrencyPairs) {
        this.cryptoCurrencyPairs = cryptoCurrencyPairs;
    }

}
