package com.example.cryptocheck.services;

import android.content.Context;

import com.example.cryptocheck.models.ResponseModel;
import com.example.cryptocheck.utils.Parser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.Method;

import cz.msebera.android.httpclient.Header;

public class ServerCommunication {

    private static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    static String END_POINT = "https://api2.binance.com/api/v3/ticker/24hr";

    private static ServerCommunication instance = null;

    private Context context;

    public interface ServerCommunicationInterface {
        void didFinish(ResponseModel response);
    }

    public static ServerCommunication getInstance(Context c) {
        if (instance == null) {
            instance = new ServerCommunication();
        }
        instance.context = c;
        return instance;
    }

    public void getCryptoCurrencyPair(ServerCommunicationInterface listener) {
        createAndSendRequest(END_POINT, "parserForCryptoCurrencyPairs", listener);
    }

    private void createAndSendRequest(final String path, final String parserMethod, final ServerCommunicationInterface handler) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ResponseModel response = null;
                if (statusCode == 204) {
                    response = new ResponseModel(ResponseModel.ResponseCode.CODE204);
                } else {
                    if (parserMethod != null) {
                        Method method;
                        try {
                            method = Parser.class.getMethod(parserMethod, byte[].class);
                            response = (ResponseModel) method.invoke(null, responseBody);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                if (handler != null) {
                    handler.didFinish(response);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (handler != null) {
                    handler.didFinish(new ResponseModel(ResponseModel.ResponseCode.fromInt(statusCode)));
                }
            }
        };

        asyncHttpClient.get(context, path, null, CONTENT_TYPE, responseHandler);
    }

}
