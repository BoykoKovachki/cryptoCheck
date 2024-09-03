package com.example.cryptocheck.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptocheck.R;

import java.text.DecimalFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_SYMBOL = "com.example.cryptocheck.EXTRA_SYMBOL";
    public static final String EXTRA_PRICE_CHANGE = "com.example.cryptocheck.EXTRA_PRICE_CHANGE";
    public static final String EXTRA_PRICE_CHANGE_PERCENT = "com.example.cryptocheck.EXTRA_PRICE_CHANGE_PERCENT";
    public static final String EXTRA_WEIGHTED_AVG_PRICE = "com.example.cryptocheck.EXTRA_WEIGHTED_AVG_PRICE";
    public static final String EXTRA_PREV_CLOSE_PRICE = "com.example.cryptocheck.EXTRA_PREV_CLOSE_PRICE";
    public static final String EXTRA_LAST_PRICE = "com.example.cryptocheck.EXTRA_LAST_PRICE";
    public static final String EXTRA_LAST_QTY = "com.example.cryptocheck.EXTRA_LAST_QTY";
    public static final String EXTRA_BID_PRICE = "com.example.cryptocheck.EXTRA_BID_PRICE";
    public static final String EXTRA_BID_QTY = "com.example.cryptocheck.EXTRA_BID_QTY";
    public static final String EXTRA_ASK_PRICE = "com.example.cryptocheck.EXTRA_ASK_PRICE";
    public static final String EXTRA_ASK_QTY = "com.example.cryptocheck.EXTRA_ASK_QTY";
    public static final String EXTRA_OPEN_PRICE = "com.example.cryptocheck.EXTRA_OPEN_PRICE";
    public static final String EXTRA_HIGH_PRICE = "com.example.cryptocheck.EXTRA_HIGH_PRICE";
    public static final String EXTRA_LOW_PRICE = "com.example.cryptocheck.EXTRA_LOW_PRICE";
    public static final String EXTRA_VOLUME = "com.example.cryptocheck.EXTRA_VOLUME";
    public static final String EXTRA_QUOTE_VOLUME = "com.example.cryptocheck.EXTRA_QUOTE_VOLUME";
    public static final String EXTRA_OPEN_TIME = "com.example.cryptocheck.EXTRA_OPEN_TIME";
    public static final String EXTRA_CLOSE_TIME = "com.example.cryptocheck.EXTRA_CLOSE_TIME";
    public static final String EXTRA_FIRST_ID = "com.example.cryptocheck.EXTRA_FIRST_ID";
    public static final String EXTRA_LAST_ID = "com.example.cryptocheck.EXTRA_LAST_ID";
    public static final String EXTRA_COUNT = "com.example.cryptocheck.EXTRA_COUNT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView symbolTextview = findViewById(R.id.symbol_textview);
        TextView priceChangeTextView = findViewById(R.id.price_change_textview);
        TextView priceChangePercentTextView = findViewById(R.id.price_change_percent_textview);
        TextView weightedAveragePriceTextView = findViewById(R.id.weighted_average_price_textview);
        TextView previousClosePriceTextView = findViewById(R.id.previous_close_price_textview);
        TextView lastPriceTextView = findViewById(R.id.last_price_textview);
        TextView lastQuantityTextView = findViewById(R.id.last_quantity_textview);
        TextView bidPriceChangeTextView = findViewById(R.id.bid_price_change_textview);
        TextView bidQuantityChangeTextView = findViewById(R.id.bid_quantity_change_textview);
        TextView askPriceChangeTextView = findViewById(R.id.ask_price_change_textview);
        TextView askQuantityChangeTextView = findViewById(R.id.ask_quantity_change_textview);
        TextView openPriceTextView = findViewById(R.id.open_price_textview);
        TextView highPriceTextView = findViewById(R.id.high_price_textview);
        TextView lowPriceTextView = findViewById(R.id.low_price_textview);
        TextView volumeTextView = findViewById(R.id.volume_textview);
        TextView quoteVolumeTextView = findViewById(R.id.quote_volume_textview);
        TextView openTimeTextView = findViewById(R.id.open_time_textview);
        TextView closeTimeVolumeTextView = findViewById(R.id.close_time_volume_textview);
        TextView firstIdTextView = findViewById(R.id.first_id_textview);
        TextView lastIdTextView = findViewById(R.id.last_id_textview);
        TextView countTextView = findViewById(R.id.count_textview);

        Intent intent = getIntent();
        symbolTextview.setText(intent.getStringExtra(EXTRA_SYMBOL));
        priceChangeTextView.setText(intent.getStringExtra(EXTRA_PRICE_CHANGE));
        double priceChangePercent = Double.parseDouble(intent.getStringExtra(EXTRA_PRICE_CHANGE_PERCENT));
        priceChangePercentTextView.setText(String.format("%s%s", new DecimalFormat("##.###").format(priceChangePercent), "%"));
        weightedAveragePriceTextView.setText(intent.getStringExtra(EXTRA_WEIGHTED_AVG_PRICE));
        previousClosePriceTextView.setText(intent.getStringExtra(EXTRA_PREV_CLOSE_PRICE));

        lastPriceTextView.setText(intent.getStringExtra(EXTRA_LAST_PRICE));
        lastQuantityTextView.setText(intent.getStringExtra(EXTRA_LAST_QTY));
        bidPriceChangeTextView.setText(intent.getStringExtra(EXTRA_BID_PRICE));
        bidQuantityChangeTextView.setText(intent.getStringExtra(EXTRA_BID_QTY));
        askPriceChangeTextView.setText(intent.getStringExtra(EXTRA_ASK_PRICE));
        askQuantityChangeTextView.setText(intent.getStringExtra(EXTRA_ASK_QTY));

        openPriceTextView.setText(intent.getStringExtra(EXTRA_OPEN_PRICE));
        highPriceTextView.setText(intent.getStringExtra(EXTRA_HIGH_PRICE));
        lowPriceTextView.setText(intent.getStringExtra(EXTRA_LOW_PRICE));
        volumeTextView.setText(intent.getStringExtra(EXTRA_VOLUME));
        quoteVolumeTextView.setText(intent.getStringExtra(EXTRA_QUOTE_VOLUME));

        Date openTime = new Date(intent.getLongExtra(EXTRA_OPEN_TIME, 0));
        openTimeTextView.setText(openTime.toString());

        Date closeTime = new Date(intent.getLongExtra(EXTRA_OPEN_TIME, 0));
        closeTimeVolumeTextView.setText(closeTime.toString());

        firstIdTextView.setText(String.valueOf(intent.getLongExtra(EXTRA_FIRST_ID, 0)));
        lastIdTextView.setText(String.valueOf(intent.getLongExtra(EXTRA_LAST_ID, 0)));
        countTextView.setText(String.valueOf(intent.getIntExtra(EXTRA_COUNT, 0)));
    }

}
