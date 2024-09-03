package com.example.cryptocheck.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cryptocheck.R;
import com.example.cryptocheck.adapters.CryptoCurrencyPairAdapter;
import com.example.cryptocheck.models.CryptoCurrencyPairModel;
import com.example.cryptocheck.models.ResponseModel;
import com.example.cryptocheck.services.DataManager;
import com.example.cryptocheck.services.LoadingDialogService;
import com.example.cryptocheck.services.ServerCommunication;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs = null;
    private static final String PREFS_NAME = "CryptoCheck";

    private ArrayList<CryptoCurrencyPairModel> cryptoCurrencyPairs;

    private TextView symbolTextView;

    private CryptoCurrencyPairAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        LoadingDialogService.getInstance().createLoadingDialog(this);

        symbolTextView = findViewById(R.id.symbolTextView);
        symbolTextView.setText(R.string.main_activity_symbol);
        symbolTextView.setVisibility(View.INVISIBLE);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        ListView listView = findViewById(R.id.listView);

        adapter = new CryptoCurrencyPairAdapter(getApplicationContext(), new ArrayList<>());

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            getCryptoCurrencyPairs();
        });

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> onItemClickedListener(i));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (prefs.getBoolean("firstrun", true)) {
            prefs.edit().putBoolean("firstrun", false).commit();
            getCryptoCurrencyPairs();
        } else {
            cryptoCurrencyPairs = DataManager.getInstance().getCryptoCurrencyPairs();
            if (!cryptoCurrencyPairs.isEmpty()) {
                adapter.updateItems(cryptoCurrencyPairs);
                symbolTextView.setVisibility(View.VISIBLE);
            } else {
                getCryptoCurrencyPairs();
            }
        }
    }

    private void getCryptoCurrencyPairs() {
        LoadingDialogService.getInstance().show();
        if (!hasInternet()) {
            LoadingDialogService.getInstance().dismiss();
            showErrorAlertDialog(getResources().getString(R.string.server_communication_error_no_internet), (dialogInterface, i) -> {
                cryptoCurrencyPairs = DataManager.getInstance().getCryptoCurrencyPairs();
                adapter.updateItems(cryptoCurrencyPairs);
                adapter.notifyDataSetChanged();
                if (!cryptoCurrencyPairs.isEmpty()) {
                    symbolTextView.setVisibility(View.VISIBLE);
                }
            });

        } else {
            ServerCommunication.getInstance(this).getCryptoCurrencyPair(response -> {
                LoadingDialogService.getInstance().dismiss();

                if (response.getCode().equals(ResponseModel.ResponseCode.CODE200)) {
                    cryptoCurrencyPairs = (ArrayList<CryptoCurrencyPairModel>) response.getData().get("data");
                    DataManager.getInstance().setCryptoCurrencyPairs(cryptoCurrencyPairs);
                    adapter.updateItems(cryptoCurrencyPairs);
                    adapter.notifyDataSetChanged();
                    symbolTextView.setVisibility(View.VISIBLE);

                } else if (response.getCode().equals(ResponseModel.ResponseCode.CODE1102)) {
                    showErrorAlertDialog(getResources().getString(R.string.server_communication_error_no_internet), (dialogInterface, i) -> {
                        cryptoCurrencyPairs = DataManager.getInstance().getCryptoCurrencyPairs();
                        adapter.updateItems(cryptoCurrencyPairs);
                        adapter.notifyDataSetChanged();
                        symbolTextView.setVisibility(View.VISIBLE);
                    });

                } else {
                    showErrorAlertDialog(getResources().getString(R.string.server_communication_error_default));
                    if (!DataManager.getInstance().getCryptoCurrencyPairs().isEmpty()) {
                        cryptoCurrencyPairs = DataManager.getInstance().getCryptoCurrencyPairs();
                        adapter.updateItems(cryptoCurrencyPairs);
                        adapter.notifyDataSetChanged();
                        symbolTextView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    private void onItemClickedListener(int position) {
        CryptoCurrencyPairModel selectedPair = cryptoCurrencyPairs.get(position);

        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_SYMBOL, selectedPair.getSymbol());
        intent.putExtra(DetailsActivity.EXTRA_PRICE_CHANGE, selectedPair.getPriceChange());
        intent.putExtra(DetailsActivity.EXTRA_PRICE_CHANGE_PERCENT, selectedPair.getPriceChangePercent());
        intent.putExtra(DetailsActivity.EXTRA_WEIGHTED_AVG_PRICE, selectedPair.getWeightedAvgPrice());
        intent.putExtra(DetailsActivity.EXTRA_PREV_CLOSE_PRICE, selectedPair.getPrevClosePrice());
        intent.putExtra(DetailsActivity.EXTRA_LAST_PRICE, selectedPair.getLastPrice());
        intent.putExtra(DetailsActivity.EXTRA_LAST_QTY, selectedPair.getLastQty());
        intent.putExtra(DetailsActivity.EXTRA_BID_PRICE, selectedPair.getBidPrice());
        intent.putExtra(DetailsActivity.EXTRA_BID_QTY, selectedPair.getBidQty());
        intent.putExtra(DetailsActivity.EXTRA_ASK_PRICE, selectedPair.getAskPrice());
        intent.putExtra(DetailsActivity.EXTRA_ASK_QTY, selectedPair.getAskQty());
        intent.putExtra(DetailsActivity.EXTRA_OPEN_PRICE, selectedPair.getOpenPrice());
        intent.putExtra(DetailsActivity.EXTRA_HIGH_PRICE, selectedPair.getHighPrice());
        intent.putExtra(DetailsActivity.EXTRA_LOW_PRICE, selectedPair.getLowPrice());
        intent.putExtra(DetailsActivity.EXTRA_VOLUME, selectedPair.getVolume());
        intent.putExtra(DetailsActivity.EXTRA_QUOTE_VOLUME, selectedPair.getQuoteVolume());
        intent.putExtra(DetailsActivity.EXTRA_OPEN_TIME, selectedPair.getOpenTime());
        intent.putExtra(DetailsActivity.EXTRA_CLOSE_TIME, selectedPair.getCloseTime());
        intent.putExtra(DetailsActivity.EXTRA_FIRST_ID, selectedPair.getFirstId());
        intent.putExtra(DetailsActivity.EXTRA_LAST_ID, selectedPair.getLastId());
        intent.putExtra(DetailsActivity.EXTRA_COUNT, selectedPair.getCount());
        this.startActivity(intent);
    }

    protected void showErrorAlertDialog(String message) {
        showErrorAlertDialog(message, null);
    }

    protected void showErrorAlertDialog(String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.warning);
        dialog.setMessage(message);
        dialog.setNeutralButton(R.string.close, listener);
        dialog.setCancelable(false);
        dialog.create();
        dialog.show();
    }

    private boolean hasInternet() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
